package cls.fitness;

import java.util.List;

public class IdentityFunction implements Function
{
    public double f(List<Double> inputs)
    {
        if (inputs.size() != 1)
            throw new IllegalArgumentException(
                    "Expecting  1 input variable.");

        return inputs.get(0);
    }
}
