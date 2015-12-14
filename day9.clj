(require 'leiningen.exec
         '[clojure.math.combinatorics :as comb])

(def input (slurp "input/9.txt"))
(def location-map
  (apply (partial merge-with (fnil merge {}) {})
                                       ;; A to B
         (map (fn [location] (hash-map (keyword (location 1))
                                       (hash-map (keyword (location 2)) (read-string (location 3)))
                                       ;; B to A
                                       (keyword (location 2))
                                       (hash-map (keyword (location 1)) (read-string (location 3))))) (map (fn [line] (re-find #"(\w+) to (\w+) = (\d+)" line)) (clojure.string/split input #"\n")))))
(def cities (keys location-map))

(defn route-length [path]
  (reduce #(+ %1 (get-in location-map [(nth path %2) (nth path (inc %2))])) 0 (range (dec (count path)))))

(def possible-routes (map (fn [route] (route-length route)) (comb/permutations cities)))
(println (str "Part 1: " (apply min possible-routes)))
(println (str "Part 2: " (apply max possible-routes)))
