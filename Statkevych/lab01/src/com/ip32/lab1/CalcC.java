package com.ip32.lab1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CalcC {
	//   C=В*МT+D*MX*a
	public Vector process(Vector B, Matrix MT, Vector D, Matrix MX, double a) throws InterruptedException, ExecutionException {
		ExecutorService pool = Executors.newFixedThreadPool(3);
		long start = System.currentTimeMillis();
		
		Future<Data> left = pool.submit(new Callable<Data>() {
			@Override
			public Data call() throws Exception {
				return Calculator.multVectorMatrix(B, MT);
			}
		});
		Future<Data> right = pool.submit(new Callable<Data>() {

			@Override
			public Data call() throws Exception {
				return Calculator.multVectorMatrix(D, Calculator.multScalarMatrix(a, MX));
			}
		});
		Future<Data> result = pool.submit(new Callable<Data>() {

			@Override
			public Data call() throws Exception {
				return Calculator.sumVectors((Vector)left.get(), (Vector)right.get());
			}
		});
		System.out.println((System.currentTimeMillis() - start )+ " time spent");

		return (Vector) result.get();
	}
}
