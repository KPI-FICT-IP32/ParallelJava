package com.ip32.lab1.mult;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.ip32.lab1.Matrix;

public class ParallelMatrixMultiply {
	private Matrix MA, MB;
	private RWDictionary rows;
	private BlockingQueue<Task> sumQueue;
	private ExecutorService pool;
	private AtomicInteger counter;
	private int POOL_SIZE = 2;
	private volatile boolean finished = false;
	
	public ParallelMatrixMultiply(Matrix MA, Matrix MB) throws InterruptedException {
		this.MA = MA;
		this.MB = MB;

		this.init();
	}

	private Matrix collectResults() {
		Matrix MR = new Matrix();
		MR.initMatrix(0, this.MA.matrix.length);
		for(Entry<Coords, Double> entry: this.rows.map.entrySet()) {
			Coords coord = entry.getKey();
			MR.matrix[coord.getX()][coord.getY()] = entry.getValue().doubleValue();
		}
		return MR;
	}
	
	public Matrix getResults() {
		while(this.counter.get() != 0) {};
		System.out.println("finished");
		this.pool.shutdown();
		return this.collectResults();
	}

	public void init() {	
		this.rows = new RWDictionary();
		for (int i = 0; i < MA.matrix.length; i++) {			
			for (int j = 0; j < MA.matrix.length; j++) {
				this.rows.put(new Coords(i, j), 0.0);
			}
		}
		this.pool = Executors.newFixedThreadPool(this.POOL_SIZE);
		for(int i=0; i<this.POOL_SIZE / 2; i++) {
			BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
			this.pool.submit(new Summator(taskQueue));
			
			this.pool.submit(new Multiplier(taskQueue));
		}
		this.counter = new AtomicInteger((int) Math.pow(MA.matrix.length, 3));
		this.sumQueue = new LinkedBlockingQueue<>();
		for(int i=0; i<this.MA.matrix.length; i++) {
			for(int j=0; j<this.MA.matrix.length; j++) {
				for (int k=0; k<this.MA.matrix.length; k++) {
					this.sumQueue.offer(new SumTask(i, j, this.MA.matrix[i][k], this.MA.matrix[k][j]));
				}
			}
		}
	}

	class Coords implements Comparable<Coords>{
		private int cellX, cellY;
		
		public Coords(int cellX, int cellY) {
			this.cellX = cellX;
			this.cellY = cellY;
		}
		
		public int getX() {
			return this.cellX;
		}

		public int getY() {
			return this.cellY;
		}

		@Override
		public int hashCode() {
			return this.cellX * ParallelMatrixMultiply.this.MA.matrix.length + cellY;
		}

		@Override
		public int compareTo(Coords arg0) {
			return arg0.hashCode() - this.hashCode();
		}
	}
	
	class Task {
		private Coords coord;
		protected double [] operands;
		public Task() {}
		public Task(int cellX, int cellY) {
			this.coord = new Coords(cellX, cellY);
		}
		public Task(Coords coords, double opers) {
			this.coord = coords;
			this.operands = new double [] {opers};
		}
		public Task(int cellX, int cellY, double data) {
			this.coord = new Coords(cellX, cellY);
			this.operands = new double [] {data};
		}
		
		public Coords getCoord() {
			return coord;
		}
		public void setCoord(Coords coord) {
			this.coord = coord;
		}
		public double[] getOperands() {
			return operands;
		}
		public void setOperands(double[] operands) {
			this.operands = operands;
		}
	}

	class SumTask extends Task {
		public SumTask(int cellX, int cellY, double opA, double opB) {
			super(cellX, cellY);
			this.operands = new double [] {opA, opB}; 
		}
	}
	class Multiplier extends Thread {
		private BlockingQueue<Task> taskQueue;
		public Multiplier(BlockingQueue<Task> task) {
			this.taskQueue = task;
		}
		public void run() {
			while(counter.get() != 0) {
				Task t = sumQueue.poll();
				double[] oper = t.getOperands();
				Coords coords = t.getCoord();
				
				double result = oper[0] * oper[1];

				try {
					while(!taskQueue.offer(new Task(coords, result), 1, TimeUnit.MICROSECONDS)){}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class Summator extends Thread {
		private BlockingQueue<Task> taskQueue;
		public Summator(BlockingQueue<Task> task) {
			this.taskQueue = task;
		}
		public void run() {
			do {
				try {
					Task t = null;
					t = taskQueue.take();
					double result = rows.get(t.getCoord());
					rows.put(t.getCoord(), result + t.getOperands()[0]);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while(counter.decrementAndGet() != 0);		
		}
	}
	
	class RWDictionary {
	    public final Map<Coords, Double> map = new TreeMap<Coords, Double>();
	    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	    private final Lock r = rwl.readLock();
	    private final Lock w = rwl.writeLock();

	    public Double get(Coords key) {
	        r.lock();
	        try { return map.get(key); }
	        finally { r.unlock(); }
	    }
	    public Double put(Coords key, Double value) {
	        w.lock();
	        try { return map.put(key, value); }
	        finally { w.unlock(); }
	    }

	 }
}
