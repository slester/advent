(require 'leiningen.exec
         '[clojure.math.combinatorics :as comb])

(let [liters 150
      input (slurp "input/17.txt")
      all-containers (zipmap (map char (range (int \a) (int \z))) (map read-string (clojure.string/split input #"\n")))
      containers (filter #(= liters (apply + %))
                         (map (fn [l] (map #(get all-containers %) l))
                              (mapcat #(comb/combinations (keys all-containers) %) (range 1 (count all-containers)))))]
  (println "Part 1:" (count containers))
  (println "Part 2:" (count (filter #(= (count (first containers)) (count %)) containers))))
