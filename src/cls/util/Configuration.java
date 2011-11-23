package cls.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

public class Configuration
{
    private static final String ALG_GA      = "algorithm.ga";
    private static final String ALG_SEL_PAR = "algorithm.selection.parent";
    private static final String ALG_SEL_SUR = "algorithm.selection.survivor";
    private static final String ALG_MUT_CHR = "algorithm.mutation.chromosome";
    private static final String ALG_MUT_POP = "algorithm.mutation.population";
    private static final String ALG_RECOMB  = "algorithm.recombination";
    private static final String ALG_DECODER = "algorithm.decoder";
    private static final String ALG_FITNESS = "algorithm.fitness.function";
    private static final String PARAM_SZ_P  = "param.size.population";
    private static final String PARAM_SZ_C  = "param.size.chromosome";
    private static final String PARAM_P_C   = "param.probability.crossover";
    private static final String PARAM_P_M   = "param.probability.mutation";
    private static final String PARAM_TRM_F = "param.terminal.fitness";
    private static final String PARAM_TRM_G = "param.terminal.generation";
    private static final String PARAM_FPP   = "param.floating.point.precision";

    private static Map<String, String> validationPatterns;

    private String  ga;
    private Integer sizePopulation;
    private Integer sizeChromosome;
    private Double  pC;
    private Double  pM;
    private Integer termGeneration;
    private Double  termFitness;
    private Integer fpp;

    static
    {
        validationPatterns = new HashMap<String, String>();

        String className = "^\\S+$";
        String integer   = "^[0-9]+$";
        String real      = "^[0-9]*(\\.[0-9]+)?$";

        validationPatterns.put(ALG_GA,      className);
        validationPatterns.put(ALG_SEL_PAR, className);
        validationPatterns.put(ALG_SEL_SUR, className);
        validationPatterns.put(ALG_MUT_CHR, className);
        validationPatterns.put(ALG_MUT_POP, className);
        validationPatterns.put(ALG_RECOMB,  className);
        validationPatterns.put(ALG_DECODER, className);
        validationPatterns.put(ALG_FITNESS, className);
        validationPatterns.put(PARAM_SZ_P,  integer);
        validationPatterns.put(PARAM_SZ_C,  integer);
        validationPatterns.put(PARAM_P_C,   real);
        validationPatterns.put(PARAM_P_M,   real);
        validationPatterns.put(PARAM_TRM_F, real);
        validationPatterns.put(PARAM_TRM_G, integer);
        validationPatterns.put(PARAM_FPP,   integer);
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

        // TODO: add validation for dynamic class-loading.

        return value;
    }

    public Configuration(String configName)
    {
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

        ga              = fetchParam(pConfig, ALG_GA);
        sizePopulation  = Integer.parseInt(fetchParam(pConfig,   PARAM_SZ_P));
        sizeChromosome  = Integer.parseInt(fetchParam(pConfig,   PARAM_SZ_C));
        pC              = Double.parseDouble(fetchParam(pConfig, PARAM_P_C));
        pM              = Double.parseDouble(fetchParam(pConfig, PARAM_P_M));
        termGeneration  = Integer.parseInt(fetchParam(pConfig,   PARAM_TRM_G));
        termFitness     = Double.parseDouble(fetchParam(pConfig, PARAM_TRM_F));
        fpp             = Integer.parseInt(fetchParam(pConfig,   PARAM_FPP));

        if (fpp > sizeChromosome - 1)
            throw new IllegalArgumentException(
                "FP precision must be greater than chromosomeSize - 1");
    }

    public String getGA()               { return ga; }
    public Integer getSizePopulation()  { return sizePopulation; }
    public Integer getSizeChromosome()  { return sizeChromosome; }
    public Double getPC()               { return pC; }
    public Double getPM()               { return pM; }
    public Integer getTermGeneration()  { return termGeneration; }
    public Double getTermFitness()      { return termFitness; }
    public Integer getFPP()             { return fpp; }
}
