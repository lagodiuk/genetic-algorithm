/*******************************************************************************
 * Copyright 2012 Yuriy Lagodiuk
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.lagodiuk.ga.Chromosome;
import com.lagodiuk.ga.Fitness;
import com.lagodiuk.ga.GeneticAlgorithm;
import com.lagodiuk.ga.IterartionListener;
import com.lagodiuk.ga.Population;

public class Demo {

	public static void main(String[] args) {
		Population<MyVector> population = createInitialPopulation(5);

		Fitness<MyVector, Double> fitness = new MyVectorFitness();

		GeneticAlgorithm<MyVector, Double> ga = new GeneticAlgorithm<MyVector, Double>(population, fitness);

		addListener(ga);

		ga.evolve(500);
	}

	/**
	 * The simplest strategy for creating initial population <br/>
	 * in real life it could be more complex
	 */
	private static Population<MyVector> createInitialPopulation(int populationSize) {
		Population<MyVector> population = new Population<MyVector>();
		MyVector base = new MyVector();
		for (int i = 0; i < populationSize; i++) {
			// each member of initial population
			// is mutated clone of base chromosome
			MyVector chr = base.mutate();
			population.addChromosome(chr);
		}
		return population;
	}

	/**
	 * After each iteration Genetic algorithm notifies listener
	 */
	private static void addListener(GeneticAlgorithm<MyVector, Double> ga) {
		// just for pretty print
		System.out.println(String.format("%s\t%s\t%s", "iter", "fit", "chromosome"));

		// Lets add listener, which prints best chromosome after each iteration
		ga.addIterationListener(new IterartionListener<MyVector, Double>() {

			private final double threshold = 1e-5;

			@Override
			public void update(GeneticAlgorithm<MyVector, Double> ga) {

				MyVector best = ga.getBest();
				double bestFit = ga.fitness(best);
				int iteration = ga.getIteration();

				// Listener prints best achieved solution
				System.out.println(String.format("%s\t%s\t%s", iteration, bestFit, best));

				// If fitness is satisfying - we can stop Genetic algorithm
				if (bestFit < this.threshold) {
					ga.terminate();
				}
			}
		});
	}

	/**
	 * Chromosome, which represents vector of five integers
	 */
	public static class MyVector implements Chromosome<MyVector>, Cloneable {

		private static final Random random = new Random();

		private final int[] vector = new int[5];

		/**
		 * Returns clone of current chromosome, which is mutated a bit
		 */
		@Override
		public MyVector mutate() {
			MyVector result = this.clone();

			// just select random element of vector
			// and increase or decrease it on small value
			int index = random.nextInt(this.vector.length);
			int mutationValue = random.nextInt(3) - random.nextInt(3);
			result.vector[index] += mutationValue;

			return result;
		}

		/**
		 * Returns list of siblings <br/>
		 * Siblings are actually new chromosomes, <br/>
		 * created using any of crossover strategy
		 */
		@Override
		public List<MyVector> crossover(MyVector other) {
			MyVector thisClone = this.clone();
			MyVector otherClone = other.clone();

			// one point crossover
			int index = random.nextInt(this.vector.length - 1);
			for (int i = index; i < this.vector.length; i++) {
				int tmp = thisClone.vector[i];
				thisClone.vector[i] = otherClone.vector[i];
				otherClone.vector[i] = tmp;
			}

			return Arrays.asList(thisClone, otherClone);
		}

		@Override
		protected MyVector clone() {
			MyVector clone = new MyVector();
			System.arraycopy(this.vector, 0, clone.vector, 0, this.vector.length);
			return clone;
		}

		public int[] getVector() {
			return this.vector;
		}

		@Override
		public String toString() {
			return Arrays.toString(this.vector);
		}
	}

	/**
	 * Fitness function, which calculates difference between chromosomes vector
	 * and target vector
	 */
	public static class MyVectorFitness implements Fitness<MyVector, Double> {

		private final int[] target = { 10, 20, 30, 40, 50 };

		@Override
		public Double calculate(MyVector chromosome) {
			double delta = 0;
			int[] v = chromosome.getVector();
			for (int i = 0; i < 5; i++) {
				delta += this.sqr(v[i] - this.target[i]);
			}
			return delta;
		}

		private double sqr(double x) {
			return x * x;
		}
	}
}
