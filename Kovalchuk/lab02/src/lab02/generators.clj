(ns lab02.generators
  (:require [lab02.core.io :as io]
            [lab02.core.util :refer :all]))

(defn gen-num []
  (- (rand 100000) 50000))

(defn gen-vec [size]
  (map-v (fn [_] (gen-num)) (range size)))

(defn gen-mtx [size]
  (map-v (fn [_] (gen-vec size)) (range size)))


(defn run []
  (prn "generators/run")
  (let [size 250]
    (doseq [f ["in/mc.in" "in/me.in" "in/md.in"
               "in/mm.in" "in/mt.in" "in/mz.in"]]
      (io/write-mtx f (gen-mtx size)))
    (doseq [f ["in/vb.in" "in/vc.in" "in/ve.in"]]
      (io/write-vec f (gen-vec size)))
    (doseq [f ["in/a.in"]]
         (io/write-num f (gen-num)))))
