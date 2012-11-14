package com.lagodiuk.ga.demo;

import java.util.LinkedList;
import java.util.List;

import com.lagodiuk.ga.Gene;

public class IntegerArrayGene implements Gene<IntegerArrayGene> {
	
	private int[] data = new int[10];
	
	//INIT
	{
		for( int i = 0; i < data.length; i++ ) {
			data[i] = (int)( Math.random() * 10 );
		}
	}
	
	public List<IntegerArrayGene> crossover(IntegerArrayGene anotherGene) {
	    	List<IntegerArrayGene> ret = new LinkedList<IntegerArrayGene>(); 
	    
	    	IntegerArrayGene crossoveredGene1 = new IntegerArrayGene();
	    	IntegerArrayGene crossoveredGene2 = new IntegerArrayGene();
		
		for( int i = 0; i < data.length; i++ ) {
			if( Math.random() < 0.3 ) {
				crossoveredGene1.data[i] = data[i];
				crossoveredGene2.data[i] = anotherGene.data[i];
			} else {
				crossoveredGene1.data[i] = anotherGene.data[i];
				crossoveredGene2.data[i] = data[i];
			}
		}
		ret.add( crossoveredGene1 );
		ret.add( crossoveredGene2 );
		
		return ret;
	}
	
	public IntegerArrayGene mutate() {
	    	IntegerArrayGene ret = new IntegerArrayGene();
	    	System.arraycopy( data, 0, ret.data, 0, data.length );
		for( int i = 0; i < ret.data.length; i++ ) {
			ret.data[i] += (int) ( ( Math.random() - Math.random() ) * 2 );
		}
		return ret;
	}
	
	public int[] getData() {
		return data;
	}
	
	@Override
	public String toString() {
	    LinkedList<Integer> ret = new LinkedList<Integer>();
	    for( int i : data ) {
		ret.add( i );
	    }
	    return ret.toString();
	}

}
