package com.lagodiuk.ga.demo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.lagodiuk.ga.Gene;

public class IntegerArrayGene implements Gene<IntegerArrayGene> {

	private static final int MUTATION_COEFFICIENT = 2;

	private static final double CROSSOVER_THRESHOLD = 0.3;

	private final int[] data;

	private final Random rand = new Random();

	public IntegerArrayGene(int length) {
		this.data = new int[length];
		// initialize with random values
		for (int i = 0; i < this.data.length; i++) {
			this.data[i] = this.rand.nextInt(10);
		}
	}

	public List<IntegerArrayGene> crossover(IntegerArrayGene anotherGene) {
		List<IntegerArrayGene> ret = new LinkedList<IntegerArrayGene>();

		IntegerArrayGene crossoveredGene1 = new IntegerArrayGene(this.data.length);
		IntegerArrayGene crossoveredGene2 = new IntegerArrayGene(this.data.length);

		for (int i = 0; i < this.data.length; i++) {
			if (this.rand.nextDouble() < CROSSOVER_THRESHOLD) {
				crossoveredGene1.data[i] = this.data[i];
				crossoveredGene2.data[i] = anotherGene.data[i];
			} else {
				crossoveredGene1.data[i] = anotherGene.data[i];
				crossoveredGene2.data[i] = this.data[i];
			}
		}
		ret.add(crossoveredGene1);
		ret.add(crossoveredGene2);

		return ret;
	}

	public IntegerArrayGene mutate() {
		IntegerArrayGene ret = new IntegerArrayGene(this.data.length);
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
