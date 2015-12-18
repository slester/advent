(require 'leiningen.exec)

(let [max-lights 100
      input (slurp "input/18.txt")
      matrix (vec (map #(clojure.string/split % #"") (clojure.string/split input #"\n")))]

  (defn num-lit-neighbors [m x y]
    (count (filter true?
                   (for [i (range -1 2)
                         j (range -1 2)
                         :let [cx (+ x i)
                               cy (+ y j)]
                         :when (and (not= 0 i j) (< cx max-lights) (> cx -1) (< cy max-lights) (> cy -1))]
                     (= (get-in m [cx cy]) "#")))))

  (defn step [m]
    (vec (map vec
              (partition 100
                         (for [x (range max-lights)
                               y (range max-lights)
                               :let [cur (get-in m [x y])]]
                           (let [nln (num-lit-neighbors m x y)]
                             (if (= cur "#")
                               (if (or (= 3 nln) (= 2 nln)) "#" ".")
                               (if (= 3 nln) "#" "."))))))))

  (defn num-lit [m]
    (count (filter true?
                   (for [x (range max-lights)
                         y (range max-lights)]
                     (= (get-in m [x y]) "#")))))

  (defn always-on [m]
   (-> m
       (assoc-in [0 0] "#")
       (assoc-in [0 99] "#")
       (assoc-in [99 0] "#")
       (assoc-in [99 99] "#")))

  (println "Part 1:" (num-lit (nth (iterate step matrix) 100)))
  (println "Part 2:" (num-lit (always-on (nth (iterate #(step (always-on %)) matrix) 100)))))
