package com.ip32.lab1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CalcMF {
	class ParalellMultiply implements Runnable {
		private Matrix MA, MB, result;
		private BlockingQueue<Matrix> queue;

		public ParalellMultiply(Matrix MA, Matrix MB, BlockingQueue<Matrix> queue) {
			this.MA = MA;
			this.MB = MB;
			this.queue = queue;
		}

		public void run() {
			this.result = Calculator.multiplyMatrixMatrix(MA, MB);
			this.queue.add(this.result);
		}
	}
	class ParalellAdd implements Runnable {
		private Matrix MA, MB, result;
		private BlockingQueue<Matrix> queue;

		public ParalellAdd(Matrix MA, Matrix MB, BlockingQueue<Matrix> queue) {
			this.MA = MA;
			this.MB = MB;
			this.queue = queue;
		}

		public void run() {
			this.result = Calculator.addMatrixMatrix(MA, MB);
			this.queue.add(this.result);
		}
	}
	
	public Matrix process(Matrix MB, Matrix MX, Matrix MC, Matrix MХ, Matrix MT) throws InterruptedException {
		long start = System.currentTimeMillis();
		
		BlockingQueue<Matrix> q1 = new LinkedBlockingQueue<>();
		BlockingQueue<Matrix> q2 = new LinkedBlockingQueue<>();
		new Thread(new ParalellMultiply(MB, MX, q1)).start();
		new Thread(new ParalellMultiply(MC, MX, q2)).start();
		new Thread(new ParalellMultiply(q2.take(), MB, q2)).start();
		new Thread(new ParalellAdd(q1.take(), q2.take(), q1)).start();
		Matrix m = q1.take();
		
		System.out.println((System.currentTimeMillis() - start )+ " time spent");
		return m;
	}
}
