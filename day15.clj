(require 'leiningen.exec
         '[clojure.math.combinatorics :as comb])

(def input (slurp "input/15.txt"))
(def ingredient-data
  (apply merge (map (fn [l] (let [regex (re-matches #"(\w+): capacity (-?\d+), durability (-?\d+), flavor (\d+), texture (\d+), calories (\d+)" l)]
                              (hash-map (keyword (regex 1)) (map read-string (drop 2 regex)))))
                    (clojure.string/split input #"\n"))))
(def ingredients (keys ingredient-data))
(def possible-amounts (filter #(= 100 (apply + (vals %))) (map #(apply merge %) (apply comb/cartesian-product (map (fn [ingredient] (map #(hash-map ingredient %) (range 101))) ingredients)))))

(defn get-ingredient-amount [amounts ingredient]
  (apply + (map (fn [amount] (* (nth (get-in ingredient-data [(first amount)]) ingredient) (last amount))) amounts)))

(defn bake-cookies [amounts]
  (apply * (map #(max 0 %)
                (map (partial get-ingredient-amount amounts) (range 4)))))

(defn cookie-calories [amounts] (get-ingredient-amount amounts 4))

;; (println "Part 1:" (apply max (pmap bake-cookies possible-amounts)))
;; (println "Part 2 map:" (time (doall (map bake-cookies (filter #(= 500 (cookie-calories %)) possible-amounts)))))
(println "Part 2:" (apply max (pmap bake-cookies (filter #(= 500 (cookie-calories %)) possible-amounts))))
