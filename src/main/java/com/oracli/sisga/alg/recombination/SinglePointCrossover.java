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

package com.oracli.sisga.alg.recombination;

import com.oracli.sisga.model.Chromosome;

public class SinglePointCrossover implements Recombination
{
	public SinglePointCrossover() {}

	/*
	 * If our random number lies within the provided probability, we
	 * crossover the two chromosomes at a random allele point.  Otherwise,
	 * we randomly select one of the two chromosomes and return it.
	 */
	public Chromosome recombinate(Chromosome dad, Chromosome mom, Double pC)
	{
		if (dad.size() != mom.size())
			throw new IllegalArgumentException("Arity of operators unequal.");

		Chromosome child;

		if (Math.random() < pC)
		{
			child = new Chromosome(dad.size());

			int alleleIndex = dad.getRandIndex();

			for (int i = 0; i < child.size(); i += 1)
				child.set(i, i < alleleIndex ? dad.get(i) : mom.get(i));
		}
		else
			child = Math.round(Math.random()) == 0 ? dad : mom;

		return child;
	}
}
