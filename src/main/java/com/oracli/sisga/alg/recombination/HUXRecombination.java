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

package com.oracli.sisga.alg.recombination;

import java.util.LinkedList;
import java.util.List;

import com.oracli.sisga.model.Chromosome;

/*
 * HUX
 *
 * "Crosses over exactly half the non-matching alleles, where the
 *  bits to be exchanged are chosen at random without replacement."
 *
 *  (Eshelman, p. 271)
 */
public class HUXRecombination implements Recombination
{
    public HUXRecombination() {}

    /**
     * @param dad First parent chromosome.
     * @param mom Second parent chromosome.
     * @param pC Recombination probability; gnored for HUXRecombination.
     */
    public Chromosome recombinate(Chromosome dad, Chromosome mom, Double pC)
    {
        if (dad.size() != mom.size())
            throw new IllegalArgumentException("Arity of operators unequal.");

        List<Integer> nonMatchingIndices = new LinkedList<Integer>();
        List<Integer> swapAlleles = new LinkedList<Integer>();

        for (int i = 0; i < dad.size(); i += 1)
            if (dad.get(i) != mom.get(i))
                nonMatchingIndices.add(i);

        for (Integer i : nonMatchingIndices)
            if (Math.round(Math.random()) == 0)
                swapAlleles.add(i);

        Chromosome child = new Chromosome(dad.size());

        for (int i = 0; i < child.size(); i += 1)
            child.set(i, swapAlleles.contains(i) ? mom.get(i) : dad.get(i));

        return child;
    }
}
