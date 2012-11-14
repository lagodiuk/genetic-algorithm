package com.lagodiuk.ga;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Environment<G extends Gene<G>, T extends Comparable<T>> {

	private static final int ALL_PARENTAL_GENES = Integer.MAX_VALUE;

	private class GenesComparator implements Comparator<G> {
		public int compare(G o1, G o2) {
			T fit1 = Environment.this.fitnessFunc.calculate(o1);
			T fit2 = Environment.this.fitnessFunc.calculate(o2);
			int ret = fit1.compareTo(fit2);
			return ret;
		};
	}

	private final GenesComparator genesComparator;

	private final Fitness<G, T> fitnessFunc;

	private Population<G> population;

	// listeners of genetic algorithm iterations (handle callback afterwards)
	private final List<IterartionListener<G, T>> iterationListeners = new LinkedList<IterartionListener<G, T>>();

	private boolean terminate = false;

	// number of parental genes, which survive (and move to new population)
	private int parentGenesSurviveCount = ALL_PARENTAL_GENES;

	private int iteration = 0;

	public Environment(Population<G> population, Fitness<G, T> fitnessFunc) {
		this.population = population;
		this.fitnessFunc = fitnessFunc;
		this.genesComparator = new GenesComparator();
	}

	public void iterate() {
		int parentPopulationSize = this.population.getSize();

		Population<G> newPopulation = new Population<G>();

		for (int i = 0; (i < parentPopulationSize) && (i < this.parentGenesSurviveCount); i++) {
			newPopulation.addGene(this.population.getGeneByIndex(i));
		}

		for (int i = 0; i < parentPopulationSize; i++) {
			G gene = this.population.getGeneByIndex(i);
			G mutated = gene.mutate();

			G otherGene = this.population.getRandomGene();
			List<G> crossovered = gene.crossover(otherGene);

			newPopulation.addGene(mutated);
			for (G g : crossovered) {
				newPopulation.addGene(g);
			}
		}

		newPopulation.sortPopulationByFitness(this.genesComparator);
		newPopulation.trim(parentPopulationSize);
		this.population = newPopulation;
	}

	public void iterate(int count) {
		this.terminate = false;

		for (int i = 0; i < count; i++) {
			if (this.terminate) {
				break;
			}
			this.iterate();
			this.iteration = i;
			for (IterartionListener<G, T> l : this.iterationListeners) {
				l.update(this);
			}
		}
	}

	public int getIteration() {
		return this.iteration;
	}

	public void terminate() {
		this.terminate = true;
	}

	public Population<G> getPopulation() {
		return this.population;
	}

	public G getBest() {
		return this.population.getGeneByIndex(0);
	}

	public G getWorst() {
		return this.population.getGeneByIndex(this.population.getSize() - 1);
	}

	public void setParentGenesSurviveCount(int parentGenesCount) {
		this.parentGenesSurviveCount = parentGenesCount;
	}

	public int getParentGenesSurviveCount() {
		return this.parentGenesSurviveCount;
	}

	public void addIterationListener(IterartionListener<G, T> listener) {
		this.iterationListeners.add(listener);
	}

	public T fitness(G gene) {
		return this.fitnessFunc.calculate(gene);
	}

}
