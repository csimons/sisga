package cls.alg.recombination;

import cls.model.Chromosome;

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
     * @param dad First parent chromosome.
     * @param mom Second parent chromosome.
     * @param pC Recombination probability; gnored for HUXRecombination.
     */
    public Chromosome recombinate(Chromosome dad, Chromosome mom, Double pC)
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

        Chromosome child = new Chromosome(dad.size());

        for (int i = 0; i < child.size(); i += 1)
            child.set(i, swapAlleles.contains(i) ? mom.get(i) : dad.get(i));

        return child;
    }
}
