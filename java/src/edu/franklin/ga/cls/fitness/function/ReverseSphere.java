package edu.franklin.ga.cls.fitness.function;

import java.util.List;

public class ReverseSphere implements Function
{
    public double f(List<Double> inputs)
    {
        if (inputs.size() < 1)
            throw new IllegalArgumentException(
                    "Expecting >= 1 input variables.");

        double result = 0;

        for (Double i : inputs)
            result -= (i * i);

        return result;
    }
}
