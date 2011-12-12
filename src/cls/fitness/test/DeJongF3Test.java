package cls.fitness.test;

import cls.fitness.DeJongF3;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeJongF3Test
{
    @Test
    public void expectedValues()
    {
        assertEquals(10, new DeJongF3().f(wrap(0.0, 1.0, 2.0, 3.0, 4.0)), 0.0001);
        assertEquals(10, new DeJongF3().f(wrap(0.5, 1.5, 2.5, 3.5, 4.5)), 0.0001);

        assertEquals(15, new DeJongF3().f(wrap(1.0, 2.0, 3.0, 4.0, 5.0)), 0.0001);
        assertEquals(15, new DeJongF3().f(wrap(1.5, 2.5, 3.5, 4.5, 5.5)), 0.0001);
    }

    private List<Double> wrap (double x1, double x2, double x3, double x4, double x5)
    {
        List<Double> variables = new LinkedList<Double>();

        variables.add(x1);
        variables.add(x2);
        variables.add(x3);
        variables.add(x4);
        variables.add(x5);

        return variables;
    }
}
