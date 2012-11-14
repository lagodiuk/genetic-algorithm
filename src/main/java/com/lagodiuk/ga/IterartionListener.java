package com.lagodiuk.ga;

public interface IterartionListener<G extends Gene<G>, T extends Comparable<T>> {

    void update( Environment<G, T> environment );
    
}
