package com.ip32.lab1;

public class Matrix extends Data{
	public double[][] matrix;
	
	public void initMatrix(double val, int dim) {
		this.matrix = new double[dim][dim];
		for(int i=0; i<this.matrix.length; i++) {
			for(int j=0; j<this.matrix.length; j++) {
				this.matrix[i][j] = val;
			}			
		}
	}
	
}
