(require 'leiningen.exec)


(let [input 34000000]
  (defn factors [n] (distinct (reduce concat (for [x (range 1 (inc (Math/sqrt n))) :when (zero? (rem n x))] [x (/ n x)]))))
  (defn factors-max-50 [n] (filter #(< n (* 50 %)) (factors n)))
  (println "Part 1:" (inc (count
                            (take-while #(< % input)
                                        (map (fn [l] (apply + (map (partial * 10) l)))
                                             (map factors (range 1 java.lang.Integer/MAX_VALUE)))))))
  (println "Part 2:" (inc (count
                            (take-while #(< % input)
                                        (map (fn [l] (apply + (map (partial * 11) l)))
                                             (map factors-max-50 (range 1 java.lang.Integer/MAX_VALUE)))))))
)
