package edu.franklin.ga.cls.decode;

import edu.franklin.ga.cls.model.Gene;

public class PositiveIntegerDecoder
{
    public int decode(Gene gene)
    {
        int value = 0;

        int base = 1;
        for (int i = gene.size() - 1; i >= 0; base *= 2) // :)
            value += gene.get(i--) ? base : 0;

        return value;
    }
}
