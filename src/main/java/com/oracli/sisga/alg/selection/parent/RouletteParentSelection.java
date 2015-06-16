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

package com.oracli.sisga.alg.selection.parent;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import com.oracli.sisga.decode.Decoder;
import com.oracli.sisga.model.Chromosome;

/*
 * Roulette (fitness-proportionate) selection.
 */
public class RouletteParentSelection implements ParentSelection
{
	private Decoder d;
	private Function<List<Double>, Double> f;

	private RouletteParentSelection() {}

	public RouletteParentSelection(Decoder d, Function<List<Double>, Double> f)
	{
		this.d = d;
		this.f = f;
	}

	public List<Chromosome> select(List<Chromosome> population)
	{
		List<Double> absoluteFitnesses = new LinkedList<Double>();
		List<Double> proportionalFitnesses = new LinkedList<Double>();

		double totalFitness = 0;

		for (Chromosome i : population)
		{
			double fitness = Math.abs(f.apply(d.decode(i)));
			totalFitness += fitness;
			absoluteFitnesses.add(fitness);
		}

		if (totalFitness == 0)
			throw new ArithmeticException(""
				+ "Sum fitness is zero in RouletteParentSelection, "
				+ "so cannot determine proportional fitness.");

		for (Double i : absoluteFitnesses)
			proportionalFitnesses.add(i / totalFitness);

		List<Chromosome> parents = new LinkedList<Chromosome>();

		while (parents.size() < 2)
		{
			double p = Math.random();

			for (int i = 0; i < population.size(); i += 1)
				if (p < proportionalFitnesses.get(i) && parents.size() < 2)
					parents.add(population.get(i));
		}

		return parents;
	}
}
