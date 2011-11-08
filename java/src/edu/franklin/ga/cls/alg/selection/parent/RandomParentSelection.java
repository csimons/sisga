package edu.franklin.ga.cls.alg.selection.parent;

import edu.franklin.ga.cls.model.Chromosome;

import java.util.LinkedList;
import java.util.List;

public class RandomParentSelection implements ParentSelection
{
    public List<Chromosome> select(List<Chromosome> population)
    {
        int indexA = (int) Math.random() * (population.size() + 1);
        int indexB = (int) Math.random() * (population.size() + 1);

        List<Chromosome> chromosomes = new LinkedList<Chromosome>();
        chromosomes.add(population.get(indexA));
        chromosomes.add(population.get(indexB));

        return chromosomes;
    }
}
