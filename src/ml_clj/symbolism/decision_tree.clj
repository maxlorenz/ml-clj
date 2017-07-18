(ns ml-clj.symbolism.decision-tree)

;; A decision tree in clojure can be done like
;; [:key split-fn node-or-val-then node-or-val-else]

;; Example from wikipedia

(def example-tree
  [:sex #(= % :male)
   [:age #(> % 9.5)
    :died
    [:sibsp #(> % 2.5)
     :died
     :survived]]
   :survived])

(def example-data
  {:sex   :male
   :age   9
   :sibsp 2})

(defn apply-tree [tree input]
  (if (vector? tree)
    (let [[k split-fn then else] tree]
      (if (split-fn (get input k))
        (recur then input)
        (recur else input)))
    tree))

(defn explain-tree [tree input]
  (if (vector? tree)
    (let [[k split-fn then else] tree]
      (if (split-fn (get input k))
        (str "Went left because " k ", " (explain-tree then input))
        (str "Went right because " k ", " (explain-tree else input))))
    (str "Final result: " tree)))

(apply-tree example-tree example-data) ;; => :survived
(explain-tree example-tree example-data)
;; "Went left because :sex, Went right because :age, Went right because :sibsp, Final result: :survived"

;; ---------- learning ---------
;; find split
;; apply split
;; repeat until done
