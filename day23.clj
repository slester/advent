(require 'leiningen.exec)

(let [input (slurp "input/23.txt")
      instructions (clojure.string/split input #"\n")
      registers {"a" 0 "b" 0}]

  (defn do-instruction [line-num registers]
    (if (>= line-num (count instructions))
      [nil registers]
      (let [line (nth instructions line-num)
            [all cmd reg extra] (re-matches #"(\w+) (\w|\-?\+?\d+),? ?(\+\d+)?" line)]
        (cond
          (= cmd "hlf") [(inc line-num) (update-in registers [reg] quot 2)]
          (= cmd "tpl") [(inc line-num) (update-in registers [reg] * 3)]
          (= cmd "inc") [(inc line-num) (update-in registers [reg] inc)]
          (= cmd "jmp") [(+ line-num (read-string reg)) registers]
          (= cmd "jie") [(if (even? (registers reg)) (+ line-num (read-string extra)) (inc line-num)) registers]
          (= cmd "jio") [(if (= 1 (registers reg)) (+ line-num (read-string extra)) (inc line-num)) registers]))))

  (println "Part 1:" (loop [c 0
                            regs registers]
                       (let [[next-line rs] (do-instruction c regs)]
                         (if (some? next-line)
                           (recur next-line rs)
                           rs))))

  (println "Part 2:" (loop [c 0
                            regs (update-in registers ["a"] inc)]
                       (let [[next-line rs] (do-instruction c regs)]
                         (if (some? next-line)
                           (recur next-line rs)
                           rs)))))
