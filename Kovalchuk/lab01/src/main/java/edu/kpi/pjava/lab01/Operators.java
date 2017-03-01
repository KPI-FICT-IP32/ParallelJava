package edu.kpi.pjava.lab01;


public class Operators {
    // return C = A + B
    public static double[][] add(double[][] left, double[][] right) {
        int rows = left.length;
        int cols = left[0].length;
        double[][] result = new double[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                result[r][c] = left[r][c] + right[r][c];
        return result;
    }

    // return C = A - B
    public static double[][] subtract(double[][] left, double[][] right) {
        int rows = left.length;
        int cols = left[0].length;
        double[][] result = new double[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                result[r][c] = left[r][c] - right[r][c];
        return result;
    }

    // return C = A * B
    public static double[][] multiply(double[][] left, double[][] right) {
        int rLeft = left.length;
        int cLeft = left[0].length;
        int rRight = right.length;
        int cRight = right[0].length;
        if (cLeft != rRight) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }
        double[][] result = new double[rLeft][cRight];
        for (int i = 0; i < rLeft; i++)
            for (int j = 0; j < cRight; j++)
                for (int k = 0; k < cLeft; k++)
                    result[i][j] += left[i][k] * right[k][j];
        return result;
    }

    // matrix-vector multiplication (y = A * x)
    public static double[] multiply(double[][] left, double[] right) {
        int rows = left.length;
        int cols = left[0].length;
        if (right.length != cols) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }
        double[] result = new double[rows];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                result[r] += left[r][c] * right[c];
        return result;
    }

    // vector-matrix multiplication (y = x^T A)
    public static double[] multiply(double[] left, double[][] right) {
        int rows = right.length;
        int cols = right[0].length;
        if (left.length != rows) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }
        double[] result = new double[cols];
        for (int c = 0; c < cols; c++)
            for (int r = 0; r < rows; r++)
                result[c] += right[r][c] * left[r];
        return result;
    }

    public static double min(double[][] matrix) {
        double min = matrix[0][0];
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix.length; c++) {
                if (matrix[r][c] < min) {
                    min = matrix[r][c];
                }
            }
        }
        return min;
    }

    public static double min(double[] vector) {
        double min = vector[0];
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] < min) {
                min = vector[i];
            }
        }
        return min;
    }

    // return C = A + B
    public static double[] add(double[] left, double[] right) {
        double[] result = new double[left.length];
        for (int i = 0; i < left.length; i++) {
            result[i] = left[i] + right[i];
        }
        return result;
    }

    // return C = A - B
    public static double[] subtract(double[] left, double[] right) {
        double[] result = new double[left.length];
        for (int i = 0; i < left.length; i++) {
            result[i] = left[i] - right[i];
        }
        return result;
    }

    public static double[][] multiply(double[][] matrix, double c) {
        double[][] result = new double[matrix.length][];
        for (int i = 0; i < result.length; i++) {
            result[i] = new double[matrix[i].length];
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = matrix[i][j] * c;
            }
        }
        return result;
    }

    public static double[][] multiply(double c, double[][] matrix) {
        return multiply(matrix, c);
    }

    public static double[] multiply(double[] vector, double c) {
        double[] result = new double[vector.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = vector[i] * c;
        }
        return result;
    }

    public static double[] multiply(double c, double[] vector) {
        return multiply(vector, c);
    }
}
