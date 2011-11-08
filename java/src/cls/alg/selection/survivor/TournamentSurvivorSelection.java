package cls.alg.selection.survivor;

import cls.fitness.FitnessDeterminant;
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
    private FitnessDeterminant fd;

    private TournamentSurvivorSelection() {}

    public TournamentSurvivorSelection(FitnessDeterminant fd)
    {
        this.fd = fd;
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

            population.remove(fd.fitness(a) < fd.fitness(b) ? a : b);
        }

        return population;
    }
}
