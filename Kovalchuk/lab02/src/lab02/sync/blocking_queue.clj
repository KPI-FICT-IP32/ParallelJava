(ns lab02.sync.blocking-queue
  (:require [lab02.core.io :as io]
            [lab02.functions :as func])
  (:import (java.util.concurrent LinkedBlockingQueue)))

(def queue-ma (new LinkedBlockingQueue))
(def queue-vd (new LinkedBlockingQueue))
(def queue-va (new LinkedBlockingQueue))
(def queue-mg (new LinkedBlockingQueue))

(def queue-va-d (new LinkedBlockingQueue))
(def queue-mg-d (new LinkedBlockingQueue))

(def queue-output (new LinkedBlockingQueue))

(defn task-read-inputs []
  (let [a (io/read-num "in/a.in")
        mc (io/read-mtx "in/mc.in")
        md (io/read-mtx "in/md.in")
        me (io/read-mtx "in/me.in")
        mm (io/read-mtx "in/mm.in")
        mt (io/read-mtx "in/mt.in")
        mz (io/read-mtx "in/mz.in")
        vb (io/read-vec "in/vb.in")
        vc (io/read-vec "in/vc.in")
        ve (io/read-vec "in/ve.in")]
    (.put queue-ma {:md md :mt mt :mz mz :me me :mm mm})
    (.put queue-vd {:vb vb :mz mz :ve ve :mm mm :a a})
    (.put queue-va {:vb vb :mc mc :mz mz :ve ve :mm mm})
    (.put queue-mg {:vc vc :md md :mt mt :mz mz :me me})))

(defn task-calc-ma []
  (let [{md :md mt :mt mz :mz me :me mm :mm} (.take queue-ma)]
    (.put queue-output {:type "mtx"
                        :data (func/calc-ma md mt mz me mm)
                        :file "out/bq/ma.out"})))

(defn task-calc-vd []
  (let [{vb :vb mz :mz ve :ve mm :mm a :a} (.take queue-vd)
        vd (func/calc-vd vb mz ve mm a)]
    (.put queue-mg-d vd)
    (.put queue-va-d vd)
    (.put queue-output {:type "vec"
                        :data vd
                        :file "out/bq/vd.out"})))

(defn task-calc-va []
  (let [{vb :vb mc :mc mz :mz ve :ve mm :mm} (.take queue-va)
        vd (.take queue-va-d)]
    (.put queue-output {:type "vec"
                        :data (func/calc-va vb mc vd mz ve mm)
                        :file "out/bq/va.out"})))

(defn task-calc-mg []
  (let [{vc :vc md :md mt :mt mz :mz me :me} (.take queue-mg)
        vd (.take queue-mg-d)]
    (.put queue-output {:type "mtx"
                        :data (func/calc-mg vd vc md mt mz me)
                        :file "out/bq/mg.out"})))

(defn task-output []
  (dotimes [n 4]
    (let [{type :type data :data file :file} (.take queue-output)]
      (case type
        "mtx" (io/write-mtx file data)
        "vec" (io/write-vec file data)))))

(defn run []
  (let [threads (map (fn [x] (new Thread x)) [task-read-inputs
                                              task-calc-va
                                              task-calc-mg
                                              task-calc-vd
                                              task-calc-ma
                                              task-output])]
    (doseq [t threads] (.start t))
    (doseq [t threads] (.join t))))