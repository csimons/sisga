package edu.franklin.ga.cls.util;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class GAResultSet
{
    public List<Double> avgFitnesses = new LinkedList<Double>();
    public List<Double> bestFitnesses = new LinkedList<Double>();
    // public List<Double> worstFitnesses = new LinkedList<Double>();

    public void writeToGnuPlotDataFile(String filename)
        throws FileNotFoundException
    {
        if (avgFitnesses.size() != bestFitnesses.size())
            throw new IllegalStateException("! |avg| == |best|");

        PrintWriter x = new PrintWriter(new FileOutputStream(filename));
        x.println("#RUN\tAVG\tBEST");

        for (int i = 0; i < avgFitnesses.size(); i += 1)
            x.println(String.format("%d\t%f\t%f",
                        i,
                        avgFitnesses.get(i),
                        bestFitnesses.get(i)));

        x.close();
    }
}
