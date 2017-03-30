(ns lab02.sync.futures
  (:require [lab02.core.io :as io]
            [lab02.functions :as func]))

(defn read-inputs []
  {:a  (future (io/read-num "in/a.in"))
   :mc (future (io/read-mtx "in/mc.in"))
   :md (future (io/read-mtx "in/md.in"))
   :me (future (io/read-mtx "in/me.in"))
   :mm (future (io/read-mtx "in/mm.in"))
   :mt (future (io/read-mtx "in/mt.in"))
   :mz (future (io/read-mtx "in/mz.in"))
   :vb (future (io/read-vec "in/vb.in"))
   :vc (future (io/read-vec "in/vc.in"))
   :ve (future (io/read-vec "in/ve.in"))})

(defn run-calculations [{a :a mc :mc md :md me :me mm :mm
                         mt :mt mz :mz vb :vb vc :vc ve :ve}]
  (let [vd (future (func/calc-vd @vb @mz @ve @mm @a))]
    {:vd vd
     :ma (future (func/calc-ma @md @mt @mz @me @mm))
     :va (future (func/calc-va @vb @mc @vd @mz @ve @mm))
     :mg (future (func/calc-mg @vd @vc @md @mt @mz @me))}))

(defn output [type fut file]
  (case type
    "mtx" (io/write-mtx file @fut)
    "vec" (io/write-vec file @fut)))

(defn run []
  (let [inputs (read-inputs)
        results (run-calculations inputs)]
    (output "vec" (get results :vd) "out/fut/vd.out")
    (output "mtx" (get results :ma) "out/fut/ma.out")
    (output "vec" (get results :va) "out/fut/va.out")
    (output "mtx" (get results :mg) "out/fut/mg.out")))
