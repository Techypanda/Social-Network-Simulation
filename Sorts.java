
/**
** PREVIOUSLY SUBMITTED FOR PRACTICAL 8.
** AUTHOR: Jonathan Wright
** DATE: 14/10/2019
** PURPOSE: THIS CLASS CONTAINS SIMPLE SORTS AND ADVANCED SORTS.
** MODIFIED BY: Jonathan Wright
** MODIFICATION DATE: 16/10/2019
*/
class Sorts
{
    // bubble sort
    /**************************************************************************
    * Purpose: Sorts data using bubbleSort method.
    * IMPORT: int[] A
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void bubbleSort(int[] A)
    {
        boolean sorted = true;
        int pass = 0;
        do
        {
            sorted = true;

            for (int i = 0; i < (A.length - pass - 1); i++)
            {
                if (A[i] > A[i+1])
                {
                    sorted = false; // need to continue sorting
                    int temp = A[i]; // temp
                    A[i] = A[i + 1];
                    A[i + 1] = temp; // swappers
                }
            }
            pass += 1; // increment the pass
        }
        while (sorted != true);
    }
    // actuall insertion selection sort
    /**************************************************************************
    * Purpose: Sorts data using insertionSort method.
    * IMPORT: int[] A
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void insertionSort(int[] A)
    {
        for (int nn = 1; nn <= A.length - 1; nn++)
        {
            int ii = nn; // set it to the current 'start'
            int temp = A[ii]; // set a temp?
            while (ii > 0 && A[ii - 1] > temp)
            {
                A[ii] = A[ii-1]; // swap if its smaller
                ii -= 1;
            }
            A[ii] = temp;
        }
    }//

    // selection sort
    /**************************************************************************
    * Purpose: Sorts data using selectionSort method.
    * IMPORT: int[] A
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void selectionSort(int[] A)
    {
        for (int nn = 0; nn <= A.length-1; nn++)
        {
            int minIndex = nn;
            for (int jj = nn + 1; jj <= A.length-1; jj++)
            {
                if (A[jj] < A[minIndex])
                {
                    minIndex = jj;
                }
            }

            int temp = A[minIndex];
            A[minIndex] = A[nn];
            A[nn] = temp;
        }
    }// selection insertionSort()

    // mergeSort - front-end for kick-starting the recursive algorithm
    /**************************************************************************
    * Purpose: wrapper method for mergeSort
    * IMPORT: int[] A
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void mergeSort(int[] A)
    {
        mergeSortRecurse(A, 0, A.length-1);
    }//mergeSort()
    /**************************************************************************
    * Purpose: Sorts data using mergeSort method recursively.
    * IMPORT: int[] A, int leftIdx, int rightIdx
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void mergeSortRecurse(int[] A, int leftIdx, int rightIdx)
    {
        if (leftIdx < rightIdx)
        {
            int midIdx = (leftIdx + rightIdx) / 2;

           mergeSortRecurse(A, leftIdx, midIdx);
            mergeSortRecurse(A, midIdx+1, rightIdx);

            merge(A, leftIdx, midIdx, rightIdx);
        }
        /* Sorted */
    }//mergeSortRecurse()
    /**************************************************************************
    * Purpose: merges data in the array.
    * IMPORT: int[] A, int leftIdx, int midIdx, int rightIdx
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void merge(int[] A, int leftIdx, int midIdx, int rightIdx)
    {
        int[] tempArr = new int[rightIdx - leftIdx + 1];
        int ii = leftIdx;
        int jj = midIdx + 1;
        int kk = 0;
        while (ii <= midIdx && jj <= rightIdx)
        {
            if (A[ii] <= A[jj])
            {
                tempArr[kk] = A[ii];
                ii++;
            }
            else
            {
                tempArr[kk] = A[jj];
                jj++;
            }
            kk++;
        }
        for (ii = ii; ii <= midIdx; ii++)
        {
             tempArr[kk] = A[ii];
             kk++;
        }
        for (jj = jj; jj <= rightIdx; jj++)
        {
            tempArr[kk] = A[jj];
            kk++;
        }
        for (kk = leftIdx; kk <= rightIdx; kk++)
        {
            A[kk] = tempArr[kk - leftIdx];
        }
    }//merge()
    /**************************************************************************
    * Purpose: Sorts data using quickSort left pivot method.
    * IMPORT: int[] A
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    // quickSort - front-end for kick-starting the recursive algorithm
    public static void quickSortLeft(int[] A)
    {
        quickSortLeftRecurse(A, 0, A.length-1);
    }//quickSort()
    /**************************************************************************
    * Purpose: Sorts data using quickSort method.
    * IMPORT: int[] A, int leftIdx, int rightIdx
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void quickSortLeftRecurse(int[] A, int leftIdx, int rightIdx)
    {
        if (rightIdx > leftIdx)
        {
        int pivotIdx = leftIdx;
        int newPivotIdx = doPartitioning(A, leftIdx, rightIdx, pivotIdx);
        quickSortLeftRecurse(A, leftIdx, newPivotIdx-1);
        quickSortLeftRecurse(A, newPivotIdx+1, rightIdx);
        }
    }//quickSortRecurse()
    /**************************************************************************
    * Purpose: Sorts data using quickSort median pivot method.
    * IMPORT: int[] A
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void quickSortMedian(int[] A)
    {
        quickSortMedianRecurse(A, 0, A.length-1);
    }//quickSort()
    /**************************************************************************
    * Purpose: Sorts data using quicksort median pivot method.
    * IMPORT: int[] A, int leftIdx, int rightIdx
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void quickSortMedianRecurse(int[] A, int leftIdx, int rightIdx)
    {
        if (rightIdx > leftIdx)
        {
        int pivotIdx = (leftIdx + rightIdx + ((leftIdx + rightIdx) / 2)) / 3; /* Median Of Three */
        int newPivotIdx = doPartitioning(A, leftIdx, rightIdx, pivotIdx);
        quickSortMedianRecurse(A, leftIdx, newPivotIdx-1);
        quickSortMedianRecurse(A, newPivotIdx+1, rightIdx);
        }
    }//quickSortRecurse()
    // quickSort - front-end for kick-starting the recursive algorithm
    /**************************************************************************
    * Purpose: Sorts data using quickSort method.
    * IMPORT: int[] A
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void quickSort(int[] A)
    {
        quickSortRecurse(A, 0, A.length-1);
    }//quickSort()
    /**************************************************************************
    * Purpose: Sorts data using quickSort method.
    * IMPORT: int[] A, leftIdx, rightIdx
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void quickSortRecurse(int[] A, int leftIdx, int rightIdx)
    {
        if (rightIdx > leftIdx)
        {
        int pivotIdx = (leftIdx + rightIdx) / 2;
        int newPivotIdx = doPartitioning(A, leftIdx, rightIdx, pivotIdx);
        quickSortRecurse(A, leftIdx, newPivotIdx-1);
        quickSortRecurse(A, newPivotIdx+1, rightIdx);
        }
    }//quickSortRecurse()
    /**************************************************************************
    * Purpose: Sorts data using quickSort method.
    * IMPORT: int[] A, int leftIdx, intRightIdx, intPivotidx
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static int doPartitioning(int[] A, int leftIdx, int rightIdx, int pivotIdx)
    {
		        int pivotVal = A[pivotIdx];
                A[pivotIdx] = A[rightIdx];
                A[rightIdx] = pivotVal;
                int currIdx = leftIdx;

                for (int i = leftIdx; i < rightIdx; i++)
                {
                    if (A[i] < pivotVal)
                    {
                        int temp = A[i];
                        A[i] = A[currIdx];
                        A[currIdx] = temp;
                        currIdx = currIdx + 1;
                    }
                }
                int newPivIdx = currIdx;
                A[rightIdx] = A[newPivIdx];
                A[newPivIdx] = pivotVal;
                return newPivIdx;
    }//doPartitioning
    /**************************************************************************
    * Purpose: Sorts data using dutchflag quickSort method.
    * IMPORT: int[] A
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void quickThreeWaySort(int[] A)
    {
        threeWayRecurse(A, 0, A.length - 1);
    }
    /**************************************************************************
    * Purpose: Sorts data using dutchflag quickSort method.
    * IMPORT: int[] A, int leftIdx, int rightIdx
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void threeWayRecurse(int[] A, int leftIdx, int rightIdx)
    {
        if (rightIdx > leftIdx)
        {
            int leftPiv = leftIdx;
            int rightPiv = rightIdx;
            int leftVal = A[leftPiv];
            int i = leftPiv;

            while (i <= rightPiv)
            {
                if (A[i] < leftVal)
                {
                    partioning(A, leftPiv++, i++);
                }
                else if (A[i] > leftVal)
                {
                    partioning(A, i, rightPiv--);
                }
                else
                {
                    i++;
                }
            }
            threeWayRecurse(A, leftIdx, leftPiv - 1);
            threeWayRecurse(A, rightPiv + 1, rightIdx);
        }
    }
    /**************************************************************************
    * Purpose: Sorts data using dutchflag quickSort method.
    * IMPORT: int[] A, int left, int right
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void partioning(int[] A, int left, int right)
    { /* Swap */
        int temp = A[left];
        A[left] = A[right];
        A[right] = temp;
    }
    /**************************************************************************
    * Purpose: Sorts Messages using dutchflag quickSort method.
    * IMPORT: Message[] messages
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void messageSort(Message[] messages)
    { /* Uses Dutch Three Way. */
        messageRecurse(messages, 0, messages.length - 1);
    }
    /**************************************************************************
    * Purpose: Sorts Messages using dutchflag quickSort method.
    * IMPORT: Message[] messages, int leftIdx, int rightIdx
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void messageRecurse(Message[] messages, int leftIdx, int rightIdx)
    {
        if (rightIdx > leftIdx)
        {
            int leftPiv = leftIdx;
            int rightPiv = rightIdx;
            int leftVal = messages[leftPiv].getLikes();
            int i = leftPiv;

            while (i <= rightPiv)
            {
                if (messages[i].getLikes() < leftVal)
                {
                    messagePartition(messages, leftPiv++, i++);
                }
                else if (messages[i].getLikes() > leftVal)
                {
                    messagePartition(messages, i, rightPiv--);
                }
                else
                {
                    i++;
                }
            }
            messageRecurse(messages, leftIdx, leftPiv - 1);
            messageRecurse(messages, rightPiv + 1, rightIdx);
        }
    }
    /**************************************************************************
    * Purpose: Sorts Messages using dutchflag quickSort method.
    * IMPORT: Message[] messages, int left, int right
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void messagePartition(Message[] messages, int left, int right)
    { /* Swap */
        Message temp = messages[left];
        messages[left] = messages[right];
        messages[right] = temp;
    }

}//end Sorts calss
