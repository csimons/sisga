package edu.franklin.ga.cls.alg.selection.survivor;

import edu.franklin.ga.cls.model.Chromosome;

import java.util.List;

public interface SurvivorSelection
{
    public List<Chromosome> select(List<Chromosome> population,
            int targetPopulationSize);
}
