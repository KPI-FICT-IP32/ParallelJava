package com.ip32.lab1;

import com.ip32.lab1.mult.ParallelMatrixMultiply;

public class Calculator {

	public static Matrix multiplyMatrixMatrix(Matrix MA, Matrix MB) {
		Matrix result = null;
		try {
			result = new ParallelMatrixMultiply(MA, MB).getResults();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Matrix addMatrixMatrix(Matrix MA, Matrix MB) {
		Matrix result = new Matrix();
		result.initMatrix(0, MA.matrix.length);
		for (int i = 0; i<MA.matrix.length; i++) {
			for(int j=0; j<MA.matrix.length; j++) {
				result.matrix[i][j] = MA.matrix[i][j] + MB.matrix[i][j]; 
			}			
		}
		return result;
	}
	
	public static Matrix multScalarMatrix(double scalar, Matrix MA) {
		Matrix result = new Matrix();
		result.initMatrix(0, MA.matrix.length);
		for (int i = 0; i<MA.matrix.length; i++) {
			for(int j=0; j<MA.matrix.length; j++) {
				result.matrix[i][j] = scalar*MA.matrix[i][j]; 
			}			
		}
		return result;
	}
	public static Vector sumVectors(Vector A, Vector B) {
		Vector result = new Vector();
		result.initVector(0, A.vector.length);
		for(int i=0; i<A.vector.length; i++) result.vector[i] = A.vector[i] + B.vector[i];
		return result;
	}
	
	public static double minVector(Vector A) {
		double a = A.vector[0];
		for (double val : A.vector) {
			a = Math.min(a, val);
		}
		return a;
	}
	
	public static Vector multVectorMatrix(Vector A, Matrix MA) {
		Vector result = new Vector();
		result.initVector(0, A.vector.length);
		
		for (int i = 0; i<A.vector.length; i++) {
			for(int j=0; j<A.vector.length; j++) {
				result.vector[i] += A.vector[j] * MA.matrix[i][j];
			}
		}
		return result;
	}
}
