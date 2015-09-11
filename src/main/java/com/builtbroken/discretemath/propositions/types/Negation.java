package com.builtbroken.discretemath.propositions.types;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;

import java.util.Map;

/** Inverse of the value of the input character
 * Created by Dark on 9/10/2015.
 */
public class Negation extends AbstractProposition
{
    public final AbstractProposition a;

    public Negation(Character a)
    {
        this(new Variable(a));
    }

    public Negation(AbstractProposition a)
    {
        super(EnumTypes.NEGATION.symbol + a.toString());
        this.a = a;
    }

    @Override
    public boolean getTruthValue(Map<Character, Boolean> inputs)
    {
        return !a.getTruthValue(inputs);
    }
}
