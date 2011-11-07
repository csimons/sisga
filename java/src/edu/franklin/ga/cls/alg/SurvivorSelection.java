package edu.franklin.ga.cls.alg;

import edu.franklin.ga.cls.model.Gene;

import java.util.List;

public interface SurvivorSelection
{
    public List<Gene> select(List<Gene> geneSet, int targetPopulationSize);
}
