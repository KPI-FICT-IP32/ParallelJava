(ns lab02.sync.delays
  (:require [lab02.core.io :as io]
            [lab02.functions :as func]))

(defn run []
  (let [a (delay (io/read-num "in/a.in"))
        mc (delay (io/read-mtx "in/mc.in"))
        md (delay (io/read-mtx "in/md.in"))
        me (delay (io/read-mtx "in/me.in"))
        mm (delay (io/read-mtx "in/mm.in"))
        mt (delay (io/read-mtx "in/mt.in"))
        mz (delay (io/read-mtx "in/mz.in"))
        vb (delay (io/read-vec "in/vb.in"))
        vc (delay (io/read-vec "in/vc.in"))
        ve (delay (io/read-vec "in/ve.in"))]
    (io/write-mtx
      "out/del/ma.out"
      (deref (delay (func/calc-ma @md @mt @mz @me @mm))))
    (let [vd (delay (func/calc-vd @vb @mz @ve @mm @a))]
      (io/write-vec "out/del/vd.out" @vd)
      (io/write-vec
        "out/del/va.out"
        (deref (delay (func/calc-va @vb @mc @vd @mz @ve @mm))))
      (io/write-mtx
        "out/del/mg.out"
        (deref (delay (func/calc-mg @vd @vc @md @mt @mz @me)))))))
