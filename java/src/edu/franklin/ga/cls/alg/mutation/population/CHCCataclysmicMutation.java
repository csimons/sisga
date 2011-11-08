package edu.franklin.ga.cls.alg.mutation.population;

import edu.franklin.ga.cls.fitness.FitnessDeterminant;
import edu.franklin.ga.cls.model.Chromosome;
import edu.franklin.ga.cls.util.PopulationAnalyzer;

import java.util.List;

/*
 * For every allele in every chromosome in the population, except for
 * the chromosome with the highest fitness, flip per probability.
 */
public class CHCCataclysmicMutation implements PopulationMutation
{
    private FitnessDeterminant fd;

    private CHCCataclysmicMutation() {}

    public CHCCataclysmicMutation(FitnessDeterminant fd)
    {
        this.fd = fd;
    }

    public List<Chromosome> mutate(List<Chromosome> population, double pCM)
    {
        Chromosome bestFitChromosome = null;

        double bestFitness = PopulationAnalyzer
            .bestFitness(population, fd);

        for (Chromosome chromosome : population)
            if (fd.fitness(chromosome) == bestFitness)
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
