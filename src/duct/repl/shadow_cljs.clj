(ns duct.repl.shadow-cljs
  (:require
   [integrant.repl.state :refer [config]]
   [shadow.cljs.devtools.api :as api]))


(defn cljs-repl
  ([]
   (cljs-repl (-> config
                  (get-in [:duct.module.shadow-cljs/server :builds])
                  keys
                  first)))
  ([build-id]
   (api/nrepl-select build-id)))
