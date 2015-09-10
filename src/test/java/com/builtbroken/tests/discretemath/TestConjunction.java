package com.builtbroken.tests.discretemath;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;
import com.builtbroken.discretemath.propositions.types.Conjunction;
import com.builtbroken.discretemath.propositions.types.Variable;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * JUnit test for {@link com.builtbroken.discretemath.propositions.types.Conjunction}
 * Created by Dark on 9/10/2015.
 */
public class TestConjunction
{
    @Test
    public void testInit()
    {
        AbstractProposition prop = new Conjunction('p', 'q');
        prop = new Conjunction(new Variable('p'), new Variable('p'));
    }

    @Test
    public void testOutput()
    {
        AbstractProposition prop = new Conjunction('p', 'q');
        HashMap<Character, Boolean> values = new HashMap();
        values.put('p', true);
        values.put('q', true);
        Assert.assertTrue(prop.getTruthValue(values));
        values.put('p', true);
        values.put('q', false);
        Assert.assertFalse(prop.getTruthValue(values));
        values.put('p', false);
        values.put('q', true);
        Assert.assertFalse(prop.getTruthValue(values));
        values.put('p', false);
        values.put('q', false);
        Assert.assertFalse(prop.getTruthValue(values));
    }
}
