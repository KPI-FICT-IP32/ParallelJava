package edu.kpi.pjava.lab01;

import java.util.Random;


public class Generators {
    public static double[][] generateMatrix(int rs, int cs) {
        Random rand = new Random();
        double[][] res = new double[rs][cs];
        for (int i = 0; i < rs; i++) {
            for (int j = 0; j < cs; j++) {
                res[i][j] = generateNumber();
            }
        }
        return res;
    }

    public static double[] generateVector(int sz) {
        Random rand = new Random();
        double[] res = new double[sz];
        for (int i = 0; i < sz; i++) {
            res[i] = generateNumber();
        }
        return res;
    }

    public static double generateNumber() {
        Random rand = new Random();
        return rand.nextDouble() + (rand.nextInt(10) - 5);
    }


}
