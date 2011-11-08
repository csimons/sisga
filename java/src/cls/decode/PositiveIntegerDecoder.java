package cls.decode;

import cls.model.Chromosome;

/**
 * Decodes chromosomes into positive integers.
 */
public class PositiveIntegerDecoder
{
    public int decode(Chromosome chromosome)
    {
        int value = 0;

        int base = 1;
        for (int i = chromosome.size() - 1; i >= 0; base *= 2) // :)
            value += chromosome.get(i--) ? base : 0;

        return value;
    }
}
