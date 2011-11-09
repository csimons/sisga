package cls.decode;

import cls.model.Chromosome;
import java.util.List;

public interface Decoder
{
    public List<Double> decode(Chromosome chromosome);
}
