package edu.franklin.ga.cls.fitness;

import edu.franklin.ga.cls.model.Gene;

public class OneMaxFitnessDeterminant implements FitnessDeterminant
{
    public double fitness(Gene gene)
    {
        int result = 0;

        for (int i = 0; i < gene.size(); i += 1)
            if (gene.get(i))
                result += 1;

        return result;
    }
}
