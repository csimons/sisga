package edu.franklin.ga.cls.decode.test;

import edu.franklin.ga.cls.decode.PositiveIntegerDecoder;
import edu.franklin.ga.cls.model.Chromosome;
import org.junit.Test;

import static edu.franklin.ga.cls.decode.test.Util.stringToChromosome;
import static org.junit.Assert.assertEquals;

public class PositiveIntegerDecoderTest
{
    @Test
    public void oneThroughEight()
    {
        PositiveIntegerDecoder decoder = new PositiveIntegerDecoder();

        assertEquals(0, decoder.decode(stringToChromosome("0")));
        assertEquals(1, decoder.decode(stringToChromosome("1")));
        assertEquals(2, decoder.decode(stringToChromosome("10")));
        assertEquals(3, decoder.decode(stringToChromosome("11")));
        assertEquals(4, decoder.decode(stringToChromosome("100")));
        assertEquals(5, decoder.decode(stringToChromosome("101")));
        assertEquals(6, decoder.decode(stringToChromosome("110")));
        assertEquals(7, decoder.decode(stringToChromosome("111")));
        assertEquals(8, decoder.decode(stringToChromosome("1000")));
    }

    @Test
    public void paddedOneThroughEight()
    {
        PositiveIntegerDecoder decoder = new PositiveIntegerDecoder();

        assertEquals(0, decoder.decode(stringToChromosome("00000")));
        assertEquals(1, decoder.decode(stringToChromosome("00001")));
        assertEquals(2, decoder.decode(stringToChromosome("000010")));
        assertEquals(3, decoder.decode(stringToChromosome("000011")));
        assertEquals(4, decoder.decode(stringToChromosome("0000100")));
        assertEquals(5, decoder.decode(stringToChromosome("0000101")));
        assertEquals(6, decoder.decode(stringToChromosome("0000110")));
        assertEquals(7, decoder.decode(stringToChromosome("0000111")));
        assertEquals(8, decoder.decode(stringToChromosome("00001000")));
    }
}
