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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Configuration
{
	private static final String PARAM_SZ_P  = "param.size.population";
	private static final String PARAM_SZ_C  = "param.size.chromosome";
	private static final String PARAM_P_C   = "param.probability.crossover";
	private static final String PARAM_P_M   = "param.probability.mutation";
	private static final String PARAM_TRM_F = "param.terminal.fitness";
	private static final String PARAM_TRM_G = "param.terminal.generation";
	private static final String PARAM_FPP   = "param.floating.point.precision";
	public  static final String ALG_GA      = "algorithm.ga";
	public  static final String ALG_DECODER = "algorithm.decoder";
	public  static final String ALG_FITNESS = "algorithm.fitness.function";
	private static final String CFG_DT_FMT  = "config.timestamp.format";

	private static Map<String, String> validationPatterns;
	private static Map<String, Class<?>> algClasses;
	private static String configName;

	private String  ga;
	private Integer sizePopulation;
	private Integer sizeChromosome;
	private Double  pC;
	private Double  pM;
	private Integer termGeneration;
	private Double  termFitness;
	private Integer fpp;
	private String  timestampFormat;

	static
	{
		validationPatterns = new HashMap<String, String>();

		String singleWord = "^\\S+$";
		String integer    = "^-?[0-9]+$";
		String real       = "^-?[0-9]*(\\.[0-9]+)?$";

		validationPatterns.put(ALG_GA,      singleWord);
		validationPatterns.put(ALG_DECODER, singleWord);
		validationPatterns.put(ALG_FITNESS, singleWord);
		validationPatterns.put(PARAM_SZ_P,  integer);
		validationPatterns.put(PARAM_SZ_C,  integer);
		validationPatterns.put(PARAM_P_C,   real);
		validationPatterns.put(PARAM_P_M,   real);
		validationPatterns.put(PARAM_TRM_F, real);
		validationPatterns.put(PARAM_TRM_G, integer);
		validationPatterns.put(PARAM_FPP,   integer);
		validationPatterns.put(CFG_DT_FMT,  singleWord);
	}

	private boolean isProbability(String paramName)
	{
		return paramName.startsWith("param.probability.");
	}

	private String fetchParam(Properties pConfig, String paramName)
	{
		if (validationPatterns.get(paramName) == null)
			throw new IllegalArgumentException(String.format(
				"Unrecognized parameter: \"%s\".", paramName));

		String value = pConfig.getProperty(paramName);

		if (! value.matches(validationPatterns.get(paramName)))
			throw new IllegalArgumentException(String.format(
				"Parameter \"%s\" fails validation against pattern \"%s\".",
				paramName, validationPatterns.get(paramName)));

		if (isProbability(paramName))
		{
			double p = Double.parseDouble(value);

			if (p < 0 || p > 1)
				throw new IllegalArgumentException(String.format(
					"Probability \"%s\" not in range 0 <= p <= 1.",
					paramName));
		}

		return value;
	}

	public Configuration(String configName)
	{
		Configuration.configName = (new File(configName)).getName();

		String filename = String.format("%s.properties", configName);
		Properties pConfig = new Properties();

		try
		{
			pConfig.load(new FileInputStream(filename));
		}
		catch (FileNotFoundException e)
		{
			System.out.println(String.format(
				"Configuration file \"%s\" not found.", filename));

			System.exit(1);
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException(String.format(
				"Error reading configuration file \"%s\".", filename),e);
		}

		ga              = fetchParam(pConfig,                    ALG_GA);
		sizePopulation  = Integer.parseInt(fetchParam(pConfig,   PARAM_SZ_P));
		sizeChromosome  = Integer.parseInt(fetchParam(pConfig,   PARAM_SZ_C));
		pC              = Double.parseDouble(fetchParam(pConfig, PARAM_P_C));
		pM              = Double.parseDouble(fetchParam(pConfig, PARAM_P_M));
		termGeneration  = Integer.parseInt(fetchParam(pConfig,   PARAM_TRM_G));
		termFitness     = Double.parseDouble(fetchParam(pConfig, PARAM_TRM_F));
		fpp             = Integer.parseInt(fetchParam(pConfig,   PARAM_FPP));

		timestampFormat = fetchParam(pConfig,					CFG_DT_FMT);

		if (fpp > sizeChromosome - 1)
			throw new IllegalArgumentException(
				"FP precision must be greater than chromosomeSize - 1");

		algClasses = new HashMap<String, Class<?>>();

		try
		{
			algClasses.put(ALG_GA, getClass().getClassLoader()
				.loadClass(String.format(
				"com.oracli.sisga.alg.ga.%s", fetchParam(pConfig, ALG_GA))));
			algClasses.put(ALG_DECODER, Class.forName(String.format(
				"com.oracli.sisga.decode.%s", fetchParam(pConfig, ALG_DECODER))));
			algClasses.put(ALG_FITNESS, Class.forName(String.format(
				"com.oracli.sisga.fitness.%s", fetchParam(pConfig, ALG_FITNESS))));
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(
				"Failed to load algorithm component class.", e);
		}
	}

	public String getConfigName()       { return configName; }
	public String getGA()               { return ga; }
	public Integer getSizePopulation()  { return sizePopulation; }
	public Integer getSizeChromosome()  { return sizeChromosome; }
	public Double getPC()               { return pC; }
	public Double getPM()               { return pM; }
	public Integer getTermGeneration()  { return termGeneration; }
	public Double getTermFitness()      { return termFitness; }
	public Integer getFPP()             { return fpp; }
	public String getTimestampFormat()  { return timestampFormat; }

	public Object getAlgorithm(String algSymbol)
	{
		Object object;

		try
		{
			@SuppressWarnings("rawtypes")
			Class c = algClasses.get(algSymbol);
			object = c.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		return object;
	}
}
