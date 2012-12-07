package com.lagodiuk.ga.demo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.lagodiuk.ga.Chromosome;

public class IntegerArrayChromosome implements Chromosome<IntegerArrayChromosome> {

	private static final int MUTATION_COEFFICIENT = 2;

	private static final double CROSSOVER_THRESHOLD = 0.3;

	private final int[] data;

	private final Random rand = new Random();

	public IntegerArrayChromosome(int length) {
		this.data = new int[length];
		// initialize with random values
		for (int i = 0; i < this.data.length; i++) {
			this.data[i] = this.rand.nextInt(10);
		}
	}

	public List<IntegerArrayChromosome> crossover(IntegerArrayChromosome anotherChromosome) {
		List<IntegerArrayChromosome> ret = new LinkedList<IntegerArrayChromosome>();

		IntegerArrayChromosome crossoveredChromosome1 = new IntegerArrayChromosome(this.data.length);
		IntegerArrayChromosome crossoveredChromosome2 = new IntegerArrayChromosome(this.data.length);

		for (int i = 0; i < this.data.length; i++) {
			if (this.rand.nextDouble() < CROSSOVER_THRESHOLD) {
				crossoveredChromosome1.data[i] = this.data[i];
				crossoveredChromosome2.data[i] = anotherChromosome.data[i];
			} else {
				crossoveredChromosome1.data[i] = anotherChromosome.data[i];
				crossoveredChromosome2.data[i] = this.data[i];
			}
		}
		ret.add(crossoveredChromosome1);
		ret.add(crossoveredChromosome2);

		return ret;
	}

	public IntegerArrayChromosome mutate() {
		IntegerArrayChromosome ret = new IntegerArrayChromosome(this.data.length);
		System.arraycopy(this.data, 0, ret.data, 0, this.data.length);
		for (int i = 0; i < ret.data.length; i++) {
			ret.data[i] += (int) ((this.rand.nextDouble() - this.rand.nextDouble()) * MUTATION_COEFFICIENT);
		}
		return ret;
	}

	public int[] getData() {
		return this.data;
	}

	@Override
	public String toString() {
		return Arrays.toString(this.data);
	}

}
