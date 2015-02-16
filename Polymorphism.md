# Polymorphism

## Multimethods

Generic form of polymorphism.

```
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

## Protocols

More efficient type-based polymorphism.

See http://clojure.org/protocols

## Java Interfaces

See https://clojuredocs.org/clojure.core/proxy
