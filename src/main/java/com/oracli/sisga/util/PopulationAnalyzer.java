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

package com.oracli.sisga.util;

import java.util.List;
import java.util.function.Function;

import com.oracli.sisga.decode.Decoder;
import com.oracli.sisga.model.Chromosome;

public class PopulationAnalyzer
{
	public static void print(List<Chromosome> population,
			Decoder d, Function<List<Double>, Double> f)
	{
		System.out.println("CHROMOSOME\tDECODED_VALUES\tFITNESS");

		for (Chromosome i : population)
		{
			List<Double> vals = d.decode(i);

			StringBuffer sb = new StringBuffer();
			sb.append("{ ");
			for (Double j : vals)
				sb.append(String.format("%f ", j));
			sb.append("}");

			System.out.println(String.format("%s\t%s\t%f",
						i.toString(), sb.toString(), f.apply(vals)));
		}
	}

	public static double avgFitness(List<Chromosome> population,
			Decoder d, Function<List<Double>, Double> f)
	{
		double acc = 0;

		for (Chromosome chromosome : population)
			acc += f.apply(d.decode(chromosome));

		return acc / population.size();
	}

	public static double bestFitness(List<Chromosome> population,
			Decoder d, Function<List<Double>, Double> f)
	{
		/*
		 * Don't initialize this to zero as it will cause incorrect
		 * results if the fitnesses of all chromosomes are negative.
		 */
		Double best = null;

		for (Chromosome chromosome : population)
		{
			if (best == null)
				best = f.apply(d.decode(chromosome));
			else
			{
				double current = f.apply(d.decode(chromosome));
				best = current > best ? current : best;
			}
		}

		return best;
	}

	public static double worstFitness(List<Chromosome> population,
			Decoder d, Function<List<Double>, Double> f)
	{
		/*
		 * Don't initialize this to zero as it will cause incorrect
		 * results if the fitnesses of all genes are negative.
		 */
		Double worst = null;

		for (Chromosome chromosome : population)
		{
			if (worst == null)
				worst = f.apply(d.decode(chromosome));
			else
			{
				double current = f.apply(d.decode(chromosome));
				worst = current < worst ? current : worst;
			}
		}

		return worst;
	}
}
