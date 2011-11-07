package edu.franklin.ga.cls.decode.test;

import edu.franklin.ga.cls.decode.PositiveIntegerDecoder;
import edu.franklin.ga.cls.model.Gene;
import org.junit.Test;

import static edu.franklin.ga.cls.decode.test.Util.stringToGene;
import static org.junit.Assert.assertEquals;

public class PositiveIntegerDecoderTest
{
    @Test
    public void oneThroughEight()
    {
        PositiveIntegerDecoder decoder = new PositiveIntegerDecoder();

        assertEquals(0, decoder.decode(stringToGene("0")));
        assertEquals(1, decoder.decode(stringToGene("1")));
        assertEquals(2, decoder.decode(stringToGene("10")));
        assertEquals(3, decoder.decode(stringToGene("11")));
        assertEquals(4, decoder.decode(stringToGene("100")));
        assertEquals(5, decoder.decode(stringToGene("101")));
        assertEquals(6, decoder.decode(stringToGene("110")));
        assertEquals(7, decoder.decode(stringToGene("111")));
        assertEquals(8, decoder.decode(stringToGene("1000")));
    }

    @Test
    public void paddedOneThroughEight()
    {
        PositiveIntegerDecoder decoder = new PositiveIntegerDecoder();

        assertEquals(0, decoder.decode(stringToGene("00000")));
        assertEquals(1, decoder.decode(stringToGene("00001")));
        assertEquals(2, decoder.decode(stringToGene("000010")));
        assertEquals(3, decoder.decode(stringToGene("000011")));
        assertEquals(4, decoder.decode(stringToGene("0000100")));
        assertEquals(5, decoder.decode(stringToGene("0000101")));
        assertEquals(6, decoder.decode(stringToGene("0000110")));
        assertEquals(7, decoder.decode(stringToGene("0000111")));
        assertEquals(8, decoder.decode(stringToGene("00001000")));
    }
}
