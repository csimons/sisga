package cls.decode.test;

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
        RealDecoder decoder = new RealDecoder();

        assertEquals(0, decoder.decode(stringToChromosome("00"), 0), 0.0001);
        assertEquals(1, decoder.decode(stringToChromosome("01"), 0), 0.0001);
        assertEquals(2, decoder.decode(stringToChromosome("010"), 0), 0.0001);
        assertEquals(3, decoder.decode(stringToChromosome("011"), 0), 0.0001);
        assertEquals(4, decoder.decode(stringToChromosome("0100"), 0), 0.0001);
        assertEquals(5, decoder.decode(stringToChromosome("0101"), 0), 0.0001);
        assertEquals(6, decoder.decode(stringToChromosome("0110"), 0), 0.0001);
        assertEquals(7, decoder.decode(stringToChromosome("0111"), 0), 0.0001);
        assertEquals(8, decoder.decode(stringToChromosome("01000"), 0), 0.0001);
    }

    @Test
    public void negativeOneThroughEight()
    {
        RealDecoder decoder = new RealDecoder();

        assertEquals( 0, decoder.decode(stringToChromosome("10"), 0), 0.0001);
        assertEquals(-1, decoder.decode(stringToChromosome("11"), 0), 0.0001);
        assertEquals(-2, decoder.decode(stringToChromosome("110"), 0), 0.0001);
        assertEquals(-3, decoder.decode(stringToChromosome("111"), 0), 0.0001);
        assertEquals(-4, decoder.decode(stringToChromosome("1100"), 0), 0.0001);
        assertEquals(-5, decoder.decode(stringToChromosome("1101"), 0), 0.0001);
        assertEquals(-6, decoder.decode(stringToChromosome("1110"), 0), 0.0001);
        assertEquals(-7, decoder.decode(stringToChromosome("1111"), 0), 0.0001);
        assertEquals(-8, decoder.decode(stringToChromosome("11000"), 0), 0.0001);
    }

    @Test
    public void oneThroughEightTimesPointOhOne()
    {
        RealDecoder decoder = new RealDecoder();

        assertEquals(0.00, decoder.decode(stringToChromosome("00"), 2), 0.0001);
        assertEquals(0.01, decoder.decode(stringToChromosome("01"), 2), 0.0001);
        assertEquals(0.02, decoder.decode(stringToChromosome("010"), 2), 0.0001);
        assertEquals(0.03, decoder.decode(stringToChromosome("011"), 2), 0.0001);
        assertEquals(0.04, decoder.decode(stringToChromosome("0100"), 2), 0.0001);
        assertEquals(0.05, decoder.decode(stringToChromosome("0101"), 2), 0.0001);
        assertEquals(0.06, decoder.decode(stringToChromosome("0110"), 2), 0.0001);
        assertEquals(0.07, decoder.decode(stringToChromosome("0111"), 2), 0.0001);
        assertEquals(0.08, decoder.decode(stringToChromosome("01000"), 2), 0.0001);
    }

    @Test
    public void negativeOneThroughEightTimesPointOne()
    {
        RealDecoder decoder = new RealDecoder();

        assertEquals( 0.0, decoder.decode(stringToChromosome("10"), 1), 0.0001);
        assertEquals(-0.1, decoder.decode(stringToChromosome("11"), 1), 0.0001);
        assertEquals(-0.2, decoder.decode(stringToChromosome("110"), 1), 0.0001);
        assertEquals(-0.3, decoder.decode(stringToChromosome("111"), 1), 0.0001);
        assertEquals(-0.4, decoder.decode(stringToChromosome("1100"), 1), 0.0001);
        assertEquals(-0.5, decoder.decode(stringToChromosome("1101"), 1), 0.0001);
        assertEquals(-0.6, decoder.decode(stringToChromosome("1110"), 1), 0.0001);
        assertEquals(-0.7, decoder.decode(stringToChromosome("1111"), 1), 0.0001);
        assertEquals(-0.8, decoder.decode(stringToChromosome("11000"), 1), 0.0001);
    }
}
