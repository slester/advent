(require 'leiningen.exec)

(def input "vzbxkghb")
(def abcs (remove #{\i \o \l} (map char (range (int \a) (inc (int \z))))))

(defn next-letter [letter]
  (let [n (inc (count (take-while (partial not= letter) abcs)))]
    (if (>= n (count abcs)) \a (nth abcs n))))

(defn inc-password [password]
  ;; Incrementing is just like counting with numbers: xx, xy, xz, ya, yb, and so on.
  ;; Increase the rightmost letter one step; if it was z, it wraps around to a,
  ;; and repeat with the next letter to the left until one doesn't wrap around.
  (let [pass (reverse password)]
    (loop [pos 0
           cl (next-letter (nth pass 0))
           incpass (list cl)]
      (if (= cl \a)
        (let [newpos (inc pos)
              newcl (next-letter (nth pass newpos))
              newincpass (conj incpass newcl)]
          (recur newpos newcl newincpass))
        (apply conj incpass (drop (inc pos) pass))))))

(defn next-password [password]
  ;; Passwords must include one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to xyz. They cannot skip letters; abd doesn't count.
  ;; Passwords may not contain the letters i, o, or l, as these letters can be mistaken for other characters and are therefore confusing.
  ;; Passwords must contain at least two different, non-overlapping pairs of letters, like aa, bb, or zz.
  (loop [n (inc-password password)]
    (let [strn (clojure.string/join "" n)]
      (if (or
            (nil? (re-matches #".*(abc|bcd|cde|def|pqr|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz).*" strn))
            (nil? (re-matches #"^.*(\w)\1.*((?!\1).)\2.*$" strn)))
        (recur (inc-password n))
        strn))))

(def part1 (next-password input))
(def part2 (next-password part1))
(println (str "Part 1: " part1))
(println (str "Part 2: " part2))
