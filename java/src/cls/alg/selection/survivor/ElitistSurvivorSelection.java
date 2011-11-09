package cls.alg.selection.survivor;

import cls.decode.Decoder;
import cls.fitness.Function;
import cls.model.Chromosome;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/*
 * Elitist truncation survivor selection.
 *
 * Rank chromosomes from best to worst, then keep the top N chromosomes.
 */
public class ElitistSurvivorSelection implements SurvivorSelection
{
    private Decoder d;
    private Function f;

    private ElitistSurvivorSelection() {}

    public ElitistSurvivorSelection(Decoder d, Function f)
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

        Map<Double, List<Chromosome>> fitnessMap
            = new HashMap<Double, List<Chromosome>>();

        List<Chromosome> survivors = new LinkedList<Chromosome>();

        for (Chromosome i : population)
        {
            double fitness = f.f(d.decode(i));

            if (fitnessMap.get(fitness) == null)
                fitnessMap.put(fitness, new LinkedList<Chromosome>());

            fitnessMap.get(fitness).add(i);
        }

        Double[] fitnesses = fitnessMap
            .keySet().toArray(new Double[fitnessMap.size()]);

        Arrays.sort(fitnesses);

        /*
         * Sorted to ascending order, so iterate backward.
         */
        for (int i = fitnesses.length - 1; i > 0; i -= 1)
        {
            if (survivors.size() + fitnessMap.get(fitnesses[i]).size()
                    > targetPopulationSize)
            {
                int chromosomesToSave
                    = targetPopulationSize - survivors.size();

                int k = 0;
                for (int j = 0; j < chromosomesToSave; j += 1)
                    survivors.add(fitnessMap.get(fitnesses[i]).get(k++));
            }
            else
                survivors.addAll(fitnessMap.get(fitnesses[i]));
        }

        return survivors;
    }
}
