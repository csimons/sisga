package edu.franklin.ga.cls.alg;

import edu.franklin.ga.cls.model.Gene;

public interface Recombination
{
    public Gene recombinate(Gene dad, Gene mom, Double pC);
}
