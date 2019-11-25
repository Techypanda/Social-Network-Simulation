/*******************************************************************************
*
*   AUTHOR: Jonathan Wright
*   PURPOSE: This is a class that holds methods multiple classes will use.
*   DATE: 26/10/2019
*
******************************************************************************/
public class Utilities
{ /* Class Contains Methods Multiple classes will use */
    private static final int DIFFERENCE = 32; /* Lower - Upper */
    private static final int[] lowerBounds = {97, 122}; /* INCLUSIVE */
    private static final int[] capitalBounds = {65, 90}; /* INCLUSIVE */
    /**************************************************************************
    * Purpose: Converts string to camelcase
    * IMPORT: String inWord
    * EXPORT: String camelCased
    * DATE: 27/10/2019
    **************************************************************************/
    public static String camelCase(String inWord)
    { /* Will Convert String To camelCase for appealing view */
        String camelCased = "";
        if (inWord.length() >= 1)
        {
            char[] inside = inWord.toCharArray();
            camelCased += isLowerCase(inside[0])
            ? String.valueOf( (char)(inside[0] - DIFFERENCE) )
            : String.valueOf(inside[0]); /* Ternary Operator if is lowercase make upper else keep same */

            for (int i = 1; i < inside.length; i++)
            {
                if (Character.isUpperCase(inside[i]))
                {
                    camelCased += String.valueOf( Character.toLowerCase(inside[i]) );
                }
                else
                {
                    camelCased += String.valueOf(inside[i]);
                }
                //if (isUpperCase(inside[i])) DOESNT WORK. SO CONFUSED WHY.
                //{
                //System.out.println((char)(inside[0] + 1)); /* For Some Reason it has lost a 1, unsure why? */
                //camelCased += String.valueOf( (char)(inside[0] + DIFFERENCE + 1) );
                //System.out.println((char)(inside[0] + DIFFERENCE + 1));
                //}
                //else
                //{
                //camelCased += String.valueOf(inside[i]);
                //}
            }
            //System.out.println(camelCased)
        }
        return camelCased;
    }
    /**************************************************************************
    * Purpose: returns if it is lowercase character
    * IMPORT: char x
    * EXPORT: isLowercase boolean
    * DATE: 27/10/2019
    **************************************************************************/
    private static boolean isLowerCase(char x)
    {
        return (x >= lowerBounds[0] && x <= lowerBounds[1]);
    }
    /**************************************************************************
    * Purpose: retunrs if it is a uppercase character
    * IMPORT: char x
    * EXPORT: isUpperCase boolean
    * DATE: 27/10/2019
    **************************************************************************/
    private static boolean isUpperCase(char x)
    {
        return (x >= capitalBounds[0] && x <= capitalBounds[1]);
    }
}
