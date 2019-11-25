import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.*;
/*******************************************************************************
*
*   AUTHOR: Jonathan Wright
*   PURPOSE: This is a class the deals with the reading and writing of networks.
*   DATE: 26/10/2019
*
******************************************************************************/
public class NetworkIO
{

    private static final char[] FUNCTIONS = {'F', 'P', 'A', 'U', 'R'};
    /* F = Follow, P = Post, A = Add, U = Unfollow, R = Remove */
    /**************************************************************************
    * Purpose: Selects format to read in network
    * IMPORT: Network network, int format
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void formatSelection(Network network, int format)
    {
        switch (format)
        {
            case 1:
                readGraph(network);
                break;
            case 2:
                try
                {
                    readSerializedGraph(network);
                }
                catch (IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
                break;
        }
    }
    /**************************************************************************
    * Purpose: reads a serialzed graph to network.
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void readSerializedGraph(Network network)
    {
        FileInputStream in;
        ObjectInputStream objIn;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter filename for network to load in.");
        String filename = sc.nextLine();
        try
        {
            in = new FileInputStream(filename);
            objIn = new ObjectInputStream(in);
            DSAGraph inGraph = (DSAGraph)objIn.readObject();
            network.redirectGraph(inGraph); /* Dereference Old Graph, Use New Graph. */
            System.out.println("Network Successfully Loaded.");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Class not found! " + e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e) /* Lecture Notes Show To Catch base exception. */
        {
            throw new IllegalArgumentException("Unable to load object!");
        }
    }
    /**************************************************************************
    * Purpose: reads a graph from csv to network.
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void readGraph(Network network)
    {
        network.resetGraph();
        String filename;
        Scanner sc = new Scanner(System.in);
        DSAGraph inGraph = network.getGraph();
        System.out.println("Enter filename for network to load in.");
        filename = sc.nextLine();
        if (filename.length() >= 4 && !(filename.substring(filename.length() - 4, filename.length()).equals(".csv")) )
        {
            filename += ".csv";
        }
        System.out.printf("Attempting to read %s...%n", filename);
        try
        {
            FileInputStream file = new FileInputStream(filename);
            InputStreamReader inputStream = new InputStreamReader(file);
            BufferedReader buffReader = new BufferedReader(inputStream);
            String currLine = buffReader.readLine();
            while (currLine != null)
            {
                String[] edge = currLine.split(":");
                if (edge.length == 1)
                { /* Wants To Insert Vertex */
                    try
                    {
                        /* Characters To CamelCase as its more appealing to human eye. */
                        currLine = Utilities.camelCase(currLine);
                        if (Character.isWhitespace(currLine.charAt(0)))
                        {
                            currLine = currLine.substring(1, currLine.length());
                            currLine = Utilities.camelCase(currLine);
                        }
                        inGraph.addVertex(currLine, currLine); /*Add User To network*/
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                else
                { /* Wants To Insert Edge */
                    try
                    {
                        edge[0] = Utilities.camelCase(edge[0]);
                        edge[1] = Utilities.camelCase(edge[1]);
                        if (Character.isWhitespace(edge[0].charAt(0)))
                        {
                            edge[0] = edge[0].substring(1, edge[0].length());
                            edge[0] = Utilities.camelCase(edge[0]);
                        }
                        if (Character.isWhitespace(edge[1].charAt(0)))
                        {
                            edge[1] = edge[1].substring(1, edge[1].length());
                            edge[1] = Utilities.camelCase(edge[1]);
                        }
                        inGraph.addEdge(edge[1], edge[0], edge[1] + ":" + edge[0], 0);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.out.println(e.getMessage() + " on line: " + currLine);
                    }
                }
                currLine = buffReader.readLine();
            }
            buffReader.close();
            System.out.println("Successfully Loaded Network.");
        }
        catch (IOException e)
        {
            System.out.println("ERROR: " + e.getMessage());
            inGraph = null; /* Return Null If Error Occurs. */
        }
    }
    /**************************************************************************
    * Purpose: reads graph from csv with filename being given.
    * IMPORT: Network network, String filename
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void readGraph(Network network, String filename) throws IOException
    {
        network.resetGraph();
        DSAGraph inGraph = network.getGraph();
        if ( !(filename.substring(filename.length() - 4, filename.length()).equals(".csv")) )
        {
            filename += ".csv";
        }
        System.out.printf("Attempting to read %s...%n", filename);
        try
        {
            FileInputStream file = new FileInputStream(filename);
            InputStreamReader inputStream = new InputStreamReader(file);
            BufferedReader buffReader = new BufferedReader(inputStream);
            String currLine = buffReader.readLine();
            while (currLine != null)
            {
                String[] edge = currLine.split(":");
                if (edge.length == 1)
                { /* Wants To Insert Vertex */
                    try
                    {
                        /* Characters To CamelCase as its more appealing to human eye. */
                        currLine = Utilities.camelCase(currLine);
                        if (Character.isWhitespace(currLine.charAt(0)))
                        {
                            currLine = currLine.substring(1, currLine.length());
                            currLine = Utilities.camelCase(currLine);
                        }
                        inGraph.addVertex(currLine, currLine); /*Add User To network*/
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                else
                { /* Wants To Insert Edge */
                    try
                    {
                        edge[0] = Utilities.camelCase(edge[0]);
                        edge[1] = Utilities.camelCase(edge[1]);
                        if (Character.isWhitespace(edge[0].charAt(0)))
                        {
                            edge[0] = edge[0].substring(1, edge[0].length());
                            edge[0] = Utilities.camelCase(edge[0]);
                        }
                        if (Character.isWhitespace(edge[1].charAt(0)))
                        {
                            edge[1] = edge[1].substring(1, edge[1].length());
                            edge[1] = Utilities.camelCase(edge[1]);
                        }
                        inGraph.addEdge(edge[1], edge[0], edge[1] + ":" + edge[0], 0);
                    }
                    catch (IllegalArgumentException e)
                    {
                        //System.out.println(e.getMessage() + " on line: " + currLine);
                        throw new IllegalArgumentException(e.getMessage() + " on line: " + currLine);
                    }
                }
                currLine = buffReader.readLine();
            }
            buffReader.close();
            System.out.println("Successfully Loaded Network.");
        }
        catch (IOException e)
        {
            //System.out.println("ERROR: " + e.getMessage());
            throw e;
        }
    }
    /**************************************************************************
    * Purpose: picks whether to save as CSV or Serial based off format.
    * IMPORT: Network network, int format, String filename
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void saveGraph(Network network, int format, String filename)
    { // WRAPPER METHOD
        switch (format)
        {
            case 1:
                saveCSV(network, filename);
                break;
            case 2:
                saveSerial(network, filename);
                break;
        }
    }
    /**************************************************************************
    * Purpose: saves graph to csv
    * IMPORT: Network network, String filename
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void saveCSV(Network network, String filename)
    {
        FileOutputStream out;
        PrintWriter pw;
        try
        {
            out = new FileOutputStream(filename + ".csv");
            pw = new PrintWriter(out);
            pw.println(network.getVerticiesList());
            pw.println(network.getEdgeList());
            pw.close();
            out.close();
            System.out.println("Successfully saved to " + filename + ".csv");
        }
        catch(IOException e)
        {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    /**************************************************************************
    * Purpose: serializes graph
    * IMPORT: Network network, String filename
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void saveSerial(Network network, String filename)
    {
        FileOutputStream out;
        ObjectOutputStream oOut;
        try
        {
            out = new FileOutputStream(filename);
            oOut = new ObjectOutputStream(out);
            oOut.writeObject(network.getGraph());
            oOut.close();
        }
        catch(Exception e) /* Lecture Slides show to catch base exception. */
        {
            System.out.println("ERROR: " + e.getMessage() + " is not serializable.");
        }
    }
    /**************************************************************************
    * Purpose: Appends/Creates file to write timeStep into.
    * IMPORT: String filename, String inData
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void timeStepAppend(String filename, String inData)
    {
        FileOutputStream out;
        PrintWriter pw;
        try
        {
            File test = new File(System.getProperty("user.dir") + "/timeSteps/" + filename);
            if (!test.exists())
            {
                test.createNewFile();
            }
            out = new FileOutputStream(System.getProperty("user.dir") + "/timeSteps/" + filename, true); /* Append = true */
            pw = new PrintWriter(out);
            pw.println(inData);
            pw.close();
            out.close();
        }
        catch(IOException e)
        {
            System.out.println("ERROR APPENDING TO TIMESTEP: " + e.getMessage());
        }
    }
    /**************************************************************************
    * Purpose: Reads eventFile
    * IMPORT: Network network, String filename
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void readEventFile(Network network, String filename) throws IOException, IllegalArgumentException
    {
        FileInputStream in;
        InputStreamReader inR;
        BufferedReader buffR;
        try
        {
            in = new FileInputStream(filename);
            inR = new InputStreamReader(in);
            buffR = new BufferedReader(inR);
            String currLine = buffR.readLine();
            while (currLine != null)
            {
                String[] lineSplit = currLine.split(":");
                if (lineSplit[0].length() != 1)
                {
                    throw new IllegalArgumentException("That is not a valid operation in the event file. Line: " + currLine);
                }
                if (!isFunction(lineSplit[0].charAt(0)))
                {
                    throw new IllegalArgumentException("That is not a valid operation in the event file. Line: " + currLine);
                }
                performFunction(lineSplit, network);
                currLine = buffR.readLine();
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        catch (IllegalArgumentException e)
        {
            throw e;
        }
    }
    /**************************************************************************
    * Purpose: determines if event file has stated a valid function.
    * IMPORT: char inFunction
    * EXPORT: boolean valid
    * DATE: 27/10/2019
    **************************************************************************/
    private static boolean isFunction(char inFunction)
    {
        boolean valid = false;
        for (char currF: FUNCTIONS)
        {
            if (inFunction == currF)
            {
                valid = true;
            }
        }
        return valid;
    }
    /**************************************************************************
    * Purpose: performs chosen function with input data.
    * IMPORT: String[] lineSplit, Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void performFunction(String[] lineSplit, Network network) throws IllegalArgumentException
    {
        switch (lineSplit[0].charAt(0))
        {
            case 'F': /* FOLLOW */
                if (lineSplit.length != 3)
                {
                    throw new IllegalArgumentException("Expected F:User1:User2, recieved " + lineSplit[0] + ":" + lineSplit[1] + ":" + lineSplit[2]);
                }
                network.follow(Utilities.camelCase(lineSplit[2]), Utilities.camelCase(lineSplit[1])); /* Assumption is made its the same as the network input file! e.g. TO:FROM */
                break;
            case 'P': /* POST */
                if (lineSplit.length != 3 && lineSplit.length != 4)
                {
                    throw new IllegalArgumentException("Expected P:User1:Post Contents:ClickBaitFactor, recieved " + lineSplit[0] + ":" + lineSplit[1] + ":" + lineSplit[2]);
                }
                if (lineSplit.length == 4 && lineSplit[3] != null)
                {
                    network.newMessageC(Utilities.camelCase(lineSplit[1]), Utilities.camelCase(lineSplit[2]), Double.parseDouble(lineSplit[3]));
                }
                else
                {
                    network.newMessage(Utilities.camelCase(lineSplit[1]), Utilities.camelCase(lineSplit[2]));
                }
                break;
            case 'A': /* ADD */
                if (lineSplit.length != 2)
                {
                    throw new IllegalArgumentException("Expected A:User, recieved " + lineSplit[0] + ":" + lineSplit[1] + " Unknown additional Parameter.");
                }
                network.addUser(Utilities.camelCase(lineSplit[1]));
                break;
            case 'U': /* UNFOLLOW */
                if (lineSplit.length != 3)
                {
                    throw new IllegalArgumentException("Expected U:User1:User2, recieved " + lineSplit[0] + ":" + lineSplit[1] + ":" + lineSplit[2]);
                }
                network.unfollow(Utilities.camelCase(lineSplit[2]),Utilities.camelCase(lineSplit[1]));
                break;
            case 'R': /* REMOVE */
                if (lineSplit.length != 2)
                {
                    throw new IllegalArgumentException("Expected R:User, recieved " + lineSplit[0] + ":" + lineSplit[1] + " Unknown additional Parameter.");
                }
                network.removeUser(Utilities.camelCase(lineSplit[1]));
                break;
        }
    }
}
