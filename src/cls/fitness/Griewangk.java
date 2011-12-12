package cls.fitness;

import java.util.List;

public class Griewangk implements Function
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

        double result = 0;

        double sumPart = 0;
        for (Double i : inputs)
            sumPart += (i / 4000);

        double productPart = 1;
        for (int i = 1; i <= inputs.size(); i += 1)
            productPart *= Math.cos(inputs.get(i - 1) / Math.sqrt(i));

        result = 1 + sumPart - productPart;

        return result;
    }
}
