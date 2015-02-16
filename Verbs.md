# Verbs

In the previous section, we've seen the different types of data we could represent in Clojure. This section describes how we can describe processes on this data using functions. Functions are the building blocks of any language of the Lisp family.

## Defining and Calling a Function

```
(fn double [x]
  (* 2 x))

#(* 2 %)

(defn double [x]
  (* 2 x))

(def double #(* 2 %))
```

## Flow Control

### Conditionals

### Loops

* loop/recur
* dotimes, doseq, for

### Exceptions

* try/catch/finally
* throw
* assert

### Laziness

* lazy-seq
* lazy-cat
* doall
* dorun

### Misc

* repeatedly
* iterate
* ->, ->>
* apply
