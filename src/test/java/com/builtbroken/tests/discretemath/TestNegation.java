package com.builtbroken.tests.discretemath;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;
import com.builtbroken.discretemath.propositions.types.Negation;
import com.builtbroken.discretemath.propositions.types.Variable;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Test for {@link Negation}
 * Created by Dark on 9/10/2015.
 */
public class TestNegation
{
    @Test
    public void testInit()
    {
        AbstractProposition prop = new Negation('p');
        prop = new Negation(new Variable('p'));
    }

    @Test
    public void testOutput()
    {
        AbstractProposition prop = new Negation('p');
        HashMap<Character, Boolean> values = new HashMap();
        values.put('p', true);
        Assert.assertFalse(prop.getTruthValue(values));
        values.put('p', false);
        Assert.assertTrue(prop.getTruthValue(values));
    }
}
