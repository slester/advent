(require 'leiningen.exec)

(def input (slurp "input/16.txt"))
(def real-aunt-sue {:children 3 :cats 7 :samoyeds 2 :pomeranians 3 :akitas 0 :vizslas 0 :goldfish 5 :trees 3 :cars 2 :perfumes 1})
(def sues
  (map (fn [l] (let [[all n k1 v1 k2 v2 k3 v3] (re-matches #"Sue (\d+): (\w+): (\d+), (\w+): (\d+), (\w+): (\d+)" l)]
                              (zipmap (map keyword (list k1 k2 k3)) (map read-string (list v1 v2 v3)) )))
                    (clojure.string/split input #"\n")))

(defn match-sue [sue] (every? true? (map #(= (sue %) (real-aunt-sue %)) (keys sue))))
(defn match-sue-updated [sue] (every? true? (map (fn [k] (cond
                                                              (or (= k :cats) (= k :trees)) (> (sue k) (real-aunt-sue k))
                                                              (or (= k :pomeranians) (= k :goldfish)) (< (sue k) (real-aunt-sue k))
                                                              :else (= (sue k) (real-aunt-sue k)))) (keys sue))))

(println "Part 1:" (loop [c 0] (if (match-sue (nth sues c)) (inc c) (recur (inc c)))))
(println "Part 2:" (loop [c 0] (if (match-sue-updated (nth sues c)) (inc c) (recur (inc c)))))
