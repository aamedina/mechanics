(ns mechanics.core
  (:gen-class)
  (:require [criterium.core :refer [quick-bench]]))

(definline sqrt
  [x]
  `(Math/sqrt ~x))

(definline square
  [x]
  `(* ~x ~x))

(defn -main
  [& args]
  (println "Hello, world!"))
