package com.lagodiuk.ga;

public interface Fitness<G extends Gene<G>, T extends Comparable<T>> {

	/**
	 * Assume that gene1 is better than gene2 <br/>
	 * fit1 = calculate(gene1) <br/>
	 * fit2 = calculate(gene2) <br/>
	 * So the following condition must be true <br/>
	 * fit1.compareTo(fit2) <= 0 <br/>
	 */
	T calculate(G gene);

}
