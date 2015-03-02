# Functional Programming and Immutability

In functional programming, we build programs by writing functions that return values instead of using side-effects. Clojure progammers write the most part of their programs in a purely functional style and isolate state management in a few places using the concurrency mechanisms presented in the other session "Threading in Clojure".

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

This example is taken from Chapter 6 of ["On Lisp" by Paul Graham](http://www.paulgraham.com/onlisptext.html).

> As an example, we will use about the simplest application possible: one of those programs that play twenty questions. Our network will be a binary tree. Each non-leaf node will contain a yes/no question, and depending on the answer to the question, the traversal will continue down the left or right subtree. Leaf nodes will contain return values. When the traversal reaches a leaf node, its value will be returned as the value of the traversal. A session with this program might look as in Figure 6.1.

```clojure

(defn make-network
  "Create a new network.

  A network is represented as a map associating the name of the
  node to its contents and the name of the other yes, no nodes."
  []
  {})

(defn make-node
  "Make a new node."
  [contents yes no]
  {:contents contents
   :yes      yes
   :no       no})

(defn add-node
  "Add a new node to the network under the given name."
  [network name contents & [yes no]]
  (assoc network name (make-node contents yes no)))

(defn test-network
  "Build a network to test the program."
  []
  (-> (make-network)
      (add-node :people   "Is the person a man?" :male     :female)
      (add-node :male     "Is he living?"        :liveman  :deadman)
      (add-node :deadman  "Was he american?"     :american :not-american)
      (add-node :american "Is he on a coin?"     :coin     :cidence)
      (add-node :coin     "Is the coin a penny?" :penny    :coins)
      (add-node :penny    :lincoln)))

(defn run [network name]
  (when-let [{:keys [contents yes no]} (network name)]
    (if yes
      (do
        (println contents)
        (run network (if (= "y" (read-line)) yes no)))
      contents)))

(run (test-network) :people)
```

Notice how our program represent data using Clojure immutable data structures instead of creating new "classes". We want to take advantage of all the Clojure core functions as well as the nice syntax to write concise programs.

We've been implementing our program in a traditional approach: data structure + function. Later today, during the exploration session, you will have the possibility to use closures to represent the network, briniging several advantages in clarity and performance.
