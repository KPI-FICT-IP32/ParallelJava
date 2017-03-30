(ns lab02.sync.executor
  (:require [lab02.core.io :as io]
            [lab02.functions :as func])
  (:import (java.util.concurrent Executors ExecutorService)))

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
    {:a  a
     :mc mc :md md :me me :mm mm :mt mt :mz mz
     :vb vb :vc vc :ve ve}))

(defn run []
  (let [pool (Executors/newFixedThreadPool 16)
        read-task (.submit pool task-read-inputs)
        task-calc-ma (.submit
                       pool
                       (cast Callable (fn []
                                        (func/calc-ma
                                          (get (.get read-task) :md)
                                          (get (.get read-task) :mt)
                                          (get (.get read-task) :mz)
                                          (get (.get read-task) :me)
                                          (get (.get read-task) :mm)))))
        task-calc-vd (.submit
                       pool
                       (cast Callable (fn []
                                        (func/calc-vd
                                          (get (.get read-task) :vb)
                                          (get (.get read-task) :mz)
                                          (get (.get read-task) :ve)
                                          (get (.get read-task) :mm)
                                          (get (.get read-task) :a)))))
        task-calc-va (.submit
                       pool
                       (cast Callable (fn []
                                        (func/calc-va
                                          (get (.get read-task) :vb)
                                          (get (.get read-task) :mc)
                                          (.get task-calc-vd)
                                          (get (.get read-task) :mz)
                                          (get (.get read-task) :ve)
                                          (get (.get read-task) :mm)
                                          ))))
        task-calc-mg (.submit
                       pool
                       (cast Callable (fn []
                                        (func/calc-mg
                                          (.get task-calc-vd)
                                          (get (.get read-task) :vc)
                                          (get (.get read-task) :md)
                                          (get (.get read-task) :mt)
                                          (get (.get read-task) :mz)
                                          (get (.get read-task) :me)))))]
    (io/write-mtx "out/exc/ma.out" (.get task-calc-ma))
    (io/write-vec "out/exc/vd.out" (.get task-calc-vd))
    (io/write-mtx "out/exc/mg.out" (.get task-calc-mg))
    (io/write-vec "out/exc/va.out" (.get task-calc-va))
    (.shutdown pool)))
