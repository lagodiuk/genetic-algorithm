package com.lagodiuk.ga.demo;

import com.lagodiuk.ga.GeneticAlgorithm;
import com.lagodiuk.ga.IterartionListener;
import com.lagodiuk.ga.Population;

public class Main {

	public static void main(String[] args) {
		int populationSize = 10;
		int geneLength = 10;
		Population<IntegerArrayChromosome> population = createPopulation(populationSize, geneLength);

		AllZerosIntegerArrayFitness fit = new AllZerosIntegerArrayFitness(geneLength);

		GeneticAlgorithm<IntegerArrayChromosome, Double> env = new GeneticAlgorithm<IntegerArrayChromosome, Double>(population, fit);

		System.out.println("Target is " + fit);
		System.out.println();

		env.addIterationListener(new IterartionListener<IntegerArrayChromosome, Double>() {
			@Override
			public void update(GeneticAlgorithm<IntegerArrayChromosome, Double> environment) {
				IntegerArrayChromosome bestChromosome = environment.getBest();

				double fitValue = environment.fitness(bestChromosome);
				System.out.println(String.format("Best chromosome: %s \t fit: %5.1f", bestChromosome, fitValue));

				if (fitValue == 0) {
					// solution found
					environment.terminate();
				}
			}
		});

		env.iterate(320);
	}

	private static Population<IntegerArrayChromosome> createPopulation(int populationSize, int chromosomeLength) {
		Population<IntegerArrayChromosome> population = new Population<IntegerArrayChromosome>();
		for (int i = 0; i < populationSize; i++) {
			IntegerArrayChromosome c = new IntegerArrayChromosome(chromosomeLength);
			population.addChromosome(c);
		}
		return population;
	}
}
