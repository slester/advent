(require 'leiningen.exec)

(defn split [m] (re-seq #"[A-z][a-z]*" m))

(let [input (slurp "input/19.txt")
      rules (apply merge-with into (map (fn [l] (let [[all m r] (re-matches #"(\w+) => (\w+)" l)] {m [r]}))  (clojure.string/split input #"\n")))
      replacements (apply merge-with into (map (fn [l] (let [[all m r] (re-matches #"(\w+) => (\w+)" l)] {r m}))  (clojure.string/split input #"\n")))
      m "CRnCaCaCaSiRnBPTiMgArSiRnSiRnMgArSiRnCaFArTiTiBSiThFYCaFArCaCaSiThCaPBSiThSiThCaCaPTiRnPBSiThRnFArArCaCaSiThCaSiThSiRnMgArCaPTiBPRnFArSiThCaSiRnFArBCaSiRnCaPRnFArPMgYCaFArCaPTiTiTiBPBSiThCaPTiBPBSiRnFArBPBSiRnCaFArBPRnSiRnFArRnSiRnBFArCaFArCaCaCaSiThSiThCaCaPBPTiTiRnFArCaPTiBSiAlArPBCaCaCaCaCaSiRnMgArCaSiThFArThCaSiThCaSiRnCaFYCaSiRnFYFArFArCaSiRnFYFArCaSiRnBPMgArSiThPRnFArCaSiRnFArTiRnSiRnFYFArCaSiRnBFArCaSiRnTiMgArSiThCaSiThCaFArPRnFArSiRnFArTiTiTiTiBCaCaSiRnCaCaFYFArSiThCaPTiBPTiBCaSiThSiRnMgArCaF"
      sm (split m)
      ;; terminals (filter some? (distinct (flatten (map (fn [r] (map #(if (contains? rules %) nil %) (split (name r)))) (keys replacements)))))
      ]


  ; e => HF or NAl or OMg
  (defn build-molecule [m]
    (let [reps (map split (rules m))
          ]
      (map (fn [l] (map println l)) reps)
      )
    )

  (println (build-molecule "e"))

  ;; (println "Part 1:"
  ;;          (count (distinct (filter some? (flatten (for [i (range (count sm))
  ;;                                                                  :let [sym (nth sm i)
  ;;                                                                        reps (or (rules sym) sym)]]
  ;;                                                              (if (vector? reps)
  ;;                                                                (map #(str (clojure.string/join #"" (take i sm)) % (clojure.string/join #"" (drop (inc i) sm))) reps)
  ;;                                                                nil)))))))


)
