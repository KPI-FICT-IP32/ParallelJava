(ns lab02.core.io
  (:require [lab02.core.util :refer :all]
            [clojure.string :as str]))

(defn str->num [s] (Double/parseDouble s))
(defn str->vec [s] (map-v str->num (str/split s #"\s+")))
(defn str->mtx [s] (map-v str->vec (str/split s #"\n")))

(defn read-num [f] (str->num (slurp f)))
(defn read-vec [f] (str->vec (slurp f)))
(defn read-mtx [f] (str->mtx (slurp f)))

(defn num->str [n] (String/valueOf n))
(defn vec->str [v] (str/join " " (map num->str v)))
(defn mtx->str [m] (str/join "\n" (map vec->str m)))

(defn write-num [f n] (spit f (num->str n)))
(defn write-vec [f v] (spit f (vec->str v)))
(defn write-mtx [f m] (spit f (mtx->str m)))
