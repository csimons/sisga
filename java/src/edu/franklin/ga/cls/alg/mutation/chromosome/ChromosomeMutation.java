package edu.franklin.ga.cls.alg.mutation.chromosome;

import edu.franklin.ga.cls.model.Chromosome;

public interface ChromosomeMutation
{
    public Chromosome mutate(Chromosome chromosome, double pM);
}
