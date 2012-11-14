package com.lagodiuk.ga.demo;

import com.lagodiuk.ga.Fitness;

public class TestFitnessFunc implements Fitness<IntegerArrayGene, Double> {

	public Double calculate(IntegerArrayGene gene) {
		double delt = 0;
		int[] arr = gene.getData();
		for( int i = 0; i < arr.length; i++ ) {
			delt += ( i*2 + 50 - arr[i] ) * ( i*2 + 50 - arr[i] );
		}
		return delt;
	}
	
}
