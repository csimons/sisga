package cls.fitness;

import java.util.List;

public class Rosenbrock implements Function
{
    public double f(List<Double> inputs)
    {
        if (inputs.size() != 2)
            throw new IllegalArgumentException(
                    "Expecting 2 input variables.");

        double x = inputs.get(0);
        double y = inputs.get(1);

        double result = 0;

        /*
         * (1 - x)^2 + 100(y - x^2)^2
         */
        result = ((1 - x) * (1 - x)) + (100 * ((y - (x * x)) * (y - (x * x))));

        return result;
    }
}
