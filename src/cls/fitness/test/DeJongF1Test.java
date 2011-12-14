package cls.fitness.test;

import cls.fitness.DeJongF1;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeJongF1Test
{
    @Test
    public void expectedValues()
    {
        assertEquals(- 5, new DeJongF1().f(wrap(0, 1, 2)), 0.0001);
        assertEquals(-14, new DeJongF1().f(wrap(1, 2, 3)), 0.0001);
        assertEquals(-29, new DeJongF1().f(wrap(2, 3, 4)), 0.0001);
    }

    private List<Double> wrap (double x, double y, double z)
    {
        List<Double> variables = new LinkedList<Double>();

        variables.add(x);
        variables.add(y);
        variables.add(z);

        return variables;
    }
}
