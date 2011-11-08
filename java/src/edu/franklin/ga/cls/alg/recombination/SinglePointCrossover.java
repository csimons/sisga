package edu.franklin.ga.cls.alg.recombination;

import edu.franklin.ga.cls.model.Chromosome;

public class SinglePointCrossover implements Recombination
{
    public SinglePointCrossover() {}

    /*
     * If our random number lies within the provided probability, we
     * crossover the two chromosomes at a random allele point.  Otherwise,
     * we randomly select one of the two chromosomes and return it.
     */
    public Chromosome recombinate(Chromosome dad, Chromosome mom, Double pC)
    {
        if (dad.size() != mom.size())
            throw new IllegalArgumentException("Arity of operators unequal.");

        Chromosome child;

        if (Math.random() < pC)
        {
            child = new Chromosome(dad.size());

            int alleleIndex = dad.getRandIndex();

            for (int i = 0; i < child.size(); i += 1)
                child.set(i, i < alleleIndex ? dad.get(i) : mom.get(i));
        }
        else
            child = Math.round(Math.random()) == 0 ? dad : mom;

        return child;
    }
}
