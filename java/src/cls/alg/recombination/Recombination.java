package cls.alg.recombination;

import cls.model.Chromosome;

public interface Recombination
{
    public Chromosome recombinate(Chromosome dad, Chromosome mom, Double pC);
}
