package edu.franklin.ga.cls.ga;

import edu.franklin.ga.cls.fitness.FitnessDeterminant;
import edu.franklin.ga.cls.alg.mutation.gene.RandomBitFlipMutation;
import edu.franklin.ga.cls.alg.recombination.SinglePointCrossover;
import edu.franklin.ga.cls.alg.selection.parent.RouletteParentSelection;
import edu.franklin.ga.cls.model.Gene;
import edu.franklin.ga.cls.util.GAResultSet;
import edu.franklin.ga.cls.util.GeneCollectionAnalyzer;

import java.util.LinkedList;
import java.util.List;

public class CanonicalGA extends AbstractGA
{
    public CanonicalGA(FitnessDeterminant fd)
    {
        super(fd,
                null,
                new RouletteParentSelection(fd),
                new SinglePointCrossover(),
                new RandomBitFlipMutation(),
                null);
    }

    /**
     * @param populationSize Population size.
     * @param geneSize Gene size (chromosomes).
     * @param pC Probability of recombination.
     * @param pM Probability of mutation.
     * @param termGeneration Number of generations to run,
     *        or null for fitness-based termination.
     * @param termFitness Fitness at which to halt,
     *        or null for generation-based termination.
     */
    public GAResultSet run(int populationSize, int geneSize,
            Double pC, Double pM,
            Integer termGeneration, Double termFitness)
    {
        GAResultSet results = new GAResultSet();

        List<Gene> geneSet = new LinkedList<Gene>();
        for (int i = 0; i < populationSize; i += 1)
            geneSet.add(new Gene(geneSize));

        Double gBest = null;
        for (int i = 0; true; i += 1)
        {
            if (gBest == null)
                gBest = GeneCollectionAnalyzer.bestFitness(geneSet, fd);
            else
                if (gBest < GeneCollectionAnalyzer.bestFitness(geneSet, fd))
                    gBest = GeneCollectionAnalyzer.bestFitness(geneSet, fd);

            results.bestFitnesses.add(gBest);
            results.avgFitnesses.add(GeneCollectionAnalyzer
                    .avgFitness(geneSet, fd));

            if (termGeneration != null && i >= termGeneration)
                break;

            // If the last best fitnesses was at least as good as the
            // terminal best fitness, break out of run.
            if (termFitness != null && results.bestFitnesses != null
                    && termFitness <= results.bestFitnesses
                                        .get(results.bestFitnesses.size() - 1))
                break;

            List<Gene> newGeneration = new LinkedList<Gene>();

            while (newGeneration.size() < geneSet.size())
            {
                List<Gene> parents = getParents(geneSet);
                Gene dad = parents.get(0);
                Gene mom = parents.get(1);
                Gene childA = mutate(recombinate(dad, mom, pC), pM);
                Gene childB = mutate(recombinate(dad, mom, pC), pM);
                newGeneration.add(childA);
                // in case of odd-numbered population sizes:
                if (newGeneration.size() < geneSet.size())
                    newGeneration.add(childA);
            }

            geneSet = newGeneration;
        }

        return results;
    }
}
