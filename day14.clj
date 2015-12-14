(require 'leiningen.exec
				 '[clojure.data.json :as json])

(def input (slurp "input/14.txt"))
(def limit 2503)

(def reindeer-data
  (apply merge (map (fn [l] (let [regex (re-matches #"(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds\." l)]
                              (hash-map (regex 1) (map read-string (drop 2 regex)))))
                    (clojure.string/split input #"\n"))))

(defn reindeer-distance [secs reindeer]
  (let [reindeer-name (key reindeer)
        reindeer-data (val reindeer)
        speed (first reindeer-data)
        burst-time (second reindeer-data)
        rest-time (last reindeer-data)
        total-time (+ burst-time rest-time)]
    (hash-map (keyword reindeer-name) (+ (* speed (min (mod secs total-time) burst-time)) (* (* speed burst-time) (quot secs total-time))))))

;; Part 2
(defn get-best-reindeer [sec]
  (let [current-positions (apply merge (map (fn [reindeer] (reindeer-distance sec reindeer)) reindeer-data))
        leading-distance (val (apply max-key val current-positions))]
    (keys (select-keys current-positions (for [[k v] current-positions :when (= v leading-distance)] k)))))

(defn reindeer-points [max-secs]
  (loop [points (apply merge (map #(hash-map (keyword %) 0) (keys reindeer-data)))
         sec 1]
    (let [best-reindeer (get-best-reindeer sec)
          new-points (reduce #(update-in %1 [%2] inc) points best-reindeer)]
      (if (< sec max-secs)
        (recur new-points (inc sec))
        new-points))))


(println "Part 1:" (apply max-key val (apply merge (map (fn [reindeer] (reindeer-distance limit reindeer)) reindeer-data))))
(println "Part 2:" (apply max-key val (reindeer-points limit)))
