package cls.fitness;

import cls.model.Chromosome;

public class OneMaxFitnessDeterminant implements FitnessDeterminant
{
    public double fitness(Chromosome chromosome)
    {
        int result = 0;

        for (int i = 0; i < chromosome.size(); i += 1)
            if (chromosome.get(i))
                result += 1;

        return result;
    }
}
