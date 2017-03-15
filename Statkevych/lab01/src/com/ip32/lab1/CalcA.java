package com.ip32.lab1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CalcA {
	abstract class WithData extends Thread {
		private Data result;
		
		protected abstract Data calculate() throws InterruptedException, BrokenBarrierException;
		protected void afterCalculate() throws InterruptedException, BrokenBarrierException {};

		@Override
		public void run() {
			try {
				this.result = this.calculate();
				this.afterCalculate();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
		public Data getResult() {
			return result;
		}

	}
	
	class Thread1 extends WithData {
		private CyclicBarrier barrier;
		private WithData right;
		private Vector A;
		private Matrix MA;
		
		public Thread1(CyclicBarrier barrier, Vector A, Matrix MA, WithData right) {
			this.barrier = barrier;
			this.A = A;
			this.MA = MA;
			this.right = right;
		}
		@Override
		protected Data calculate() throws InterruptedException, BrokenBarrierException {
			Vector left = Calculator.multVectorMatrix(this.A, this.MA);
			this.barrier.await();
			return Calculator.sumVectors(left, (Vector)right.getResult());
		}
		
	}
	
	class Thread2 extends WithData {
		private CyclicBarrier barrier;
		private WithData right;
		private Vector D, C;
		private Matrix MZ;
		
		public Thread2(CyclicBarrier barrier, Vector D, Vector C, Matrix MZ) {
			this.C = C;
			this.D = D;
			this.MZ = MZ;
			this.barrier = barrier;
		}
		
		@Override
		protected Data calculate() throws InterruptedException, BrokenBarrierException {
			Vector left = Calculator.sumVectors(this.C, this.D);
			Vector result = Calculator.multVectorMatrix(left, this.MZ);
			return result;
		}
		@Override
		protected void afterCalculate() throws InterruptedException, BrokenBarrierException {
			this.barrier.await();
		}
	}

	/* A = B*MC + (D+C)*MZ */
	public Vector process(Vector B, Matrix MC, Vector D, Vector C, Matrix MZ) throws InterruptedException, BrokenBarrierException {
		long start = System.currentTimeMillis();
		CyclicBarrier barier = new CyclicBarrier(2);
		Thread2 t2 = new Thread2(barier, D, C, MZ);
		Thread1 t1 = new Thread1(barier, B, MC, t2);
		t2.start();
		t1.start();
		t1.join();
		System.out.println((System.currentTimeMillis() - start )+ " time spent");

		return (Vector)t1.getResult();
	}
}
