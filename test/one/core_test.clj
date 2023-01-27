(ns one.core_test
  (:require [clojure.test :refer :all]))

(defrecord Vertex [name])


(defn parse-graph [input]
  {:vertices [(Vertex. "")]
   :edges    []
   })

(println (parse-graph "()"))

(deftest graph
  (testing "graph"
    (is (= (parse-graph "()") {
                               :vertices [(Vertex. "")]
                               :edges    []
                               }))))