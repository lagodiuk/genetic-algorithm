package com.lagodiuk.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Population<G extends Gene<G>> implements Iterable<G> {

	private List<G> genes = new ArrayList<G>();

	public void addGene(G gene) {
		this.genes.add(gene);
	}

	public int getSize() {
		return this.genes.size();
	}

	public G getRandomGene() {
		int numOfGenes = this.genes.size();
		// TODO improve random generator
		// maybe use pattern strategy ?
		int indx = (int) (Math.random() * numOfGenes);
		return this.genes.get(indx);
	}

	public G getGeneByIndex(int indx) {
		return this.genes.get(indx);
	}

	public void sortPopulationByFitness(Comparator<G> genesComparator) {
		Collections.shuffle(this.genes);
		Collections.sort(this.genes, genesComparator);
	}

	/**
	 * shortening population till specific number
	 */
	public void trim(int len) {
		this.genes = this.genes.subList(0, len);
	}

	public Iterator<G> iterator() {
		return this.genes.iterator();
	}

}
