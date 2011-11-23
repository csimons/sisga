package cls.decode;

import cls.model.Chromosome;
import java.util.LinkedList;
import java.util.List;

/**
 * Counts the number of set bits in the bit string (chromosome).
 */
public class NumSetBitsDecoder extends AbstractDecoder
{
    public List<Double> decode(Chromosome chromosome)
    {
        List<Double> results = new LinkedList<Double>();

        double value = 0.0;
        for (int i = 0; i < chromosome.size(); i += 1)
            value += chromosome.get(i) ? 1 : 0;

        results.add(value);
        return results;
    }
}
