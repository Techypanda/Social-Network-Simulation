/*
    ORIGINALLY SUBMITTED FOR PRAC 2 BY JONATHAN WRIGHT
    AUTHOR: Jonathan Wright
    DATE: 12/08/2019 Unknown Origin Date
    PURPOSE: DSA Queue Interface to be implemented by a queue.
    MODIFIED BY: Jonathan Wright
    DATE OF MODIFICATION: 15/10/2019
*/
interface DSAQueue
{
    abstract void enqueue(Object inObject);
    abstract Object dequeue();
    abstract Object peek();
}
