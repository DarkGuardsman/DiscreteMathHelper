package com.builtbroken.tests.discretemath;

import com.builtbroken.discretemath.propositions.Proposition;
import com.builtbroken.discretemath.propositions.types.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

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

    @Test
    public void testSimpleANDStatement()
    {
        Proposition prop = new Proposition("p" + EnumTypes.CONJUNCTION.symbol + "q");
        Assert.assertTrue(prop.characters.contains('p'));
        Assert.assertTrue(prop.characters.contains('q'));
        Assert.assertTrue("Proposition should be an instance of Conjunction, " + prop.proposition, prop.proposition instanceof Conjunction);
        Assert.assertTrue(((Conjunction) prop.proposition).a instanceof Variable && ((Variable) ((Conjunction) prop.proposition).a).character == 'p');
        Assert.assertTrue(((Conjunction) prop.proposition).b instanceof Variable && ((Variable) ((Conjunction) prop.proposition).b).character == 'q');
    }

    @Test
    public void testSimpleORStatement()
    {
        Proposition prop = new Proposition("p" + EnumTypes.DISJUNCTION.symbol + "q");
        Assert.assertTrue(prop.characters.contains('p'));
        Assert.assertTrue(prop.characters.contains('q'));
        Assert.assertTrue("Proposition should be an instance of Disjunction, " + prop.proposition, prop.proposition instanceof Disjunction);
        Assert.assertTrue(((Disjunction) prop.proposition).a instanceof Variable && ((Variable) ((Disjunction) prop.proposition).a).character == 'p');
        Assert.assertTrue(((Disjunction) prop.proposition).b instanceof Variable && ((Variable) ((Disjunction) prop.proposition).b).character == 'q');
    }

    @Test
    public void testSimpleBiconditionalStatement()
    {
        Proposition prop = new Proposition("p" + EnumTypes.BICONDITIONAL.symbol + "q");
        Assert.assertTrue(prop.characters.contains('p'));
        Assert.assertTrue(prop.characters.contains('q'));
        Assert.assertTrue("Proposition should be an instance of BiConditional, " + prop.proposition, prop.proposition instanceof Biconditional);
        Assert.assertTrue(((Biconditional) prop.proposition).a instanceof Variable && ((Variable) ((Biconditional) prop.proposition).a).character == 'p');
        Assert.assertTrue(((Biconditional) prop.proposition).b instanceof Variable && ((Variable) ((Biconditional) prop.proposition).b).character == 'q');
    }

    @Test
    public void testSimpleConditionalStatement()
    {
        Proposition prop = new Proposition("p" + EnumTypes.CONDITIONAL.symbol + "q");
        Assert.assertTrue(prop.characters.contains('p'));
        Assert.assertTrue(prop.characters.contains('q'));
        Assert.assertTrue("Proposition should be an instance of Conditional, " + prop.proposition, prop.proposition instanceof Conditional);
        Assert.assertTrue(((Conditional) prop.proposition).a instanceof Variable && ((Variable) ((Conditional) prop.proposition).a).character == 'p');
        Assert.assertTrue(((Conditional) prop.proposition).b instanceof Variable && ((Variable) ((Conditional) prop.proposition).b).character == 'q');
    }

    @Test
    public void testSimpleXORStatement()
    {
        Proposition prop = new Proposition("p" + EnumTypes.XOR.symbol + "q");
        Assert.assertTrue(prop.characters.contains('p'));
        Assert.assertTrue(prop.characters.contains('q'));
        Assert.assertTrue("Proposition should be an instance of Disjunction, " + prop.proposition, prop.proposition instanceof XOR);
        Assert.assertTrue(((XOR) prop.proposition).a instanceof Variable && ((Variable) ((XOR) prop.proposition).a).character == 'p');
        Assert.assertTrue(((XOR) prop.proposition).b instanceof Variable && ((Variable) ((XOR) prop.proposition).b).character == 'q');
    }

    @Test
    public void testJunctionStatement()
    {
        Proposition prop = new Proposition("(p" + EnumTypes.XOR.symbol + "q)" + EnumTypes.CONJUNCTION.symbol + "(r" + EnumTypes.CONDITIONAL.symbol + "c)");
        Assert.assertTrue(prop.characters.contains('p'));
        Assert.assertTrue(prop.characters.contains('q'));
        Assert.assertTrue(prop.characters.contains('r'));
        Assert.assertTrue(prop.characters.contains('c'));
        Assert.assertTrue("Proposition should be an instance of Disjunction, " + prop.proposition, prop.proposition instanceof Conjunction);
        Assert.assertTrue(((Conjunction) prop.proposition).a instanceof XOR);
        Assert.assertTrue(((Conjunction) prop.proposition).b instanceof Conditional);
    }

    @Test
    public void testJunctionWithPrefixStatement()
    {
        Proposition prop = new Proposition(EnumTypes.NEGATION.symbol + "(p" + EnumTypes.XOR.symbol + "q)" + EnumTypes.CONJUNCTION.symbol + EnumTypes.NEGATION.symbol + "(r" + EnumTypes.CONDITIONAL.symbol + "c)");
        Assert.assertTrue(prop.characters.contains('p'));
        Assert.assertTrue(prop.characters.contains('q'));
        Assert.assertTrue(prop.characters.contains('r'));
        Assert.assertTrue(prop.characters.contains('c'));
        Assert.assertTrue("Proposition should be an instance of Disjunction, " + prop.proposition, prop.proposition instanceof Conjunction);
        Assert.assertTrue(((Conjunction) prop.proposition).a instanceof Negation && ((Negation) ((Conjunction) prop.proposition).a).a instanceof XOR);
        Assert.assertTrue(((Conjunction) prop.proposition).b instanceof Negation && ((Negation) ((Conjunction) prop.proposition).b).a instanceof Conditional);
    }

    @Test
    public void testSectionOne()
    {
        //These are problems taken from Discrete Mathematics and Its Applications 7th edition by Kenneth H. Rosen
        //These problems have been checked against correct answers so should make for good test matter

        //Page 15 #33A
        Proposition prop = new Proposition("(p" + EnumTypes.DISJUNCTION.symbol + "q)" + EnumTypes.CONDITIONAL.symbol + "(p" + EnumTypes.XOR.symbol + "q)");
        Assert.assertTrue(prop.characters.contains('p'));
        Assert.assertTrue(prop.characters.contains('q'));
        Assert.assertTrue("Proposition should be an instance of Conditional, " + prop.proposition, prop.proposition instanceof Conditional);
        Assert.assertTrue(((Conditional) prop.proposition).a instanceof Disjunction);
        Assert.assertTrue(((Conditional) prop.proposition).b instanceof XOR);

        HashMap<Character, Boolean> map = new HashMap();
        map.put('p', true);
        map.put('q', true);
        Assert.assertFalse(prop.getTruthForValues(map));

        map.put('p', true);
        map.put('q', false);
        Assert.assertTrue(prop.getTruthForValues(map));


        map.put('p', false);
        map.put('q', true);
        Assert.assertTrue(prop.getTruthForValues(map));


        map.put('p', false);
        map.put('q', false);
        Assert.assertTrue(prop.getTruthForValues(map));
    }

}
