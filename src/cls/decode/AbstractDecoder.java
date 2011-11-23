package cls.decode;

import cls.model.Chromosome;
import java.util.List;

public abstract class AbstractDecoder implements Decoder
{
    public void setPrecision(Integer decimalPlaces) {};
    public abstract List<Double> decode(Chromosome chromosome);
}
