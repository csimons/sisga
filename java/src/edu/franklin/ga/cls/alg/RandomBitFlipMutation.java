package edu.franklin.ga.cls.alg;

import edu.franklin.ga.cls.model.Gene;

public class RandomBitFlipMutation implements GeneMutation
{
    public RandomBitFlipMutation() {}

    public Gene mutate(Gene gene, double pM)
    {
        if (Math.random() < pM)
            gene.flip(gene.getRandIndex());

        return gene;
    }
}
