package com.builtbroken.tests.discretemath;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;
import com.builtbroken.discretemath.propositions.types.Variable;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Test for {@link Variable}
 * Created by Dark on 9/10/2015.
 */
public class TestVariable
{
    @Test
    public void testInit()
    {
        AbstractProposition prop = new Variable('p');
    }

    @Test
    public void testOutput()
    {
        AbstractProposition prop = new Variable('p');
        HashMap<Character, Boolean> values = new HashMap();
        values.put('p', true);
        Assert.assertTrue(prop.getTruthValue(values));
        values.put('p', false);
        Assert.assertFalse(prop.getTruthValue(values));
    }
}
