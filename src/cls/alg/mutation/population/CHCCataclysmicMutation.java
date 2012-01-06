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

package cls.alg.mutation.population;

import cls.decode.Decoder;
import cls.fitness.Function;
import cls.model.Chromosome;
import cls.util.PopulationAnalyzer;
import java.util.List;

/*
 * For every allele in every chromosome in the population, except for
 * the chromosome with the highest fitness, flip per probability.
 */
public class CHCCataclysmicMutation implements PopulationMutation
{
    private Decoder d;
    private Function f;

    private CHCCataclysmicMutation() {}

    public CHCCataclysmicMutation(Decoder d, Function f)
    {
        this.d = d;
        this.f = f;
    }

    public List<Chromosome> mutate(List<Chromosome> population, double pCM)
    {
        Chromosome bestFitChromosome = null;

        double bestFitness = PopulationAnalyzer
            .bestFitness(population, d, f);

        for (Chromosome chromosome : population)
            if (f.f(d.decode(chromosome)) == bestFitness)
                bestFitChromosome = chromosome;

        if (bestFitChromosome == null)
            throw new IllegalStateException(
                "Unable to determine best-fit chromosome.");

        for (Chromosome chromosome : population)
            if (chromosome != bestFitChromosome)
                for (int i = 0; i < chromosome.size(); i += 1)
                    if (Math.random() < pCM)
                        chromosome.flip(i);

        return population;
    }
}
