package com.ip32.lab1;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class CalcMA {
	abstract class Recursive  extends RecursiveTask<Data> {
		private ArrayList<Recursive> actions;
		private Data awaited, result;

		public Recursive(ArrayList<Recursive> actions) {
			this.actions = actions;
		}

		public void run() {
			for (Recursive action : this.actions) {
				action.fork();
				this.awaited = (Data) action.join();
			}
			this.result = this.process();
		}

		public Data getResult() {
			return this.result;
		}

		@Override
		protected Data compute() {
			this.run();
			return this.result;
		}

		abstract Data process();
	}
	
	class AddMatrix extends RecursiveTask<Matrix> {
		RecursiveTask<Matrix> MA, MB;
		
		public AddMatrix(RecursiveTask MA, RecursiveTask MB) {
			this.MA = MA;
			this.MB = MB;
		}

		@Override
		public Matrix compute() {
			this.MA.fork();
			this.MB.fork();
			Matrix MA = (Matrix) this.MA.join();
			Matrix MB = (Matrix) this.MB.join();

			return Calculator.addMatrixMatrix(MA, MB);
		}
	}

	class MultMatrix extends RecursiveTask<Matrix> {
		Matrix MA, MB;
		
		public MultMatrix(Matrix MA, Matrix MB) {
			this.MA = MA;
			this.MB = MB;
		}

		@Override
		public Matrix compute() {
			return Calculator.multiplyMatrixMatrix(MA, MB);
		}
	}

	class AddVector extends RecursiveTask<Vector> {
		Vector A, B;
		public AddVector(Vector A, Vector B) {
			this.A = A;
			this.B = B;
		}
		@Override
		public Vector compute() {
			return Calculator.sumVectors(A, B);
		}
	}
	
	class MultMinVectorMatrix extends RecursiveTask<Matrix> {
		private RecursiveTask<Vector> vector;
		private RecursiveTask<Matrix> MA;

		public MultMinVectorMatrix(RecursiveTask<Vector> vector, RecursiveTask<Matrix> MA) {
			this.vector = vector;
			this.MA = MA;
		}

		@Override
		protected Matrix compute() {
			this.vector.fork();
			this.MA.fork();
			Vector vector = this.vector.join();
			Matrix MA = this.MA.join();
			return Calculator.multScalarMatrix(Calculator.minVector(vector), MA);
		}
		
	}

	public Matrix process(Vector A, Vector B, Matrix MD, Matrix MT, Matrix MZ, Matrix ME) throws InterruptedException, ExecutionException {
		ForkJoinPool forkJoinPool = new ForkJoinPool(4);
		long start = System.currentTimeMillis();
		
		Matrix result = forkJoinPool.submit(new RecursiveTask<Matrix>() {

			@Override
			protected Matrix compute() {
				MultMatrix task1 = new MultMatrix(MD, MT);
				MultMatrix task2 = new MultMatrix(MZ, ME);
				AddVector task3 = new AddVector(A, B);
				MultMinVectorMatrix task4 = new MultMinVectorMatrix(task3, task1); 
				AddMatrix task5 = new AddMatrix(task4, task2);
				task5.fork();
				return task5.join();
			}
			
		}).get();
		System.out.println((System.currentTimeMillis() - start )+ " time spent");

		return result;
	}
}
