package cls.ga;

import cls.util.GAResultSet;

public interface GA
{
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
            Integer termGeneration, Double termFitness,
            boolean verbose);
}
