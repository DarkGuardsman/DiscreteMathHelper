package com.builtbroken.discretemath.propositions;

import com.builtbroken.discretemath.propositions.extend.AbstractProposition;
import com.builtbroken.discretemath.propositions.types.EnumTypes;
import com.builtbroken.discretemath.propositions.types.Negation;
import com.builtbroken.discretemath.propositions.types.Variable;
import com.builtbroken.jlib.lang.EnglishLetters;
import com.builtbroken.jlib.lang.StringHelpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Statement that equals true or false but not both
 * Created by Dark on 9/10/2015.
 */
public class Proposition
{
    public final String statement;
    /** Main proposition, most likely containing sub propositions */
    public AbstractProposition proposition;
    /** List of characters, used to generate truth table values */
    public List<Character> characters = new ArrayList();
    /** List of propositions, mainly used for display of data */
    public List<AbstractProposition> propositions = new ArrayList();

    public List<Map<Character, Boolean>> bool_cache;


    /**
     * Creates a new proposition out of the string
     *
     * @param statement - statement, needs to be broken into groups of 2 so that its easy to validate
     * @throws RuntimeException - thrown if the statement is invalid in any ways see {@link Proposition#parse()} for more details
     */
    public Proposition(String statement) throws RuntimeException
    {
        if (statement == null || statement.replace(" ", "").isEmpty())
        {
            throw new RuntimeException("Statement can not be empty");
        }
        this.statement = statement.replace(" ", "");
        parse();
    }

    /**
     * Parses the statement {@link Proposition#statement} into a {@link Proposition#proposition} that
     * can then be validated with a truth table
     */
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
            if ((chars[0] + "").equalsIgnoreCase("v"))
            {
                throw new RuntimeException("Invalid statement, v is reserved for conjunction statements and shouldn't be used as a variable");
            }
            else if (EnglishLetters.isLetter(chars[0]))
            {
                proposition = new Variable(chars[0]);
                if (!characters.contains(chars[0]))
                {
                    characters.add(chars[0]);
                }
                add(proposition);
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
                if (!characters.contains(chars[1]))
                {
                    characters.add(chars[1]);
                }
                add(proposition);
            }
            else
            {
                throw new RuntimeException("Invalid statement");
            }
        }
        //Multi statement
        else
        {
            proposition = parse(statement);
            add(proposition);
        }
    }

    /**
     * Internal parser for breaking down segements into small segements turning then into Propositions that
     * can easily be processed
     *
     * @param s - statement that can be a single value or a series of small propositions
     * @return new Proposition of the logic contained in the statement provided
     */
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

        //System.out.println(s);
        for (int i = 0; i < s.length(); )
        {
            Character c = s.charAt(i);
            //System.out.println(c);
            if (c == '(')
            {
                int index = s.indexOf(")", i);
                if (index == -1)
                {
                    throw new RuntimeException("Invalid statement grouping, missing end cap ) from index " + i);
                }
                else if (segmentA == null)
                {
                    segmentA = s.substring(i + 1, index);
                    i = index + 1;
                }
                else if (segmentB == null)
                {
                    segmentB = s.substring(i + 1, index);
                    i = index + 1;
                }
                else
                {
                    throw new RuntimeException("Invalid statement grouping, reduce multi statements into sets of two inside ( )");
                }
            }
            else if (!(c + "").equalsIgnoreCase("v") && EnglishLetters.isLetter(c))
            {
                if (!characters.contains(c))
                {
                    characters.add(c);
                }
                if (segmentA == null)
                {
                    segmentA = "" + c;
                }
                else if (mid == null)
                {
                    throw new RuntimeException("Invalid statement, missing middle");
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
            else if (c.equals(EnumTypes.NEGATION.c))
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
                    throw new RuntimeException("Invalid statement, mid is already set to " + mid + " but another symbol was found " + c);
                }
                i++;
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

        add(a);
        add(b);
        return mid.newProposition(a, b);
    }

    /**
     * Adds a proposition to the list for display
     *
     * @param proposition
     */
    protected void add(AbstractProposition proposition)
    {
        if (!propositions.contains(proposition) && !(proposition instanceof Variable))
        {
            propositions.add(proposition);
        }
    }

    /**
     * Gets the truth value for the input variables. Does not check if
     * the input contains all the correct characters. If some characters
     * are missing the result most likely will be false.
     *
     * @param values - map of characters to there respective value
     * @return true or false base on the data provided
     */
    public boolean getTruthForValues(HashMap<Character, Boolean> values)
    {
        return proposition.getTruthValue(values);
    }

    public List<Map<Character, Boolean>> buildLines()
    {
        List<Map<Character, Boolean>> lines = new ArrayList();

        //Number of rows to create for the number of character c^2
        int rows = (int) Math.pow(2, characters.size());

        //Loop threw rows
        for (int i = 0; i < rows; i++)
        {
            //Loop threw characters
            Map<Character, Boolean> map = new HashMap();
            for (int j = characters.size() - 1; j >= 0; j--)
            {
                int v = (i / (int) Math.pow(2, j)) % 2;
                map.put(characters.get(j), v == 1);
            }
            lines.add(map);
        }
        return lines;
    }

    public List<Map<Character, Boolean>> getLines()
    {
        if (bool_cache == null)
            bool_cache = buildLines();
        return bool_cache;
    }

    public void print() throws IOException
    {
        List<Map<Character, Boolean>> lines = buildLines();
        String firstLine = "|";
        for (int line = 0; line < lines.size(); line++)
        {
            Map<Character, Boolean> map = lines.get(line);
            if (line == 0)
            {
                for (Map.Entry<Character, Boolean> entry : map.entrySet())
                {
                    firstLine += " " + entry.getKey() + " |";
                }
                for (AbstractProposition prop : propositions)
                {
                    firstLine += "  " + prop.toConsole() + "  |";
                }
                System.out.println(firstLine);
                for (int i = 0; i < firstLine.length() + 1; i++)
                {
                    System.out.print("-");
                }
                System.out.println();
            }
            else if (line % 4 == 0)
            {
                System.out.println();
            }

            String outputLine = "|";
            for (Map.Entry<Character, Boolean> entry : map.entrySet())
            {
                outputLine += " " + (entry.getValue() ? "T" : "F") + " |";
            }
            for (int p = 0; p < propositions.size(); p++)
            {
                AbstractProposition prop = propositions.get(p);
                int index = firstLine.indexOf("|", outputLine.length());
                int l = index - outputLine.length();
                //System.out.println(index + "   " + outputLine.length() + "   " + l + "   " + outputLine);
                outputLine += StringHelpers.padLeft((prop.getTruthValue(map) ? "T" : "F"), l / 2 + l % 2);
                outputLine += StringHelpers.padRight("", l / 2);
                outputLine += "|";
            }
            System.out.println(outputLine);
        }
    }
}
