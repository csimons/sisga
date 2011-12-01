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
import cls.util.Configuration;
import cls.util.GAResultSet;

public class GARunner
{
    private static boolean verbose = false;

    public static void main(String[] args)
    {
        Configuration config = configure(args);

        Function function = (Function) config.getAlgorithm(config.ALG_FITNESS);
        Decoder decoder = (Decoder) config.getAlgorithm(config.ALG_DECODER);
        decoder.setPrecision(config.getFPP());
        GA ga = (GA) config.getAlgorithm(config.ALG_GA);
        ga.setDecoder(decoder);
        ga.setFunction(function);
        ga.init();

        if (verbose)
            printBeginInfo(config);

        GAResultSet results = ga.run
        (
            config.getSizePopulation(),
            config.getSizeChromosome(),
            config.getPC(),
            config.getPM(),
            config.getTermGeneration(),
            config.getTermFitness(),
            verbose
        );

        if (verbose)
            printEndInfo(results);

        try
        {
            results.writeToGnuPlotDataFile(config.getGA() + ".dat");
        }
        catch (Exception e)
        {
            System.out.println("Could not write results: " + e.getMessage());
        }
    }

    private static Configuration configure(String[] args)
    {
        String configName  = null;
        String verboseFlag = null;

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

        return new Configuration(configName);
    }

    private static void usage()
    {
        System.out.println("usage: <program> [-v] <configurationName>");
        System.exit(1);
    }

    private static void printBeginInfo(Configuration config)
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
            config.getGA(),
            config.getTermGeneration(),
            config.getTermFitness(),
            config.getSizePopulation(),
            config.getSizeChromosome(),
            config.getPC(),
            config.getPM(),
            config.getTermGeneration(),
            config.getTermFitness(),
            config.getFPP()));
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
