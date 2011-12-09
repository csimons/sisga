package cls.alg.ga;

import cls.alg.mutation.population.CHCCataclysmicMutation;
import cls.alg.recombination.HUXRecombination;
import cls.alg.selection.parent.RouletteParentSelection;
import cls.alg.selection.survivor.ElitistSurvivorSelection;
import cls.decode.Decoder;
import cls.fitness.Function;
import cls.model.Chromosome;
import cls.util.GAResultSet;
import cls.util.PopulationAnalyzer;
import java.util.LinkedList;
import java.util.List;

public class CHC extends AbstractGA
{
    private static final boolean debugging = false;

    private static void debug(String message)
    {
        if (debugging)
            System.out.println("DEBUGGING: " + message);
    }

    @Override
    public void init()
    {
        super.init();

        algSS = new ElitistSurvivorSelection(d, f);
        algPS = new RouletteParentSelection(d, f);
        algRec = new HUXRecombination();
        algMutP = new CHCCataclysmicMutation(d, f);
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
     * @param verbose Whether to print periodic statistics.
     */
    public GAResultSet run(int populationSize, int chromosomeSize,
            Double pC, Double pCM,
            Integer termGeneration, Double termFitness,
            boolean verbose)
    {
        GAResultSet results = new GAResultSet();

        List<Chromosome> population = new LinkedList<Chromosome>();
        for (int i = 0; i < populationSize; i += 1)
            population.add(new Chromosome(chromosomeSize));

        int threshold = Math.round(population.size() / 4);

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

            int matings = Math.round(population.size() / 2);
            List<Chromosome> children = new LinkedList<Chromosome>();
            List newGeneration = new LinkedList<Chromosome>();
            newGeneration.addAll(population);
            for (int j = 0; j < matings; j += 1)
            {
                List<Chromosome> parents = getParents(population);
                Chromosome dad = parents.get(0);
                Chromosome mom = parents.get(1);

                if ((dad.hammingDistanceTo(mom) / 2) > threshold)
                    for (int k = 0; k < 2; k += 1)
                        children.add(recombinate(dad, mom, pC));
            }

            if (children.isEmpty())
                threshold -= 1;
            else
                newGeneration.addAll(children);

debug("Children added: " + (newGeneration.size() - population.size()));
            if (threshold <= 0)
            {
debug("CATACLYSMIC MUTATION!");
                population = mutate(newGeneration, pCM);
                threshold = (int) Math.round(
                    pCM * (1.0 - pCM) * chromosomeSize);
            }

            newGeneration = getSurvivors(newGeneration, population.size());
        }

        return results;
    }
}
