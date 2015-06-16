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
import com.oracli.sisga.decode.TwoRealDecoder;
import com.oracli.sisga.model.Chromosome;

import static com.oracli.sisga.test.decode.Util.stringToChromosome;
import static org.junit.Assert.assertEquals;

public class TwoRealDecoderTest
{
	private Chromosome s2c(String bitString)
	{
		return stringToChromosome(bitString);
	}

	@Test
	public void oneThroughFour()
	{
		Decoder decoder = new TwoRealDecoder();
		decoder.setPrecision(0);

		assertEquals(1, decoder.decode(s2c("0000100010")).get(0), 0.0001);
		assertEquals(2, decoder.decode(s2c("0000100010")).get(1), 0.0001);

		assertEquals(3, decoder.decode(s2c("0001100100")).get(0), 0.0001);
		assertEquals(4, decoder.decode(s2c("0001100100")).get(1), 0.0001);
	}

	@Test
	public void negativeOneThroughFour()
	{
		Decoder decoder = new TwoRealDecoder();
		decoder.setPrecision(0);

		assertEquals(-1, decoder.decode(s2c("1000110010")).get(0), 0.0001);
		assertEquals(-2, decoder.decode(s2c("1000110010")).get(1), 0.0001);

		assertEquals(-3, decoder.decode(s2c("1001110100")).get(0), 0.0001);
		assertEquals(-4, decoder.decode(s2c("1001110100")).get(1), 0.0001);
	}

	@Test
	public void oneThroughFourTimesPointOne()
	{
		Decoder decoder = new TwoRealDecoder();
		decoder.setPrecision(1);

		assertEquals(0.1, decoder.decode(s2c("0000100010")).get(0), 0.0001);
		assertEquals(0.2, decoder.decode(s2c("0000100010")).get(1), 0.0001);

		assertEquals(0.3, decoder.decode(s2c("0001100100")).get(0), 0.0001);
		assertEquals(0.4, decoder.decode(s2c("0001100100")).get(1), 0.0001);
	}

	@Test
	public void negativeOneThroughFourTimesPointOne()
	{
		Decoder decoder = new TwoRealDecoder();
		decoder.setPrecision(1);

		assertEquals(-0.1, decoder.decode(s2c("1000110010")).get(0), 0.0001);
		assertEquals(-0.2, decoder.decode(s2c("1000110010")).get(1), 0.0001);

		assertEquals(-0.3, decoder.decode(s2c("1001110100")).get(0), 0.0001);
		assertEquals(-0.4, decoder.decode(s2c("1001110100")).get(1), 0.0001);
	}
}
