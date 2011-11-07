package edu.franklin.ga.cls.alg.mutation.gene;

import edu.franklin.ga.cls.model.Gene;

public interface GeneMutation
{
    public Gene mutate(Gene gene, double pM);
}
