package com.lagodiuk.ga.demo;

import com.lagodiuk.ga.Environment;
import com.lagodiuk.ga.IterartionListener;
import com.lagodiuk.ga.Population;

public class Main {

	public static void main(String[] args) {
		int populationSize = 10;
		int geneLength = 10;
		Population<IntegerArrayGene> population = createPopulation(populationSize, geneLength);

		AllZerosIntegerArrayFitness fit = new AllZerosIntegerArrayFitness(geneLength);

		Environment<IntegerArrayGene, Double> env = new Environment<IntegerArrayGene, Double>(population, fit);

		System.out.println("Target is " + fit);
		System.out.println();

		env.addIterationListener(new IterartionListener<IntegerArrayGene, Double>() {
			public void update(Environment<IntegerArrayGene, Double> environment) {
				IntegerArrayGene bestGene = environment.getBest();

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

	private static Population<IntegerArrayGene> createPopulation(int populationSize, int geneLength) {
		Population<IntegerArrayGene> population = new Population<IntegerArrayGene>();
		for (int i = 0; i < populationSize; i++) {
			IntegerArrayGene g = new IntegerArrayGene(geneLength);
			population.addGene(g);
		}
		return population;
	}
}
