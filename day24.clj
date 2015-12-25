(require 'leiningen.exec
         '[clojure.math.combinatorics :as comb])

(let [presents (map read-string (clojure.string/split-lines (slurp "input/24.txt")))
      total-sum (apply + presents)]

  (println "Part 1:" (apply min (map #(apply * %)
                                     (loop [c 1]
                                       (let [ms (filter #(= (/ total-sum 3) (apply + %)) (comb/combinations presents c))]
                                         (if (< 0 (count ms)) ms (recur (inc c))))))))

  (println "Part 2:" (apply min (map #(apply * %)
                                     (loop [c 1]
                                       (let [ms (filter #(= (/ total-sum 4) (apply + %)) (comb/combinations presents c))]
                                         (if (< 0 (count ms)) ms (recur (inc c)))))))))
