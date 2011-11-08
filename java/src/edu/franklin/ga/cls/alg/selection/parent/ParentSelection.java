package edu.franklin.ga.cls.alg.selection.parent;

import edu.franklin.ga.cls.model.Chromosome;

import java.util.List;

public interface ParentSelection
{
    public List<Chromosome> select(List<Chromosome> population);
}
