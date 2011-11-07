package edu.franklin.ga.cls.model;

import java.util.ArrayList;
import java.util.List;

public class Gene
{
    private List<Boolean> gene;

    private Gene() {} // deny access to default constructor.

    public Gene(int length)
    {
        gene = new ArrayList<Boolean>(length);

        // initialize to random values
        for (int i = 0; i < length; i += 1)
            gene.add(Math.round(Math.random()) == 0);
    }

    public int size()
    {
        return gene.size();
    }

    public Boolean get(int index)
    {
        return gene.get(index);
    }

    public void set(int index, boolean value)
    {
        gene.set(index, value);
    }

    public int getRandIndex()
    {
        return (int) (Math.random() * gene.size());
    }

    public void flip(int index)
    {
        gene.set(index, ! gene.get(index));
    }

    public int hammingDistanceTo(Gene otherGene)
    {
        if (gene.size() != otherGene.size())
            throw new IllegalArgumentException("Arity of operators unequal.");

        int distance = 0;

        for (int i = 0; i < gene.size(); i += 1)
            distance += gene.get(i) == otherGene.get(i) ? 0 : 1;

        return distance;
    }
}
