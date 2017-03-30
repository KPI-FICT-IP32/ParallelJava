(ns lab02.core.op
  (:require [lab02.core.util :refer :all]))

(defn dot [l r]
  (let [ps (map * l r)]
    (reduce + (sort ps))))

(defn transpose [m] (apply map-v vector m))

(defn mm-mul [l r]
  (let [tr (transpose r)]
    (map-v (fn [x] (map-v (fn [y] (dot x y)) tr)) l)))

(defn vm-mul [l r]
  (let [tr (transpose r)]
    (map-v (fn [x] (dot l x)) tr)))

(defn mv-mul [l r]
  (map-v (fn [x] (dot x r)) l))

(defn vn-mul [v n]
  (map-v (fn [x] (* n x)) v))

(defn nm-mul [n m]
  (map-v (fn [v] (map-v (fn [x] (* n x)) v)) m))

(defn vv-add [l r] (map-v + l r))

(defn vv-sub [l r] (map-v - l r))

(defn mm-add [l r] (map-v vv-add l r))

(defn mm-sub [l r] (map-v vv-sub l r))

(defn v-min [v]
  (reduce (fn [acc x] (if (< acc x) acc x)) v))
