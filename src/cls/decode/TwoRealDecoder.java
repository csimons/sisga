package cls.decode;

import cls.model.Chromosome;
import java.util.LinkedList;
import java.util.List;

/**
 * Decodes a chromosome into two real-valued, positive or negative numbers.
 */
public class TwoRealDecoder implements Decoder
{
    private Integer decimalPlaces;

    /**
     * @param decimalPlaces
     * @param splitIndex Index of the first bit of the second number
     *                   (remember, these are zero-based indices).
     */
    public TwoRealDecoder(int decimalPlaces)
    {
        this.decimalPlaces = decimalPlaces;
    }

    public List<Double> decode(Chromosome chromosome)
    {
        if (chromosome.size() % 2 != 0)
            throw new IllegalArgumentException(
                "Chromosomze length must be an even number.");

        if (decimalPlaces < 0)
            throw new IllegalArgumentException("decimalPlaces must be >= 0");

        List<Double> list = new LinkedList<Double>();
        int splitIndex = chromosome.size() / 2;
        double valueA = 0;
        double valueB = 0;
        int base;

        /*
         * Note we stop before the first bit of each number's chromosome.
         *
         * This is intentional, as this bit is not part of the number itself
         * but instead represents whether the number is positive (false) or
         * negative (true).
         */
        base = 1;
        for (int i = splitIndex - 1; i >= 1; base *= 2) // :)
            valueA += chromosome.get(i--) ? base : 0;

        base = 1;
        for (int i = chromosome.size() - 1; i >= splitIndex + 1; base *= 2)
            valueB += chromosome.get(i--) ? base : 0;

        /*
         * Convert to a floating-point numbers per 'decimalPlaces' argument.
         */
        for (int i = 0; i < decimalPlaces; i += 1)
        {
            valueA *= 0.1;
            valueB *= 0.1;
        }

        if (chromosome.get(0))
            valueA *= (-1);

        if (chromosome.get(splitIndex))
            valueB *= (-1);

        list.add(valueA);
        list.add(valueB);

        return list;
    }
}
