package cls.alg.selection.parent;

import cls.decode.Decoder;
import cls.fitness.Function;
import cls.model.Chromosome;
import java.util.LinkedList;
import java.util.List;

/*
 * Roulette (fitness-proportionate) selection.
 */
public class RouletteParentSelection implements ParentSelection
{
    private Decoder d;
    private Function f;

    private RouletteParentSelection() {}

    public RouletteParentSelection(Decoder d, Function f)
    {
        this.d = d;
        this.f = f;
    }

    public List<Chromosome> select(List<Chromosome> population)
    {
        List<Double> absoluteFitnesses = new LinkedList<Double>();
        List<Double> proportionalFitnesses = new LinkedList<Double>();

        double totalFitness = 0;

        for (Chromosome i : population)
        {
            double fitness = f.f(d.decode(i));
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
