(require 'leiningen.exec)

(defn split [m] (re-seq #"[A-z][a-z]*" m))

(let [input (slurp "input/19.txt")
      rules (apply merge-with into (map (fn [l] (let [[all m r] (re-matches #"(\w+) => (\w+)" l)] {m [r]}))  (clojure.string/split input #"\n")))
      replacements  (map (fn [l] (let [[all m r] (re-matches #"(\w+) => (\w+)" l)] [r m]))  (clojure.string/split input #"\n"))
      m "CRnCaCaCaSiRnBPTiMgArSiRnSiRnMgArSiRnCaFArTiTiBSiThFYCaFArCaCaSiThCaPBSiThSiThCaCaPTiRnPBSiThRnFArArCaCaSiThCaSiThSiRnMgArCaPTiBPRnFArSiThCaSiRnFArBCaSiRnCaPRnFArPMgYCaFArCaPTiTiTiBPBSiThCaPTiBPBSiRnFArBPBSiRnCaFArBPRnSiRnFArRnSiRnBFArCaFArCaCaCaSiThSiThCaCaPBPTiTiRnFArCaPTiBSiAlArPBCaCaCaCaCaSiRnMgArCaSiThFArThCaSiThCaSiRnCaFYCaSiRnFYFArFArCaSiRnFYFArCaSiRnBPMgArSiThPRnFArCaSiRnFArTiRnSiRnFYFArCaSiRnBFArCaSiRnTiMgArSiThCaSiThCaFArPRnFArSiRnFArTiTiTiTiBCaCaSiRnCaCaFYFArSiThCaPTiBPTiBCaSiThSiRnMgArCaF"
      sm (split m)]


  (println "Part 1:"
           (count (distinct (filter some? (flatten (for [i (range (count sm))
                                                                   :let [sym (nth sm i)
                                                                         reps (or (rules sym) sym)]]
                                                               (if (vector? reps)
                                                                 (map #(str (clojure.string/join #"" (take i sm)) % (clojure.string/join #"" (drop (inc i) sm))) reps)
                                                                 nil)))))))

  (println "Part 2:" (loop [li [0 m]]
                       (let [[num-replacements final-string] li]
                       (if (= final-string "e")
                         num-replacements
                         (recur (reduce (fn [[c mstr] [s t]]
                                                (loop [new-c c
                                                       m mstr]
                                                  (let [new-m (clojure.string/replace-first m s t)]
                                                    (if (= m new-m)
                                                      [new-c new-m]
                                                      (recur (inc new-c) new-m))))) [num-replacements final-string] replacements))))))
)
