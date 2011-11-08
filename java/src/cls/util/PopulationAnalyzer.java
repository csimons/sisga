package cls.util;

import cls.fitness.FitnessDeterminant;
import cls.model.Chromosome;
import java.util.Collection;

public class PopulationAnalyzer
{
    public static double avgFitness(Collection<Chromosome> population,
            FitnessDeterminant fd)
    {
        double acc = 0;

        for (Chromosome chromosome : population)
            acc += fd.fitness(chromosome);

        return acc / population.size();
    }

    public static double bestFitness(Collection<Chromosome> population,
            FitnessDeterminant fd)
    {
        /*
         * Don't initialize this to zero as it will cause incorrect
         * results if the fitnesses of all chromosomes are negative.
         */
        Double best = null;

        for (Chromosome chromosome : population)
        {
            if (best == null)
                best = fd.fitness(chromosome);
            else
            {
                double current = fd.fitness(chromosome);
                best = current > best ? current : best;
            }
        }

        return best;
    }

    public static double worstFitness(Collection<Chromosome> population,
            FitnessDeterminant fd)
    {
        /*
         * Don't initialize this to zero as it will cause incorrect
         * results if the fitnesses of all genes are negative.
         */
        Double worst = null;

        for (Chromosome chromosome : population)
        {
            if (worst == null)
                worst = fd.fitness(chromosome);
            else
            {
                double current = fd.fitness(chromosome);
                worst = current < worst ? current : worst;
            }
        }

        return worst;
    }
}
