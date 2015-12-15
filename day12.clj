(require 'leiningen.exec
				 '[clojure.data.json :as json])

(def input (slurp "input/12.txt"))

(defn parse [node]
  (cond
    (number? node) node
    (vector? node) (reduce (fn [sum v] (+ sum (parse v))) 0 node)
    (map? node) (reduce (fn [sum m] (+ sum (parse (second m)))) 0 node)
    :else 0))

(defn parse-without-red [node]
  (cond
    (number? node) node
    (vector? node) (reduce (fn [sum v] (+ sum (parse-without-red v))) 0 node)
    (map? node) (if (some #(= (second %) "red") node) 0 (reduce (fn [sum m] (+ sum (parse-without-red (second m)))) 0 node))
    :else 0))

(println "Part 1: " (parse input))
(println "Part 2: " (parse-without-red input))
