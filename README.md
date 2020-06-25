# Duct module.shadow-cljs

[![Clojars Project](https://img.shields.io/clojars/v/g7s/module.shadow-cljs.svg)](https://clojars.org/g7s/module.shadow-cljs)

A [Duct](https://github.com/duct-framework/duct) module for compiling and dynamically reloading ClojureScript files using [shadow-cljs](https://github.com/thheller/shadow-cljs).


## Installation

To install, add the following to your project `:dependencies`:

    [g7s/module.shadow-cljs "0.1.2"]


## Usage

This library provides the `:duct.module/shadow-cljs` key, and accepts the
same options as [shadow-cljs](https://shadow-cljs.github.io/docs/UsersGuide.html)
as well as an (optional) `:logger` top-level key that is a `:duct/logger` that will
be used to log information about your builds in development.

Additionally it defines two other keys namely `:duct.server/shadow-cljs` and `:duct.compiler/shadow-cljs`.

### Server

The server key `:duct.server/shadow-cljs` is used to start a shadow-cljs server for use in the development phase of your project.
If you want to make changes in the main `:duct.module/shadow-cljs` you should use this key in your development profile configuration.

### Compiler

The compiler key inherits from `:duct/compiler` and it is used only when compiling your code for a release.
If you want to make changes in the main `:duct.module/shadow-cljs` you should use this key in your production profile configuration.

### Example

Example module configuration:

```edn
:duct.module/shadow-cljs
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

    :pages {:entries    [my.app.pages]
            :depends-on #{:common}}}}}}
```

Override in development profile:

```edn
:duct.server/shadow-cljs
{:logger           #ig/ref :duct/logger
 :compiler-options {:output-feature-set :es5}
 :builds
 {:app
  {:closure-defines {goog.DEBUG true}
   :devtools        {:preloads [devtools.preload]}}}}
```

## License

Copyright © 2020 Gerasimos

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
