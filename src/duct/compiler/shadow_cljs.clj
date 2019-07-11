(ns duct.compiler.shadow-cljs
  "Integrant methods for compiling with shadow-cljs."
  (:require
   [integrant.core :as ig]
   [duct.logger :as logger]
   [shadow.cljs.devtools.config :as config]
   [shadow.cljs.devtools.api :as shadow]))


(defn- release-options-for-build
  [build-conf]
  (select-keys (:compiler-options build-conf) [:pseudo-names :pretty-print :debug :source-maps]))


(defn- release*
  [{:keys [build-id] :as build-conf} {:keys [logger] :as release-opts}]
  (shadow/release* build-conf release-opts)
  (logger/log logger :info ::complete-build {:build-id build-id}))


(defn- release-build
  [build-conf opts]
  (let [ropts (release-options-for-build build-conf)]
    (release* build-conf (merge ropts opts))))


(defmethod ig/init-key :duct.compiler/shadow-cljs
  [_ {:keys [logger] :as conf}]
  (let [conf (config/normalize conf)]
    (doseq [build-conf (-> conf :builds vals)]
      (release-build build-conf {:logger logger}))))
