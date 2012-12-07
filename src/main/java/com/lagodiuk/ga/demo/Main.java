package com.lagodiuk.ga.demo;

import com.lagodiuk.ga.Environment;
import com.lagodiuk.ga.IterartionListener;
import com.lagodiuk.ga.Population;

public class Main {

	public static void main(String[] args) {
		int populationSize = 10;
		int geneLength = 10;
		Population<IntegerArrayChromosome> population = createPopulation(populationSize, geneLength);

		AllZerosIntegerArrayFitness fit = new AllZerosIntegerArrayFitness(geneLength);

		Environment<IntegerArrayChromosome, Double> env = new Environment<IntegerArrayChromosome, Double>(population, fit);

		System.out.println("Target is " + fit);
		System.out.println();

		env.addIterationListener(new IterartionListener<IntegerArrayChromosome, Double>() {
			public void update(Environment<IntegerArrayChromosome, Double> environment) {
				IntegerArrayChromosome bestGene = environment.getBest();

				double fitValue = environment.fitness(bestGene);
				System.out.println(String.format("Best gene: %s \t fit: %5.1f", bestGene, fitValue));

				if (fitValue == 0) {
					// solution found
					environment.terminate();
				}
			}
		});

		env.iterate(320);
	}

	private static Population<IntegerArrayChromosome> createPopulation(int populationSize, int geneLength) {
		Population<IntegerArrayChromosome> population = new Population<IntegerArrayChromosome>();
		for (int i = 0; i < populationSize; i++) {
			IntegerArrayChromosome g = new IntegerArrayChromosome(geneLength);
			population.addChromosome(g);
		}
		return population;
	}
}
