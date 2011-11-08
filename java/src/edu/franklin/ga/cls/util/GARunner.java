package edu.franklin.ga.cls.util;

import edu.franklin.ga.cls.ga.GA;
import edu.franklin.ga.cls.ga.CanonicalGA;
import edu.franklin.ga.cls.ga.CHC;
import edu.franklin.ga.cls.fitness.OneMaxFitnessDeterminant;
import edu.franklin.ga.cls.util.GAResultSet;

public class GARunner
{
    public static void main(String[] args)
    {
        if (args.length != 7)
            usage();

        String  a   = null;
        Integer m   = null;
        Integer s   = null;
        Double  pC  = null;
        Double  pM  = null;
        Integer g   = null;
        Double  f   = null;

        try
        {
            a  = args[0];
            m  = Integer.parseInt(args[1]);
            s  = Integer.parseInt(args[2]);
            pC = Double.parseDouble(args[3]);
            pM = Double.parseDouble(args[4]);
            g  = Integer.parseInt(args[5]);
            f  = Double.parseDouble(args[6]);

            if (! (a.equals("GA") || a.equals("CHC")))
                throw new NumberFormatException();
        }
        catch (NumberFormatException e) { usage(); }

        GA ga = null;

        if (a.equals("GA"))
            ga = new CanonicalGA(new OneMaxFitnessDeterminant());
        else if (a.equals("CHC"))
            ga = new CHC(new OneMaxFitnessDeterminant());
        else
            throw new IllegalArgumentException(
                    "Algortihm should be one of {GA, CHC}.");

        GAResultSet results = ga.run
        (
            m,      // population size
            s,      // gene size
            pC,     // recombination (crossover) probability
            pM,     // mutation probability
            g,      // terminal number of generations
            f       // terminal fitness
        );

        try
        {
            results.writeToGnuPlotDataFile(a + ".dat");
        }
        catch (Exception e)
        {
            System.err.println("Could not write results: " + e.getMessage());
        }
    }

    private static void usage()
    {
        System.out.println(""
            + "\nusage: <program> < GA | CHC > m s pM pC g f\n\n"
            + "\tm\tPopulation size         (integer)\n"
            + "\ts\tAlleles per chromosome  (integer)\n"
            + "\tpC\tCrossover probability   (real; 0 < x < 1)\n"
            + "\tpM\tMutation probability    (real; 0 < x < 1)\n"
            + "\tg\tGenerations to run      (integer)\n"
            + "\tf\tTerminal fitness        (real)\n\n");

        System.exit(1);
    }
}
