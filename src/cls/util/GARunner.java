package cls.util;

import cls.alg.ga.GA;
import cls.alg.ga.CanonicalGA;
import cls.alg.ga.CHC;
import cls.decode.Decoder;
import cls.decode.NumSetBitsDecoder;
import cls.decode.PositiveRealDecoder;
import cls.decode.RealDecoder;
import cls.fitness.Function;
import cls.fitness.IdentityFunction;
import cls.fitness.ReverseParabola;
import cls.util.GAResultSet;

public class GARunner
{
    public static void main(String[] args)
    {
        String configName  = null;
        String verboseFlag = null;
        boolean verbose = false;

        if (args.length == 1)
            configName = args[0];
        else if (args.length == 2)
        {
            verboseFlag = args[0];
            configName  = args[1];
            verbose     = true;
        }
        else
            usage();

        if (verboseFlag != null && (! verboseFlag.equals("-v")))
           usage();

        Configuration config = new Configuration(configName);

        GA ga = null;

        // ############################################################
        // # LOW-LEVEL ALGORITHM CONFIGURATION
        // ############################################################
        /*
         * TODO: Rip this out, load classes dynamically based on config.
         */
        Decoder decoder = new PositiveRealDecoder(config.getFPP());
        Function function = new IdentityFunction();

        if (config.getGA().equals("CanonicalGA"))
            ga = new CanonicalGA(decoder, function);
        else if (config.getGA().equals("CHC"))
            ga = new CHC(decoder, function);
        else
            throw new IllegalArgumentException(
                "Algortihm should be one of {CanonicalGA, CHC}.");
        // ############################################################

        String  a   = config.getGA();
        Integer m   = config.getSizePopulation();
        Integer s   = config.getSizeChromosome();
        Double  pC  = config.getPC();
        Double  pM  = config.getPM();
        Integer g   = config.getTermGeneration();
        Double  f   = config.getTermFitness();
        Integer fpp = config.getFPP();

        if (verbose)
            printBeginInfo(a, m, s, pC, pM, g, f, fpp);

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
        System.out.println("usage: <program> [-v] <configurationName>");
        System.exit(1);
    }

    private static void printBeginInfo(String a, Integer m, Integer s,
        Double pC, Double pM, Integer g, Double f, Integer fpp)
    {
        System.out.println(String.format(""
            + "\nRunning %s for %d generations "
                + "or until fitness %f is reached.\n\n"
            + "    Population size:       %d\n"
            + "    Chromosome size:       %d\n"
            + "    Crossover probability: %f\n"
            + "    Mutation probability:  %f\n"
            + "    Terminal generation:   %d\n"
            + "    Terminal fitness:      %f\n"
            + "    FPP (decimal places):  %d\n",
            a, g, f, m, s, pC, pM, g, f, fpp));
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
