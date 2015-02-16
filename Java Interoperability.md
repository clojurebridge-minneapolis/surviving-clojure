# Java Interoperability

Clojure is a very practical language. The integration with the host platform has been designed as carefully as the functional part of the language.

Clojure programmers use Java libraries every day. The interoperability is seamless.

## Example

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

## Features

The example above illustrates how to:

* import Java classes in the current namespace: `(import ...)`
* instantiate a Java class: `(JFrame. "Celsius Converter")`
* call a Java method: `(.addActionListener convert-button <args>)`
* how to realize an interface: `(reify ActionListener (actionPerformed [_ evt] ...))`
* how to chain method calls: `(doto frame (.setLayout ...) ...)`
