package cls.fitness.test;

import cls.fitness.IdentityFunction;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IdentityFunctionTest
{
    @Test
    public void expectedValues()
    {
        assertEquals(  0, new IdentityFunction().f(wrap(0.0)), 0.0001);
        assertEquals(  1, new IdentityFunction().f(wrap(1.0)), 0.0001);
        assertEquals(2.5, new IdentityFunction().f(wrap(2.5)), 0.0001);
    }

    private List<Double> wrap (double x)
    {
        List<Double> variables = new LinkedList<Double>();

        variables.add(x);

        return variables;
    }
}
