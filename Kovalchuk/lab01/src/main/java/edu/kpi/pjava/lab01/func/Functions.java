package edu.kpi.pjava.lab01.func;


public interface Functions {
    /** А=В*МС+D*MZ+E*MM; */
    double[] func1(
        double[] vb, double[][] mc,
        double[] vd, double[][] mz,
        double[] ve, double[][] mm);

    /** D=В*МZ-E*MM*a; */
    double[] func2(
        double[] vb, double[][] mz,
        double[] ve, double[][] mm, double a);

    /** MА=MD*(MT+MZ)-ME*MM */
    double[][] func3(
        double[][] md, double[][] mt, double[][] mz,
        double[][] me, double[][] mm);

    /** MG=min(D+C)*MD*MT-MZ*ME; */
    double[][] func4(
        double[] vd, double[] vc, double[][] md, double[][] mt,
        double[][] mz, double[][] me);
}
