(ns surviving.core
  (:require [clojure.string :as string]
            [clojure.java.io :as io]
            [instaparse.core :as insta]
            [fipp.clojure :as fipp-clj]
            [fipp.printer :as fipp-pp]
            [fipp.edn :as fipp-edn]
            ))

(def whitespace
  (insta/parser
    "whitespace = #'\\s+'"))

(defn remove-comments [input & opts]
  (let [ch (first input)
        {:keys [state output]} opts
        prev-state (if state state :normal)
        prev-output (if output output "")]
    (if ch
      (recur (subs input 1)
        (do
          ;; (println prev-state prev-output "'" ch "'" (subs input 1))
          (condp = prev-state
            :normal (if (= ch \/)
                      (list :state :maybe :output prev-output)
                      (list :state prev-state :output (str prev-output ch)))
            :maybe  (if (= ch \/)
                      (list :state :single :output prev-output)
                      (if (= ch \*)
                        (list :state :multi :output prev-output)
                        (list :state :normal :output (str prev-output \/ ch))))
            :single (if (= ch \newline)
                      (list :state :normal :output (str prev-output \newline))
                      (list :state prev-state :output prev-output))
            :multi (if (= ch \*)
                     (list :state :multi-stop :output prev-output)
                     (if (= ch \newline)
                       (list :state prev-state :output
                         (str prev-output \newline))
                       (list :state prev-state :output prev-output)))
            :multi-stop (if (= ch \/)
                          (list :state :normal :output prev-output)
                          (list :state :multi :output prev-output))
            )))
      output)))

(defn read-sample []
  (remove-comments (slurp (io/file "resources/sample.prog"))))

(defn -main [ & args ]
  (println "surviving args:" args)
  (println "-- sample.prog --\n" (read-sample)))

;; various demonstration functions ...

(defn fibs []
  (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1])))

(defn fstep [[a b]]
  [b (+ a b)])

(defn random-ints [limit]
  (lazy-seq
    (cons
      (let [r (rand-int limit)]
        (println "realizing random number" r)
        r)
      (random-ints limit))))

(defn letterfreq [s]
  (->> s
    (filter #(java.lang.Character/isLetter %))
    (map #(java.lang.Character/toUpperCase %))
    frequencies
    (sort-by second >)))
