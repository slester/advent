(require 'leiningen.exec)

(def input "Faerun to Norrath = 129;Faerun to Tristram = 58;Faerun to AlphaCentauri = 13;Faerun to Arbre = 24;Faerun to Snowdin = 60;Faerun to Tambi = 71;Faerun to Straylight = 67;Norrath to Tristram = 142;Norrath to AlphaCentauri = 15;Norrath to Arbre = 135;Norrath to Snowdin = 75;Norrath to Tambi = 82;Norrath to Straylight = 54;Tristram to AlphaCentauri = 118;Tristram to Arbre = 122;Tristram to Snowdin = 103;Tristram to Tambi = 49;Tristram to Straylight = 97;AlphaCentauri to Arbre = 116;AlphaCentauri to Snowdin = 12;AlphaCentauri to Tambi = 18;AlphaCentauri to Straylight = 91;Arbre to Snowdin = 129;Arbre to Tambi = 53;Arbre to Straylight = 40;Snowdin to Tambi = 15;Snowdin to Straylight = 99;Tambi to Straylight = 70")

(def locations (map (fn [line] (re-find #"(\w+) to (\w+) = (\d+)" line)) (clojure.string/split input #";")))

(defn build-map [locations]
  (apply (partial merge-with (fnil merge {}) {})
                                       ;; A to B
         (map (fn [location] (hash-map (keyword (location 1))
                                       (hash-map (keyword (location 2)) (read-string (location 3)))
                                       ;; B to A
                                       (keyword (location 2))
                                       (hash-map (keyword (location 1)) (read-string (location 3))))) locations)))

(defn list-cities [location-map] (keys location-map))
(def location-map (build-map locations))
(def cities (list-cities location-map))

(defn find-route [location-map already-visited start-city distance f]
  (println (str "currently in:" start-city))
  (println (str "already visited: " already-visited))
  (println (str "distance: " distance))
  (if (< (count already-visited) (count location-map))
    (let [city-connections (apply dissoc (location-map start-city) already-visited)
          need-visited (filter (fn [city] (not (some #{city} already-visited))) cities)
          _ (println (str "need visited: " (vector need-visited)))
          next-cities (apply merge (map (fn [city] (do (println (str "trying ... " city)) (hash-map city (find-route location-map (conj already-visited city) city (+ distance (city-connections city)) f)))) need-visited))
          _ (println (str "next cities: " (vector next-cities)))
          next-city (key (apply f val next-cities))
          leg-distance (city-connections next-city)]
      (find-route location-map (conj already-visited start-city) next-city (+ distance leg-distance) f))
    distance))

(println (str "Part 1: " (apply min (map (fn [city] (find-route location-map (list city) city 0 min-key)) cities))))
(println (str "Part 2: " (apply max (map (fn [city] (find-route location-map (list city) city 0 max-key)) cities))))
