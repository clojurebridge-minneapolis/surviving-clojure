# Clojure is Dynamic

> "First and foremost, Clojure is dynamic. That means that a Clojure program is not just something you compile and run, but something with which you can interact. Clojure is not a language abstraction, but an environment, where almost all of the language constructs are reified, and thus can be examined and changed. This leads to a substantially different experience from running a program, examining its results (or failures) and trying again. In particular, you can grow your program, with data loaded, adding features, fixing bugs, testing, in an unbroken stream."

[Source: clojure.org](http://clojure.org/dynamic)

* Should we move the section about the REPL here?
* Talk about bottom-up development
* How Lisp programs distinguish between language and application
* Conclude on an overview of how to extend Clojure which is the plan of the rest of the document: functions, Java interop, polymorphism, macros