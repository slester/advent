(require 'leiningen.exec)

(def input "1321131112")

(defn say [n]
  (let [regex (re-seq #"(\d)\1*" n)]
    (apply str (map #(str (count (first %)) (second %)) regex))))

(def part1 (first (drop 40 (iterate say input))))
(println (str "Part 1: " (count part1)))
(def part2 (first (drop 10 (iterate say part1))))
(println (str "Part 2: " (count part2)))
