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

package com.oracli.sisga.test.decode;

import org.junit.Test;

import com.oracli.sisga.decode.Decoder;
import com.oracli.sisga.decode.RealDecoder;
import com.oracli.sisga.model.Chromosome;

import static com.oracli.sisga.test.decode.Util.stringToChromosome;
import static org.junit.Assert.assertEquals;

public class RealDecoderTest
{
	private Chromosome s2c(String bitString)
	{
		return stringToChromosome(bitString);
	}

	@Test
	public void oneThroughEight()
	{
		Decoder decoder = new RealDecoder();
		decoder.setPrecision(0);

		assertEquals(0, decoder.decode(s2c("00")).get(0), 0.0001);
		assertEquals(1, decoder.decode(s2c("01")).get(0), 0.0001);
		assertEquals(2, decoder.decode(s2c("010")).get(0), 0.0001);
		assertEquals(3, decoder.decode(s2c("011")).get(0), 0.0001);
		assertEquals(4, decoder.decode(s2c("0100")).get(0), 0.0001);
		assertEquals(5, decoder.decode(s2c("0101")).get(0), 0.0001);
		assertEquals(6, decoder.decode(s2c("0110")).get(0), 0.0001);
		assertEquals(7, decoder.decode(s2c("0111")).get(0), 0.0001);
		assertEquals(8, decoder.decode(s2c("01000")).get(0), 0.0001);
	}

	@Test
	public void negativeOneThroughEight()
	{
		Decoder decoder = new RealDecoder();
		decoder.setPrecision(0);

		assertEquals( 0, decoder.decode(s2c("10")).get(0), 0.0001);
		assertEquals(-1, decoder.decode(s2c("11")).get(0), 0.0001);
		assertEquals(-2, decoder.decode(s2c("110")).get(0), 0.0001);
		assertEquals(-3, decoder.decode(s2c("111")).get(0), 0.0001);
		assertEquals(-4, decoder.decode(s2c("1100")).get(0), 0.0001);
		assertEquals(-5, decoder.decode(s2c("1101")).get(0), 0.0001);
		assertEquals(-6, decoder.decode(s2c("1110")).get(0), 0.0001);
		assertEquals(-7, decoder.decode(s2c("1111")).get(0), 0.0001);
		assertEquals(-8, decoder.decode(s2c("11000")).get(0), 0.0001);
	}

	@Test
	public void oneThroughEightTimesPointOhOne()
	{
		Decoder decoder = new RealDecoder();
		decoder.setPrecision(2);

		assertEquals(0.00, decoder.decode(s2c("00")).get(0), 0.0001);
		assertEquals(0.01, decoder.decode(s2c("01")).get(0), 0.0001);
		assertEquals(0.02, decoder.decode(s2c("010")).get(0), 0.0001);
		assertEquals(0.03, decoder.decode(s2c("011")).get(0), 0.0001);
		assertEquals(0.04, decoder.decode(s2c("0100")).get(0), 0.0001);
		assertEquals(0.05, decoder.decode(s2c("0101")).get(0), 0.0001);
		assertEquals(0.06, decoder.decode(s2c("0110")).get(0), 0.0001);
		assertEquals(0.07, decoder.decode(s2c("0111")).get(0), 0.0001);
		assertEquals(0.08, decoder.decode(s2c("01000")).get(0), 0.0001);
	}

	@Test
	public void negativeOneThroughEightTimesPointOne()
	{
		Decoder decoder = new RealDecoder();
		decoder.setPrecision(1);

		assertEquals( 0.0, decoder.decode(s2c("10")).get(0), 0.0001);
		assertEquals(-0.1, decoder.decode(s2c("11")).get(0), 0.0001);
		assertEquals(-0.2, decoder.decode(s2c("110")).get(0), 0.0001);
		assertEquals(-0.3, decoder.decode(s2c("111")).get(0), 0.0001);
		assertEquals(-0.4, decoder.decode(s2c("1100")).get(0), 0.0001);
		assertEquals(-0.5, decoder.decode(s2c("1101")).get(0), 0.0001);
		assertEquals(-0.6, decoder.decode(s2c("1110")).get(0), 0.0001);
		assertEquals(-0.7, decoder.decode(s2c("1111")).get(0), 0.0001);
		assertEquals(-0.8, decoder.decode(s2c("11000")).get(0), 0.0001);
	}
}
