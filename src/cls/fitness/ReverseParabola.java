package cls.fitness;

import java.util.List;

public class ReverseParabola implements Function
{
    private double max;

    public ReverseParabola(double max)
    {
        this.max = max;
    }

    public double f(List<Double> inputs)
    {
        if (inputs.size() != 1)
            throw new IllegalArgumentException(
                    "Expecting  1 input variable.");

        double x = inputs.get(0);

        return max - (x * x);
    }
}
