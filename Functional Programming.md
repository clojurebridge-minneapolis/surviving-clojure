# Functional Programming and Immutability

In functional programming, we build programs by writing functions that return values instead of using side-effects. Clojure progammers write the most part of their programs in a purely functional style and isolate state management in a few places using the concurrency mechanisms presented in the other session "Threading in Clojure".

I don't think we need to reinvent much here. There are many good articles covering the basic data types and functions in Clojure. For example:

* https://aphyr.com/posts/301-clojure-from-the-ground-up-welcome
* https://aphyr.com/posts/302-clojure-from-the-ground-up-basic-types
* https://aphyr.com/posts/303-clojure-from-the-ground-up-functions
* https://aphyr.com/posts/304-clojure-from-the-ground-up-sequences

I think it is important to present all the examples using REPL input/output and illustrate clearly what was said in the previous section: we're interacting with a dynamic system. When we write `(defn my-func ...)` we're actually calling a function that creates another function and associate it to a var called `'#my-func` in the current namespace.

## First-Class Functions

As we have seen in the previous section, Clojure provides all of the data types usually found in programming languages: numbers, strings, composites data structures... But Clojure also supports the function as a data type.

We say that functions are first-class because they can be used as any other values. A function in Clojure can be:

* created on demand,
* stored in a data structure,
* be passed as an argument to a function, and
* be returned as the value of a function.

### How to Create Functions on Demand

* comp
* partial
* complement
* memoize

## Example

This example is taken from Chapter 6 of "On Lisp" by Paul Graham. It implements the game of twenty questions. The computer tries to guess what you are thinking about in less than 20 yes/no questions.

We represent the game in the computer as a network of questions. The branch nodes contaisn a question and pointers to other nodes depending on the answer the user gives. The leaf nodes just contain the guess.

The example shows 3 different implementations:

1. Using immutable data structures
2. Using closures
3. Using "compiled" functions.

```
;; First, we have a few functions used by the 3 implementations

(defn network
  "Create a new network for the given node constructor.

  A network is represented as a map associating the name of the
  node to its contents and the pointers to the other yes, no nodes.

  Use `add-node` to add nodes to the network. The network will
  instantiate each node using the constructor passed here."
  [make-node]
  (with-meta {}
    {:make-node make-node}))

(defn add-node [network name contents & [yes no]]
  (assoc network
    name ((:make-node (meta network)) contents yes no)))

(defn test-network
  "Build a network to test the program using the given node constructor."
  [make-node]
  (-> (network make-node)
      (add-node :people   "Is the person a man?" :male     :female)
      (add-node :male     "Is he living?"        :liveman  :deadman)
      (add-node :deadman  "Was he american?"     :american :not-american)
      (add-node :american "Is he on a coin?"     :coin     :cidence)
      (add-node :coin     "Is the coin a penny?" :penny    :coins)
      (add-node :penny    :lincoln)))

;; 1. Data + Function (Interpreter)

(defn make-node [contents yes no]
  {:contents contents
   :yes yes
   :no no})

(def network-1
  (test-network make-node))

(defn run-node [network node-name]
  (when-let [{:keys [contents yes no]} (network node-name)]
    (if yes
      (do
        (println contents)
        (run-node network (if (= "y" (read-line)) yes no)))
      contents)))

(run-node network-1 :people)

;; 2. Closure

(def network-2
  (test-network
   (fn [contents yes no]
     (if yes
       (fn [network]
         (println contents)
         (when-let [f (network (if (= "y" (read-line)) yes no))]
           (f network)))
       (fn [network]
         contents)))))

((network-2 :people) network-2)

;; It is interesting to note here how we need to pass the state
;; around (`network` argument) because we don't maintain state.

;; 3. Data + Function (Compiler)

(defn compile-net [network node-name]
  (when-let [{:keys [contents yes no]} (network node-name)]
    (if yes
      (let [yes-fn (compile-net network yes)
            no-fn (compile-net network no)]
        (fn []
          (println contents)
          (if (= "y" (read-line))
            (yes-fn)
            (no-fn))))
      (fn []
        contents))))

(def network-3 (compile-net network-1 :people))

(network-3)
```