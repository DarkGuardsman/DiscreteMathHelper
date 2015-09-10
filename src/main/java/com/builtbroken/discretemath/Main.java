package com.builtbroken.discretemath;

import java.util.Scanner;

/**
 * Main class for the math helper. Loads up the GUI or cmd process for the user to work threw discrete math problems. Mainly
 * by solving complex problems involving truth tables. That are not very easy to visualize on paper and could use a computer
 * to check if they are correct.
 * Created by Robert Seifert on 9/10/2015.
 */
public class Main
{
    public final static String VERSION = "0.0.1";

    public static void main(String... args)
    {

        System.out.println("===========================================");
        System.out.println("=== Loading Discrete Math Helper v" + VERSION + " ===");
        System.out.println("===========================================");
        Scanner in = new Scanner(System.in);

        if (args != null && args.length > 0 && args[0].equalsIgnoreCase("-noGUI"))
        {
            //Run console mode
        }
        else
        {
            //Run GUI mode
        }

        System.out.println("===========================================");
        System.out.println("Done... (press 'any' key to exit");
        in.nextLine();
        in.close();
    }
}
