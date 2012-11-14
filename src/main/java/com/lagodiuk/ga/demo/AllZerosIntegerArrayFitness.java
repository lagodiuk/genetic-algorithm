package com.lagodiuk.ga.demo;

import java.util.Arrays;

import com.lagodiuk.ga.Fitness;

public class AllZerosIntegerArrayFitness implements Fitness<IntegerArrayGene, Double> {

	private final int[] target;

	public AllZerosIntegerArrayFitness(int geneLength) {
		this.target = new int[geneLength];

		for (int i = 0; i < geneLength; i++) {
			this.target[i] = 0;
		}
	}

	public Double calculate(IntegerArrayGene gene) {
		double delt = 0;
		int[] arr = gene.getData();
		for (int i = 0; i < arr.length; i++) {
			delt += Math.pow(this.target[i] - arr[i], 2);
		}
		return delt;
	}

	@Override
	public String toString() {
		return Arrays.toString(this.target);
	}

}
