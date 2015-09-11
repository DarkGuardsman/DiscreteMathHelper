package com.builtbroken.discretemath.propositions.types;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;
import com.builtbroken.discretemath.propositions.extend.Statement;

import java.util.Map;

/**
 * Proposition of two values that equals true if either value is true
 * Created by Dark on 9/10/2015.
 */
public class Disjunction extends Statement
{
    public Disjunction(Character p, Character q)
    {
        this(new Variable(p), new Variable(q));
    }

    public Disjunction(AbstractProposition a, AbstractProposition b)
    {
        super(a, b, EnumTypes.DISJUNCTION.symbol);
    }

    @Override
    public boolean getTruthValue(Map<Character, Boolean> inputs)
    {
        return a.getTruthValue(inputs) || b.getTruthValue(inputs);
    }
}
