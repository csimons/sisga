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

import cls.alg.mutation.chromosome.ChromosomeMutation;
import cls.alg.mutation.population.PopulationMutation;
import cls.alg.selection.parent.ParentSelection;
import cls.alg.selection.survivor.SurvivorSelection;
import cls.alg.recombination.Recombination;
import cls.decode.Decoder;
import cls.fitness.Function;
import cls.model.Chromosome;
import cls.util.GAResultSet;
import java.util.List;

public abstract class AbstractGA implements GA
{
    protected Decoder               d;
    protected Function              f;
    protected SurvivorSelection     algSS;
    protected ParentSelection       algPS;
    protected Recombination         algRec;
    protected ChromosomeMutation    algMutC;
    protected PopulationMutation    algMutP;

    public void setDecoder(Decoder d)   { this.d = d; }
    public void setFunction(Function f) { this.f = f; }

    public void init()
    {
        if (d == null || f == null)
            throw new IllegalStateException(
                "Cannot initialize without decoder and fitness function.");
    }

    /**
     * @param populationSize Population size.
     * @param chromosomeSize Chromosome size (genes per chromosome).
     * @param pC Crossover probability.
     * @param pM Mutation probability.
     * @param termGeneration Number of generations to run,
     *        or null for fitness-based termination.
     * @param termFitness Fitness at which to halt,
     *        or null for generation-based termination.
     * @param verbose Whether to print periodic statistics.
     */
    public abstract GAResultSet run(int populationSize, int chromosomeSize,
            Double pC, Double pM,
            Integer termGeneration, Double termFitness, boolean verbose);

    protected Object decode(Chromosome chromosome)
    {
        return d.decode(chromosome);
    }

    protected double fitness(List<Double> input)
    {
        return f.f(input);
    }

    protected List<Chromosome> getSurvivors(List<Chromosome> population,
            int targetPopSize)
    {
        return algSS.select(population, targetPopSize);
    }

    protected List<Chromosome> getParents(List<Chromosome> population)
    {
        return algPS.select(population);
    }

    protected Chromosome recombinate(Chromosome a, Chromosome b, double pC)
    {
        return algRec.recombinate(a, b, pC);
    }

    protected Chromosome mutate(Chromosome chromosome, double pM)
    {
        return algMutC.mutate(chromosome, pM);
    }

    protected List<Chromosome> mutate(List<Chromosome> population, double pM)
    {
        return algMutP.mutate(population, pM);
    }
}
