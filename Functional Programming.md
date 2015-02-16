# Functional Programming and Immutability

TODO: Don't make this page a reference of core clojure functions. The goal of this section is to show how to write functional programs with immutable data in Clojure.

## Functions

In functional programming, we build programs by writing functions that return values instead of using side-effects. Clojure progammers write the most part of their programs in a purely functional style and isolate state management in a few places using the concurrency mechanisms presented in the other session "Threading in Clojure".

This section presents the functions and control structures in Clojure.

## Defining and Calling a Function

```
(fn double [x]
  (* 2 x))

(defn double [x]
  (* 2 x))
```

## Control Structures

### Conditionals

* when
* if
* cond
* condp
* when-let
* if-let

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

## Primitive Data Types

* Nil
* Truthiness
* Symbols
* Numbers
* Keywords
* Strings
* Characters

## Immutable Data Structures

* A data structure is immutable if it cannot be changed after it has been created.
* But the point of a data structure is to modify it as the program runs.
* How do you program with immutable data structure?
* Huge advantage for concurrency.

## Firtst-Class Functions

As we have seen in the previous section, Clojure provides all of the data types usually found in programming languages: numbers, strings, composites data structures... But Clojure also supports the function as a data type.

We say that functions are first-class because they can be used as any other values. A function in Clojure can be:

* created on demand,
* stored in a data structure,
* be passed as an argument to a function, and
* be returned as the value of a function.

### Examples

* comp
* partial
* complement
* memoize