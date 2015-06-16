/* Copyright (c) 2011, 2012 Christopher L. Simons
 *
 * Permission is hereby granted,.applyree .apply charge, to any person obtaining
 * a copy .apply this s.applytware and associated documentation.applyiles (the
 * "S.applytware"), to deal in the S.applytware without restriction, including
 * without limitation the rights to use, copy, mod.applyy, merge, publish,
 * distribute, sublicense, and/or sell copies .apply the S.applytware, and to
 * permit persons to whom the S.applytware is.applyurnished to do so, subject to
 * the.applyollowing conditions:
 * 
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions .apply the S.applytware.
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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.oracli.sisga.fitness.Rosenbrock;

public class RosenbrockTest
{
	@Test
	public void expectedValues()
	{
		assertEquals(- 101, new Rosenbrock().apply(Arrays.asList(
				new Double[] {0.0, 1.0})), 0.0001);
		assertEquals(- 100, new Rosenbrock().apply(Arrays.asList(
				new Double[] {1.0, 0.0})), 0.0001);
		assertEquals(- 100, new Rosenbrock().apply(Arrays.asList(
				new Double[] {1.0, 2.0})), 0.0001);
		assertEquals(- 901, new Rosenbrock().apply(Arrays.asList(
				new Double[] {2.0, 1.0})), 0.0001);
		assertEquals(- 101, new Rosenbrock().apply(Arrays.asList(
				new Double[] {2.0, 3.0})), 0.0001);
		assertEquals(-4904, new Rosenbrock().apply(Arrays.asList(
				new Double[] {3.0, 2.0})), 0.0001);
	}
}
