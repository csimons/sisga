package cls.fitness;

import java.util.List;

public class DeJongF3 implements Function
{
    public double f(List<Double> inputs)
    {
        if (inputs.size() != 5)
            throw new IllegalArgumentException(
                    "Expecting 5 input variables.");

        double result = 0;

        for (Double i : inputs)
            result += Math.floor(i);

        return result;
    }
}
