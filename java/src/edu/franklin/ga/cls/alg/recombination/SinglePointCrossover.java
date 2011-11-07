package edu.franklin.ga.cls.alg.recombination;

import edu.franklin.ga.cls.model.Gene;

public class SinglePointCrossover implements Recombination
{
    public SinglePointCrossover() {}

    /*
     * If our random number lies within the provided probability, we
     * crossover the two genes at a random allele point.  Otherwise,
     * we randomly select one of the two genes and return it.
     */
    public Gene recombinate(Gene dad, Gene mom, Double pC)
    {
        if (dad.size() != mom.size())
            throw new IllegalArgumentException("Arity of operators unequal.");

        Gene child;

        if (Math.random() < pC)
        {
            child = new Gene(dad.size());

            int alleleIndex = dad.getRandIndex();

            for (int i = 0; i < child.size(); i += 1)
                child.set(i, i < alleleIndex ? dad.get(i) : mom.get(i));
        }
        else
            child = Math.round(Math.random()) == 0 ? dad : mom;

        return child;
    }
}
