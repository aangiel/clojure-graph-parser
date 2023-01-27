(ns graph.core_test
  (:require [clojure.test :refer [deftest, is  , testing]]
            [graph.core :refer [graph make-edge]])
  (:import (graph.core Vertex)))

(deftest node-matcher-name
  (testing "retrieving name from node"
    (is (= (graph "()")
           {:vertices #{(Vertex. "")}
            :edges    #{}}))

    (is (= (graph "()()")
           {:vertices #{(Vertex. "")}
            :edges    #{}}))

    (is (= (graph "(A)")
           {:vertices #{(Vertex. "A")}
            :edges    #{}}))

    (is (= (graph "(A)(A)")
           {:vertices #{(Vertex. "A")}
            :edges    #{}}))

    (is (= (graph "(A)(B)")
           {:vertices #{(Vertex. "A") (Vertex. "B")}
            :edges    #{}}))

    (is (= (graph "(A)     (B)")
           {:vertices #{(Vertex. "A") (Vertex. "B")}
            :edges    #{}}))

    (is (= (graph "(A)->(B)")
           {:vertices #{(Vertex. "A") (Vertex. "B")}
            :edges    #{(make-edge (Vertex. "A") (Vertex. "B"))}}))

    (is (= (graph "(A)<-(B)")
           {:vertices #{(Vertex. "A") (Vertex. "B")}
            :edges    #{(make-edge (Vertex. "B") (Vertex. "A"))}}))

    (is (= (graph "(A)<->(B)")
           {:vertices #{(Vertex. "A") (Vertex. "B")}
            :edges    #{(make-edge (Vertex. "A") (Vertex. "B"))
                        (make-edge (Vertex. "B") (Vertex. "A"))}}))

    ))


(deftest a-b-c
  (testing "a-b-c"
    (is (= (graph "(A)->(B)->(C)")
           {:vertices #{(Vertex. "A") (Vertex. "B") (Vertex. "C")}
            :edges    #{(make-edge (Vertex. "A") (Vertex. "B"))
                        (make-edge (Vertex. "B") (Vertex. "C"))}}))

    (is (= (graph "(A)<-(B)<-(C)")
           {:vertices #{(Vertex. "A") (Vertex. "B") (Vertex. "C")}
            :edges    #{(make-edge (Vertex. "B") (Vertex. "A"))
                        (make-edge (Vertex. "C") (Vertex. "B"))}}))

    (is (= (graph "(A)->(B)(A)->(C)")
           {:vertices #{(Vertex. "A") (Vertex. "B") (Vertex. "C")}
            :edges    #{(make-edge (Vertex. "A") (Vertex. "B"))
                        (make-edge (Vertex. "A") (Vertex. "C"))}}))

    (is (= (graph "(A)<->(B)<->(C)")
           {:vertices #{(Vertex. "A") (Vertex. "B") (Vertex. "C")}
            :edges    #{(make-edge (Vertex. "A") (Vertex. "B"))
                        (make-edge (Vertex. "B") (Vertex. "A"))
                        (make-edge (Vertex. "B") (Vertex. "C"))
                        (make-edge (Vertex. "C") (Vertex. "B"))}}))

    (is (= (graph "  (A)     <->   (B)   <->   (C)")
           {:vertices #{(Vertex. "A") (Vertex. "B") (Vertex. "C")}
            :edges    #{(make-edge (Vertex. "A") (Vertex. "B"))
                        (make-edge (Vertex. "B") (Vertex. "A"))
                        (make-edge (Vertex. "B") (Vertex. "C"))
                        (make-edge (Vertex. "C") (Vertex. "B"))}}))
    ))

(deftest edge-label
  (testing "labels"
    (is (= (graph "(A)-c->(B)")
           {:vertices #{(Vertex. "A") (Vertex. "B")}
            :edges    #{(make-edge "c" (Vertex. "A") (Vertex. "B"))}}))

    (is (= (graph "(A)<-c-(B)")
           {:vertices #{(Vertex. "A") (Vertex. "B")}
            :edges    #{(make-edge "c" (Vertex. "B") (Vertex. "A"))}}))

    (is (= (graph "(A)<-c->(B)")
           {:vertices #{(Vertex. "A") (Vertex. "B")}
            :edges    #{(make-edge "c" (Vertex. "A") (Vertex. "B"))
                        (make-edge "c" (Vertex. "B") (Vertex. "A"))}}))))


