## Leiningen

> Leiningen: Pronounced lein-ing-en - a Clojure project management tool.

### Leiningen at a high level

Leiningen works with the idea of projects, a project is simply a bunch
of Clojure source files with some meta-data associated with them.
The meta-data is stored inside a file called project.clj at the project root.
These meta-data can be configured to set various things such as:

* project name/description
* project dependencies
* clojure version the app runs on
* where source files are located

### Creating a project

When creating a new project Leiningen lets us pick several from pre-configured
project templates. Creating a base Clojure project is as simple as doing the following:

`$ lein new app your-app-name`

Lets take a look at what this generated:

```
your-app-name
├── .gitignore
├── .hgignore
├── LICENSE
├── README.md
├── doc
│   └── intro.md
├── project.clj
├── resources
├── src
│   └── your_app_name
│       └── core.clj
└── test
    └── your_app_name
        └── core_test.clj
```

Key things that you'll have to interact with are:
* src: this is where you put all your Clojure source files.
* test: all your tests go in here.
* project.clj: project meta-data live here.

For additional fun, try making new leiningen projects based on different
templates and see how they differ from each other. Try looking at some of
these:

```
$ lein new compojure [app-name]
$ lein new compojure-app [app-name]
$ lein new lein-droid [app-name]
$ lein new [library-name]
```

#### project.clj
Lets take a look at project.clj in more detail, I've added
comments to provide a quick description of what each field means.

~~~clojure
(defproject your-app-name "0.1.0-SNAPSHOT"
  ;; Project meta-data that you can set yourself.
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  ;; Libraries the project depends on, this is a key field that
  ;; will likely need to be updated frequently. We'll cover this
  ;; in more detail later.
  :dependencies [[org.clojure/clojure "1.6.0"]]
  ;; This specifies the namespace where our project main lives.
  ;; The '^:skip-aot' means that we want to skip ahead of time
  ;; compilation for this namespace.
  :main ^:skip-aot your-app-name.core
  ;; This is where generated files will go. Thankfully lein was
  ;; nice enough to automatically add this to our .gitignore
  ;; and .hgignore files.
  :target-path "target/%s"
  ;; Lein can automatically build uber-jars for us, this tells us
  ;; we want to use ahead-of-time compilation for all our source
  ;; files when they get packaged.
  :profiles {:uberjar {:aot :all}})
~~~

##### Adding a dependency

Lets say our app needs access to an HTTP client so we can make requests
to a website. A good library for this is clj-http. To add this as a
dependency to our project we can simply update the dependencies section
of our project.clj file to include it.

~~~clojure
:dependencies [[org.clojure/clojure "1.6.0"]
               [clj-http "1.0.0"]]
~~~

Once we have updated our project.clj file, install any new dependencies
with the following:

`$ lein deps`

### Writing the code

We'll see that Leiningen automatically created a source file for us located at:

`src/your_app_name/core.clj`

Lets open this file in an editor. I've again added comments describing the file:

~~~clojure
;; The namespace for this file. You'll notice this is the same namespace
;; specified in our project.clj.
(ns your-app-name.core
  ;; This indicates that we should generate a .class file for this namespace
  ;; when it is compiled.
  (:gen-class))

;; The main function, this is what will be executed when we run the project.
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
~~~

### Running the project

We can run our project with the following:

`$ lein run`

> Note that if we have dependencies that aren't
installed yet running this will attempt to download and install them, just
like when we ran `$ lein deps` earlier.

We should see the main function execute.

#### Using the REPL

Clojure development is often driven by interacting with a REPL.
To start a REPL, run the following from the project root:

`$ lein repl`

This will place us in a shell that is already inside our projects main namespace.
We have full access to all the functions inside that namespace, so we can invoke
the main directly.

```
your-app-name.core=> (-main)
Hello, World!
nil
```

Lets add the definition of PI to our core.clj file:

~~~clojure
(ns your-app-name.core
  (:gen-class))

(def PI 3.14)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
~~~

If we try to use this new value inside our REPL we'll see that it's not defined, our
REPL is out of sync with our source file.

```
your-app-name.core=> PI

CompilerException java.lang.RuntimeException: Unable to resolve symbol: PI in this context, compiling:(/private/var/folders/b9/bxrqrdtn5m14tmw3nb4p3qg5d5gq2z/T/form-init4206019808827210840.clj:1:4865)
```

To access PI inside the REPL we could just
restart the REPL, but that's annoying. What we can do instead is reload the namespace
from the REPL.

```
your-app-name.core=> (use 'your-app-name.core :reload)
nil
```

Now we can use PI.

```
your-app-name.core=> PI
3.14
```
Clojure provides a lot of functions aimed at working inside the REPL.

Lets take a look at doc, doc will display the doc-string for the var passed in. Lets use
doc to tell us how to use doc:

```
your-app-name.core=> (doc doc)
-------------------------
clojure.repl/doc
([name])
Macro
  Prints documentation for a var or special form given its name
nil
```

We can also use the http client library we added to our project.clj file.

```
your-app-name.core=> (require '[clj-http.client :as client])
nil
your-app-name.core=> (client/get "http://www.google.com")
```

You should see an HTTP response.

### Writing/Running tests

Tests are located in the test directory. Leiningen created a sample test for us at:

`test/your_app_name/core_test.clj`

Lets take a look at that file, I've again added comments describing things:

~~~clojure
;; The namespace of this file.
(ns your-app-name.core-test
  ;; We're importing Clojure test functions and our core.clj file.
  (:require [clojure.test :refer :all]
            [your-app-name.core :refer :all]))

;; A new test
(deftest a-test
  (testing "FIXME, I fail."
    ;; Assert is 0 equal to 1?
    (is (= 0 1))))
~~~

We can see that the test defined in that file should fail. Lets verify this by running
all the tests:

`$ lein test`

You should see something similar to the following:

```
$ lein test

lein test your-app-name.core-test

lein test :only your-app-name.core-test/a-test

FAIL in (a-test) (core_test.clj:7)
FIXME, I fail.
expected: (= 0 1)
  actual: (not (= 0 1))

Ran 1 tests containing 1 assertions.
1 failures, 0 errors.
Tests failed.
```
