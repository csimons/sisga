package edu.franklin.ga.cls.alg.selection.parent;

import edu.franklin.ga.cls.fitness.FitnessDeterminant;
import edu.franklin.ga.cls.model.Chromosome;

import java.util.LinkedList;
import java.util.List;

/*
 * Roulette (fitness-proportionate) selection.
 */
public class RouletteParentSelection implements ParentSelection
{
    private FitnessDeterminant fd;

    private RouletteParentSelection() {}

    public RouletteParentSelection(FitnessDeterminant fd)
    {
        this.fd = fd;
    }

    public List<Chromosome> select(List<Chromosome> population)
    {
        List<Double> absoluteFitnesses = new LinkedList<Double>();
        List<Double> proportionalFitnesses = new LinkedList<Double>();

        double totalFitness = 0;

        for (Chromosome i : population)
        {
            double fitness = fd.fitness(i);
            totalFitness += fitness;
            absoluteFitnesses.add(fitness);
        }

        for (Double i : absoluteFitnesses)
            proportionalFitnesses.add(i / totalFitness);

        List<Chromosome> parents = new LinkedList<Chromosome>();

        for (int i = 0; i < 2; i += 1)
        {
            double p = Math.random();

            for (int j = 0; j < population.size(); j += 1)
                if (p < proportionalFitnesses.get(j))
                    parents.add(population.get(j));
                else
                    p -= proportionalFitnesses.get(j);
        }

        return parents;
    }
}
