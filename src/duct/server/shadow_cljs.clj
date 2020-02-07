(ns duct.server.shadow-cljs
  "Integrant methods for shadow-cljs server."
  (:require
   [clojure.core.async :as async :refer [<!]]
   [integrant.core :as ig]
   [duct.logger :as logger]
   [shadow.cljs.devtools.config :as config]
   [shadow.cljs.devtools.server :as server]
   [shadow.cljs.devtools.server.worker :as worker]
   [shadow.cljs.devtools.server.runtime :as runtime]
   [shadow.cljs.devtools.server.supervisor :as super]
   [shadow.cljs.devtools.api :as shadow]))


(defn- watch-options-for-build
  "Return the options for the watcher for a specific build configuration."
  [build-conf]
  (select-keys (get-in build-conf [:devtools :watch-options]) [:autobuild :verbose :sync]))


(defn log-worker-msg
  [logger verbose]
  (let [chan (-> (async/sliding-buffer 1)
                 (async/chan))]
    (async/go
      (loop []
        (when-some [x (<! chan)]
          (case (:type x)
            :build-log
            (when verbose
              (logger/log logger :trace ::trace-build (:event x)))

            :build-configure
            (logger/log logger :info ::configure-build (select-keys x [:build-id]))

            :build-start
            (logger/log logger :info ::start-build (select-keys x [:build-id]))

            :build-failure
            (logger/log logger :error ::build-failed (select-keys x [:build-id :report]))

            :build-complete
            (let [{:keys [build-id info]} x
                  {:keys [sources compiled flush-complete compile-complete compile-start]} info
                  duration (/ (double (- (or flush-complete compile-complete) compile-start)))
                  warnings (reduce #(+ %1 (count (:warnings %2))) 0 sources)]
              (logger/log logger :report ::complete-build {:build-id build-id
                                                           :files    (count sources)
                                                           :compiled (count compiled)
                                                           :warnings warnings
                                                           :duration (format "%.2fs" duration)}))

            :worker-shutdown
            (logger/log logger :debug ::worker-shutdown (select-keys x [:proc-id]))

            :println
            (logger/log logger :info ::worker-msg x)

            (:repl/result :repl/error :repl/out :repl/err)
            (do)

            (logger/log logger :debug ::log x))
          (recur))))
    chan))


(defn watch*
  [{:keys [build-id] :as build-conf} {:keys [autobuild verbose sync logger] :as opts}]
  (let [{:keys [supervisor]} (runtime/get-instance!)]
    (-> (super/start-worker supervisor build-conf opts)
        (worker/watch (log-worker-msg logger verbose) true)
        (cond->
            (not (false? autobuild))
            (worker/start-autobuild)

            (false? autobuild)
            (worker/compile)

            (not (false? sync))
            (worker/sync!)))
    (logger/log logger :info ::worker-started {:build-id build-id})))


(defn- watch-build
  [build-conf {:keys [logger] :as opts}]
  (if (shadow/worker-running? (:build-id build-conf))
    :running
    (let [wopts (watch-options-for-build build-conf)]
      (if logger
        (watch* build-conf (merge wopts opts))
        (shadow/watch* build-conf wopts))
      :watching)))


(defmethod ig/init-key :duct.server/shadow-cljs
  [_ {:keys [logger] :as conf}]
  (let [conf (config/normalize conf)]
    (server/start! conf)
    (doseq [build-conf (-> conf :builds vals)]
      (watch-build build-conf {:logger logger}))
    conf))


(defmethod ig/halt-key! :duct.server/shadow-cljs
  [_ _]
  (server/stop!))


(defmethod ig/suspend-key! :duct.server/shadow-cljs
  [_ _])


(defmethod ig/resume-key :duct.server/shadow-cljs
  [k new-shadow-conf old-shadow-conf old-impl]
  (when-not (= (dissoc old-shadow-conf :logger :duct.core/requires)
               (dissoc new-shadow-conf :logger :duct.core/requires))
    (ig/halt-key! k old-impl)
    (ig/init-key k new-shadow-conf)))
