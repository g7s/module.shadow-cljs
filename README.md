# Duct module.shadow-cljs

[![Clojars Project](https://img.shields.io/clojars/v/g7s/module.shadow-cljs.svg)](https://clojars.org/g7s/module.shadow-cljs)

A [Duct](https://github.com/duct-framework/duct) module for compiling and dynamically reloading ClojureScript files using [shadow-cljs](https://github.com/thheller/shadow-cljs).


## Installation

To install, add the following to your project `:dependencies`:

    [g7s/module.shadow-cljs "0.1.2"]


## Usage

This library provides the `:duct.module/shadow-cljs` key, and accepts the
same options as [shadow-cljs](https://shadow-cljs.github.io/docs/UsersGuide.html).

Example:

```edn
{:duct.module/shadow-cljs
 {:builds
  {:app
   {:target     :browser
    :output-dir "target/resources/my/app/web/public/js"
    :asset-path "/js"
    :devtools   {:watch-dir      "resources/my/app/web/public"
                 :watch-options  {:verbose   true
                                  :autobuild true}
                 :browser-inject :common}
    :modules
    {:base {:entries [cljs.core]}

     :common {:entries    [my.app.utils]
              :depends-on #{:base}}

     :mod {:entries    [my.app.mod]
           :depends-on #{:common}}

     :mod-worker {:entries    [my.app.mod.worker]
                  :depends-on #{:base}
                  :web-worker true}

     :pages {:entries    [my.app.pages.personal]
             :depends-on #{:common}}}}}}}
```

## License

Copyright © 2020 Gerasimos

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
