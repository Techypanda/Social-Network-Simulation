/*
    ORIGINALLY SUBMITTED FOR PRAC 2 BY JONATHAN WRIGHT
    AUTHOR: Jonathan Wright
    DATE: ?/?/2019 Unknown Origin Date
    PURPOSE: Stack Abstract Datatype.
    MODIFIED BY: Jonathan Wright
    DATE OF MODIFICATION: 15/10/2019
*/
import java.io.Serializable;
/*
    ORIGINALLY SUBMITTED FOR PRAC 5 BY JONATHAN WRIGHT
    AUTHOR: Jonathan Wright
    DATE: 16/09/2019 Unknown Origin Date
    PURPOSE: DSA Queue Interface to be implemented by a queue.
    MODIFIED BY: Jonathan Wright
    DATE OF MODIFICATION: 15/10/2019
*/
public class DSAStack implements Serializable
{
    // classfields
    private Object[] stack;
    private int count;
    public static final int DEF_CAP = 100;
    // class constructors
    /**************************************************************************
    * Purpose: default constructor
    * IMPORT: none
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public DSAStack()
    {
        stack = new Object[DEF_CAP];
        count = 0;
    }
    /**************************************************************************
    * Purpose: alternate constructor
    * IMPORT: int maxSize
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public DSAStack(int maxSize)
    {
        stack = new Object[maxSize];
        count = 0;
    }
    // class methods
    /**************************************************************************
    * Purpose: puts object at top of stack
    * IMPORT: Object inObject
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void push(Object inObject)
    {
        if (isFull())
        {
            throw new IllegalArgumentException("Stack is full");
        }
        else
        {
            stack[count] = inObject;
            count++;
        }
    }
    /**************************************************************************
    * Purpose: takes object from top of stack and reutrns.
    * IMPORT: none
    * EXPORT: Object topVal
    * DATE: 27/10/2019
    **************************************************************************/
    public Object pop()
    {
        Object topVal = new Object();
        try
        {
            topVal = top();
            count--; // one less item in stack
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        return topVal;
    }
    /**************************************************************************
    * Purpose: returns topVal without taking out.
    * IMPORT: none
    * EXPORT: topVal object
    * DATE: 27/10/2019
    **************************************************************************/
    public Object top()
    {
        Object topVal = new Object();
        if (isEmpty())
        {
            throw new IllegalArgumentException("Stack is Empty");
        }
        else
        {
            topVal = stack[count - 1];
        }
        return topVal;
    }
    /**************************************************************************
    * Purpose: return if empty
    * IMPORT: none
    * EXPORT: empty boolean
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean isEmpty() {return (count == 0);}
    /**************************************************************************
    * Purpose: return if full
    * IMPORT: none
    * EXPORT: full boolean
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean isFull() {return (count == stack.length);}
    /**************************************************************************
    * Purpose: return count of stack.
    * IMPORT: none
    * EXPORT: count int
    * DATE: 27/10/2019
    **************************************************************************/
    public int count() {return count;}
}
