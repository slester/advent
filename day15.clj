(require 'leiningen.exec)

(def input (slurp "input/15.txt"))
(def ingredient-data
  (apply merge (map (fn [l] (let [regex (re-matches #"(\w+): capacity (-?\d+), durability (-?\d+), flavor (\d+), texture (\d+), calories (\d+)" l)]
                              (hash-map (keyword (regex 1)) (map read-string (drop 2 regex)))))
                    (clojure.string/split input #"\n"))))
(def ingredients (keys ingredient-data))

(defn get-ingredient-amount [amounts ingredient]
  (apply + (map (fn [amount] (* (nth (get-in ingredient-data [(first amount)]) ingredient) (last amount))) amounts)))

(defn bake-cookies [amounts]
  (apply * (map #(max 0 %)
                (map (partial get-ingredient-amount amounts) (range 4)))))

(defn cookie-calories [amounts] (get-ingredient-amount amounts 4))

(println "Part 1:" (apply max (for [x (range 101) y (range 101) z (range 101)
                                    :let [a (- 100 x y z) ingredient-list (zipmap ingredients (list x y z a))]
                                    :when (>= 100 (+ x y z a))]
                                (bake-cookies ingredient-list))))

(println "Part 2:" (apply max (for [x (range 101) y (range 101) z (range 101)
                                    :let [a (- 100 x y z) ingredient-list (zipmap ingredients (list x y z a))]
                                    :when (and (= 500 (cookie-calories ingredient-list)) (>= 100 (+ x y z a)))]
                                (bake-cookies ingredient-list))))
