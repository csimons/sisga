package edu.franklin.ga.cls.alg.selection.survivor;

import edu.franklin.ga.cls.fitness.FitnessDeterminant;
import edu.franklin.ga.cls.model.Gene;

import java.util.LinkedList;
import java.util.List;

/*
 * Tournament truncation survivor selection.
 *
 * Randomly pick two genes from the population
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

    public List<Gene> select(List<Gene> geneSet, int targetPopulationSize)
    {
        if (targetPopulationSize > geneSet.size())
            throw new IllegalArgumentException("targetSize > geneSetSize");

        List<Gene> survivorSet = new LinkedList<Gene>();

        while(geneSet.size() > targetPopulationSize)
        {
            int indexA = (int) Math.random() * (geneSet.size() + 1);
            int indexB = (int) Math.random() * (geneSet.size() + 1);

            Gene a = geneSet.get(indexA);
            Gene b = geneSet.get(indexB);

            geneSet.remove(fd.fitness(a) < fd.fitness(b) ? a : b);
        }

        return geneSet;
    }
}
