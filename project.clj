(defproject g7s/module.shadow-cljs "0.1.2"
  :description "Duct module for compiling ClojureScript with shadow-cljs"
  :url "https://github.com/g7s/module.shadow-cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies
  [[org.clojure/clojure "1.10.1" :scope "provided"]
   [org.clojure/clojurescript "1.10.597" :scope "provided"]
   [thheller/shadow-cljs "2.8.90"]
   [duct/core "0.8.0"]
   [duct/logger "0.3.0"]
   [integrant "0.8.0"]
   [integrant/repl "0.3.1"]])
