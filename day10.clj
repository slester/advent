(require 'leiningen.exec)

(def input "1321131112")

(defn say [n so-far]
  (let [digits (clojure.string/split n #"")
        break (split-with #(= (first digits) %) digits)
        new-so-far (str so-far (count (clojure.string/join "" (break 0))) (first digits))
        new-n (clojure.string/join "" (break 1))]
   (if (< 0 (count new-n))
     (say new-n new-so-far)
     new-so-far)))

(println (str "Part 1: " (count (reduce (fn [output iteration] (println (str "iteration: " iteration)) (say output "")) input (range 40)))))
