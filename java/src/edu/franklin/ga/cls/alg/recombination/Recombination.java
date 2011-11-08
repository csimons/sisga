package edu.franklin.ga.cls.alg.recombination;

import edu.franklin.ga.cls.model.Chromosome;

public interface Recombination
{
    public Chromosome recombinate(Chromosome dad, Chromosome mom, Double pC);
}
