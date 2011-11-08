package edu.franklin.ga.cls.ga;

import edu.franklin.ga.cls.alg.mutation.chromosome.ChromosomeMutation;
import edu.franklin.ga.cls.alg.mutation.population.PopulationMutation;
import edu.franklin.ga.cls.alg.selection.parent.ParentSelection;
import edu.franklin.ga.cls.alg.selection.survivor.SurvivorSelection;
import edu.franklin.ga.cls.alg.recombination.Recombination;
import edu.franklin.ga.cls.fitness.FitnessDeterminant;
import edu.franklin.ga.cls.model.Chromosome;
import edu.franklin.ga.cls.util.GAResultSet;

import java.util.List;

public abstract class AbstractGA implements GA
{
    protected FitnessDeterminant    fd;
    protected SurvivorSelection     algSS;
    protected ParentSelection       algPS;
    protected Recombination         algRec;
    protected ChromosomeMutation    algMutC;
    protected PopulationMutation    algMutP;

    public AbstractGA(
            FitnessDeterminant  fd,
            SurvivorSelection   algSS,
            ParentSelection     algPS,
            Recombination       algRec,
            ChromosomeMutation  algMutC,
            PopulationMutation  algMutP )
    {
        this.fd         = fd;
        this.algSS      = algSS;
        this.algPS      = algPS;
        this.algRec     = algRec;
        this.algMutC    = algMutC;
        this.algMutP    = algMutP;
    }

    /**
     * @param populationSize Population size.
     * @param chromosomeSize Chromosome size (genes per chromosome).
     * @param pC Crossover probability.
     * @param pM Mutation probability.
     * @param termGeneration Number of generations to run,
     *        or null for fitness-based termination.
     * @param termFitness Fitness at which to halt,
     *        or null for generation-based termination.
     */
    public abstract GAResultSet run(int populationSize, int chromosomeSize,
            Double pC, Double pM,
            Integer termGeneration, Double termFitness);

    protected double fitness(Chromosome chromosome)
    {
        return fd.fitness(chromosome);
    }

    protected List<Chromosome> getSurvivors(List<Chromosome> population,
            int targetPopSize)
    {
        return algSS.select(population, targetPopSize);
    }

    protected List<Chromosome> getParents(List<Chromosome> population)
    {
        return algPS.select(population);
    }

    protected Chromosome recombinate(Chromosome a, Chromosome b, double pC)
    {
        return algRec.recombinate(a, b, pC);
    }

    protected Chromosome mutate(Chromosome chromosome, double pM)
    {
        return algMutC.mutate(chromosome, pM);
    }

    protected List<Chromosome> mutate(List<Chromosome> population, double pM)
    {
        return algMutP.mutate(population, pM);
    }
}
