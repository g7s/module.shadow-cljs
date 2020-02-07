(defproject g7s/module.shadow-cljs "0.1.1"
  :description "Integrant methods for running shadow-cljs"
  :url "https://github.com/g7s/server.shadow"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies
  [[org.clojure/clojure "1.10.1" :scope "provided"]
   [org.clojure/clojurescript "1.10.597" :scope "provided"]
   [com.google.javascript/closure-compiler-unshaded "v20190325"]
   [org.clojure/google-closure-library "0.0-20190213-2033d5d9"]
   [thheller/shadow-cljs "2.8.74"]
   [binaryage/devtools "0.9.10"]
   [cider/piggieback "0.4.0"]
   [duct/core "0.7.0"]
   [duct/logger "0.3.0"]
   [integrant "0.7.0"]
   [integrant/repl "0.3.1"]])
