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

import static com.oracli.sisga.test.decode.Util.stringToChromosome;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.oracli.sisga.decode.Decoder;
import com.oracli.sisga.decode.ThreeRealDecoder;
import com.oracli.sisga.model.Chromosome;

public class ThreeRealDecoderTest
{
	private Chromosome s2c(String bitString)
	{
		return stringToChromosome(bitString);
	}

	@Test
	public void oneThroughSix()
	{
		Decoder decoder = new ThreeRealDecoder();
		decoder.setPrecision(0);

		assertEquals(1, decoder.decode(s2c("000010001000011")).get(0), 0.0001);
		assertEquals(2, decoder.decode(s2c("000010001000011")).get(1), 0.0001);
		assertEquals(3, decoder.decode(s2c("000010001000011")).get(2), 0.0001);

		assertEquals(4, decoder.decode(s2c("001000010100110")).get(0), 0.0001);
		assertEquals(5, decoder.decode(s2c("001000010100110")).get(1), 0.0001);
		assertEquals(6, decoder.decode(s2c("001000010100110")).get(2), 0.0001);
	}

	@Test
	public void negativeOneThroughSix()
	{
		Decoder decoder = new ThreeRealDecoder();
		decoder.setPrecision(0);

		assertEquals(-1, decoder.decode(s2c("100011001010011")).get(0), 0.0001);
		assertEquals(-2, decoder.decode(s2c("100011001010011")).get(1), 0.0001);
		assertEquals(-3, decoder.decode(s2c("100011001010011")).get(2), 0.0001);

		assertEquals(-4, decoder.decode(s2c("101001010110110")).get(0), 0.0001);
		assertEquals(-5, decoder.decode(s2c("101001010110110")).get(1), 0.0001);
		assertEquals(-6, decoder.decode(s2c("101001010110110")).get(2), 0.0001);
	}

	@Test
	public void oneThroughSixTimesPointOne()
	{
		Decoder decoder = new ThreeRealDecoder();
		decoder.setPrecision(1);

		assertEquals(0.1, decoder.decode(s2c("000010001000011")).get(0), 0.0001);
		assertEquals(0.2, decoder.decode(s2c("000010001000011")).get(1), 0.0001);
		assertEquals(0.3, decoder.decode(s2c("000010001000011")).get(2), 0.0001);

		assertEquals(0.4, decoder.decode(s2c("001000010100110")).get(0), 0.0001);
		assertEquals(0.5, decoder.decode(s2c("001000010100110")).get(1), 0.0001);
		assertEquals(0.6, decoder.decode(s2c("001000010100110")).get(2), 0.0001);
	}

	@Test
	public void negativeOneThroughSixTimesPointOne()
	{
		Decoder decoder = new ThreeRealDecoder();
		decoder.setPrecision(1);

		assertEquals(-0.1, decoder.decode(s2c("100011001010011")).get(0), 0.0001);
		assertEquals(-0.2, decoder.decode(s2c("100011001010011")).get(1), 0.0001);
		assertEquals(-0.3, decoder.decode(s2c("100011001010011")).get(2), 0.0001);

		assertEquals(-0.4, decoder.decode(s2c("101001010110110")).get(0), 0.0001);
		assertEquals(-0.5, decoder.decode(s2c("101001010110110")).get(1), 0.0001);
		assertEquals(-0.6, decoder.decode(s2c("101001010110110")).get(2), 0.0001);
	}
}
