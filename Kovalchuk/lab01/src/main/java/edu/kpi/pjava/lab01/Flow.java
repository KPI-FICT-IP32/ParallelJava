package edu.kpi.pjava.lab01;


import edu.kpi.pjava.lab01.func.Functions;

import static edu.kpi.pjava.lab01.Reader.*;
import static edu.kpi.pjava.lab01.Writer.write;

public class Flow {
    private final Functions funcs;

    public Flow(Functions funcs) {
        this.funcs = funcs;
    }

    public void flow1() {
        System.out.printf("%d [Flow 1] started\n", System.currentTimeMillis());
        double[] vb = readVector("data/vb.in");
        double[][] mc = readMatrix("data/mc.in");
        double[] vd = readVector("data/vd.in");
        double[][] mz = readMatrix("data/mz.in");
        double[] ve = readVector("data/ve.in");
        double[][] mm = readMatrix("data/mm.in");

        double[] va = funcs.func1(vb, mc, vd, mz, ve, mm);
        write(va, "out/va.out");
        System.out.printf("%d [Flow 1] finished\n", System.currentTimeMillis());
    }

    public void flow2() {
        System.out.printf("%d [Flow 2] started\n", System.currentTimeMillis());
        double[] vb = readVector("data/vb.in");
        double[][] mz = readMatrix("data/mz.in");
        double[] ve = readVector("data/ve.in");
        double[][] mm = readMatrix("data/mm.in");
        double a = readNumber("data/a.in");

        double[] vd = funcs.func2(vb, mz, ve, mm, a);
        write(vd, "out/vd.out");
        System.out.printf("%d [Flow 2] finished\n", System.currentTimeMillis());
    }

    public void flow3() {
        System.out.printf("%d [Flow 3] started\n", System.currentTimeMillis());
        double[][] md = readMatrix("data/md.in");
        double[][] mt = readMatrix("data/mt.in");
        double[][] mz = readMatrix("data/mz.in");
        double[][] me = readMatrix("data/me.in");
        double[][] mm = readMatrix("data/mm.in");

        double[][] ma = funcs.func3(md, mt, mz, me, mm);
        write(ma, "out/ma.out");
        System.out.printf("%d [Flow 3] finished\n", System.currentTimeMillis());
    }

    public void flow4() {
        System.out.printf("%d [Flow 4] started\n", System.currentTimeMillis());
        double[] vd = readVector("data/vd.in");
        double[] vc = readVector("data/vc.in");
        double[][] md = readMatrix("data/md.in");
        double[][] mt = readMatrix("data/mt.in");
        double[][] mz = readMatrix("data/mz.in");
        double[][] me = readMatrix("data/me.in");

        double[][] mg = funcs.func4(vd, vc, md, mt, mz, me);
        write(mg, "out/mg.out");
        System.out.printf("%d [Flow 4] finished\n", System.currentTimeMillis());
    }
}
