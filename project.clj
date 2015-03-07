(defproject surviving "0.1.0-SNAPSHOT"
  :description "surviving"

  :dependencies [[org.clojure/clojure "1.7.0-alpha5"]
                 [org.clojure/tools.nrepl "0.2.7"]
                 [instaparse "1.3.5"]
                 [rhizome "0.2.4"]
                 [fipp "0.5.2"]]

  :source-paths ["src/clj"]

  :jvm-opts ["-server" "-Xmx256m"] ;; optional

  :main surviving.core
  )
