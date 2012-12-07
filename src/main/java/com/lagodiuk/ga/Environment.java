package com.lagodiuk.ga;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Environment<C extends Chromosome<C>, T extends Comparable<T>> {

	private static final int ALL_PARENTAL_CHROMOSOMES = Integer.MAX_VALUE;

	private class ChromosomesComparator implements Comparator<C> {
		@Override
		public int compare(C o1, C o2) {
			T fit1 = Environment.this.fitnessFunc.calculate(o1);
			T fit2 = Environment.this.fitnessFunc.calculate(o2);
			int ret = fit1.compareTo(fit2);
			return ret;
		};
	}

	private final ChromosomesComparator chromosomesComparator;

	private final Fitness<C, T> fitnessFunc;

	private Population<C> population;

	// listeners of genetic algorithm iterations (handle callback afterwards)
	private final List<IterartionListener<C, T>> iterationListeners = new LinkedList<IterartionListener<C, T>>();

	private boolean terminate = false;

	// number of parental chromosomes, which survive (and move to new
	// population)
	private int parentChromosomesSurviveCount = ALL_PARENTAL_CHROMOSOMES;

	private int iteration = 0;

	public Environment(Population<C> population, Fitness<C, T> fitnessFunc) {
		this.population = population;
		this.fitnessFunc = fitnessFunc;
		this.chromosomesComparator = new ChromosomesComparator();
	}

	public void iterate() {
		int parentPopulationSize = this.population.getSize();

		Population<C> newPopulation = new Population<C>();

		for (int i = 0; (i < parentPopulationSize) && (i < this.parentChromosomesSurviveCount); i++) {
			newPopulation.addChromosome(this.population.getChromosomeByIndex(i));
		}

		for (int i = 0; i < parentPopulationSize; i++) {
			C chromosome = this.population.getChromosomeByIndex(i);
			C mutated = chromosome.mutate();

			C otherChromosome = this.population.getRandomChromosome();
			List<C> crossovered = chromosome.crossover(otherChromosome);

			newPopulation.addChromosome(mutated);
			for (C c : crossovered) {
				newPopulation.addChromosome(c);
			}
		}

		newPopulation.sortPopulationByFitness(this.chromosomesComparator);
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
			for (IterartionListener<C, T> l : this.iterationListeners) {
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

	public Population<C> getPopulation() {
		return this.population;
	}

	public C getBest() {
		return this.population.getChromosomeByIndex(0);
	}

	public C getWorst() {
		return this.population.getChromosomeByIndex(this.population.getSize() - 1);
	}

	public void setParentChromosomesSurviveCount(int parentChromosomesCount) {
		this.parentChromosomesSurviveCount = parentChromosomesCount;
	}

	public int getParentChromosomesSurviveCount() {
		return this.parentChromosomesSurviveCount;
	}

	public void addIterationListener(IterartionListener<C, T> listener) {
		this.iterationListeners.add(listener);
	}

	public T fitness(C chromosome) {
		return this.fitnessFunc.calculate(chromosome);
	}

}
