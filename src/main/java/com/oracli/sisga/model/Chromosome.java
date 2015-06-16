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

package com.oracli.sisga.model;

import java.util.ArrayList;
import java.util.List;

public class Chromosome
{
	private List<Boolean> chromosome;

	private Chromosome() {}

	public Chromosome(int length)
	{
		chromosome = new ArrayList<Boolean>(length);

		// initialize to random values
		for (int i = 0; i < length; i += 1)
			chromosome.add(Math.round(Math.random()) == 0);
	}

	public int size()
	{
		return chromosome.size();
	}

	public Boolean get(int index)
	{
		return chromosome.get(index);
	}

	public void set(int index, boolean value)
	{
		chromosome.set(index, value);
	}

	public int getRandIndex()
	{
		return (int) (Math.random() * chromosome.size());
	}

	public void flip(int index)
	{
		chromosome.set(index, ! chromosome.get(index));
	}

	public int hammingDistanceTo(Chromosome otherChromosome)
	{
		if (chromosome.size() != otherChromosome.size())
			throw new IllegalArgumentException("Arity of operators unequal.");

		int distance = 0;

		for (int i = 0; i < chromosome.size(); i += 1)
			distance += chromosome.get(i) == otherChromosome.get(i) ? 0 : 1;

		return distance;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		for (Boolean i : chromosome)
			sb.append(i ? "1" : "0");

		return sb.toString();
	}
}
