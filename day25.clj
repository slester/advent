(require 'leiningen.exec)

(let [lines (map read-string (clojure.string/split-lines (slurp "input/24.txt")))
      row 2978
      col 3083]

  (defn code-gen [n] (rem (* n 252533) 33554393))
  (defn get-num [r c] (+ (apply + (range row)) (apply + (range (inc row) (+ row col)))))

  (println (nth (iterate code-gen 20151125) (get-num row col))))
