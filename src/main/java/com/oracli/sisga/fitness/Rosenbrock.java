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

package com.oracli.sisga.fitness;

import java.util.List;

/*
 * Also known as F2 in Kenneth De Jong's test suite.
 * See Kenneth De Jong's doctoral thesis for details.
 * Available at the time of this writing at:
 *
 * http://cs.gmu.edu/~eclab/kdj_thesis.html
 */
public class Rosenbrock implements Function
{
	public double f(List<Double> inputs)
	{
		if (inputs.size() != 2)
			throw new IllegalArgumentException(
					"Expecting 2 input variables.");

		double result = 0;

		double x = inputs.get(0);
		double y = inputs.get(1);

		/*
		 * 100(x^2 - y)^2 + (1 - x)^2
		 */
		result = (100 * ((x * x) - y) * ((x * x) - y)) + ((1 - x) * (1 - x));

		result *= (-1);
		return result;
	}
}
