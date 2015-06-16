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

package com.oracli.sisga.alg.selection.survivor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.oracli.sisga.decode.Decoder;
import com.oracli.sisga.model.Chromosome;

/*
 * Elitist truncation survivor selection.
 *
 * Rank chromosomes from best to worst, then keep the top N chromosomes.
 */
public class ElitistSurvivorSelection implements SurvivorSelection
{
	private Decoder d;
	private Function<List<Double>, Double> f;

	private ElitistSurvivorSelection() {}

	public ElitistSurvivorSelection(Decoder d, Function<List<Double>, Double> f)
	{
		this.d = d;
		this.f = f;
	}

	public List<Chromosome> select(List<Chromosome> population,
			int targetPopulationSize)
	{
		if (targetPopulationSize > population.size())
			throw new IllegalArgumentException(
					"targetPopulationSize > populationSize");

		Map<Double, List<Chromosome>> fitnessMap
			= new HashMap<Double, List<Chromosome>>();

		for (Chromosome i : population)
		{
			double fitness = f.apply(d.decode(i));

			if (fitnessMap.get(fitness) == null)
				fitnessMap.put(fitness, new LinkedList<Chromosome>());

			fitnessMap.get(fitness).add(i);
		}

		Double[] fitnesses = fitnessMap
			.keySet().toArray(new Double[fitnessMap.size()]);

		Arrays.sort(fitnesses);

		/*
		 * Sorted to ascending order, so iterate backward.
		 */
		List<Chromosome> survivors = new LinkedList<Chromosome>();
		for (int i = fitnesses.length - 1; i >= 0; i -= 1)
		{
			if (survivors.size() + fitnessMap.get(fitnesses[i]).size()
					<= targetPopulationSize)
				survivors.addAll(fitnessMap.get(fitnesses[i]));
			else
			{
				int chromosomesToSave
					= targetPopulationSize - survivors.size();

				for (int j = 0; j < chromosomesToSave; j += 1)
					survivors.add(fitnessMap.get(fitnesses[i]).get(j));
			}
		}

		return survivors;
	}
}
