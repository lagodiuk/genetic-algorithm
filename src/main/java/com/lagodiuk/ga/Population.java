package com.lagodiuk.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Population<C extends Chromosome<C>> implements Iterable<C> {

	private List<C> chromosomes = new ArrayList<C>();

	public void addChromosome(C chromosome) {
		this.chromosomes.add(chromosome);
	}

	public int getSize() {
		return this.chromosomes.size();
	}

	public C getRandomChromosome() {
		int numOfChromosomes = this.chromosomes.size();
		// TODO improve random generator
		// maybe use pattern strategy ?
		int indx = (int) (Math.random() * numOfChromosomes);
		return this.chromosomes.get(indx);
	}

	public C getChromosomeByIndex(int indx) {
		return this.chromosomes.get(indx);
	}

	public void sortPopulationByFitness(Comparator<C> chromosomesComparator) {
		Collections.shuffle(this.chromosomes);
		Collections.sort(this.chromosomes, chromosomesComparator);
	}

	/**
	 * shortening population till specific number
	 */
	public void trim(int len) {
		this.chromosomes = this.chromosomes.subList(0, len);
	}

	@Override
	public Iterator<C> iterator() {
		return this.chromosomes.iterator();
	}

}
