package com.lagodiuk.ga;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class ArrayGeneTest {

	@Test
	public void test() {
		int populationSize = 10;
		// create initial population of array-genes
		Population<ArrayGene> population = new Population<ArrayGeneTest.ArrayGene>();
		for (int i = 0; i < populationSize; i++) {
			population.addGene(new ArrayGene());
		}

		// create instance of fitness function calculator
		ConsecutiveNumbersFitness fitness = new ConsecutiveNumbersFitness();

		// genetic-algorithm environment
		Environment<ArrayGene, Integer> environment = new Environment<ArrayGeneTest.ArrayGene, Integer>(population, fitness);

		// evolve 10 iterations
		environment.iterate(10);
		// and get best gene after 10 iterations
		ArrayGene firstBestGene = environment.getBest();
		// calculate fitness
		Integer firstFitness = fitness.calculate(firstBestGene);

		// evolve next 10 iterations
		environment.iterate(10);
		ArrayGene secondBestGene = environment.getBest();
		Integer secondFitness = fitness.calculate(secondBestGene);

		environment.iterate(80);
		ArrayGene thirdBestGene = environment.getBest();
		Integer thirdFitness = fitness.calculate(thirdBestGene);

		// second gene is better than first
		assertTrue(secondFitness <= firstFitness);
		// third gene is better than first
		assertTrue(thirdFitness <= firstFitness);
		// third gene is better than second
		assertTrue(thirdFitness <= secondFitness);
	}

	private static final int ARR_LEN = 10;

	private static class ArrayGene implements Gene<ArrayGene> {

		private final int[] array = new int[ARR_LEN];

		private final Random rand = new Random();

		public ArrayGene() {
			for (int i = 0; i < ARR_LEN; i++) {
				this.array[i] = this.rand.nextInt(10) - this.rand.nextInt(10);
			}
		}

		public List<ArrayGene> crossover(ArrayGene anotherGene) {
			List<ArrayGene> childs = new LinkedList<ArrayGeneTest.ArrayGene>();
			ArrayGene child1 = new ArrayGene();
			ArrayGene child2 = new ArrayGene();
			for (int i = 0; i < ARR_LEN; i++) {
				if (this.rand.nextBoolean()) {
					child1.array[i] = this.array[i];
					child2.array[i] = anotherGene.array[i];
				} else {
					child1.array[i] = anotherGene.array[i];
					child2.array[i] = this.array[i];
				}
			}
			childs.add(child1);
			childs.add(child2);
			return childs;
		}

		public ArrayGene mutate() {
			ArrayGene mutated = new ArrayGene();
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

	private static class ConsecutiveNumbersFitness implements Fitness<ArrayGene, Integer> {

		private final int[] target = new int[ARR_LEN];

		public ConsecutiveNumbersFitness() {
			for (int i = 0; i < ARR_LEN; i++) {
				this.target[i] = i;
			}
		}

		public Integer calculate(ArrayGene gene) {
			int delt = 0;
			int[] geneArray = gene.getArray();
			for (int i = 0; i < ARR_LEN; i++) {
				delt += Math.pow((geneArray[i] - this.target[i]), 2);
			}
			return delt;
		}
	}

}
