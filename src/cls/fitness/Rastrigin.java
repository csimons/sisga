package cls.fitness;

import java.util.List;

public class Rastrigin implements Function
{
    public double f(List<Double> inputs)
    {
        if (inputs.size() < 1)
            throw new IllegalArgumentException(
                    "Expecting at least one input variable.");

        for (Double i : inputs)
            if ((i > 5.12) || (i < (-5.12)))
                throw new IllegalArgumentException(
                    "Inputs must be in range -5.12 <= x <= 5.12.");

        int A = 10;
        double result = 0;

        for (Double i : inputs)
            result += (i * i) - (10 * Math.cos(2 * Math.PI * i));

        result += (A * inputs.size());

        return result;
    }
}
