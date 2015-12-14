(require 'leiningen.exec
         '[clojure.math.combinatorics :as comb])

(def input (slurp "input/13.txt"))

(def input-map
	(reduce (fn [m l] (let [regex (re-matches #"^(\w+) would (lose|gain) (\d+) happiness units by sitting next to (\w+)\.$" l)]
											(assoc-in m [(keyword (regex 1)) (keyword (regex 4))] (* (if (= "lose" (regex 2)) -1 1) (read-string (regex 3))))))
					{} (clojure.string/split input #"\n")))

(def guestlist (keys input-map))

(defn calc-happiness [table p1 p2] (if (or (= p1 :me) (= p2 :me)) 0 (+ (get-in table [p1 p2]) (get-in table [p2 p1]))))

(defn table-happiness [table guests]
	(let [pairs (concat (partition 2 guests) (partition 2 (drop 1 guests)) (list (list (first guests) (last guests))))]
		(reduce (fn [sum pair] (+ sum (calc-happiness table (first pair) (last pair)))) 0 pairs)))

(def part1 (apply max (map (fn [guests] (table-happiness input-map guests)) (comb/permutations guestlist))))
(def part2 (apply max (map (fn [guests] (table-happiness input-map guests)) (comb/permutations (conj guestlist :me)))))

(println "Part 1:" part1)
(println "Part 2:" part2)
