package edu.franklin.ga.cls.alg.selection.parent;

import edu.franklin.ga.cls.model.Gene;

import java.util.LinkedList;
import java.util.List;

public class RandomParentSelection implements ParentSelection
{
    public List<Gene> select(List<Gene> geneSet)
    {
        int indexA = (int) Math.random() * (geneSet.size() + 1);
        int indexB = (int) Math.random() * (geneSet.size() + 1);

        List<Gene> genes = new LinkedList<Gene>();
        genes.add(geneSet.get(indexA));
        genes.add(geneSet.get(indexB));

        return genes;
    }
}
