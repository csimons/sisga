package edu.franklin.ga.cls;

import edu.franklin.ga.cls.fitness.FitnessDeterminant;
import edu.franklin.ga.cls.alg.CHCCataclysmicMutation;
import edu.franklin.ga.cls.alg.ElitistSurvivorSelection;
import edu.franklin.ga.cls.alg.HUXRecombination;
import edu.franklin.ga.cls.alg.RouletteParentSelection;
import edu.franklin.ga.cls.model.Gene;
import edu.franklin.ga.cls.util.GAResultSet;
import edu.franklin.ga.cls.util.GeneCollectionAnalyzer;

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
     * @param geneSize Gene size (chromosomes).
     * @param pC Crossover probability (ignored for CHC).
     * @param pCM Cataclysmic mutation probability.
     * @param termGeneration Number of generations to run,
     *        or null for fitness-based termination.
     * @param termFitness Fitness at which to halt,
     *        or null for generation-based termination.
     */
    public GAResultSet run(int populationSize, int geneSize,
            Double pC, Double pCM,
            Integer termGeneration, Double termFitness)
    {
        GAResultSet results = new GAResultSet();

        List<Gene> geneSet = new LinkedList<Gene>();
        for (int i = 0; i < populationSize; i += 1)
            geneSet.add(new Gene(geneSize));

        int threshold = Math.round(geneSet.size() / 4);

        Double gBest = null;
        for (int i = 0; true; i += 1)
        {
            if (gBest == null)
                gBest = GeneCollectionAnalyzer.bestFitness(geneSet, fd);
            else
                if (gBest < GeneCollectionAnalyzer.bestFitness(geneSet, fd))
                    gBest = GeneCollectionAnalyzer.bestFitness(geneSet, fd);

            if (termGeneration != null && i >= termGeneration)
                break;

            // If the last best fitnesses was at least as good as the
            // terminal best fitness, break out of run.
            if (termFitness != null && results.bestFitnesses != null
                    && termFitness <= results.bestFitnesses
                                        .get(results.bestFitnesses.size() - 1))
                break;

            List newGeneration = new LinkedList<Gene>();
            int matings = Math.round(geneSet.size() / 2);
            List<Gene> children = new LinkedList<Gene>();

            for (int j = 0; j < matings; j += 1)
            {
                List<Gene> parents = getParents(geneSet);
                Gene dad = parents.get(0);
                Gene mom = parents.get(1);

                if ((dad.hammingDistanceTo(mom) / 2) > threshold)
                    for (int k = 0; k < 2; k += 1)
                        children.add(recombinate(dad, mom, pC));

                if (children.size() == 0)
                {
                    newGeneration = geneSet;
                    threshold -= 1;
                }
                else
                {
                    newGeneration.addAll(geneSet);
                    newGeneration.addAll(children);

                    newGeneration = getSurvivors(newGeneration, geneSet.size());
                }

                if (threshold <= 0)
                {
                    geneSet = mutate(newGeneration, pCM);
                    threshold = (int) Math.round(pCM * (1.0 - pCM) * geneSize);
                }
            }
        }

        return results;
    }
}
