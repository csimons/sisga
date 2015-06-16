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

import java.text.SimpleDateFormat;
import java.util.Date;

import com.oracli.sisga.alg.ga.GA;
import com.oracli.sisga.decode.Decoder;
import com.oracli.sisga.fitness.Function;
import com.oracli.sisga.util.Configuration;
import com.oracli.sisga.util.GAResultSet;

public class GARunner
{
	private static boolean verbose = false;

	public static void main(String[] args)
	{
		Configuration config = configure(args);

		Function function = (Function) config
			.getAlgorithm(Configuration.ALG_FITNESS);
		Decoder decoder = (Decoder) config
			.getAlgorithm(Configuration.ALG_DECODER);
		decoder.setPrecision(config.getFPP());
		GA ga = (GA) config.getAlgorithm(Configuration.ALG_GA);
		ga.setDecoder(decoder);
		ga.setFunction(function);
		ga.init();

		if (verbose)
			System.out.println("\n" + getBeginInfo(config) + "\n");

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
			System.out.println(getEndInfo(results) + "\n");

		try
		{
			results.writeToGnuPlotDataFile(String.format(
				"%s-%s.dat", config.getConfigName(),
				getDateString(config.getTimestampFormat())),
				getBeginInfo(config), getEndInfo(results));
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

	private static String getBeginInfo(Configuration config)
	{
		return String.format(""
			+ "# Run parameters:\n#\n"
			+ "#     GA Class:              %s\n"
			+ "#     Population size:       %d\n"
			+ "#     Chromosome size:       %d\n"
			+ "#     Crossover probability: %f\n"
			+ "#     Mutation probability:  %f\n"
			+ "#     Terminal generation:   %d\n"
			+ "#     Terminal fitness:      %f\n"
			+ "#     FPP (decimal places):  %d",
			config.getGA(),
			config.getSizePopulation(),
			config.getSizeChromosome(),
			config.getPC(),
			config.getPM(),
			config.getTermGeneration(),
			config.getTermFitness(),
			config.getFPP());
	}

	private static String getEndInfo(GAResultSet results)
	{
		return String.format(""
			+ "# Results:\n#\n"
			+ "#     Beginning best fitness:    %f\n"
			+ "#     Beginning worst fitness:   %f\n"
			+ "#     Beginning average fitness: %f\n#\n"
			+ "#     Ending best fitness:       %f\n"
			+ "#     Ending worst fitness:      %f\n"
			+ "#     Ending average fitness:    %f\n"
			+ "#     Ending generation:         %d",
			results.bestFitnesses.get(0),
			results.worstFitnesses.get(0),
			results.avgFitnesses.get(0),
			results.bestFitnesses.get(results.bestFitnesses.size() - 1),
			results.worstFitnesses.get(results.worstFitnesses.size() - 1),
			results.avgFitnesses.get(results.avgFitnesses.size() - 1),
			results.avgFitnesses.size() - 1);
	}

	private static String getDateString(String formatString)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		return sdf.format(new Date());
	}
}
