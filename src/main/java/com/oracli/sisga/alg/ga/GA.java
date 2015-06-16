/* Copyright (c) 2011, 2012 Christopher L. Simons
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.oracli.sisga.alg.ga;

import com.oracli.sisga.decode.Decoder;
import com.oracli.sisga.fitness.Function;
import com.oracli.sisga.util.GAResultSet;

public interface GA
{
	/**
	 * @param populationSize Population size.
	 * @param chromosomeSize Chromosome size (genes per chromosome).
	 * @param pC Crossover probability.
	 * @param pM Mutation probability.
	 * @param termGeneration Number of generations to run,
	 *		or null for fitness-based termination.
	 * @param termFitness Fitness at which to halt,
	 *		or null for generation-based termination.
	 * @param verbose Whether to print periodic statistics.
	 */
	public abstract GAResultSet run(int populationSize, int chromosomeSize,
			Double pC, Double pM,
			Integer termGeneration, Double termFitness,
			boolean verbose);

	public abstract void setDecoder(Decoder d);
	public abstract void setFunction(Function f);
	public abstract void init();
}
