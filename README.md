# Duct server.shadow

Integrant methods for compiling and dynamically reloading ClojureScript files in the Duct framework using shadow-cljs.


## Installation

To install, add the following to your project `:dependencies`:

    [g7s/server.shadow "0.1.0"]


## Usage

This library provides the `:duct.server/shadow` key, and accepts the
same options as shadow-cljs.

```edn
{:duct.server/shadow
 {:source-paths ["src"]
  :builds {:app {:id :dev
                 :source-paths  ["src"]
                 :build-options {:output-to "target/js/public/main.js"
                                 :output-dir "target/js/public"
                                 :optimizations :none}}}]}}
```


## License

Copyright © 2019 g7s

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
