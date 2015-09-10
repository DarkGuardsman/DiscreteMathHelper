package com.builtbroken.discretemath.propositions.types;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;

import java.util.HashMap;

/**
 * The inverse of AND
 * Created by Dark on 9/10/2015.
 */
public class NAND extends Conjunction
{
    public NAND(Character p, Character q)
    {
        super(p, q);
    }

    public NAND(AbstractProposition a, AbstractProposition b)
    {
        super(a, b);
    }

    @Override
    public boolean getTruthValue(HashMap<Character, Boolean> inputs)
    {
        return !super.getTruthValue(inputs);
    }
}
