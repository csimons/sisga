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
