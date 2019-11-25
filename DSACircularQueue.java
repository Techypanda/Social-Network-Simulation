/*
    ORIGINALLY SUBMITTED FOR PRAC 2 BY JONATHAN WRIGHT
    AUTHOR: Jonathan Wright
    DATE: ?/?/2019 Unknown Origin Date
    PURPOSE: Circular Queue Abstract Datatype.
    MODIFIED BY: Jonathan Wright
    DATE OF MODIFICATION: 15/10/2019
*/
import java.io.Serializable;
/*
    ORIGINALLY SUBMITTED FOR PRAC 5 BY JONATHAN WRIGHT
    AUTHOR: Jonathan Wright
    DATE: 16/09/2019 Unknown Origin Date
    PURPOSE: CircularQueue ADT implemented in java.
    MODIFIED BY: Jonathan Wright
    DATE OF MODIFICATION: 15/10/2019
*/
class DSACircularQueue implements DSAQueue, Serializable
{
    // Classfields
    private int front, back, count;
    private Object[] queue;
    public static final int DEF_CAP = 100;
    // Class Constructors
    /**************************************************************************
    * Purpose: default constructor
    * IMPORT: none
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public DSACircularQueue()
    {
        front = 0; back = 0; count = 0;
        queue = new Object[DEF_CAP];
    }
    /**************************************************************************
    * Purpose: alternate constructor
    * IMPORT: int maxSize
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public DSACircularQueue(int maxSize)
    {
        front = 0; back = 0; count = 0;
        queue = new Object[maxSize];
    }
    // Class Methods
    /**************************************************************************
    * Purpose: will queue up a object into the queue.
    * IMPORT: Object inObject
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    @Override
    public void enqueue(Object inObject)
    {
        if (isFull())
        {
            throw new IllegalArgumentException("Queue is full");
        }
        else
        {
            queue[back] = inObject;
            back++;
            count++;
            if (back == queue.length)
            {
                back = 0;
            }
        }
    }
    /**************************************************************************
    * Purpose: will unqueue a object from the front of queue.
    * IMPORT: none
    * EXPORT: removed object.
    * DATE: 27/10/2019
    **************************************************************************/
    @Override
    public Object dequeue()
    {
        Object frontVal = new Object();
        try
        {
            frontVal = peek();
            count--;
            front++;
            if (front == queue.length)
            {
                front = 0;
            }
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        return frontVal;
    }
    /**************************************************************************
    * Purpose: Will show the object at front of queue
    * IMPORT: none
    * EXPORT: Object at front of queue.
    * DATE: 27/10/2019
    **************************************************************************/
    @Override
    public Object peek()
    {
        if (isEmpty())
        {
            throw new IllegalArgumentException("Queue is empty");
        }
        else
        {
            return queue[front];
        }
    }
    /**************************************************************************
    * Purpose: returns if the queue is empty or not
    * IMPORT: none
    * EXPORT: if queue is empty boolean
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean isEmpty()
    {
        return (count == 0);
    }
    /**************************************************************************
    * Purpose: returns if the queue is full
    * IMPORT: none
    * EXPORT: count == queue.length (bool)
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean isFull()
    {
        return (count == queue.length);
    }
    /**************************************************************************
    * Purpose: returns count of queue
    * IMPORT: none
    * EXPORT: count (int)
    * DATE: 27/10/2019
    **************************************************************************/
    public int count()
    {
        return count;
    }
}
