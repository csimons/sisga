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

package com.oracli.sisga.test.fitness;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.oracli.sisga.fitness.IdentityFunction;

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
