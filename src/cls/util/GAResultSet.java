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

package cls.util;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class GAResultSet
{
    public List<Double> avgFitnesses = new LinkedList<Double>();
    public List<Double> bestFitnesses = new LinkedList<Double>();
    public List<Double> worstFitnesses = new LinkedList<Double>();

    public void writeToGnuPlotDataFile(String filename,
            String beginInfo, String endInfo)
        throws FileNotFoundException
    {
        if (avgFitnesses.size() != bestFitnesses.size())
            throw new IllegalStateException("! |avg| == |best|");

        PrintWriter x = new PrintWriter(new FileOutputStream(filename));
        x.println("# Generated by SISGA.\n#");
        x.println(beginInfo + "\n#");
        x.println(endInfo + "\n");
        x.println("#\tAVG\tBEST\tWORST");

        for (int i = 0; i < avgFitnesses.size(); i += 1)
            x.println(String.format("%d\t%f\t%f\t%f",
                        i,
                        avgFitnesses.get(i),
                        bestFitnesses.get(i),
                        worstFitnesses.get(i)));

        x.close();
    }
}
