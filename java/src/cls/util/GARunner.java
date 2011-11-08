package cls.util;

import cls.ga.GA;
import cls.ga.CanonicalGA;
import cls.ga.CHC;
import cls.fitness.OneMaxFitnessDeterminant;
import cls.util.GAResultSet;

public class GARunner
{
    public static void main(String[] args)
    {
        boolean verbose = false;

        if (! (args.length == 7 || args.length == 8))
            usage();

        String  vf  = null;
        String  a   = null;
        Integer m   = null;
        Integer s   = null;
        Double  pC  = null;
        Double  pM  = null;
        Integer g   = null;
        Double  f   = null;

        try
        {
            if (args.length == 7)
            {
                a  = args[0];
                m  = Integer.parseInt(args[1]);
                s  = Integer.parseInt(args[2]);
                pC = Double.parseDouble(args[3]);
                pM = Double.parseDouble(args[4]);
                g  = Integer.parseInt(args[5]);
                f  = Double.parseDouble(args[6]);
            }
            else if (args.length == 8)
            {
                vf = args[0];
                a  = args[1];
                m  = Integer.parseInt(args[2]);
                s  = Integer.parseInt(args[3]);
                pC = Double.parseDouble(args[4]);
                pM = Double.parseDouble(args[5]);
                g  = Integer.parseInt(args[6]);
                f  = Double.parseDouble(args[7]);

                verbose = true;
            }
            else
                throw new IllegalStateException("Should not reach here.");

            if (vf != null)
                if (! vf.equals("-v"))
                    throw new NumberFormatException();

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

        if (verbose)
            printBeginInfo(a, m, s, pC, pM, g, f);

        GAResultSet results = ga.run
        (
            m,      // population size
            s,      // gene size
            pC,     // recombination (crossover) probability
            pM,     // mutation probability
            g,      // terminal number of generations
            f,      // terminal fitness
            verbose // verbose flag
        );

        try
        {
            results.writeToGnuPlotDataFile(a + ".dat");
        }
        catch (Exception e)
        {
            System.err.println("Could not write results: " + e.getMessage());
        }

        if (verbose)
            printEndInfo(results);
    }

    private static void usage()
    {
        System.out.println(""
            + "\nusage: <program> [-v] < GA | CHC > m s pC pM g f\n\n"
            + "\tm\tPopulation size         (integer)\n"
            + "\ts\tAlleles per chromosome  (integer)\n"
            + "\tpC\tCrossover probability   (real; 0 < x < 1)\n"
            + "\tpM\tMutation probability    (real; 0 < x < 1)\n"
            + "\tg\tGenerations to run      (integer)\n"
            + "\tf\tTerminal fitness        (real)\n\n");

        System.exit(1);
    }

    private static void printBeginInfo(String a, Integer m, Integer s,
        Double pC, Double pM, Integer g, Double f)
    {
        System.out.println(String.format(""
            + "\nRunning %s for %d generations "
                + "or until fitness %f is reached.\n\n"
            + "    Population size:       %d\n"
            + "    Chromosome size:       %d\n"
            + "    Crossover probability: %f\n"
            + "    Mutation probability:  %f\n"
            + "    Terminal generation:   %d\n"
            + "    Terminal fitness:      %f\n",
            a, g, f, m, s, pC, pM, g, f));
    }

    private static void printEndInfo(GAResultSet results)
    {
        System.out.println(String.format(""
            + "Results:\n\n"
            + "    Beginning best fitness:    %f\n"
            + "    Beginning worst fitness:   %f\n"
            + "    Beginning average fitness: %f\n\n"
            + "    Terminal best fitness:     %f\n"
            + "    Terminal worst fitness:    %f\n"
            + "    Terminal average fitness:  %f\n"
            + "    Terminal generation:       %d\n\n",
            results.bestFitnesses.get(0),
            results.worstFitnesses.get(0),
            results.avgFitnesses.get(0),
            results.bestFitnesses.get(results.bestFitnesses.size() - 1),
            results.worstFitnesses.get(results.worstFitnesses.size() - 1),
            results.avgFitnesses.get(results.avgFitnesses.size() - 1),
            results.avgFitnesses.size() - 1));
    }
}
