package edu.franklin.ga.cls.alg.recombination;

import edu.franklin.ga.cls.model.Gene;

public interface Recombination
{
    public Gene recombinate(Gene dad, Gene mom, Double pC);
}
