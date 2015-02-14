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

package cls.alg.selection.survivor;

import cls.decode.Decoder;
import cls.fitness.Function;
import cls.model.Chromosome;
import java.util.LinkedList;
import java.util.List;

/*
 * Tournament truncation survivor selection.
 *
 * Randomly pick two chromosomes from the population
 * and discard the one with the lower fitness.
 */
public class TournamentSurvivorSelection implements SurvivorSelection
{
    private Decoder d;
    private Function f;

    private TournamentSurvivorSelection() {}

    public TournamentSurvivorSelection(Decoder d, Function f)
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

        List<Chromosome> survivorSet = new LinkedList<Chromosome>();

        while(population.size() > targetPopulationSize)
        {
            int indexA = (int) Math.random() * (population.size() + 1);
            int indexB = (int) Math.random() * (population.size() + 1);

            Chromosome a = population.get(indexA);
            Chromosome b = population.get(indexB);

            population.remove(f.f(d.decode(a)) < f.f(d.decode(b)) ? a : b);
        }

        return population;
    }
}