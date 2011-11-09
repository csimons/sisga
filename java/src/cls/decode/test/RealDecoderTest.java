package cls.decode.test;

import cls.decode.Decoder;
import cls.decode.RealDecoder;
import cls.model.Chromosome;
import org.junit.Test;

import static cls.decode.test.Util.stringToChromosome;
import static org.junit.Assert.assertEquals;

public class RealDecoderTest
{
    @Test
    public void oneThroughEight()
    {
        Decoder decoder = new RealDecoder(0);

        assertEquals(0, decoder.decode(stringToChromosome("00")).get(0), 0.0001);
        assertEquals(1, decoder.decode(stringToChromosome("01")).get(0), 0.0001);
        assertEquals(2, decoder.decode(stringToChromosome("010")).get(0), 0.0001);
        assertEquals(3, decoder.decode(stringToChromosome("011")).get(0), 0.0001);
        assertEquals(4, decoder.decode(stringToChromosome("0100")).get(0), 0.0001);
        assertEquals(5, decoder.decode(stringToChromosome("0101")).get(0), 0.0001);
        assertEquals(6, decoder.decode(stringToChromosome("0110")).get(0), 0.0001);
        assertEquals(7, decoder.decode(stringToChromosome("0111")).get(0), 0.0001);
        assertEquals(8, decoder.decode(stringToChromosome("01000")).get(0), 0.0001);
    }

    @Test
    public void negativeOneThroughEight()
    {
        Decoder decoder = new RealDecoder(0);

        assertEquals( 0, decoder.decode(stringToChromosome("10")).get(0), 0.0001);
        assertEquals(-1, decoder.decode(stringToChromosome("11")).get(0), 0.0001);
        assertEquals(-2, decoder.decode(stringToChromosome("110")).get(0), 0.0001);
        assertEquals(-3, decoder.decode(stringToChromosome("111")).get(0), 0.0001);
        assertEquals(-4, decoder.decode(stringToChromosome("1100")).get(0), 0.0001);
        assertEquals(-5, decoder.decode(stringToChromosome("1101")).get(0), 0.0001);
        assertEquals(-6, decoder.decode(stringToChromosome("1110")).get(0), 0.0001);
        assertEquals(-7, decoder.decode(stringToChromosome("1111")).get(0), 0.0001);
        assertEquals(-8, decoder.decode(stringToChromosome("11000")).get(0), 0.0001);
    }

    @Test
    public void oneThroughEightTimesPointOhOne()
    {
        Decoder decoder = new RealDecoder(2);

        assertEquals(0.00, decoder.decode(stringToChromosome("00")).get(0), 0.0001);
        assertEquals(0.01, decoder.decode(stringToChromosome("01")).get(0), 0.0001);
        assertEquals(0.02, decoder.decode(stringToChromosome("010")).get(0), 0.0001);
        assertEquals(0.03, decoder.decode(stringToChromosome("011")).get(0), 0.0001);
        assertEquals(0.04, decoder.decode(stringToChromosome("0100")).get(0), 0.0001);
        assertEquals(0.05, decoder.decode(stringToChromosome("0101")).get(0), 0.0001);
        assertEquals(0.06, decoder.decode(stringToChromosome("0110")).get(0), 0.0001);
        assertEquals(0.07, decoder.decode(stringToChromosome("0111")).get(0), 0.0001);
        assertEquals(0.08, decoder.decode(stringToChromosome("01000")).get(0), 0.0001);
    }

    @Test
    public void negativeOneThroughEightTimesPointOne()
    {
        Decoder decoder = new RealDecoder(1);

        assertEquals( 0.0, decoder.decode(stringToChromosome("10")).get(0), 0.0001);
        assertEquals(-0.1, decoder.decode(stringToChromosome("11")).get(0), 0.0001);
        assertEquals(-0.2, decoder.decode(stringToChromosome("110")).get(0), 0.0001);
        assertEquals(-0.3, decoder.decode(stringToChromosome("111")).get(0), 0.0001);
        assertEquals(-0.4, decoder.decode(stringToChromosome("1100")).get(0), 0.0001);
        assertEquals(-0.5, decoder.decode(stringToChromosome("1101")).get(0), 0.0001);
        assertEquals(-0.6, decoder.decode(stringToChromosome("1110")).get(0), 0.0001);
        assertEquals(-0.7, decoder.decode(stringToChromosome("1111")).get(0), 0.0001);
        assertEquals(-0.8, decoder.decode(stringToChromosome("11000")).get(0), 0.0001);
    }
}
