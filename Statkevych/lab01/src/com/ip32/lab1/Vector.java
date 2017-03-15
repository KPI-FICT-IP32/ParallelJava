package com.ip32.lab1;

public class Vector extends Data {
	public double[] vector;
	
	public void initVector(double val, int dim) {
		this.vector = new double[dim];
		for (int i=0; i<dim; i++) this.vector[i]=val;
	}
}
