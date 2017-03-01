package edu.kpi.pjava.lab01;

import edu.kpi.pjava.lab01.func.NormalFunctions;
import lombok.SneakyThrows;

import static edu.kpi.pjava.lab01.Generators.generateMatrix;
import static edu.kpi.pjava.lab01.Generators.generateNumber;
import static edu.kpi.pjava.lab01.Generators.generateVector;
import static edu.kpi.pjava.lab01.Writer.write;


public class Main {
    public static final int SIZE = 1000;

    public static void main(String[] args) {
        boolean isGenerator = System.getProperty("generate") != null;
        if (isGenerator) {
            generate();
        } else {
            runThreads();
        }
    }

    static void generate() {
        write(generateNumber(), "data/a.in");
        write(generateMatrix(SIZE, SIZE), "data/mc.in");
        write(generateMatrix(SIZE, SIZE), "data/md.in");
        write(generateMatrix(SIZE, SIZE), "data/me.in");
        write(generateMatrix(SIZE, SIZE), "data/mm.in");
        write(generateMatrix(SIZE, SIZE), "data/mt.in");
        write(generateMatrix(SIZE, SIZE), "data/mz.in");
        write(generateVector(SIZE), "data/vb.in");
        write(generateVector(SIZE), "data/vc.in");
        write(generateVector(SIZE), "data/vd.in");
        write(generateVector(SIZE), "data/ve.in");
    }

    @SneakyThrows(InterruptedException.class)
    static void runThreads() {
        Flow fl = new Flow(new NormalFunctions());

        Thread[] ts = new Thread[4];

        ts[0] = new Thread(fl::flow1);
        ts[1] = new Thread(fl::flow2);
        ts[2] = new Thread(fl::flow3);
        ts[3] = new Thread(fl::flow4);

        System.out.println("Ready to start");
        for (Thread t : ts) t.start();
        for (Thread t : ts) t.join();
        System.out.println("Finished");
    }

}
