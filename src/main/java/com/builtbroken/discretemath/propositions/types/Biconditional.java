package com.builtbroken.discretemath.propositions.types;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;
import com.builtbroken.discretemath.propositions.extend.Statement;

import java.util.Map;


/**
 * Statement were both p and q must match each other
 * Created by Dark on 9/10/2015.
 */
public class Biconditional extends Statement
{
    public Biconditional(Character p, Character q)
    {
        this(new Variable(p), new Variable(q));
    }

    public Biconditional(AbstractProposition a, AbstractProposition b)
    {
        super(a, b, EnumTypes.BICONDITIONAL.symbol);
    }

    @Override
    public boolean getTruthValue(Map<Character, Boolean> inputs)
    {
        return a.getTruthValue(inputs) == b.getTruthValue(inputs);
    }

    @Override
    public String toConsole()
    {
        return "(" + a.toConsole() + "<->" + b.toConsole() + ")";
    }
}
