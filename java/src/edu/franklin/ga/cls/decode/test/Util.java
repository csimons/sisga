package edu.franklin.ga.cls.decode.test;

import edu.franklin.ga.cls.model.Chromosome;

public class Util
{
    public static Chromosome stringToChromosome(String bitstring)
    {
        if (! bitstring.matches("^[01]+$"))
            throw new IllegalArgumentException();

        Chromosome chromosome = new Chromosome(bitstring.length());

        for (int i = 0; i < bitstring.length(); i += 1)
            chromosome.set(i, bitstring.charAt(i) == '1');

        return chromosome;
    }
}
