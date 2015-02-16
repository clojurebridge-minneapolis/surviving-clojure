# Brick Laying - Example

Let's illustrate what we've learnt so far by implementing the simple game of the twenty questions. This example is taken from Chapter 6 of "On Lisp" by Paul Graham.

The idea of the game is to have the computer guess what you are thinking about by asking less than 20 questions.

We can represent our series of questions as a network.

```
(defn make-node [contents yes no]
  {:contents contents
   :yes yes
   :no no})
```

```
(defn make-network []
  (-> {}
      (assoc :people   (make-node "Is the person a man?" :male     :female))
      (assoc :male     (make-node "Is he living?"        :liveman  :deadman))
      (assoc :deadman  (make-node "Was he american?"     :american :not-american))
      (assoc :american (make-node "Is he on a coin?"     :coin     :cidence))
      (assoc :coin     (make-node "Is the coin a penny?" :penny    :coins))
      (assoc :penny    (make-node :lincoln))))
```

So we have defined the data types for our program. We now need to write the functions that is going to run on this data.

```
(defn run-node [network node-name]
  (when-let [{:keys [contents yes no]} (network node-name)]
    (if yes
      (do
        (println contents)
        (run-node network (if (= "y" (read-line)) yes no)))
      contents)))
```

This is a very simple program but it illustrates:

* how to use Clojure core data structure to represent our data
* how to write functions in Clojure
