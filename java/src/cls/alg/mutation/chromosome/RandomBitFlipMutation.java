package cls.alg.mutation.chromosome;

import cls.model.Chromosome;

public class RandomBitFlipMutation implements ChromosomeMutation
{
    public RandomBitFlipMutation() {}

    public Chromosome mutate(Chromosome chromosome, double pM)
    {
        if (Math.random() < pM)
            chromosome.flip(chromosome.getRandIndex());

        return chromosome;
    }
}
