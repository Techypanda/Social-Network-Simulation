import java.io.*;
/*
    ORIGINALLY SUBMITTED FOR PRAC 5 BY JONATHAN WRIGHT
    AUTHOR: Jonathan Wright
    DATE: 16/09/2019 Unknown Origin Date
    PURPOSE: This is FILEIO for the testing class, it is not needed but kept incase
    you would like to run my testharness for graph.
    MODIFIED BY: Jonathan Wright
    DATE OF MODIFICATION: 15/10/2019
*/
public class FileIOGraph implements Serializable
{
    /**************************************************************************
    * Purpose: reads graph from file
    * IMPORT: String fileName
    * EXPORT: graph outGraph
    * DATE: 27/10/2019
    **************************************************************************/
    public static DSAGraph readGraph(String filename)
    {
        DSAGraph outGraph = new DSAGraph();
        FileInputStream fileStream;
        InputStreamReader inputStream;
        BufferedReader bufferedReader;
        try
        {
            fileStream = new FileInputStream(filename);
            inputStream = new InputStreamReader(fileStream);
            bufferedReader = new BufferedReader(inputStream);
            String line = bufferedReader.readLine();
            while (line != null)
            {
                String[] x = line.split(",");
                try
                {
                    outGraph.addVertex(x[0],x[0]);
                }
                catch (IllegalArgumentException e)
                {
                    // ignore because it means the verticies are already there.
                }
                try
                {
                    outGraph.addVertex(x[1],x[1]);
                }
                catch (IllegalArgumentException e)
                {
                    // ignore because it means the verticies are already there.
                }
                outGraph.addEdge(x[0],x[1], "A Edge", new Integer(1));
                line = bufferedReader.readLine();
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return outGraph;
    }
}
