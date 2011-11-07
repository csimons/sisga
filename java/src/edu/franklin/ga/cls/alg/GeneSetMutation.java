package edu.franklin.ga.cls.alg;

import edu.franklin.ga.cls.model.Gene;

import java.util.List;

public interface GeneSetMutation
{
    public List<Gene> mutate(List<Gene> geneSet, double pM);
}
