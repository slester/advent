(require 'leiningen.exec
				 '[clojure.math.combinatorics :as comb])

(def input "Alice would lose 2 happiness units by sitting next to Bob.
Alice would lose 62 happiness units by sitting next to Carol.
Alice would gain 65 happiness units by sitting next to David.
Alice would gain 21 happiness units by sitting next to Eric.
Alice would lose 81 happiness units by sitting next to Frank.
Alice would lose 4 happiness units by sitting next to George.
Alice would lose 80 happiness units by sitting next to Mallory.
Bob would gain 93 happiness units by sitting next to Alice.
Bob would gain 19 happiness units by sitting next to Carol.
Bob would gain 5 happiness units by sitting next to David.
Bob would gain 49 happiness units by sitting next to Eric.
Bob would gain 68 happiness units by sitting next to Frank.
Bob would gain 23 happiness units by sitting next to George.
Bob would gain 29 happiness units by sitting next to Mallory.
Carol would lose 54 happiness units by sitting next to Alice.
Carol would lose 70 happiness units by sitting next to Bob.
Carol would lose 37 happiness units by sitting next to David.
Carol would lose 46 happiness units by sitting next to Eric.
Carol would gain 33 happiness units by sitting next to Frank.
Carol would lose 35 happiness units by sitting next to George.
Carol would gain 10 happiness units by sitting next to Mallory.
David would gain 43 happiness units by sitting next to Alice.
David would lose 96 happiness units by sitting next to Bob.
David would lose 53 happiness units by sitting next to Carol.
David would lose 30 happiness units by sitting next to Eric.
David would lose 12 happiness units by sitting next to Frank.
David would gain 75 happiness units by sitting next to George.
David would lose 20 happiness units by sitting next to Mallory.
Eric would gain 8 happiness units by sitting next to Alice.
Eric would lose 89 happiness units by sitting next to Bob.
Eric would lose 69 happiness units by sitting next to Carol.
Eric would lose 34 happiness units by sitting next to David.
Eric would gain 95 happiness units by sitting next to Frank.
Eric would gain 34 happiness units by sitting next to George.
Eric would lose 99 happiness units by sitting next to Mallory.
Frank would lose 97 happiness units by sitting next to Alice.
Frank would gain 6 happiness units by sitting next to Bob.
Frank would lose 9 happiness units by sitting next to Carol.
Frank would gain 56 happiness units by sitting next to David.
Frank would lose 17 happiness units by sitting next to Eric.
Frank would gain 18 happiness units by sitting next to George.
Frank would lose 56 happiness units by sitting next to Mallory.
George would gain 45 happiness units by sitting next to Alice.
George would gain 76 happiness units by sitting next to Bob.
George would gain 63 happiness units by sitting next to Carol.
George would gain 54 happiness units by sitting next to David.
George would gain 54 happiness units by sitting next to Eric.
George would gain 30 happiness units by sitting next to Frank.
George would gain 7 happiness units by sitting next to Mallory.
Mallory would gain 31 happiness units by sitting next to Alice.
Mallory would lose 32 happiness units by sitting next to Bob.
Mallory would gain 95 happiness units by sitting next to Carol.
Mallory would gain 91 happiness units by sitting next to David.
Mallory would lose 66 happiness units by sitting next to Eric.
Mallory would lose 75 happiness units by sitting next to Frank.
Mallory would lose 99 happiness units by sitting next to George.")

(def input-map
  (reduce (fn [m l] (let [regex (re-matches #"^(\w+) would (lose|gain) (\d+) happiness units by sitting next to (\w+)\.$" l)]
                     (assoc-in m [(keyword (regex 1)) (keyword (regex 4))] (* (if (= "lose" (regex 2)) -1 1) (read-string (regex 3))))
                      )) {} (clojure.string/split input #"\n")))

(def guestlist (keys input-map))
(def guestlist-plus-self (conj (keys input-map) :me))

(defn calc-happiness [table p1 p2] (if (or (= p1 :me) (= p2 :me)) 0 (+ (get-in table [p1 p2]) (get-in table [p2 p1]))))

(defn table-happiness [table guests]
  ;; (println "guests:" guests)
	(let [pairs (concat (partition 2 guests) (partition 2 (drop 1 guests)) (list (list (first guests) (last guests))))]
		(reduce (fn [sum pair] (+ sum (calc-happiness table (first pair) (last pair)))) 0 pairs)))

(def part1 (apply max (map (fn [guests] (table-happiness input-map guests)) (comb/permutations guestlist))))
(def part2 (- (apply max (map (fn [guests] (table-happiness input-map guests)) (comb/permutations guestlist-plus-self))) part1))

(println "Part 1:" part1)
(println "Part 2:" part2)
