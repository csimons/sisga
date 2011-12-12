package cls.fitness.test;

import cls.fitness.Griewangk;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GriewangkTest
{
    @Test
    public void expectedValues()
    {
        assertEquals(0, new Griewangk().f(wrap(0)), 0.0001);

        /*
         * TODO: Add more tests.
         */
    }

    private List<Double> wrap(double x)
    {
        List<Double> variables = new LinkedList<Double>();

        variables.add(x);

        return variables;
    }

    private List<Double> wrap(double x, double y)
    {
        List<Double> variables = new LinkedList<Double>();

        variables.add(x);
        variables.add(y);

        return variables;
    }
}
