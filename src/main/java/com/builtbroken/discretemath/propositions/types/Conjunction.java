package com.builtbroken.discretemath.propositions.types;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;
import com.builtbroken.discretemath.propositions.extend.Statement;

import java.util.HashMap;

/**
 * Propotion of the combination of two values that both equal true for the result to be true
 * <p/>
 * && in java terms, ^ in discrete math terms
 * <p/>
 * Created by Dark on 9/10/2015.
 */
public class Conjunction extends Statement
{
    public Conjunction(Character p, Character q)
    {
        this(new Variable(p), new Variable(q));
    }

    public Conjunction(AbstractProposition a, AbstractProposition b)
    {
        super(a, b, EnumTypes.CONJUNCTION.symbol);
    }

    @Override
    public boolean getTruthValue(HashMap<Character, Boolean> inputs)
    {
        return a.getTruthValue(inputs) && b.getTruthValue(inputs);
    }
}
