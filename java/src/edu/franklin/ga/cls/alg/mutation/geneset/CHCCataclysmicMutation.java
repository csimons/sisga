package edu.franklin.ga.cls.alg.mutation.geneset;

import edu.franklin.ga.cls.fitness.FitnessDeterminant;
import edu.franklin.ga.cls.model.Gene;
import edu.franklin.ga.cls.util.GeneCollectionAnalyzer;

import java.util.List;

/*
 * For every bit in every gene in the gene set, except for the
 * gene with the highest fitness, flip per probability 'p' (pCM).
 */
public class CHCCataclysmicMutation implements GeneSetMutation
{
    private FitnessDeterminant fd;

    private CHCCataclysmicMutation() {}

    public CHCCataclysmicMutation(FitnessDeterminant fd)
    {
        this.fd = fd;
    }

    public List<Gene> mutate(List<Gene> geneSet, double pCM)
    {
        Gene bestFitGene = null;

        double bestFitness = GeneCollectionAnalyzer
            .bestFitness(geneSet, fd);

        for (Gene gene : geneSet)
            if (fd.fitness(gene) == bestFitness)
                bestFitGene = gene;

        if (bestFitGene == null)
            throw new IllegalStateException(
                "Unable to determine best-fit gene.");

        for (Gene gene : geneSet)
            if (gene != bestFitGene)
                for (int i = 0; i < gene.size(); i += 1)
                    if (Math.random() < pCM)
                        gene.flip(i);

        return geneSet;
    }
}
