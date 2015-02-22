# Java Interoperability

Clojure is a very practical language. The integration with the host platform has been designed as carefully as the functional part of the language.

Clojure programmers use Java libraries every day. The interoperability is seamless.

Lets open up a REPL to take a look at this.

```
$ lein repl
```

## Calling methods on Java objects

Calling a method on a Java object is easy, it follows the following format:

`(.methodName object *args)`

Since Clojure strings are actually just Java strings we can call all the normal Java methods on them.

```
user=> (.toLowerCase "I LIKE SHOUTING")
"i like shouting"
```

If we want to be able to look at what members Java objects have
we can do the following.

```
user=> (use 'clojure.reflect 'clojure.pprint)
nil
user=> (pprint (reflect "string"))
```

This will pretty print all the methods on the string object.

## Static methods

Static methods can be called like `(JavaClassName/staticMethod *args)`.

```
user=> (Math/cos 34)
-0.8485702747846052
```

## Creating Java objects

New Java objects can be created in Clojure in two different ways.

`(new JavaClassName *args)` or `(JavaClassName. *args)`

At our REPL lets create a new instance of a Date object using the
second method of creating new instances.

```
user=> (import java.util.Date)
java.util.Date
user=> (Date.)
#inst "2015-02-22T18:21:51.656-00:00"
```

Lets explicitly invoke the toString method on a new Date object.

```
user=> (def currentTime (Date.))
#'user/currentTime
user=> (.toString currentTime)
"Sun Feb 22 12:31:23 CST 2015"
```

## Convenience functions/macros

Clojure provides the `doto` macro which allows us to perform multiple method
calls more succinctly 

```
user=> (import java.util.HashMap)
java.util.HashMap
user=> (doto (HashMap.) (.put "red" 1) (.put "blue" 2))
{"red" 1, "blue" 2}
```

## Comprehensive example

Lets tie everything together and take a look at some
code that interacts with swing and awt.

```Clojure
(import '(javax.swing JFrame JLabel JTextField JButton)
        '(java.awt.event ActionListener)
        '(java.awt GridLayout))
(defn celsius []
  (let [frame (JFrame. "Celsius Converter")
        temp-text (JTextField.)
        celsius-label (JLabel. "Celsius")
        convert-button (JButton. "Convert")
        fahrenheit-label (JLabel. "Fahrenheit")]
    (.addActionListener
     convert-button
     (reify ActionListener
            (actionPerformed
             [_ evt]
             (let [c (Double/parseDouble (.getText temp-text))]
               (.setText fahrenheit-label
                         (str (+ 32 (* 1.8 c)) " Fahrenheit"))))))
    (doto frame
      (.setLayout (GridLayout. 2 2 3 3))
      (.add temp-text)
      (.add celsius-label)
      (.add convert-button)
      (.add fahrenheit-label)
      (.setSize 300 80)
      (.setVisible true))))
(celsius)
```

[Source: clojure.org](http://clojure.org/jvm_hosted)

The example above illustrates how to:

* import Java classes in the current namespace: `(import ...)`
* instantiate a Java class: `(JFrame. "Celsius Converter")`
* call a Java method: `(.addActionListener convert-button <args>)`
* how to realize an interface: `(reify ActionListener (actionPerformed [_ evt] ...))`
* how to chain method calls: `(doto frame (.setLayout ...) ...)`
