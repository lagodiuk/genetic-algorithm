package com.lagodiuk.ga;

public interface Fitness<G extends Gene<G>, T extends Comparable<T>> {

	T calculate( G gene ); 
	
}
