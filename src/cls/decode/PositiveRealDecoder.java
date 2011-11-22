package cls.decode;

import cls.model.Chromosome;
import java.util.LinkedList;
import java.util.List;

/**
 * Decodes a chromosome into a positive real-valued number.
 */
public class PositiveRealDecoder implements Decoder
{
    private Integer decimalPlaces;

    public PositiveRealDecoder(int decimalPlaces)
    {
        this.decimalPlaces = decimalPlaces;
    }

    public List<Double> decode(Chromosome chromosome)
    {
        if (decimalPlaces < 0)
            throw new IllegalArgumentException("decimalPlaces must be >= 0");

        double value = 0;

        /*
         * Note we stop before the first bit (while "i >= 1").
         *
         * This is intentional, as this bit will represent whether
         * the number is positive (false) or negative (true).
         */
        int base = 1;
        for (int i = chromosome.size() - 1; i >= 0; base *= 2) // :)
            value += chromosome.get(i--) ? base : 0;

        /*
         * Convert to a floating-point number per 'decimalPlaces' argument.
         */
        for (int i = 0; i < decimalPlaces; i += 1)
            value *= 0.1;

        List<Double> list = new LinkedList<Double>();
        list.add(value);

        return list;
    }
}
