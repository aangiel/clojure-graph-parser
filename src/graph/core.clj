(ns graph.core
  (:require [clojure.set :refer [union]]))

(defrecord Vertex [name])

(defrecord Edge [label from to])
(defn strip-label [label]
  (.replaceAll label "[<>-]" ""))

(defn make-edge
  ([from to] (Edge. "" from to))
  ([label from to] (Edge. (strip-label label) from to)))
(defn- is-vertex? [token]
  (not (nil? (re-find (re-matcher #"\(\w*\)" token)))))

(defn- vertex-name [token]
  (.trim (.replaceAll token "\\(|\\)" "")))

(defn- add-vertex [result token]
  (assoc result :vertices (conj (:vertices result)
                                (Vertex. (vertex-name token)))))

(defn- strip [operator]
  (.replaceAll (.replaceAll operator "\\w+" "") "-+" "-"))

(defn- create-edge [result operator]
  (let [operators {"->"  (fn [left right] #{(make-edge operator left right)})
                   "<-"  (fn [left right] #{(make-edge operator right left)})
                   "<->" (fn [left right] #{(make-edge operator left right)
                                            (make-edge operator right left)})}]
    (apply (get operators (strip operator))
           [(first (reverse (:vertices result)))
            (second (reverse (:vertices result)))])))

(defn- add-edge [result token]
  (assoc result :edges (union (:edges result)
                              (create-edge result token))))

(defn- swap-operator-with-head [result token]
  (conj (conj (pop result) token)
        (peek result)))

(defn- postfix [input]
  (loop [result []
         tokens (re-seq #"\(\w*\)|<?-\w*-?>?" input)]
    (if-not (empty? tokens)
      (recur (if (is-vertex? (first tokens))
               (conj result (first tokens))
               (swap-operator-with-head result (first tokens)))
             (next tokens))
      (reverse result))))

(defn graph [input]
  (loop [result {:vertices []
                 :edges    []}
         tokens (postfix input)]
    (if-not (empty? tokens)
      (recur (if (is-vertex? (peek tokens))
               (add-vertex result (peek tokens))
               (add-edge result (peek tokens)))
             (pop tokens))
      {:vertices (set (:vertices result))
       :edges    (set (:edges result))})))
