import java.util.*;
import java.io.*;
/*
    ORIGINALLY SUBMITTED FOR PRAC 5 BY JONATHAN WRIGHT
    AUTHOR: Jonathan Wright
    DATE: 16/09/2019 Unknown Origin Date
    PURPOSE: Generic Linked List Abstract Datatype.
    MODIFIED BY: Jonathan Wright
    DATE OF MODIFICATION: 15/10/2019
*/
public class DSALinkedList<T> implements Iterable, Serializable
{
    private class DSAListNode<T> implements Serializable
    {
        // classfields
        private T value;
        private DSAListNode<T> next;
        private DSAListNode<T> prev;
        // class constructors
        /**************************************************************************
        * Purpose: Alternate constructor
        * IMPORT: T inValue
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public DSAListNode(T inValue)
        {
            value = inValue;
            next = null;
            prev = null;
        }
        // class methods
        /**************************************************************************
        * Purpose: returns value of node
        * IMPORT: none
        * EXPORT: T value
        * DATE: 27/10/2019
        **************************************************************************/
        public T getValue()
        {
            return value;
        }
        /**************************************************************************
        * Purpose: sets value to inValue
        * IMPORT: T inValue
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void setValue(T inValue)
        {
            value = inValue;
        }
        /**************************************************************************
        * Purpose: getsNext in list.
        * IMPORT: none
        * EXPORT: next (T)
        * DATE: 27/10/2019
        **************************************************************************/
        public DSAListNode getNext()
        {
            return next;
        }
        /**************************************************************************
        * Purpose: sets next to inNext
        * IMPORT: DSAListNode<T> inNext
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void setNext(DSAListNode<T> inNext)
        {
            next = inNext;
        }
        /**************************************************************************
        * Purpose: gets prev
        * IMPORT: none
        * EXPORT: T prev
        * DATE: 27/10/2019
        **************************************************************************/
        public DSAListNode getPrev()
        {
            return prev;
        }
        /**************************************************************************
        * Purpose: sets prev to inprev
        * IMPORT: DSAListNode<T> inPrev
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void setPrev(DSAListNode<T> inPrev)
        {
            prev = inPrev;
        }
    }
    /**************************************************************************
    * Purpose: Iterator that will iterate the list, (ForEach Loops, etc.)
    * IMPORT: none
    * EXPORT: new DSAListIterator
    * DATE: 27/10/2019
    **************************************************************************/
    public Iterator iterator()
    {
        return new DSAListIterator(this);
    }
    private class DSAListIterator<T> implements Iterator
    {
        private DSAListNode<T> iterNext;
        /**************************************************************************
        * Purpose: alternate constructor
        * IMPORT: DSALinkedList theList
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public DSAListIterator(DSALinkedList theList)
        {
            iterNext = theList.head;
        }
        // iterator implementation
        /**************************************************************************
        * Purpose: returns if iterator has next.
        * IMPORT: none
        * EXPORT: boolean hasNext
        * DATE: 27/10/2019
        **************************************************************************/
        public boolean hasNext()
        {
            return (iterNext != null);
        }
        /**************************************************************************
        * Purpose: returns next object in list.
        * IMPORT: none
        * EXPORT: T value.
        * DATE: 27/10/2019
        **************************************************************************/
        public T next()
        {
            T value;
            if (iterNext == null)
            {
                value = null;
            }
            else
            {
                value = iterNext.getValue();
                iterNext = iterNext.getNext();
            }
            return value;
        }
        /**************************************************************************
        * Purpose: removes object (iterator implementation not supported.)
        * IMPORT: none
        * EXPORT: nill, throws exception.
        * DATE: 27/10/2019
        **************************************************************************/
        public void remove()
        {
            throw new UnsupportedOperationException("Not supported!");
        }
    }
    // classfields
    private DSAListNode<T> head;
    private DSAListNode<T> tail;
    // class constructors
    /**************************************************************************
    * Purpose: default constructor
    * IMPORT: none
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public DSALinkedList()
    {
        head = null;
        tail = null;
    }
    // class methods
    /**************************************************************************
    * Purpose: Inserts value to head.
    * IMPORT: T newValue
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void insertFirst(T newValue)
    {
        DSAListNode newNode = new DSAListNode(newValue);
        if (isEmpty())
        {
            head = newNode;
            tail = newNode;
        }
        else
        {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
            updateTail(head);
        }
    }
    /**************************************************************************
    * Purpose: Updates tail of list.
    * IMPORT: DSAListNode currNode
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void updateTail(DSAListNode currNode)
    {
        if (currNode != null)
        {
            while (currNode.getNext() != null)
            { // goes until end
                currNode = currNode.getNext();
            }
            tail = currNode; // End of linked List so its the tail!
        }
        else if (currNode == null)
        {
            tail = null; // this means theres nothing in the head??
        }
    }
    /**************************************************************************
    * Purpose: removes a selected object from list.
    * IMPORT: Object inObject
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void remove(Object inObject)
    { /* This Finds and Removes A Object. */
        DSAListNode curr = head;
        while (curr != null)
        {
            if (curr.getValue().equals(inObject))
            {
                if (curr.getPrev() != null && curr.getNext() != null)
                { /* In The Middle Somewhere */
                    //System.out.println("FOUND NODE! CONDITION 1");
                    ((DSAListNode)curr).getPrev().setNext(((DSAListNode)curr).getNext());
                    ((DSAListNode)curr).getNext().setPrev(((DSAListNode)curr).getPrev());
                }
                else if (curr.getPrev() == null && curr.getNext() != null) /* It is the head. */
                {
                    //System.out.println("FOUND NODE! CONDITION 2");
                    removeFirst();
                    /*curr.getNext().setPrev(null);
                    head = curr.getNext();*/
                }
                else if (curr.getNext() == null && curr.getPrev() != null) /* It is the tail. */
                {
                    //System.out.println("FOUND NODE! CONDITION 3" + ((DSAListNode)curr.getPrev()).getValue() + " <-");
                    removeLast();
                    /*curr.getPrev().setNext(null);*/
                }
                else
                {
                    /*Its Alone*/
                    removeLast();
                }
            }
            curr = curr.getNext();
        }
    }
    /**************************************************************************
    * Purpose: insert object into tail.
    * IMPORT: T newValue
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void insertLast(T newValue)
    {
        DSAListNode newNode = new DSAListNode(newValue);
        if (isEmpty())
        {
            head = newNode;
            tail = newNode;
        }
        /*else if (tail == null)
        {
            DSAListNode latestNode = head;
            // gotta find tail...
            while (latestNode.getNext() != null)
            {

            }
        }*/
        else
        {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
    }
    /**************************************************************************
    * Purpose: returns if list is empty.
    * IMPORT: none
    * EXPORT: isEmpty (bollean)
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean isEmpty()
    {
        return (head == null);
    }
    /**************************************************************************
    * Purpose: returns first val in list.
    * IMPORT: none
    * EXPORT: T headVal
    * DATE: 27/10/2019
    **************************************************************************/
    public T peekFirst()
    {
        if (isEmpty())
        {
            throw new IllegalArgumentException("List is empty!");
        }
        else
        {
            return head.getValue();
        }
    }
    /**************************************************************************
    * Purpose: return last val in list.
    * IMPORT: none
    * EXPORT: T tailVal
    * DATE: 27/10/2019
    **************************************************************************/
    public T peekLast()
    {
        if (isEmpty())
        {
            throw new IllegalArgumentException("List is empty!");
        }
        else
        {
            return tail.getValue();
        }
    }
    /**************************************************************************
    * Purpose: returns and removes first val in list.
    * IMPORT: none
    * EXPORT: T firstVal
    * DATE: 27/10/2019
    **************************************************************************/
    public T removeFirst()
    {
        if (isEmpty())
        {
            throw new IllegalArgumentException("List is empty!");
        }
        else
        {
            DSAListNode<T> outV = head;
            head = head.getNext();
            if (head != null)
            {
                head.setPrev(null);
            }
            updateTail(head);
            return outV.getValue();
        }
    }
    /**************************************************************************
    * Purpose: returns and removes last val in list.
    * IMPORT: none
    * EXPORT: T lastVal
    * DATE: 27/10/2019
    **************************************************************************/
    public T removeLast()
    {
        if (isEmpty()) // Use Tail
        {
            throw new IllegalArgumentException("List is empty!");
        }
        else
        {
            DSAListNode<T> outV = tail;
            tail = outV.getPrev();
            if (tail != null)
            {
                tail.setNext(null);
            }
            else
            {
                head = null; /* List Is Empty! */
            }
            return outV.getValue();
        }
    }
    /**************************************************************************
    * Purpose: returns size of linkedList
    * IMPORT: none
    * EXPORT: currSize (int)
    * DATE: 27/10/2019
    **************************************************************************/
    public int size()
    {
        int currSize = 0;
        for (Object o: this)
        {
            currSize++;
        }
        return currSize;
    }
    /**************************************************************************
    * Purpose: returns if the list contains object
    * IMPORT: Object o
    * EXPORT: boolean outV
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean contains(Object o)
    {
        boolean outV = false;
        for (Object i: this)
        {
            if (i.equals(o))
            {
                outV = true;
            }
        }
        return outV;
    }
}
