package edu.franklin.ga.cls.alg;

import edu.franklin.ga.cls.fitness.FitnessDeterminant;
import edu.franklin.ga.cls.model.Gene;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/*
 * Elitist truncation survivor selection.
 *
 * Rank genes from best to worst, then keep the top N genes.
 */
public class ElitistSurvivorSelection implements SurvivorSelection
{
    private FitnessDeterminant fd;

    private ElitistSurvivorSelection() {}

    public ElitistSurvivorSelection(FitnessDeterminant fd)
    {
        this.fd = fd;
    }

    public List<Gene> select(List<Gene> geneSet, int targetPopulationSize)
    {
        if (targetPopulationSize > geneSet.size())
            throw new IllegalArgumentException("targetSize > geneSetSize");

        Map<Double, List<Gene>> fitnessMap = new HashMap<Double, List<Gene>>();
        List<Gene> survivors = new LinkedList<Gene>();

        for (Gene i : geneSet)
        {
            double fitness = fd.fitness(i);

            if (fitnessMap.get(fitness) == null)
                fitnessMap.put(fitness, new LinkedList<Gene>());

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
                int genesToSave = targetPopulationSize - survivors.size();
                int k = 0;
                for (int j = 0; j < genesToSave; j += 1)
                    survivors.add(fitnessMap.get(fitnesses[i]).get(k++));
            }
            else
                survivors.addAll(fitnessMap.get(fitnesses[i]));
        }

        return survivors;
    }
}
