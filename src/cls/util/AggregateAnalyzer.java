package cls.util;

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

        for (int i = 0; i < optima.size(); i += 1)
        {
            totalFitness += fitnesses.get(i);
            totalEvals   += generations.get(i);

            if ((optima.get(i) - fitnesses.get(i)) < 0.000001)
                successes += 1;
        }

        double mbf = totalFitness / optima.size();
        double sr  = (((double) successes) / optima.size());
        double aes = totalEvals / successes;

        String output = String.format("\n"
            + "    Runs analyzed:        %d\n"
            + "    Successes:            %d (%d%%)\n"
            + "    Avg. Evals. to Soln.: %f\n"
            + "    Mean Best Fitness:    %f\n"
            + "\n", optima.size(), successes, Math.round(sr * 100), aes, mbf);

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