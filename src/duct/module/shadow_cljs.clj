(ns duct.module.shadow-cljs
  "Integrant methods for running shadow-cljs."
  (:require
   [integrant.core :as ig]
   [duct.core :as core]
   [duct.core.merge :as merge]))


(defn- env-duct-key
  [env]
  (case env
    :production :duct.compiler/shadow-cljs
    :duct.server/shadow-cljs))


(def ^:private dev-config
  {:dependencies ^:distinct []
   :nrepl        (merge/displace false)
   :source-paths ^:displace ["src"]
   :cache-root   (merge/displace ".shadow-cljs")})


(def ^:private prod-config
  {:dependencies ^:distinct []
   :source-paths ^:displace ["src"]
   :cache-root   (merge/displace ".shadow-cljs")})


(def ^:private env-configs
  {:production  prod-config
   :development dev-config})


(defmethod ig/init-key :duct.module/shadow-cljs
  [_ shadow-conf]
  (fn [duct-conf]
    (let [env  (::core/environment duct-conf :production)
          dkey (env-duct-key env)]
      (core/merge-configs duct-conf
                          {dkey shadow-conf}
                          {dkey (get env-configs env)}))))
