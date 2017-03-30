(ns lab02.sync.semaphore
  (:require [lab02.core.io :as io]
            [lab02.functions :as func])
  (:import (java.util.concurrent Semaphore)))

(def a (atom 0))
(def mc (atom [[]]))
(def md (atom [[]]))
(def me (atom [[]]))
(def mm (atom [[]]))
(def mt (atom [[]]))
(def mz (atom [[]]))
(def vb (atom []))
(def vc (atom []))
(def ve (atom []))

(def vd (atom []))

(def input-read (new Semaphore 4))
(def vd-computed (new Semaphore 2))
(.acquire input-read 4)
(.acquire vd-computed 2)

(defn task-read-inputs []
  (reset! a (io/read-num "in/a.in"))

  (reset! mc (io/read-mtx "in/mc.in"))
  (reset! md (io/read-mtx "in/md.in"))
  (reset! me (io/read-mtx "in/me.in"))
  (reset! mm (io/read-mtx "in/mm.in"))
  (reset! mt (io/read-mtx "in/mt.in"))
  (reset! mz (io/read-mtx "in/mz.in"))

  (reset! vb (io/read-vec "in/vb.in"))
  (reset! vc (io/read-vec "in/vc.in"))
  (reset! ve (io/read-vec "in/ve.in"))

  (.release input-read 4))

(defn task-calc-ma []
  (.acquire input-read)
  (io/write-mtx
    "out/sem/ma.out"
    (func/calc-ma @md @mt @mz @me @mm)))

(defn task-calc-vd []
  (.acquire input-read)
  (reset! vd (func/calc-vd @vb @mz @ve @mm @a))
  (.release vd-computed 2)
  (io/write-vec "out/sem/vd.out" @vd))

(defn task-calc-va []
  (.acquire input-read)
  (.acquire vd-computed)
  (io/write-vec
    "out/sem/va.out"
    (func/calc-va @vb @mc @vd @mz @ve @mm)))

(defn task-calc-mg []
  (.acquire input-read)
  (.acquire vd-computed)
  (io/write-mtx
    "out/sem/mg.out"
    (func/calc-mg @vd @vc @md @mt @mz @me)))


(defn run []
  (let [threads (map (fn [x] (new Thread x)) [task-read-inputs
                                              task-calc-va
                                              task-calc-mg
                                              task-calc-vd
                                              task-calc-ma])]
    (doseq [t threads] (.start t))
    (doseq [t threads] (.join t))))
