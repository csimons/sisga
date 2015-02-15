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

package com.oracli.sisga.decode;

import java.util.LinkedList;
import java.util.List;

import com.oracli.sisga.model.Chromosome;

/**
 * Decodes a chromosome into a positive real-valued number.
 */
public class PositiveRealDecoder extends AbstractDecoder
{
    private Integer decimalPlaces;

    public void setPrecision(Integer decimalPlaces)
    {
        this.decimalPlaces = decimalPlaces;
    }

    public List<Double> decode(Chromosome chromosome)
    {
        if (decimalPlaces < 0)
            throw new IllegalArgumentException("decimalPlaces must be >= 0");

        double value = 0;

        /*
         * Note we stop before the first bit (while "i >= 1").
         *
         * This is intentional, as this bit will represent whether
         * the number is positive (false) or negative (true).
         */
        int base = 1;
        for (int i = chromosome.size() - 1; i >= 0; base *= 2)
            value += chromosome.get(i--) ? base : 0;

        /*
         * Convert to a floating-point number per 'decimalPlaces' argument.
         */
        for (int i = 0; i < decimalPlaces; i += 1)
            value *= 0.1;

        List<Double> list = new LinkedList<Double>();
        list.add(value);

        return list;
    }
}
