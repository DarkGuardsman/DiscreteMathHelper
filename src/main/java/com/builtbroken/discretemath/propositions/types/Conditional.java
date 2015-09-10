package com.builtbroken.discretemath.propositions.types;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;
import com.builtbroken.discretemath.propositions.extend.Statement;

import java.util.HashMap;

/**
 * If then statement in which the only time it is false is when a is true and b is false
 * Created by Dark on 9/10/2015.
 */
public class Conditional extends Statement
{
    public Conditional(Character p, Character q)
    {
        this(new Variable(p), new Variable(q));
    }

    public Conditional(AbstractProposition a, AbstractProposition b)
    {
        super(a, b, EnumTypes.CONDITIONAL.symbol);
    }

    @Override
    public boolean getTruthValue(HashMap<Character, Boolean> inputs)
    {
        return a.getTruthValue(inputs) ? b.getTruthValue(inputs) : true;
    }
}
