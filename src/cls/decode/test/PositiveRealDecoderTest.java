package cls.decode.test;

import cls.decode.Decoder;
import cls.decode.PositiveRealDecoder;
import cls.model.Chromosome;
import org.junit.Test;

import static cls.decode.test.Util.stringToChromosome;
import static org.junit.Assert.assertEquals;

public class PositiveRealDecoderTest
{
    private Chromosome s2c(String bitString)
    {
        return stringToChromosome(bitString);
    }

    @Test
    public void oneThroughEight()
    {
        Decoder decoder = new PositiveRealDecoder(0);

        assertEquals(0, decoder.decode(s2c("0")).get(0), 0.0001);
        assertEquals(1, decoder.decode(s2c("1")).get(0), 0.0001);
        assertEquals(2, decoder.decode(s2c("10")).get(0), 0.0001);
        assertEquals(3, decoder.decode(s2c("11")).get(0), 0.0001);
        assertEquals(4, decoder.decode(s2c("100")).get(0), 0.0001);
        assertEquals(5, decoder.decode(s2c("101")).get(0), 0.0001);
        assertEquals(6, decoder.decode(s2c("110")).get(0), 0.0001);
        assertEquals(7, decoder.decode(s2c("111")).get(0), 0.0001);
        assertEquals(8, decoder.decode(s2c("1000")).get(0), 0.0001);
    }

    @Test
    public void oneThroughEightTimesPointOhOne()
    {
        Decoder decoder = new PositiveRealDecoder(2);

        assertEquals(0.00, decoder.decode(s2c("0")).get(0), 0.0001);
        assertEquals(0.01, decoder.decode(s2c("1")).get(0), 0.0001);
        assertEquals(0.02, decoder.decode(s2c("10")).get(0), 0.0001);
        assertEquals(0.03, decoder.decode(s2c("11")).get(0), 0.0001);
        assertEquals(0.04, decoder.decode(s2c("100")).get(0), 0.0001);
        assertEquals(0.05, decoder.decode(s2c("101")).get(0), 0.0001);
        assertEquals(0.06, decoder.decode(s2c("110")).get(0), 0.0001);
        assertEquals(0.07, decoder.decode(s2c("111")).get(0), 0.0001);
        assertEquals(0.08, decoder.decode(s2c("1000")).get(0), 0.0001);
    }
}
