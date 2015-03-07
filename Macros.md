# Macros

## What are macros?

Clojure programs are composed of expressions. `1`, `(+ 1 2)`, and `(defn double [x] (* x 2)`
are all expressions. These expressions are evaluated to yield a value
(or sometimes cause side effects).

Macros are different, instead of returning values, they return expressions.
Macros let you transform your program before it is evaluated.

## Exploring the threading macro

Lets take a look at a very useful macro provided by Clojure, the
threading macro.

Lets say we had the following piece of code:

~~~clojure
(def resp {:response {:red "123" :green "2" :blue "9"}})
~~~

What if we wanted to get the value of red and convert it to an integer?
Lets explore this in a REPL with:

`$ lein repl`

The obvious way is to do something like the following:

```
user=> (def resp {:response {:red "123" :green "2" :blue "9"}})
nil
user=> (read-string (get (get resp :response) :red))
123
```

Kinda annoying to read right? This is where the threading macro `->` will
help us. Lets take a look at `->` using `doc`.

```
user=> (doc ->)
-------------------------
clojure.core/->
([x & forms])
Macro
  Threads the expr through the forms. Inserts x as the
  second item in the first form, making a list of it if it is not a
  list already. If there are more forms, inserts the first form as the
  second item in second form, etc.
nil
```

So essentially what this macro will do is nest the first argument through the
remaining arguments. Here's how we can clean up our code above:

```
user=> (-> resp (get :response) (get :red) (read-string))
123
```

Neat! To be sure that the threading macro is doing exactly what we expect we can
use `macroexpand`. Lets take a look at its docs.

```
user=> (doc macroexpand)
-------------------------
clojure.core/macroexpand
([form])
  Repeatedly calls macroexpand-1 on form until it no longer
  represents a macro form, then returns it.  Note neither
  macroexpand-1 nor macroexpand expand macros in subforms.
nil
```

So we should be able to see what the threading macro does to our code. Lets give
it a try:

```
user=> (macroexpand '(-> resp (get :response) (get :red) (read-string)))
(read-string (get (get resp :response) :red))
```

We need to convert our forms into a list with the apostrophe so it
doesn't get evaluated. This is code as data in action!
Notice that macroexpand resulted in the obvious approach we took earlier?

## Limitations of macros

Since macros execute at compile time they cannot be passed around
like functions can.

For example, lets say we wanted to perform a reduce on a vector of booleans.
One might expect the following to work:

~~~clojure
(reduce and [true false true])
~~~

But if we try to evaluate it in a repl we get:

```
CompilerException java.lang.RuntimeException: Can't take value of a macro: #'clojure.core/and, compiling:(NO_SOURCE_PATH:1:1)
```

This is because `and` is a macro and as such we can't pass it to a function as a parameter. Instead
we need to build a function...

~~~clojure
(reduce (fn [a b] (and a b)) [true false true])
~~~

Which gets us what we want.

## When to use macros?

Macros are a complex topic. It takes a lot of practice to write them correctly.

As we've seen in the previous section, macros can't be passed around like functions and
we lose all the great code organizational properties of first-class functions. Therefore,
we should not use macros where a function would do.

There are 2 main use cases for macros. One is used for programming in the small, the other in the large.

As we have seen in the previous section, macros like `->` let us write more concise code by avoiding
redundancies. You're going to encounter a few macros like this and that is probably the first
kind of macros you're going to write. These macros are generally small and isolated.

But macros are also used to implement new languages on top of Clojure. It can be small domain-specific-languages
like a query language for a database or completely new programming languages. As long as the source code
can be represented as s-expressions, any kind of programming language can be implemented with macros.