package com.lagodiuk.ga;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class ArrayChromosomeTest {

	@Test
	public void test1() {
		Population<ArrayChromosome> population = this.createPopulation();

		// create instance of fitness function calculator
		ConsecutiveNumbersFitness fitness = new ConsecutiveNumbersFitness();

		// genetic-algorithm environment
		GeneticAlgorithm<ArrayChromosome, Integer> environment = new GeneticAlgorithm<ArrayChromosomeTest.ArrayChromosome, Integer>(population, fitness);

		environment.iterate(10);
		// and get best chromosome after 10 iterations
		ArrayChromosome firstBestChromosome = environment.getBest();
		// calculate fitness
		Integer firstFitness = fitness.calculate(firstBestChromosome);

		environment.iterate(10);
		ArrayChromosome secondBestChromosome = environment.getBest();
		Integer secondFitness = fitness.calculate(secondBestChromosome);

		environment.iterate(80);
		ArrayChromosome thirdBestChromosome = environment.getBest();
		Integer thirdFitness = fitness.calculate(thirdBestChromosome);

		// second chromosome is better than first
		assertTrue(secondFitness <= firstFitness);
		// third chromosome is better than first
		assertTrue(thirdFitness <= firstFitness);
		// third chromosome is better than second
		assertTrue(thirdFitness <= secondFitness);
	}

	private Population<ArrayChromosome> createPopulation() {
		int populationSize = 10;
		// create initial population of array-chromosomes
		Population<ArrayChromosome> population = new Population<ArrayChromosomeTest.ArrayChromosome>();
		for (int i = 0; i < populationSize; i++) {
			population.addChromosome(new ArrayChromosome());
		}
		return population;
	}

	@Test
	public void test2() {
		Population<ArrayChromosome> population = this.createPopulation();

		// create instance of fitness function calculator
		ConsecutiveNumbersFitness fitness = new ConsecutiveNumbersFitness();

		// genetic-algorithm environment
		GeneticAlgorithm<ArrayChromosome, Integer> environment = new GeneticAlgorithm<ArrayChromosomeTest.ArrayChromosome, Integer>(population, fitness);

		environment.addIterationListener(new IterartionListener<ArrayChromosomeTest.ArrayChromosome, Integer>() {
			private ArrayChromosome previousBestChromosome = null;
			private Integer previousBestChromosomeFitness = null;

			@Override
			public void update(GeneticAlgorithm<ArrayChromosome, Integer> environment) {
				ArrayChromosome currentBestChromosome = environment.getBest();
				Integer currentBestChromosomeFitness = environment.fitness(currentBestChromosome);

				if (this.previousBestChromosome != null) {
					// after each iteration - best chromosome of population must
					// not be worse than previous population best chromosome
					assertTrue(currentBestChromosomeFitness.compareTo(this.previousBestChromosomeFitness) <= 0);
				}

				this.previousBestChromosome = currentBestChromosome;
				this.previousBestChromosomeFitness = currentBestChromosomeFitness;

				environment.clearCache();
			}
		});

		environment.iterate(100);
	}

	private static final int ARR_LEN = 10;

	private static class ArrayChromosome implements Chromosome<ArrayChromosome> {

		private final int[] array = new int[ARR_LEN];

		private final Random rand = new Random();

		public ArrayChromosome() {
			for (int i = 0; i < ARR_LEN; i++) {
				this.array[i] = this.rand.nextInt(10) - this.rand.nextInt(10);
			}
		}

		@Override
		public List<ArrayChromosome> crossover(ArrayChromosome anotherChromosome) {
			List<ArrayChromosome> childs = new LinkedList<ArrayChromosomeTest.ArrayChromosome>();
			ArrayChromosome child1 = new ArrayChromosome();
			ArrayChromosome child2 = new ArrayChromosome();
			for (int i = 0; i < ARR_LEN; i++) {
				if (this.rand.nextBoolean()) {
					child1.array[i] = this.array[i];
					child2.array[i] = anotherChromosome.array[i];
				} else {
					child1.array[i] = anotherChromosome.array[i];
					child2.array[i] = this.array[i];
				}
			}
			childs.add(child1);
			childs.add(child2);
			return childs;
		}

		@Override
		public ArrayChromosome mutate() {
			ArrayChromosome mutated = new ArrayChromosome();
			for (int i = 0; i < ARR_LEN; i++) {
				mutated.array[i] = this.array[i];
				if (this.rand.nextBoolean()) {
					mutated.array[i] += this.rand.nextInt(4) - this.rand.nextInt(4);
				}
			}
			return mutated;
		}

		public int[] getArray() {
			return this.array;
		}
	}

	private static class ConsecutiveNumbersFitness implements Fitness<ArrayChromosome, Integer> {

		private final int[] target = new int[ARR_LEN];

		/**
		 * Target array is [0, 1, 2, 3, ... (ARR_LEN-1)]
		 */
		public ConsecutiveNumbersFitness() {
			for (int i = 0; i < ARR_LEN; i++) {
				this.target[i] = i;
			}
		}

		@Override
		public Integer calculate(ArrayChromosome chromosome) {
			int delt = 0;
			int[] chromosomeArray = chromosome.getArray();
			for (int i = 0; i < ARR_LEN; i++) {
				delt += Math.pow((chromosomeArray[i] - this.target[i]), 2);
			}
			return delt;
		}
	}

}
