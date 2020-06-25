(ns duct.repl.shadow-cljs
  (:require
   [integrant.repl.state :refer [config]]
   [shadow.cljs.devtools.api :as api]))


(defn cljs-repl
  ([]
   (cljs-repl (-> config
                  (get-in [:duct.server/shadow-cljs :builds])
                  keys
                  first)))
  ([build-id]
   (api/nrepl-select build-id)))
