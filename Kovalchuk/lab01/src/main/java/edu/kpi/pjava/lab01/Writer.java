package edu.kpi.pjava.lab01;

import com.google.common.io.Files;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.google.common.base.Charsets.UTF_8;


public class Writer {
    public static void write(double[][] data, String filepath) {
        String contents = Arrays.stream(data)
            .map(row -> Arrays.stream(row)
                .mapToObj(Double::toString)
                .collect(Collectors.joining(" ")))
            .collect(Collectors.joining("\n"));
        _doWrite(contents, filepath);
    }

    public static void write(double[] data, String filepath) {
        String contents = Arrays.stream(data)
            .mapToObj(Double::toString)
            .collect(Collectors.joining(" "));
        _doWrite(contents, filepath);
    }

    public static void write(double data, String filepath) {
        String contents = String.valueOf(data);
        _doWrite(contents, filepath);
    }

    @SneakyThrows(IOException.class)
    private static void _doWrite(String contents, String filepath) {
        Files.write(contents, new File(filepath), UTF_8);
    }
}
