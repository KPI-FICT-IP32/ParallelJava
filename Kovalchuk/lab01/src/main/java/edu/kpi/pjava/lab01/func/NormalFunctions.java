package edu.kpi.pjava.lab01.func;

import static edu.kpi.pjava.lab01.Operators.*;

public class NormalFunctions implements Functions {
    public double[] func1(
        double[] vb, double[][] mc,
        double[] vd, double[][] mz,
        double[] ve, double[][] mm) {
        return add(
            add(multiply(vb, mc),
                multiply(vd, mz)),
            multiply(ve, mm));
    }

    public double[] func2(
        double[] vb, double[][] mz,
        double[] ve, double[][] mm, double a) {
        return subtract(multiply(vb, mz),
                        multiply(multiply(ve, mm), a));
    }

    public double[][] func3(
        double[][] md, double[][] mt, double[][] mz,
        double[][] me, double[][] mm) {
        return subtract(
            multiply(md, add(mt, mz)),
            multiply(me, mm));
    }

    public double[][] func4(
        double[] vd, double[] vc, double[][] md, double[][] mt,
        double[][] mz, double[][] me) {
        return subtract(
            multiply(
                multiply(min(add(vd, vc)), md),
                mt),
            multiply(mz, me));
    }
}
