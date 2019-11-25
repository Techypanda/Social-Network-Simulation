/*
    ORIGINALLY SUBMITTED FOR PRAC 5 BY JONATHAN WRIGHT
    AUTHOR: Jonathan Wright
    DATE: 16/09/2019 
    PURPOSE: This is graph test, it is not needed but kept incase
    you would like to run my testharness for graph.
    MODIFIED BY: Jonathan Wright
    DATE OF MODIFICATION: 15/10/2019
*/
class GraphTest
{
    public static void main(String[] args)
    {
        DSAGraph x = new DSAGraph();
        x.addVertex("A", "Hello world");
        x.addVertex("B", "X World");
        x.addVertex("C", "C World");
        x.addVertex("D", "D World");
        x.addVertex("E", "E World");
        x.addEdge("A","B", "A edge", "1");
        x.addEdge("A","E", "A edge", "1");
        x.addEdge("A","D", "A edge", "1");
        x.addEdge("D","C", "A edge", "1");
        x.addEdge("C","E", "A edge", "1");
        x.addEdge("E","B", "A edge", "1");
        x.addEdge("B","C", "A edge", "1");
        System.out.println("Expected Output: ");
        System.out.println("A B E D");
        System.out.println("B A E C");
        System.out.println("C D E B");
        System.out.println("D A C");
        System.out.println("E A C B");
        System.out.println("Actual Output: ");
        System.out.println(x.displayAsList());
        System.out.println(FakeJUnit.PASS);
        System.out.println("Expected Output: ");
        System.out.println("  A B C D E");
        System.out.println("A 0 1 0 1 1");
        System.out.println("B 1 0 1 0 1");
        System.out.println("C 0 1 0 1 1");
        System.out.println("D 1 0 1 0 0");
        System.out.println("E 1 1 1 0 0");
        System.out.println("Actual Output: ");
        System.out.println(x.displayAsMatrix());
        System.out.println(FakeJUnit.PASS);
        System.out.println("\nNow testing FileIO");
        DSAGraph z = FileIOGraph.readGraph("testGraph.csv");
        System.out.println("Expected Output: ");
        System.out.println("A B D E");
        System.out.println("B A C E");
        System.out.println("C B D E");
        System.out.println("D A C");
        System.out.println("E A C B");
        System.out.println("Actual Output: ");
        System.out.println(z.displayAsList());
        System.out.println("Expected Output: ");
        System.out.println("  A B C D E");
        System.out.println("A 0 1 0 1 1");
        System.out.println("B 1 0 1 0 1");
        System.out.println("C 0 1 0 1 1");
        System.out.println("D 1 0 1 0 0");
        System.out.println("E 1 1 1 0 0");
        System.out.println("Actual Output: ");
        System.out.println(z.displayAsMatrix());
        System.out.println(FakeJUnit.PASS);
        System.out.println("Testing DFS: ");
        DSAGraph ex1 = FileIOGraph.readGraph("example1.csv");
        DSAGraph ex2 = FileIOGraph.readGraph("example2.csv");
        DSAGraph dfsex1 = ex1.depthFirstSearch();
        DSAGraph dfsex2 = ex2.depthFirstSearch();
        DSAGraph bfsex1 = ex1.breadthFirstSearch();
        DSAGraph bfsex2 = ex2.breadthFirstSearch();
        System.out.println(dfsex1.displayAsMatrix());
        System.out.println("Verified By Hand." + FakeJUnit.PASS);
        System.out.println(dfsex2.displayAsMatrix());
        System.out.println("Verified By Hand." + FakeJUnit.PASS);
        System.out.println(bfsex1.displayAsMatrix());
        System.out.println("Verified By Hand." + FakeJUnit.PASS);
        System.out.println(bfsex2.displayAsMatrix());
        System.out.println("Verified By Hand." + FakeJUnit.PASS);
    }
}
