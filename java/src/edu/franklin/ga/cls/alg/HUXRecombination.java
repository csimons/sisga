package edu.franklin.ga.cls.alg;

import edu.franklin.ga.cls.model.Gene;

import java.util.LinkedList;
import java.util.List;

/*
 * HUX
 *
 * "Crosses over exactly half the non-matching alleles, where the
 *  bits to be exchanged are chosen at random without replacement."
 *
 *  (Eshelman, p. 271)
 */
public class HUXRecombination implements Recombination
{
    public HUXRecombination() {}

    /**
     * @param dad First parent gene.
     * @param mom Second parent gene.
     * @param pC Recombination probability; gnored for HUXRecombination.
     */
    public Gene recombinate(Gene dad, Gene mom, Double pC)
    {
        if (dad.size() != mom.size())
            throw new IllegalArgumentException("Arity of operators unequal.");

        List<Integer> nonMatchingIndices = new LinkedList<Integer>();
        List<Integer> swapAlleles = new LinkedList<Integer>();

        for (int i = 0; i < dad.size(); i += 1)
            if (dad.get(i) != mom.get(i))
                nonMatchingIndices.add(i);

        for (Integer i : nonMatchingIndices)
            if (Math.round(Math.random()) == 0)
                swapAlleles.add(i);

        Gene child = new Gene(dad.size());

        for (int i = 0; i < child.size(); i += 1)
            child.set(i, swapAlleles.contains(i) ? mom.get(i) : dad.get(i));

        return child;
    }
}
