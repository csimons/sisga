package cls.fitness;

import java.util.List;

/*
 * See Kenneth De Jong's doctoral thesis for details.
 *
 * Available at the time of this writing at:
 *
 * http://cs.gmu.edu/~eclab/kdj_thesis.html
 */
public class DeJongF1 implements Function
{
    public double f(List<Double> inputs)
    {
        if (inputs.size() < 1)
            throw new IllegalArgumentException(
                    "Expecting at least one input variable.");

        double result = 0;

        for (Double i : inputs)
            result += (i * i);

        result *= (-1);
        return result;
    }
}
