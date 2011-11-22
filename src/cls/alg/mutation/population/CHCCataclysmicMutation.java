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
