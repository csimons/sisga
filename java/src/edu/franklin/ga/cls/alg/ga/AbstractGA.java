package edu.franklin.ga.cls.ga;

import edu.franklin.ga.cls.alg.mutation.gene.GeneMutation;
import edu.franklin.ga.cls.alg.mutation.geneset.GeneSetMutation;
import edu.franklin.ga.cls.alg.selection.parent.ParentSelection;
import edu.franklin.ga.cls.alg.selection.survivor.SurvivorSelection;
import edu.franklin.ga.cls.alg.recombination.Recombination;
import edu.franklin.ga.cls.fitness.FitnessDeterminant;
import edu.franklin.ga.cls.model.Gene;
import edu.franklin.ga.cls.util.GAResultSet;

import java.util.List;

public abstract class AbstractGA implements GA
{
    protected FitnessDeterminant    fd;
    protected SurvivorSelection     algSS;
    protected ParentSelection       algPS;
    protected Recombination         algRec;
    protected GeneMutation          algMutG;
    protected GeneSetMutation       algMutGS;

    public AbstractGA(
            FitnessDeterminant  fd,
            SurvivorSelection   algSS,
            ParentSelection     algPS,
            Recombination       algRec,
            GeneMutation        algMutG,
            GeneSetMutation     algMutGS )
    {
        this.fd         = fd;
        this.algSS      = algSS;
        this.algPS      = algPS;
        this.algRec     = algRec;
        this.algMutG    = algMutG;
        this.algMutGS   = algMutGS;
    }

    /**
     * @param populationSize Population size.
     * @param geneSize Gene size (chromosomes).
     * @param pC Crossover probability.
     * @param pM Mutation probability.
     * @param termGeneration Number of generations to run,
     *        or null for fitness-based termination.
     * @param termFitness Fitness at which to halt,
     *        or null for generation-based termination.
     */
    public abstract GAResultSet run(int populationSize, int geneSize,
            Double pC, Double pM,
            Integer termGeneration, Double termFitness);

    protected double fitness(Gene gene)
    {
        return fd.fitness(gene);
    }

    protected List<Gene> getSurvivors(List<Gene> geneSet, int targetPopSize)
    {
        return algSS.select(geneSet, targetPopSize);
    }

    protected List<Gene> getParents(List<Gene> geneSet)
    {
        return algPS.select(geneSet);
    }

    protected Gene recombinate(Gene a, Gene b, double pC)
    {
        return algRec.recombinate(a, b, pC);
    }

    protected Gene mutate(Gene gene, double pM)
    {
        return algMutG.mutate(gene, pM);
    }

    protected List<Gene> mutate(List<Gene> geneSet, double pM)
    {
        return algMutGS.mutate(geneSet, pM);
    }
}
