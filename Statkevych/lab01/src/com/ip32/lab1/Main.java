package com.ip32.lab1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutionException;

import com.ip32.lab1.mult.ParallelMatrixMultiply;

public class Main {
	public static void main(String[] args) throws InterruptedException, ExecutionException, BrokenBarrierException {
		Matrix MB, MX, MT, MC;
		Vector A, B, D, C;
		MC = new Matrix();
		MX = new Matrix();
		MT = new Matrix();
		MB = new Matrix();
		A = new Vector();
		B = new Vector();
		D = new Vector();
		C = new Vector();
		MC.initMatrix(1., 100);
		MX.initMatrix(1., 100);
		MT.initMatrix(1., 100);
		MB.initMatrix(1., 100);
		A.initVector(1.000001, 100);
		B.initVector(1.000001, 100);
		D.initVector(1.000001, 100);
		C.initVector(1.000001, 100);

		System.out.println((new CalcMF().process(MB, MX, MC, MX, MT)).matrix[0][0]);
		//System.out.println((new CalcMA().process(A, B, MX, MC, MX, MT)).matrix[0][0]);
		System.out.println((new CalcA().process(B, MC, D, C, MB)).vector[0]);
		System.out.println((new CalcC().process(B, MC, D, MB, 1)).vector[0]);
		
		//System.out.println(new ParallelMatrixMultiply(MC, MB).getResults().matrix[0][0] + " lel");
		System.out.println("HEIL!");
	}
}
