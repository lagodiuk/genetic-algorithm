package com.lagodiuk.ga.demo;

import com.lagodiuk.ga.Environment;
import com.lagodiuk.ga.Fitness;
import com.lagodiuk.ga.IterartionListener;
import com.lagodiuk.ga.Population;

public class Main {

	public static void main(String[] args) {
		Population<IntegerArrayGene> population = new Population<IntegerArrayGene>();
		for (int i = 0; i < 10; i++) {
			IntegerArrayGene g = new IntegerArrayGene();
			population.addGene(g);
		}

		Fitness<IntegerArrayGene, Double> fit = new TestFitnessFunc();

		Environment<IntegerArrayGene, Double> env = new Environment<IntegerArrayGene, Double>(population, fit);

		env.addIterationListener(new IterartionListener<IntegerArrayGene, Double>() {
			public void update(Environment<IntegerArrayGene, Double> environment) {
				IntegerArrayGene bestGene = environment.getBest();

				double fitValue = environment.fitness(bestGene);
				System.out.println((int) fitValue + "\t" + bestGene);

				if (fitValue == 0) {
					environment.terminate();
				}
			}
		});

		env.iterate(320);
	}
}
