package edu.kpi.pjava.lab01;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Charsets.UTF_8;


public class Reader {
    public static double[][] readMatrix(String filepath) {
        String content = _doRead(filepath);

        final List<double[]> dynamicMatrix = Lists.newArrayList();
        Arrays.stream(content.split("\n"))
            .forEach(line -> {
                dynamicMatrix.add(
                    Arrays.stream(line.split("\\s+"))
                        .mapToDouble(Double::parseDouble)
                        .toArray()
                );
            });
        return dynamicMatrix.stream().toArray(double[][]::new);
    }

    public static double[] readVector(String filepath) {
        String content = _doRead(filepath);
        return Arrays.stream(content.split("\\s+"))
            .mapToDouble(Double::parseDouble)
            .toArray();
    }

    public static double readNumber(String filepath) {
        String content = _doRead(filepath);
        return Double.parseDouble(content);
    }

    @SneakyThrows(IOException.class)
    private static String _doRead(String filepath) {
        return Files.toString(new File(filepath), UTF_8);
    }

}
