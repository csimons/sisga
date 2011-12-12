package cls.fitness.test;

import cls.fitness.DeJongF2;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeJongF2Test
{
    @Test
    public void expectedValues()
    {
        assertEquals( 101, new DeJongF2().f(wrap(0, 1)), 0.0001);
        assertEquals( 100, new DeJongF2().f(wrap(1, 0)), 0.0001);
        assertEquals( 100, new DeJongF2().f(wrap(1, 2)), 0.0001);
        assertEquals( 901, new DeJongF2().f(wrap(2, 1)), 0.0001);
        assertEquals( 101, new DeJongF2().f(wrap(2, 3)), 0.0001);
        assertEquals(4904, new DeJongF2().f(wrap(3, 2)), 0.0001);
    }

    private List<Double> wrap (double x, double y)
    {
        List<Double> variables = new LinkedList<Double>();

        variables.add(x);
        variables.add(y);

        return variables;
    }
}
