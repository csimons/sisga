package cls.fitness.function;

import java.util.List;

public class ReverseParabola implements Function
{
    public double f(List<Double> inputs)
    {
        if (inputs.size() != 1)
            throw new IllegalArgumentException(
                    "Expecting  1 input variable.");

        double x = inputs.get(0);

        return 100 - (x * x);
    }
}
