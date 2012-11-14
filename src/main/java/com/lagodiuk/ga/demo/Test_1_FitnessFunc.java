package com.lagodiuk.ga.demo;

import com.lagodiuk.ga.Fitness;

public class Test_1_FitnessFunc implements Fitness<IntegerArrayGene, Double> {

	public Double calculate(IntegerArrayGene gene) {
		double delt = 0;
		int[] arr = gene.getData();
		for( int i = 0; i < arr.length; i++ ) {
			delt += arr[i];
		}
		return delt * delt;
	}
	
}
