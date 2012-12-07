package com.lagodiuk.ga;

public interface Fitness<C extends Chromosome<C>, T extends Comparable<T>> {

	/**
	 * Assume that chromosome1 is better than chromosome2 <br/>
	 * fit1 = calculate(chromosome1) <br/>
	 * fit2 = calculate(chromosome2) <br/>
	 * So the following condition must be true <br/>
	 * fit1.compareTo(fit2) <= 0 <br/>
	 */
	T calculate(C chromosome);

}
