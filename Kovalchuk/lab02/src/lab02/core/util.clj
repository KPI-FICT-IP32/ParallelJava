(ns lab02.core.util)

(defn map-v [fn & rest] (vec (apply map fn rest)))
