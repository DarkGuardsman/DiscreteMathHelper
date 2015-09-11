package com.builtbroken.discretemath.propositions.extend;

/**
 * Combination of two proposition into a more complex statement
 * Created by Dark on 9/10/2015.
 */
public abstract class Statement extends AbstractProposition
{
    public final String symbol;
    public final AbstractProposition a;
    public final AbstractProposition b;

    public Statement(AbstractProposition a, AbstractProposition b, String symbol)
    {
        super("(" + a.toString() + " " + symbol + " " + b.toString() + ")");
        this.symbol = symbol;
        this.a = a;
        this.b = b;
    }

    @Override
    public String symbol()
    {
        return symbol;
    }
}
