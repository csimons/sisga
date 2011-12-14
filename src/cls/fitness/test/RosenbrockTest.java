package cls.fitness.test;

import cls.fitness.Rosenbrock;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RosenbrockTest
{
    @Test
    public void expectedValues()
    {
        assertEquals(- 101, new Rosenbrock().f(wrap(0, 1)), 0.0001);
        assertEquals(- 100, new Rosenbrock().f(wrap(1, 0)), 0.0001);
        assertEquals(- 100, new Rosenbrock().f(wrap(1, 2)), 0.0001);
        assertEquals(- 901, new Rosenbrock().f(wrap(2, 1)), 0.0001);
        assertEquals(- 101, new Rosenbrock().f(wrap(2, 3)), 0.0001);
        assertEquals(-4904, new Rosenbrock().f(wrap(3, 2)), 0.0001);
    }

    private List<Double> wrap (double x, double y)
    {
        List<Double> variables = new LinkedList<Double>();

        variables.add(x);
        variables.add(y);

        return variables;
    }
}
