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
package com.lagodiuk.ga;

public interface Fitness<C extends Chromosome<C>, T extends Comparable<T>> {

	/**
	 * Assume that chromosome1 is better than chromosome2 <br/>
	 * fit1 = calculate(chromosome1) <br/>
	 * fit2 = calculate(chromosome2) <br/>
	 * So the following condition must be true <br/>
	 * fit1.compareTo(fit2) <= 0 <br/>
	 */
	T calculate(C chromosome);

}
