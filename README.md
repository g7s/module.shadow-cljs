# Duct module.shadow-cljs

A [Duct](https://github.com/duct-framework/duct) module for compiling and dynamically reloading ClojureScript files using [shadow-cljs](https://github.com/thheller/shadow-cljs).


## Installation

To install, add the following to your project `:dependencies`:

    [g7s/module.shadow-cljs "0.1.1"]


## Usage

This library provides the `:duct.module/shadow-cljs` key, and accepts the
same options as [shadow-cljs](https://shadow-cljs.github.io/docs/UsersGuide.html).

```edn
{:duct.module/shadow-cljs
 {:source-paths ["src"]
  :builds {:app {:id :dev
                 :source-paths  ["src"]
                 :build-options {:output-to "target/js/public/main.js"
                                 :output-dir "target/js/public"
                                 :optimizations :none}}}]}}
```


## License

Copyright © 2020 Gerasimos

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
