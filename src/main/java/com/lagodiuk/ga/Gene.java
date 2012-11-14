package com.lagodiuk.ga;

import java.util.List;

public interface Gene<G extends Gene<G>> {
	
	List<G> crossover( G anotherGene );
	
	G mutate();

}
