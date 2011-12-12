package cls.fitness;

import java.util.List;

/*
 * See Kenneth De Jong's doctoral thesis for details.
 *
 * Available at the time of this writing at:
 *
 * http://cs.gmu.edu/~eclab/kdj_thesis.html
 */
public class DeJongF2 implements Function
{
    public double f(List<Double> inputs)
    {
        if (inputs.size() != 2)
            throw new IllegalArgumentException(
                    "Expecting 2 input variables.");

        double result = 0;

        double x = inputs.get(0);
        double y = inputs.get(1);

        /*
         * 100(x^2 - y)^2 + (1 - x)^2
         */
        result = (100 * ((x * x) - y) * ((x * x) - y)) + ((1 - x) * (1 - x));

        return result;
    }
}
