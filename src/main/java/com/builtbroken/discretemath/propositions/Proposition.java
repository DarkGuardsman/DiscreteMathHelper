package com.builtbroken.discretemath.propositions;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;
import com.builtbroken.discretemath.propositions.types.EnumTypes;
import com.builtbroken.discretemath.propositions.types.Negation;
import com.builtbroken.discretemath.propositions.types.Variable;
import com.builtbroken.jlib.lang.EnglishLetters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Statement that equals true or false but not both
 * Created by Dark on 9/10/2015.
 */
public class Proposition
{
    public final String statement;
    protected AbstractProposition proposition;
    protected List<Character> characters = new ArrayList();

    public Proposition(String statement) throws RuntimeException
    {
        if (statement == null || statement.replace(" ", "").isEmpty())
        {
            throw new RuntimeException("Statement can not be empty");
        }
        this.statement = statement.replace(" ", "");
        parse();
    }

    public void parse()
    {
        char[] chars = statement.toCharArray();
        //Ensure all characters are valid
        for (Character c : chars)
        {
            if (!EnumTypes.isSymbol(c) && !EnglishLetters.isLetter(c) && c != '(' && c != ')')
            {
                throw new RuntimeException("Invalid character " + c);
            }
        }

        if (statement.length() == 1)
        {
            if (EnglishLetters.isLetter(chars[0]))
            {
                proposition = new Variable(chars[0]);
            }
            else
            {
                throw new RuntimeException("Invalid statement");
            }
        }
        // most like negation
        else if (statement.length() == 2)
        {
            if (chars[0] == EnumTypes.NEGATION.symbol.charAt(0) && EnglishLetters.isLetter(chars[1]))
            {
                proposition = new Negation(chars[1]);
            }
            else
            {
                throw new RuntimeException("Invalid statement");
            }
        }
        //Multi statement
        else
        {
            parse(statement);
        }
    }

    protected AbstractProposition parse(String s)
    {
        if (s.length() == 1 && EnglishLetters.isLetter(s.charAt(0)))
        {
            if (!characters.contains(s.charAt(0)))
            {
                characters.add(s.charAt(0));
            }
            return new Variable(s.charAt(0));
        }

        String segmentA = null;
        String segmentB = null;

        EnumTypes mid = null;
        boolean prefix = false;
        boolean prefix2 = false;

        for (int i = 0; i < s.length(); )
        {
            Character c = s.charAt(i);
            if (c == '(')
            {
                int index = s.indexOf(')', i);
                if (segmentA == null)
                {
                    segmentA = s.substring(i, index);
                    i = index + 1;
                }
                else if (segmentB == null)
                {
                    segmentB = s.substring(i, index);
                    i = index + 1;
                }
                else
                {
                    throw new RuntimeException("Invalid statement grouping, reduce multi statements into sets of two inside ( )");
                }
            }
            else if (EnglishLetters.isLetter(c))
            {
                if (!characters.contains(c))
                {
                    characters.add(c);
                }
                if (segmentA == null)
                {
                    segmentA = "" + c;
                }
                else if (segmentB == null)
                {
                    segmentB = "" + c;
                }
                else
                {
                    throw new RuntimeException("Invalid statement grouping, reduce multi statements into sets of two inside ( )");
                }
                i++;
            }
            else if (c == EnumTypes.NEGATION.c)
            {
                if (segmentA == null)
                {
                    prefix = true;
                }
                else if (segmentB == null && mid != null)
                {
                    prefix2 = true;
                }
                else
                {
                    throw new RuntimeException("Invalid statement");
                }
                i++;
            }
            else if (EnumTypes.isSymbol(c))
            {
                if (mid == null)
                {
                    mid = EnumTypes.get(c);
                }
                else
                {
                    throw new RuntimeException("Invalid statement");
                }
            }
            else
            {
                throw new RuntimeException("Invalid character in statement, " + c);
            }
        }

        //Turn into statement
        AbstractProposition a = parse(segmentA);
        AbstractProposition b = parse(segmentB);

        //Apply negations if prefix was found
        if (prefix)
            a = new Negation(a);
        if (prefix2)
            b = new Negation(b);

        return mid.newProposition(a, b);
    }

    public boolean getTruthForValues(HashMap<Character, Boolean> values)
    {
        return proposition.getTruthValue(values);
    }
}
