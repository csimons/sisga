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

package cls.alg.ga;

import cls.alg.mutation.chromosome.RandomBitFlipMutation;
import cls.alg.recombination.SinglePointCrossover;
import cls.alg.selection.parent.RouletteParentSelection;
import cls.alg.selection.survivor.TournamentSurvivorSelection;
import cls.decode.Decoder;
import cls.fitness.Function;
import cls.model.Chromosome;
import cls.util.GAResultSet;
import cls.util.PopulationAnalyzer;
import java.util.LinkedList;
import java.util.List;

public class CanonicalGA extends AbstractGA
{
    @Override
    public void init()
    {
        super.init();

        algPS = new RouletteParentSelection(d, f);
        algSS = new TournamentSurvivorSelection(d, f);
        algRec = new SinglePointCrossover();
        algMutC = new RandomBitFlipMutation();
    }

    /**
     * @param populationSize Population size.
     * @param chromosomeSize Chromosome size (genes per chromosome).
     * @param pC Probability of recombination.
     * @param pM Probability of mutation.
     * @param termGeneration Number of generations to run,
     *        or null for fitness-based termination.
     * @param termFitness Fitness at which to halt,
     *        or null for generation-based termination.
     * @param verbose Whether to print periodic statistics.
     */
    public GAResultSet run(int populationSize, int chromosomeSize,
            Double pC, Double pM,
            Integer termGeneration, Double termFitness,
            boolean verbose)
    {
        GAResultSet results = new GAResultSet();

        List<Chromosome> population = new LinkedList<Chromosome>();
        for (int i = 0; i < populationSize; i += 1)
            population.add(new Chromosome(chromosomeSize));

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

            List<Chromosome> newGeneration = new LinkedList<Chromosome>();
            while (newGeneration.size() < population.size())
            {
                List<Chromosome> parents = getParents(population);
                Chromosome dad = parents.get(0);
                Chromosome mom = parents.get(1);
                Chromosome childA = mutate(recombinate(dad, mom, pC), pM);
                Chromosome childB = mutate(recombinate(dad, mom, pC), pM);
                newGeneration.add(childA);
                // in case of odd-numbered population sizes:
                if (newGeneration.size() < population.size())
                    newGeneration.add(childB);
            }

            newGeneration.addAll(population);
            newGeneration = getSurvivors(newGeneration, population.size());
            population = newGeneration;
        }

        return results;
    }
}
