package com.lagodiuk.ga;

public interface IterartionListener<C extends Chromosome<C>, T extends Comparable<T>> {

    void update( GeneticAlgorithm<C, T> environment );
    
}
