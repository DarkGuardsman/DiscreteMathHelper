package com.builtbroken.tests.discretemath;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;
import com.builtbroken.discretemath.propositions.types.Disjunction;
import com.builtbroken.discretemath.propositions.types.Variable;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * JUnit test for {@link Disjunction}
 * Created by Dark on 9/10/2015.
 */
public class TestDisjunction
{
    @Test
    public void testInit()
    {
        AbstractProposition prop = new Disjunction('p', 'q');
        prop = new Disjunction(new Variable('p'), new Variable('p'));
    }

    @Test
    public void testOutput()
    {
        AbstractProposition prop = new Disjunction('p', 'q');
        HashMap<Character, Boolean> values = new HashMap();
        values.put('p', true);
        values.put('q', true);
        Assert.assertTrue(prop.getTruthValue(values));
        values.put('p', true);
        values.put('q', false);
        Assert.assertTrue(prop.getTruthValue(values));
        values.put('p', false);
        values.put('q', true);
        Assert.assertTrue(prop.getTruthValue(values));
        values.put('p', false);
        values.put('q', false);
        Assert.assertFalse(prop.getTruthValue(values));
    }
}
