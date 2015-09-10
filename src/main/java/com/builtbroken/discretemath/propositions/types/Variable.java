package com.builtbroken.discretemath.propositions.types;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;

import java.util.HashMap;

/**
 * Proposition with a single character that returns the value of the character
 * Created by Dark on 9/10/2015.
 */
public class Variable extends AbstractProposition
{
    public final Character character;

    public Variable(Character a)
    {
        super(EnumTypes.VAL.symbol + a);
        this.character = a;
    }

    @Override
    public boolean getTruthValue(HashMap<Character, Boolean> inputs)
    {
        return inputs.containsKey(character) ? inputs.get(character) : false;
    }
}
