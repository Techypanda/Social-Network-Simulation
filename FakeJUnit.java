/*******************************************************************************
*
* This FakeJUnit was submitted for my Prac5 Signoff aswell as my GitHub
* (Techypanda), which I now regret as it will likely trigger plagerism check
* However this is my previous work for Practical 5.
*
* Purpose: This is supposed to be my custom "JUnit".
* Date: 28/08/2019
* Author: Jonathan Wright
*
*******************************************************************************/
public class FakeJUnit
{
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String WBG = "\u001B[47m"; // DECIDED TO NOT USE!
    private static final String RBG = "\u001B[41m"; // DECIDED TO NOT USE!
    public static final String PASS = (RED + "P" + YELLOW + "A" + GREEN + "S" + BLUE + "S" + RESET);
    public static final String FAIL = (RED + "FAILED!");
    /**************************************************************************
    * Date: 28/08/2019
    * Purpose: To assert input 1 = input 2 (DATATYPE: INTEGER)
    ***************************************************************************/
    public static void assertEquals(int x, int y)
    {
        if (x == y) { System.out.println(PASS + " value 1: " + x + " is equal to value 2: " + y); }
        else { System.out.println(FAIL + " value 1: " + x + " is not equal to value 2: " + y); }
    }
    /**************************************************************************
    * Date: 28/08/2019
    * Purpose: To assert input 1 = input 2 (DATATYPE: DOUBLE)
    ***************************************************************************/
    public static void assertEquals(double x, double y)
    {
        if (x == y) { System.out.println(PASS + " value 1: " + x + " is equal to value 2: " + y); }
        else { System.out.println(FAIL + " value 1: " + x + " is not equal to value 2: " + y); }
    }
    public static void assertDoubleEquals(double x, double y)
    {
        if (x == y) { System.out.println(PASS + " value 1: " + x + " is equal to value 2: " + y); }
        else { System.out.println(FAIL + " value 1: " + x + " is not equal to value 2: " + y); }
    }
    /**************************************************************************
    * Date: 28/08/2019
    * Purpose: To assert input 1 = input 2 (DATATYPE: OBJECT)
    ***************************************************************************/
    public static void assertEquals(Object x, Object y)
    {
        if (x.equals(y)) { System.out.println(PASS + " value 1: " + x + " is equal to value 2: " + y); }
        else { System.out.println(FAIL + " value 1: " + x + " is not equal to value 2: " + y); }
    }
    /**************************************************************************
    * Date: 16/10/2019
    * Purpose: To assert input 1 = input 2 (DATATYPE: STRING)
    ***************************************************************************/
    public static void assertEquals(String x, String y)
    {
        if (x.equals(y)) { System.out.println(PASS + " value 1: " + x + " is equal to value 2: " + y); }
        else { System.out.println(FAIL + " value 1: " + x + " is not equal to value 2: " + y); }
    }
    /**************************************************************************
    * Date: 28/08/2019
    * Purpose: To assert input 1 != input 2 (DATATYPE: INTEGER)
    ***************************************************************************/
    public static void assertNotEquals(int x, int y)
    {
        if (x != y) { System.out.println(PASS + " value 1: " + x + " is not equal to value 2: " + y); }
        else { System.out.println(FAIL + " value 1: " + x + " is equal to value 2: " + y); }
    }
    /**************************************************************************
    * Date: 28/08/2019
    * Purpose: To assert input 1 != input 2 (DATATYPE: DOUBLE)
    ***************************************************************************/
    public static void assertNotEquals(double x, double y)
    {
        if (x != y) { System.out.println(PASS + " value 1: " + x + " is not equal to value 2: " + y); }
        else { System.out.println(FAIL + " value 1: " + x + " is equal to value 2: " + y); }
    }
    /**************************************************************************
    * Date: 28/08/2019
    * Purpose: To assert input 1 != input 2 (DATATYPE: OBJECT)
    ***************************************************************************/
    public static void assertNotEquals(Object x, Object y)
    {
        if (!x.equals(y)) { System.out.println(PASS + " value 1: " + x + " is not equal to value 2: " + y); }
        else { System.out.println(FAIL + " value 1: " + x + " is equal to value 2: " + y); }
    }
}
