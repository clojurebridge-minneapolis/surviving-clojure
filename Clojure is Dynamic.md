# Clojure is Dynamic

> "First and foremost, Clojure is dynamic. That means that a Clojure program is not just something you compile and run, but something with which you can interact. Clojure is not a language abstraction, but an environment, where almost all of the language constructs are reified, and thus can be examined and changed. This leads to a substantially different experience from running a program, examining its results (or failures) and trying again. In particular, you can grow your program, with data loaded, adding features, fixing bugs, testing, in an unbroken stream."

[Source: clojure.org](http://clojure.org/dynamic)

## Everything is a list!

| Typical functional call | Clojure function call |
|-------------------------|-----------------------|
| f(a, b, c);             | (f a b c)             |

## The REPL

```
user> (type 1)
java.lang.Long
user> (doc inc)
-------------------------
clojure.core/inc
([x])
  Returns a number one greater than num. Does not auto-promote
  longs, will throw on overflow. See also: inc'
nil
user> (class true)
java.lang.Boolean
user> (* 7 6)
42
user> (inc 1)
2
user> (inc (inc 1))
3
user> (* (+ 4 3) (- 7 1))
42
user> (str "The answer to the universe and everything: " *1)
"The answer to the universe and everything: 42"
user> (list 1 2 3)
(1 2 3)
user> (vector 4 5 6)
[4 5 6]
user> (hash-map :a 1 :b 2)
{:b 2, :a 1}
user> (get *1 :a)
1
user> (:b *2)
2
user> (fn [n] (+ n 1))
#<core$eval9146$fn__9147 user$eval9146$fn__9147@596761f6>
user> ((fn [n] (+ n 1)) 1000)
1001
user> (defn myinc [n] (+ n 1))
#'user/myinc
user> (myinc 1000)
1001
user> (myinc (myinc 1000))
1002
user> (if true :a :b)
:a
user> (if false :a :b)
:b
user> (def myvec [4 5 6])
#'user/myvec
user> myvec
[4 5 6]
user> (first myvec)
4
user> (rest myvec)
(5 6)
user> (nth myvec 1)
5
user> (map inc myvec)
(5 6 7)
user> (reduce + myvec)
15
user>
```

## Side-by-side code examples (from rosettacode)

### Arithmetic mean

http://rosettacode.org/wiki/Averages/Arithmetic_mean#Clojure

Clojure:
```
(defn mean [sq]
  (if (empty? sq)
      0
      (/ (reduce + sq) (count sq))))
```

Python:
```
def mean(x):
    return sum(x)/float(len(x)) if x else 0
```

Ruby:
```
def mean(nums)
  nums.inject(0.0, :+) / nums.size
end
```

### Letter frequency

http://rosettacode.org/wiki/Letter_frequency#Clojure


Clojure:
```
(defn letterfreq [s]
  (->> s
    (filter #(java.lang.Character/isLetter %))
    (map #(java.lang.Character/toUpperCase %))
    frequencies
    (sort-by second >)))
```

Python:
```
import string

def letterfreq(s):
    results = {}
    for ch in string.ascii_uppercase:
        results[ch] = 0
    for ch in s:
        ch = ch.upper()
        if ch.isupper():
            results[ch] += 1
    # NOTE: return value is not in freq order
    return [(k, v) for (k, v) in results.items() if v > 0]
```

Ruby:
```
def letterfreq(file)
  letters = 'a' .. 'z'
  File.read(file) .
       split(//) .
       group_by {|letter| letter.downcase} .
       select   {|key, val| letters.include? key} .
       collect  {|key, val| [key, val.length]}
end
letterfreq(ARGV[0]).sort_by {|key, val| -val}.each {|pair| p pair}
```
