package edu.kpi.pjava.lab01;


import edu.kpi.pjava.lab01.func.Functions;

import static edu.kpi.pjava.lab01.Reader.readMatrix;
import static edu.kpi.pjava.lab01.Reader.readNumber;
import static edu.kpi.pjava.lab01.Reader.readVector;
import static edu.kpi.pjava.lab01.Writer.write;

public class Flow {
    private final Functions funcs;

    public Flow(Functions funcs) {
        this.funcs = funcs;
    }

    public void flow1() {
        double[] vb = readVector("data/vb.in");
        double[][] mc = readMatrix("data/mc.in");
        double[] vd = readVector("data/vd.in");
        double[][] mz = readMatrix("data/mz.in");
        double[] ve = readVector("data/ve.in");
        double[][] mm = readMatrix("data/mm.in");

        double[] va = funcs.func1(vb, mc, vd, mz, ve, mm);
        write(va, "out/va.out");
    }

    public void flow2() {
        double[] vb = readVector("data/vb.in");
        double[][] mz = readMatrix("data/mz.in");
        double[] ve = readVector("data/ve.in");
        double[][] mm = readMatrix("data/mm.in");
        double a = readNumber("data/a.in");

        double[] vd = funcs.func2(vb, mz, ve, mm, a);
        write(vd, "out/vd.out");
    }

    public void flow3() {
        double[][] md = readMatrix("data/md.in");
        double[][] mt = readMatrix("data/mt.in");
        double[][] mz = readMatrix("data/mz.in");
        double[][] me = readMatrix("data/me.in");
        double[][] mm = readMatrix("data/mm.in");

        double[][] ma = funcs.func3(md, mt, mz, me, mm);
        write(ma, "out/ma.out");
    }

    public void flow4() {
        double[] vd = readVector("data/vd.in");
        double[] vc = readVector("data/vc.in");
        double[][] md = readMatrix("data/md.in");
        double[][] mt = readMatrix("data/mt.in");
        double[][] mz = readMatrix("data/mz.in");
        double[][] me = readMatrix("data/me.in");

        double[][] mg = funcs.func4(vd, vc, md, mt, mz, me);
        write(mg, "out/mg.out");
    }
}
