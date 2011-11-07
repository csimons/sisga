package edu.franklin.ga.cls.ga;

import edu.franklin.ga.cls.util.GAResultSet;

public interface GA
{
    /**
     * @param populationSize Population size.
     * @param geneSize Gene size (chromosomes).
     * @param pC Crossover probability.
     * @param pM Mutation probability.
     * @param termGeneration Number of generations to run,
     *        or null for fitness-based termination.
     * @param termFitness Fitness at which to halt,
     *        or null for generation-based termination.
     */
    public abstract GAResultSet run(int populationSize, int geneSize,
            Double pC, Double pM,
            Integer termGeneration, Double termFitness);
}
