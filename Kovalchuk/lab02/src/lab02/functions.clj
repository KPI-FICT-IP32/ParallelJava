(ns lab02.functions
  (:require [lab02.core.op :refer :all]))

(defn calc-va [vb mc vd mz ve mm]
  "А=В*МС+D*MZ+E*MM;"
  (vv-add
    (vm-mul vb mc)
    (vv-add
      (vm-mul vd mz)
      (vm-mul ve mm))))

(defn calc-vd [vb mz ve mm a]
  "D=В*МZ-E*MM*a;"
  (vv-sub
    (vm-mul vb mz)
    (vn-mul
      (vm-mul ve mm)
      a)))

(defn calc-ma [md mt mz me mm]
  "MА=MD*(MT+MZ)-ME*MM;"
1  (mm-sub
    (mm-mul md (mm-add mt mz))
    (mm-mul me mm)))

(defn calc-mg [vd vc md mt mz me]
  "MG=min(D+C)*MD*MT-MZ*ME."
  (mm-sub
    (mm-mul
      (nm-mul (v-min (vv-add vd vc)) md)
      mt)
    (mm-mul mz me)))
