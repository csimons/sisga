package cls.decode.test;

import cls.decode.Decoder;
import cls.decode.TwoRealDecoder;
import cls.model.Chromosome;
import org.junit.Test;

import static cls.decode.test.Util.stringToChromosome;
import static org.junit.Assert.assertEquals;

public class TwoRealDecoderTest
{
    private Chromosome s2c(String bitString)
    {
        return stringToChromosome(bitString);
    }

    @Test
    public void oneThroughFour()
    {
        Decoder decoder = new TwoRealDecoder();
        decoder.setPrecision(0);

        assertEquals(1, decoder.decode(s2c("0000100010")).get(0), 0.0001);
        assertEquals(2, decoder.decode(s2c("0000100010")).get(1), 0.0001);

        assertEquals(3, decoder.decode(s2c("0001100100")).get(0), 0.0001);
        assertEquals(4, decoder.decode(s2c("0001100100")).get(1), 0.0001);
    }

    @Test
    public void negativeOneThroughFour()
    {
        Decoder decoder = new TwoRealDecoder();
        decoder.setPrecision(0);

        assertEquals(-1, decoder.decode(s2c("1000110010")).get(0), 0.0001);
        assertEquals(-2, decoder.decode(s2c("1000110010")).get(1), 0.0001);

        assertEquals(-3, decoder.decode(s2c("1001110100")).get(0), 0.0001);
        assertEquals(-4, decoder.decode(s2c("1001110100")).get(1), 0.0001);
    }

    @Test
    public void oneThroughFourTimesPointOne()
    {
        Decoder decoder = new TwoRealDecoder();
        decoder.setPrecision(1);

        assertEquals(0.1, decoder.decode(s2c("0000100010")).get(0), 0.0001);
        assertEquals(0.2, decoder.decode(s2c("0000100010")).get(1), 0.0001);

        assertEquals(0.3, decoder.decode(s2c("0001100100")).get(0), 0.0001);
        assertEquals(0.4, decoder.decode(s2c("0001100100")).get(1), 0.0001);
    }

    @Test
    public void negativeOneThroughFourTimesPointOne()
    {
        Decoder decoder = new TwoRealDecoder();
        decoder.setPrecision(1);

        assertEquals(-0.1, decoder.decode(s2c("1000110010")).get(0), 0.0001);
        assertEquals(-0.2, decoder.decode(s2c("1000110010")).get(1), 0.0001);

        assertEquals(-0.3, decoder.decode(s2c("1001110100")).get(0), 0.0001);
        assertEquals(-0.4, decoder.decode(s2c("1001110100")).get(1), 0.0001);
    }
}
