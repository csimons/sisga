package cls.fitness.function.test;

import cls.fitness.function.ReverseParabola;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReverseParabolaTest
{
    @Test
    public void expectedValues()
    {
        assertEquals(100, new ReverseParabola().f(wrap(0)), 0.0001);
    }

    private List<Double> wrap (double x)
    {
        List<Double> variables = new LinkedList<Double>();
        variables.add(x);
        return variables;
    }
}
