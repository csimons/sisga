package cls.fitness.test;

import cls.fitness.ReverseParabola;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReverseParabolaTest
{
    @Test
    public void expectedValues()
    {
        assertEquals(0, new ReverseParabola(0).f(wrap(0)), 0.0001);
        assertEquals(100, new ReverseParabola(100).f(wrap(0)), 0.0001);
        assertEquals(-100, new ReverseParabola(-100).f(wrap(0)), 0.0001);
    }

    private List<Double> wrap (double x)
    {
        List<Double> variables = new LinkedList<Double>();
        variables.add(x);
        return variables;
    }
}
