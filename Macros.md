# Macros

## What are macros?

Lisps embody the idea of code is data, data is code.
Macros can be thought of as functions that alter
the behavior of your code at compile time.

TODO fixup

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

## Advantages of macros

## Limitations of macros

Since macros execute at compile time they cannot be passed around
like functions can.

TODO flesh out more
