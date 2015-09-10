package com.builtbroken.discretemath.propositions.extend;

import java.util.HashMap;

/**
 * Sentence that declares a fact that can be true or false but not both
 * Created by Dark on 9/10/2015.
 */
public abstract class AbstractProposition
{
    /** String value of this statement, mainly used for display */
    public final String statement;

    public AbstractProposition(String statement)
    {
        this.statement = statement.trim();
    }

    /**
     * Gets the Truth value for the statement from the provided inputs
     *
     * @param inputs - map of input character to boolean value
     * @return true or false
     */
    public abstract boolean getTruthValue(final HashMap<Character, Boolean> inputs);

    public String symbol()
    {
        return "";
    }
}