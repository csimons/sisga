package edu.franklin.ga.cls.decode.test;

import edu.franklin.ga.cls.model.Gene;

public class Util
{
    public static Gene stringToGene(String bitstring)
    {
        if (! bitstring.matches("^[01]+$"))
            throw new IllegalArgumentException();

        Gene gene = new Gene(bitstring.length());

        for (int i = 0; i < bitstring.length(); i += 1)
            gene.set(i, bitstring.charAt(i) == '1');

        return gene;
    }
}
