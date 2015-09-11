package com.builtbroken.discretemath.propositions.types;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;
import com.builtbroken.discretemath.propositions.extend.Statement;

import java.util.Map;

/**
 * Exclusive or
 * Created by Dark on 9/10/2015.
 */
public class XOR extends Statement
{
    public XOR(Character a, Character b)
    {
        this(new Variable(a), new Variable(b));
    }

    public XOR(AbstractProposition a, AbstractProposition b)
    {
        super(a, b, EnumTypes.XOR.symbol);
    }

    @Override
    public boolean getTruthValue(Map<Character, Boolean> inputs)
    {
        return a.getTruthValue(inputs) != b.getTruthValue(inputs);
    }

    @Override
    public String toConsole()
    {
        return "(" + a.toConsole() + "+" + b.toConsole() + ")";
    }
}
