package cls.fitness;

import java.util.List;

public class Schwefel implements Function
{
    public double f(List<Double> inputs)
    {
        if (inputs.size() < 1)
            throw new IllegalArgumentException(
                    "Expecting at least one input variable.");

        double result = 0;

        for (Double i : inputs)
            result += (i * (-1)) * Math.sin(Math.sqrt(Math.abs(i)));

        return result;
    }
}
