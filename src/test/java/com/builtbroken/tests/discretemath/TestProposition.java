package com.builtbroken.tests.discretemath;

import com.builtbroken.discretemath.propositions.Proposition;
import com.builtbroken.discretemath.propositions.types.EnumTypes;
import com.builtbroken.discretemath.propositions.types.Negation;
import com.builtbroken.discretemath.propositions.types.Variable;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link com.builtbroken.discretemath.propositions.Proposition}
 * Created by Dark on 9/10/2015.
 */
public class TestProposition
{
    @Test
    public void testInit()
    {
        Proposition prop = new Proposition("p");
        try
        {
            prop = new Proposition("");
            Assert.fail("Should have thrown a RunTimeException");
        } catch (RuntimeException e)
        {
            if (!e.getMessage().equalsIgnoreCase("Statement can not be empty"))
                throw e;
        }

        try
        {
            prop = new Proposition(null);
            Assert.fail("Should have thrown a RunTimeException");
        } catch (RuntimeException e)
        {
            if (!e.getMessage().equalsIgnoreCase("Statement can not be empty"))
                throw e;
        }
    }

    @Test
    public void testInvalidParse()
    {
        char[] chars = new char[]{']', '[', '1', '2', '5', '%', '&'};
        for (Character c : chars)
        {
            try
            {
                Proposition prop = new Proposition("" + c);
                Assert.fail("Should have thrown error for " + c);
            } catch (RuntimeException e)
            {
                if (!e.getMessage().startsWith("Invalid character "))
                    throw e;
            }
        }
        for (EnumTypes type : EnumTypes.values())
        {
            if (type != EnumTypes.VAL)
            {
                try
                {
                    Proposition prop = new Proposition("" + type.symbol);
                    Assert.fail("Should have thrown error for " + type.symbol);
                } catch (RuntimeException e)
                {
                    if (!e.getMessage().startsWith("Invalid statement"))
                        throw e;
                }
            }
        }
    }

    @Test
    public void testSingleParse()
    {
        Proposition prop = new Proposition("p");
        Assert.assertTrue(prop.characters.contains('p'));
        Assert.assertTrue(prop.proposition instanceof Variable);
    }

    @Test
    public void testSingleParseNegation()
    {
        Proposition prop = new Proposition(EnumTypes.NEGATION.symbol + "p");
        Assert.assertTrue(prop.characters.contains('p'));
        Assert.assertTrue(prop.proposition instanceof Negation);

        //Test some invalid prefix
        for (EnumTypes type : EnumTypes.values())
        {
            if (type != EnumTypes.VAL && type != EnumTypes.NEGATION)
            {
                try
                {
                    prop = new Proposition(type.symbol + "p");
                    Assert.fail("Should have thrown error for " + type.symbol);
                } catch (RuntimeException e)
                {
                    if (!e.getMessage().startsWith("Invalid statement"))
                        throw e;
                }
            }
        }
    }
}
