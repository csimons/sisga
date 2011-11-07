package edu.franklin.ga.cls.util;

import java.util.Collection;

import edu.franklin.ga.cls.fitness.FitnessDeterminant;
import edu.franklin.ga.cls.model.Gene;

public class GeneCollectionAnalyzer
{
    public static double avgFitness(Collection<Gene> geneSet,
            FitnessDeterminant fd)
    {
        double acc = 0;

        for (Gene gene : geneSet)
            acc += fd.fitness(gene);

        return acc / geneSet.size();
    }

    public static double bestFitness(Collection<Gene> geneSet,
            FitnessDeterminant fd)
    {
        /*
         * Don't initialize this to zero as it will cause incorrect
         * results if the fitnesses of all genes are negative.
         */
        Double best = null;

        for (Gene gene : geneSet)
        {
            if (best == null)
                best = fd.fitness(gene);
            else
            {
                double current = fd.fitness(gene);
                best = current > best ? current : best;
            }
        }

        return best;
    }

    public static double worstFitness(Collection<Gene> geneSet,
            FitnessDeterminant fd)
    {
        /*
         * Don't initialize this to zero as it will cause incorrect
         * results if the fitnesses of all genes are negative.
         */
        Double worst = null;

        for (Gene gene : geneSet)
        {
            if (worst == null)
                worst = fd.fitness(gene);
            else
            {
                double current = fd.fitness(gene);
                worst = current < worst ? current : worst;
            }
        }

        return worst;
    }
}
