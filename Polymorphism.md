# Polymorphism

## Multimethods

Generic form of polymorphism.

```Clojure

(defmulti encounter (fn [x y] [(:Species x) (:Species y)]))

(defmethod encounter [:Bunny :Lion] [b l] :run-away)
(defmethod encounter [:Lion :Bunny] [l b] :eat)
(defmethod encounter [:Lion :Lion] [l1 l2] :fight)
(defmethod encounter [:Bunny :Bunny] [b1 b2] :mate)

(def b1 {:Species :Bunny :other :stuff})
(def b2 {:Species :Bunny :other :stuff})
(def l1 {:Species :Lion :other :stuff})
(def l2 {:Species :Lion :other :stuff})

(encounter b1 b2)
-> :mate

(encounter b1 l1)
-> :run-away

(encounter l1 b1)
-> :eat

(encounter l1 l2)
-> :fight
```

[Source: clojure.org](http://clojure.org/runtime_polymorphism)

## Protocols & Datatypes

Clojure is written in terms of abstractions and your programs should too. Clojure
provides protocols for describing abstractions and various datatypes to implement
these abstractions.

### Protocols

There are several motivations for protocols:

* Provide a high-performance, dynamic polymorphism construct as an alternative to interfaces
* Support the best parts of interfaces: 1. specification only, no implementation 2. a single type can implement multiple protocols
* While avoiding some of the drawbacks: 1. Which interfaces are implemented is a design-time choice of the type author, cannot be extended later (although interface injection might eventually address this) 2. implementing an interface creates an isa/instanceof type relationship and hierarchy
* Avoid the 'expression problem' by allowing independent extension of the set of types, protocols, and implementations of protocols on types, by different parties: do so without wrappers/adapters
* Support the 90% case of multimethods (single dispatch on type) while providing higher-level abstraction/organization

Source: [clojure.org](http://clojure.org/protocols)

Example:

```Clojure
(defprotocol AProtocol
  "A doc string for AProtocol abstraction"
  (bar [a b] "bar docs")
  (baz [a] [a b] [a b c] "baz docs"))
```

### Datatypes

We can create a type (Java class) implementing the protocol:

```Clojure
(deftype Foo [a b c]
  AProtocol
  (bar [a b] a)
  (baz
    ([a] nil)
    ([a b] b)
    ([a b c] c)))
```

We can also create a record. A record also generates a Java class, like
`deftype` but also contains an implementation of a persistent map so
you can `assoc` and `dissoc` keywords on your record instances.

```Clojure
(defrecord Foo [a b c]
  AProtocol
  (bar [a b] a)
  (baz
    ([a] nil)
    ([a b] b)
    ([a b c] c)))
```

Finally, `reify` allows you to create an anonymous type and
instantiate it:

```Clojure
(let [foo (reify AProtocol
            AProtocol
            (bar [a b] a)
            (baz
              ([a] nil)
              ([a b] b)
              ([a b c] c)))]
  ...)
```
