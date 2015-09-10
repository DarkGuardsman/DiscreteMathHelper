package com.builtbroken.discretemath.propositions.types;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;

/**
 * Enum of valid types of propositions supported by this software
 * Created by Dark on 9/10/2015.
 */
public enum EnumTypes
{
    VAL(' '),
    NEGATION('\u00AC'),
    CONJUNCTION('\u03BB'),
    DISJUNCTION('v'),
    XOR('\u2295'),
    CONDITIONAL('\u2192'),
    BICONDITIONAL('\u2194');

    public final String symbol;
    public final Character c;

    EnumTypes(Character symbol)
    {
        c = symbol;
        this.symbol = "" + symbol;
    }

    public static boolean isSymbol(Character c)
    {
        return get(c) != null;
    }

    public static EnumTypes get(Character c)
    {
        for (int i = 1; i < values().length; i++)
        {
            if (values()[i].c.equals(c))
            {
                return values()[i];
            }
        }
        return null;
    }

    public AbstractProposition newProposition(AbstractProposition p, AbstractProposition q)
    {
        switch (this)
        {
            case CONJUNCTION:
                return new Conjunction(p, q);
            case DISJUNCTION:
                return new Disjunction(p, q);
            case XOR:
                return new XOR(p, q);
            case CONDITIONAL:
                return new Conditional(p, q);
            case BICONDITIONAL:
                return new Biconditional(p, q);
        }
        return null;
    }
}
