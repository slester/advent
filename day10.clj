(require 'leiningen.exec)

(def input "1321131112")

(defn say [n]
  (loop [digits n
         answer ""]
    (let [break (split-with #(= (first n) %) digits)
          left (count (break 0))
          right (break 1)]
     (if (< 0 (count digits))
       (recur right (str answer left (first n)))
       answer))))

(def part1 (count (reduce (fn [output iteration] (say (seq output))) input (range 40))))
;; (def part2 (count (reduce (fn [output iteration] (say (seq output))) part1 (range 10))))

(println (str "Part 1: " part1))
(println (str "Part 2: " part2))
