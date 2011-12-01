package cls.alg.ga;

import cls.alg.mutation.chromosome.RandomBitFlipMutation;
import cls.alg.recombination.SinglePointCrossover;
import cls.alg.selection.parent.RouletteParentSelection;
import cls.decode.Decoder;
import cls.fitness.Function;
import cls.model.Chromosome;
import cls.util.GAResultSet;
import cls.util.PopulationAnalyzer;
import java.util.LinkedList;
import java.util.List;

public class CanonicalGA extends AbstractGA
{
    @Override
    public void init()
    {
        super.init();

        algPS = new RouletteParentSelection(d, f);
        algRec = new SinglePointCrossover();
        algMutC = new RandomBitFlipMutation();
    }

    /**
     * @param populationSize Population size.
     * @param chromosomeSize Chromosome size (genes per chromosome).
     * @param pC Probability of recombination.
     * @param pM Probability of mutation.
     * @param termGeneration Number of generations to run,
     *        or null for fitness-based termination.
     * @param termFitness Fitness at which to halt,
     *        or null for generation-based termination.
     * @param verbose Whether to print periodic statistics.
     */
    public GAResultSet run(int populationSize, int chromosomeSize,
            Double pC, Double pM,
            Integer termGeneration, Double termFitness,
            boolean verbose)
    {
        GAResultSet results = new GAResultSet();

        List<Chromosome> population = new LinkedList<Chromosome>();
        for (int i = 0; i < populationSize; i += 1)
            population.add(new Chromosome(chromosomeSize));

        Double gBest  = null;
        Double gWorst = null;
        for (int i = 0; i <= termGeneration
            && (gBest == null || gBest < termFitness); i += 1)
        {
            if (gBest == null)
                gBest = PopulationAnalyzer.bestFitness(population, d, f);
            else
                if (gBest < PopulationAnalyzer.bestFitness(population, d, f))
                    gBest = PopulationAnalyzer.bestFitness(population, d, f);

            if (gWorst == null)
                gWorst = PopulationAnalyzer.worstFitness(population, d, f);
            else
                if (gWorst > PopulationAnalyzer.worstFitness(population, d, f))
                    gWorst = PopulationAnalyzer.worstFitness(population, d, f);

            results.bestFitnesses.add(gBest);
            results.avgFitnesses.add(PopulationAnalyzer
                    .avgFitness(population, d, f));
            results.worstFitnesses.add(gWorst);

            List<Chromosome> newGeneration = new LinkedList<Chromosome>();
            while (newGeneration.size() < population.size())
            {
                List<Chromosome> parents = getParents(population);
                Chromosome dad = parents.get(0);
                Chromosome mom = parents.get(1);
                Chromosome childA = mutate(recombinate(dad, mom, pC), pM);
                Chromosome childB = mutate(recombinate(dad, mom, pC), pM);
                newGeneration.add(childA);
                // in case of odd-numbered population sizes:
                if (newGeneration.size() < population.size())
                    newGeneration.add(childB);
            }

            population = newGeneration;
        }

        return results;
    }
}
