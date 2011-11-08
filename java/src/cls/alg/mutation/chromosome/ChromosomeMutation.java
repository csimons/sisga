package cls.alg.mutation.chromosome;

import cls.model.Chromosome;

public interface ChromosomeMutation
{
    public Chromosome mutate(Chromosome chromosome, double pM);
}
