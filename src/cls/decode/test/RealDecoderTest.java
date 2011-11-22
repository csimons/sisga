package cls.decode.test;

import cls.decode.Decoder;
import cls.decode.RealDecoder;
import cls.model.Chromosome;
import org.junit.Test;

import static cls.decode.test.Util.stringToChromosome;
import static org.junit.Assert.assertEquals;

public class RealDecoderTest
{
    private Chromosome s2c(String bitString)
    {
        return stringToChromosome(bitString);
    }

    @Test
    public void oneThroughEight()
    {
        Decoder decoder = new RealDecoder(0);

        assertEquals(0, decoder.decode(s2c("00")).get(0), 0.0001);
        assertEquals(1, decoder.decode(s2c("01")).get(0), 0.0001);
        assertEquals(2, decoder.decode(s2c("010")).get(0), 0.0001);
        assertEquals(3, decoder.decode(s2c("011")).get(0), 0.0001);
        assertEquals(4, decoder.decode(s2c("0100")).get(0), 0.0001);
        assertEquals(5, decoder.decode(s2c("0101")).get(0), 0.0001);
        assertEquals(6, decoder.decode(s2c("0110")).get(0), 0.0001);
        assertEquals(7, decoder.decode(s2c("0111")).get(0), 0.0001);
        assertEquals(8, decoder.decode(s2c("01000")).get(0), 0.0001);
    }

    @Test
    public void negativeOneThroughEight()
    {
        Decoder decoder = new RealDecoder(0);

        assertEquals( 0, decoder.decode(s2c("10")).get(0), 0.0001);
        assertEquals(-1, decoder.decode(s2c("11")).get(0), 0.0001);
        assertEquals(-2, decoder.decode(s2c("110")).get(0), 0.0001);
        assertEquals(-3, decoder.decode(s2c("111")).get(0), 0.0001);
        assertEquals(-4, decoder.decode(s2c("1100")).get(0), 0.0001);
        assertEquals(-5, decoder.decode(s2c("1101")).get(0), 0.0001);
        assertEquals(-6, decoder.decode(s2c("1110")).get(0), 0.0001);
        assertEquals(-7, decoder.decode(s2c("1111")).get(0), 0.0001);
        assertEquals(-8, decoder.decode(s2c("11000")).get(0), 0.0001);
    }

    @Test
    public void oneThroughEightTimesPointOhOne()
    {
        Decoder decoder = new RealDecoder(2);

        assertEquals(0.00, decoder.decode(s2c("00")).get(0), 0.0001);
        assertEquals(0.01, decoder.decode(s2c("01")).get(0), 0.0001);
        assertEquals(0.02, decoder.decode(s2c("010")).get(0), 0.0001);
        assertEquals(0.03, decoder.decode(s2c("011")).get(0), 0.0001);
        assertEquals(0.04, decoder.decode(s2c("0100")).get(0), 0.0001);
        assertEquals(0.05, decoder.decode(s2c("0101")).get(0), 0.0001);
        assertEquals(0.06, decoder.decode(s2c("0110")).get(0), 0.0001);
        assertEquals(0.07, decoder.decode(s2c("0111")).get(0), 0.0001);
        assertEquals(0.08, decoder.decode(s2c("01000")).get(0), 0.0001);
    }

    @Test
    public void negativeOneThroughEightTimesPointOne()
    {
        Decoder decoder = new RealDecoder(1);

        assertEquals( 0.0, decoder.decode(s2c("10")).get(0), 0.0001);
        assertEquals(-0.1, decoder.decode(s2c("11")).get(0), 0.0001);
        assertEquals(-0.2, decoder.decode(s2c("110")).get(0), 0.0001);
        assertEquals(-0.3, decoder.decode(s2c("111")).get(0), 0.0001);
        assertEquals(-0.4, decoder.decode(s2c("1100")).get(0), 0.0001);
        assertEquals(-0.5, decoder.decode(s2c("1101")).get(0), 0.0001);
        assertEquals(-0.6, decoder.decode(s2c("1110")).get(0), 0.0001);
        assertEquals(-0.7, decoder.decode(s2c("1111")).get(0), 0.0001);
        assertEquals(-0.8, decoder.decode(s2c("11000")).get(0), 0.0001);
    }
}
