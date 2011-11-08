package edu.franklin.ga.cls.alg.mutation.population;

import edu.franklin.ga.cls.model.Chromosome;

import java.util.List;

public interface PopulationMutation
{
    public List<Chromosome> mutate(List<Chromosome> population, double pM);
}
