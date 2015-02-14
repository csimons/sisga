/* Copyright (c) 2011, 2012 Christopher L. Simons
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
