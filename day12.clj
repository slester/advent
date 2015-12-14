(require 'leiningen.exec
				 '[clojure.data.json :as json])

(def input (slurp "input/12.txt"))

(def cleaned-input (clojure.string/replace input #"\{[^\{]*:\"red\"[^\}]*\}" "0"))
(def matcher (re-matcher #"[\[\:\{,]*(\-?\d+)[\}\,\]]*" input))
(def matcher2 (re-matcher #"[\[\:\{,]*(\-?\d+)[\}\,\]]*" cleaned-input))

(def json (json/read-str input :key-fn keyword))
(defn remove-red [node]
  (map (fn [j] (cond (map? j) (remove-red j)
                     (vector? j) (println j)
                     )) node))

(println (remove-red json))

; idea is: delete all levels where 'red' is prop, then convert to string and run matcher again
(def part1
  (loop [sum 0]
    (let [m (re-find matcher)]
      (if (nil? m)
      sum
      (recur (+ sum (read-string (m 1))))))))

(def part2
  (loop [sum 0]
    (let [m (re-find matcher2)]
      (if (nil? m)
      sum
      (recur (+ sum (read-string (m 1))))))))

(println (str "Part 1: " part1))
(println (str "Part 2: " part2))
