(require 'leiningen.exec
         '[clojure.math.combinatorics :as comb])

(let [boss {:hp 104 :damage 8 :armor 1}
      hp 100
      weapons [[8      4       0]
               [10     5       0]
               [25     6       0]
               [40     7       0]
               [74     8       0]]
      armor [[0      0       0]
             [13     0       1]
             [31     0       2]
             [53     0       3]
             [75     0       4]
             [102    0       5]]
      rings [[0      0       0]
             [25     1       0]
             [50     2       0]
             [100    3       0]
             [20     0       1]
             [40     0       2]
             [80     0       3]]]

  (defn attack [player boss]
   [(update-in boss [:hp] - (max 1 (- (player :damage) (boss :armor))))
    (update-in player [:hp] - (max 1 (- (boss :damage) (player :armor))))])

  (defn attack-til-dead [player boss]
    (loop [[b p] (attack player boss)]
      (if (>= 0 (b :hp))
        "player"
        (if (>= 0 (p :hp))
          "boss"
          (recur (attack p b))))))

  (defn calculate-cost [items] (reduce + 0 (map first items)))
  (defn calculate-damage [items] (reduce + 0 (map second items)))
  (defn calculate-armor [items] (reduce + 0 (map last items)))

  (println "Part 1:"
             (first (sort (filter #(not= 0 %)
                                  (map calculate-cost (map
                                                        (fn [[w a r1 r2]]
                                                          (let [items (vector (nth weapons w) (nth armor a) (nth rings r1) (nth rings r2))
                                                                player {:hp hp :damage (calculate-damage items) :armor (calculate-armor items)}
                                                                player-won (= "player" (attack-til-dead player boss))]
                                                            (if (not= r1 r2) (if player-won items nil))))
                                                        (comb/cartesian-product (range (count weapons)) (range (count armor)) (range (count rings)) (range (count rings)))))))))

  (println "Part 2:"
             (last (sort (filter #(not= 0 %)
                                 (map calculate-cost (map
                                                       (fn [[w a r1 r2]]
                                                         (let [items (vector (nth weapons w) (nth armor a) (nth rings r1) (nth rings r2))
                                                               player {:hp hp :damage (calculate-damage items) :armor (calculate-armor items)}
                                                               player-won (= "player" (attack-til-dead player boss))]
                                                           (if (not= r1 r2) (if (not player-won) items nil))))
                                                       (comb/cartesian-product (range (count weapons)) (range (count armor)) (range (count rings)) (range (count rings))))))))))
