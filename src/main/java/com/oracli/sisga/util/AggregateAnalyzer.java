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

package com.oracli.sisga.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class AggregateAnalyzer
{
    private static List<Double>  optima      = new LinkedList<Double>();
    private static List<Double>  fitnesses   = new LinkedList<Double>();
    private static List<Integer> generations = new LinkedList<Integer>();

    public static void main(String[] args)
    {
        if (args.length < 1)
            usage();

        extract(getFiles(args));

        double totalFitness = 0;
        double totalEvals   = 0;
        int    successes    = 0;

        for (int i = 0; i < fitnesses.size(); i += 1)
        {
            totalFitness += fitnesses.get(i);
            totalEvals   += generations.get(i);

            if ((optima.get(i) - fitnesses.get(i)) < 0.000001)
                successes += 1;
        }

        double sr  = (((double) successes) / optima.size());
        double mbf = totalFitness / optima.size();
        double aes = totalEvals / successes;

        double mbfSD = 0;
        double aesSD = 0;
        for (int i = 0; i < fitnesses.size(); i += 1)
        {
            mbfSD += Math.pow((fitnesses.get(i) - mbf), 2);
            aesSD += Math.pow((generations.get(i) - aes), 2);
        }
        mbfSD = Math.sqrt(mbfSD / fitnesses.size());
        aesSD = Math.sqrt(aesSD / successes);

        String output = String.format("\n"
            + "         Runs: %d\n"
            + "    Successes: %d (%d%%)\n\n"
            + "          MBF: %f\n"
            + "        SDMBF: %f\n"
            + "          AES: %f\n"
            + "        SDAES: %f\n"
            + "\n", optima.size(), successes, Math.round(sr * 100),
            mbf, mbfSD, aes, aesSD);

        System.out.print(output);
    }

    private static void usage()
    {
        System.out.println("usage: <program> file1 [file2 [file3 [...]]]");
        System.exit(1);
    }

    private static List<File> getFiles(String[] filenames)
    {
        List<File> files = new LinkedList<File>();

        for (String filename : filenames)
        {
            File file = new File(filename);

            if (file.canRead())
                files.add(file);
            else
            {
                System.out.println(String
                    .format("Could not read file \"%s\".", filename));
            }
        }

        if (files.size() < filenames.length)
            System.exit(1);

        return files;
    }

    private static void extract(List<File> files)
    {
        for (File file : files)
        {
            try
            {
                Double optimum     = null;
                Double fitness     = null;
                Integer generation = null;

                BufferedReader r = new BufferedReader(new FileReader(file));
                String line;
                while ((line = r.readLine()) != null)
                {
                    if (line.matches("^.*Terminal fitness:\\s+(\\S+)$"))
                        optimum = Double.parseDouble(line.replaceAll(
                            "^.*Terminal fitness:\\s+(\\S+)$", "$1"));
                    else if (line.matches("^.*Ending best fitness:\\s+(\\S+)$"))
                        fitness = Double.parseDouble(line.replaceAll(
                            "^.*Ending best fitness:\\s+(\\S+)$", "$1"));
                    else if (line.matches("^.*Ending generation:\\s+(\\S+)$"))
                        generation = Integer.parseInt(line.replaceAll(
                            "^.*Ending generation:\\s+(\\S+)$", "$1"));
                }
                r.close();

                if (optimum == null)
                    throw new IllegalArgumentException(
                        "Terminal fitness line not found in file: "
                        + file.getName());
                if (fitness == null)
                    throw new IllegalArgumentException(
                        "Ending best fitness line not found in file: "
                        + file.getName());
                if (generation == null)
                    throw new IllegalArgumentException(
                        "Ending generation line found in file: "
                        + file.getName());

                optima.add(optimum);
                fitnesses.add(fitness);
                generations.add(generation);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
