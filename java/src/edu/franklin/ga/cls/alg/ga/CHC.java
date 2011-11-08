package edu.franklin.ga.cls.ga;

import edu.franklin.ga.cls.fitness.FitnessDeterminant;
import edu.franklin.ga.cls.alg.mutation.population.CHCCataclysmicMutation;
import edu.franklin.ga.cls.alg.recombination.HUXRecombination;
import edu.franklin.ga.cls.alg.selection.parent.RouletteParentSelection;
import edu.franklin.ga.cls.alg.selection.survivor.ElitistSurvivorSelection;
import edu.franklin.ga.cls.model.Chromosome;
import edu.franklin.ga.cls.util.GAResultSet;
import edu.franklin.ga.cls.util.PopulationAnalyzer;

import java.util.LinkedList;
import java.util.List;

public class CHC extends AbstractGA
{
    public CHC(FitnessDeterminant fd)
    {
        super(fd,
                new ElitistSurvivorSelection(fd),
                new RouletteParentSelection(fd),
                new HUXRecombination(),
                null,
                new CHCCataclysmicMutation(fd));
    }

    /**
     * @param populationSize Population size.
     * @param chromosomeSize Chromosome size (genes per chromosome).
     * @param pC Crossover probability (ignored for CHC).
     * @param pCM Cataclysmic mutation probability.
     * @param termGeneration Number of generations to run,
     *        or null for fitness-based termination.
     * @param termFitness Fitness at which to halt,
     *        or null for generation-based termination.
     */
    public GAResultSet run(int populationSize, int chromosomeSize,
            Double pC, Double pCM,
            Integer termGeneration, Double termFitness)
    {
        GAResultSet results = new GAResultSet();

        List<Chromosome> population = new LinkedList<Chromosome>();
        for (int i = 0; i < populationSize; i += 1)
            population.add(new Chromosome(chromosomeSize));

        int threshold = Math.round(population.size() / 4);

        Double gBest = null;
        for (int i = 0; true; i += 1)
        {
            if (gBest == null)
                gBest = PopulationAnalyzer.bestFitness(population, fd);
            else
                if (gBest < PopulationAnalyzer.bestFitness(population, fd))
                    gBest = PopulationAnalyzer.bestFitness(population, fd);

            if (termGeneration != null && i >= termGeneration)
                break;

            // If the last best fitnesses was at least as good as the
            // terminal best fitness, break out of run.
            if (termFitness != null && results.bestFitnesses != null
                    && termFitness <= results.bestFitnesses
                                        .get(results.bestFitnesses.size() - 1))
                break;

            List newGeneration = new LinkedList<Chromosome>();
            int matings = Math.round(population.size() / 2);
            List<Chromosome> children = new LinkedList<Chromosome>();

            for (int j = 0; j < matings; j += 1)
            {
                List<Chromosome> parents = getParents(population);
                Chromosome dad = parents.get(0);
                Chromosome mom = parents.get(1);

                if ((dad.hammingDistanceTo(mom) / 2) > threshold)
                    for (int k = 0; k < 2; k += 1)
                        children.add(recombinate(dad, mom, pC));

                if (children.size() == 0)
                {
                    newGeneration = population;
                    threshold -= 1;
                }
                else
                {
                    newGeneration.addAll(population);
                    newGeneration.addAll(children);

                    newGeneration = getSurvivors(newGeneration,
                            population.size());
                }

                if (threshold <= 0)
                {
                    population = mutate(newGeneration, pCM);
                    threshold = (int) Math.round(pCM * (1.0 - pCM) * chromosomeSize);
                }
            }
        }

        return results;
    }
}
