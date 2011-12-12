package cls.decode.test;

import cls.decode.Decoder;
import cls.decode.ThreeRealDecoder;
import cls.model.Chromosome;
import org.junit.Test;

import static cls.decode.test.Util.stringToChromosome;
import static org.junit.Assert.assertEquals;

public class ThreeRealDecoderTest
{
    private Chromosome s2c(String bitString)
    {
        return stringToChromosome(bitString);
    }

    @Test
    public void oneThroughSix()
    {
        Decoder decoder = new ThreeRealDecoder();
        decoder.setPrecision(0);

        assertEquals(1, decoder.decode(s2c("000010001000011")).get(0), 0.0001);
        assertEquals(2, decoder.decode(s2c("000010001000011")).get(1), 0.0001);
        assertEquals(3, decoder.decode(s2c("000010001000011")).get(2), 0.0001);

        assertEquals(4, decoder.decode(s2c("001000010100110")).get(0), 0.0001);
        assertEquals(5, decoder.decode(s2c("001000010100110")).get(1), 0.0001);
        assertEquals(6, decoder.decode(s2c("001000010100110")).get(2), 0.0001);
    }

    @Test
    public void negativeOneThroughSix()
    {
        Decoder decoder = new ThreeRealDecoder();
        decoder.setPrecision(0);

        assertEquals(-1, decoder.decode(s2c("100011001010011")).get(0), 0.0001);
        assertEquals(-2, decoder.decode(s2c("100011001010011")).get(1), 0.0001);
        assertEquals(-3, decoder.decode(s2c("100011001010011")).get(2), 0.0001);

        assertEquals(-4, decoder.decode(s2c("101001010110110")).get(0), 0.0001);
        assertEquals(-5, decoder.decode(s2c("101001010110110")).get(1), 0.0001);
        assertEquals(-6, decoder.decode(s2c("101001010110110")).get(2), 0.0001);
    }

    @Test
    public void oneThroughSixTimesPointOne()
    {
      Decoder decoder = new ThreeRealDecoder();
      decoder.setPrecision(1);

      assertEquals(0.1, decoder.decode(s2c("000010001000011")).get(0), 0.0001);
      assertEquals(0.2, decoder.decode(s2c("000010001000011")).get(1), 0.0001);
      assertEquals(0.3, decoder.decode(s2c("000010001000011")).get(2), 0.0001);

      assertEquals(0.4, decoder.decode(s2c("001000010100110")).get(0), 0.0001);
      assertEquals(0.5, decoder.decode(s2c("001000010100110")).get(1), 0.0001);
      assertEquals(0.6, decoder.decode(s2c("001000010100110")).get(2), 0.0001);
    }

    @Test
    public void negativeOneThroughSixTimesPointOne()
    {
      Decoder decoder = new ThreeRealDecoder();
      decoder.setPrecision(1);

      assertEquals(-0.1, decoder.decode(s2c("100011001010011")).get(0), 0.0001);
      assertEquals(-0.2, decoder.decode(s2c("100011001010011")).get(1), 0.0001);
      assertEquals(-0.3, decoder.decode(s2c("100011001010011")).get(2), 0.0001);

      assertEquals(-0.4, decoder.decode(s2c("101001010110110")).get(0), 0.0001);
      assertEquals(-0.5, decoder.decode(s2c("101001010110110")).get(1), 0.0001);
      assertEquals(-0.6, decoder.decode(s2c("101001010110110")).get(2), 0.0001);
    }
}
