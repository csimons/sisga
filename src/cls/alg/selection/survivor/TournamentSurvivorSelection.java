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
