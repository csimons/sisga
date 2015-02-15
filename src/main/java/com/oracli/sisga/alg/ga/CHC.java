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

import java.util.LinkedList;
import java.util.List;

import com.oracli.sisga.alg.mutation.population.CHCCataclysmicMutation;
import com.oracli.sisga.alg.recombination.HUXRecombination;
import com.oracli.sisga.alg.selection.parent.RouletteParentSelection;
import com.oracli.sisga.alg.selection.survivor.ElitistSurvivorSelection;
import com.oracli.sisga.decode.Decoder;
import com.oracli.sisga.fitness.Function;
import com.oracli.sisga.model.Chromosome;
import com.oracli.sisga.util.GAResultSet;
import com.oracli.sisga.util.PopulationAnalyzer;

public class CHC extends AbstractGA
{
    @Override
    public void init()
    {
        super.init();

        algSS = new ElitistSurvivorSelection(d, f);
        algPS = new RouletteParentSelection(d, f);
        algRec = new HUXRecombination();
        algMutP = new CHCCataclysmicMutation(d, f);
    }

    /**
     * @param populationSize Population size.
     * @param chromosomeSize Chromosome size (genes per chromosome).
     * @param pC Crossover probability (ignored for CHC).
     * @param pCM Cataclysmic mutation probability.
     * @param termGeneration Number of generations to run,
     *        or null for fitness-based termination.
     * @param termFitness Fitness at which to halt,
     *        or null for generation-based termination.
     * @param verbose Whether to print periodic statistics.
     */
    public GAResultSet run(int populationSize, int chromosomeSize,
            Double pC, Double pCM,
            Integer termGeneration, Double termFitness,
            boolean verbose)
    {
        GAResultSet results = new GAResultSet();

        List<Chromosome> population = new LinkedList<Chromosome>();
        for (int i = 0; i < populationSize; i += 1)
            population.add(new Chromosome(chromosomeSize));

        int threshold = Math.round(population.size() / 4);

        Double gBest  = null;
        Double gWorst = null;
        for (int i = 0; i <= termGeneration
            && (gBest == null || gBest < termFitness); i += 1)
        {
            if (gBest == null)
                gBest = PopulationAnalyzer.bestFitness(population, d, f);
            else
                if (gBest < PopulationAnalyzer.bestFitness(population, d, f))
                    gBest = PopulationAnalyzer.bestFitness(population, d, f);

            if (gWorst == null)
                gWorst = PopulationAnalyzer.worstFitness(population, d, f);
            else
                if (gWorst > PopulationAnalyzer.worstFitness(population, d, f))
                    gWorst = PopulationAnalyzer.worstFitness(population, d, f);

            results.bestFitnesses.add(gBest);
            results.avgFitnesses.add(PopulationAnalyzer
                    .avgFitness(population, d, f));
            results.worstFitnesses.add(gWorst);

            int matings = Math.round(population.size() / 2);
            List<Chromosome> children = new LinkedList<Chromosome>();
            List<Chromosome> newGeneration = new LinkedList<Chromosome>();
            for (int j = 0; j < matings; j += 1)
            {
                List<Chromosome> parents = getParents(population);
                Chromosome dad = parents.get(0);
                Chromosome mom = parents.get(1);

                if ((dad.hammingDistanceTo(mom) / 2) > threshold)
                    for (int k = 0; k < 2; k += 1)
                        children.add(recombinate(dad, mom, pC));
            }

            newGeneration.addAll(population);

            if (children.isEmpty())
                threshold -= 1;
            else
                newGeneration.addAll(children);

            if (threshold <= 0)
            {
                population = mutate(newGeneration, pCM);
                threshold = (int) Math.round(
                    pCM * (1.0 - pCM) * chromosomeSize);
            }

            population = getSurvivors(newGeneration, population.size());
        }

        return results;
    }
}
