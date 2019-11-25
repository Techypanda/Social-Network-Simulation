import java.io.Serializable;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.lang.Math;
/*******************************************************************************
*
*   AUTHOR: Jonathan Wright
*   PURPOSE: This is a class that is the social media network, it contains a graph
*   and extra details retaining to the network such as followChance and likeChance.
*   DATE: 26/10/2019
*
******************************************************************************/
public class Network implements Serializable
{
    private DSAGraph graph;
    private double followChance;
    private double likeChance;
    /**************************************************************************
    * Purpose: Alternate Constructor
    * IMPORT: DSAGraph inGraph
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public Network(DSAGraph inGraph) /* Network Will Start Without Chances. */
    {
        graph = inGraph;
        followChance = 0; /* Needs To Be Set Via Options */
        likeChance = 0; /* Needs To Be Set Via Options */
    }
    /**************************************************************************
    * Purpose: Default Constructor
    * IMPORT: none
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public Network() /* Network Will Start Without Chances. */
    {
        graph = new DSAGraph();
        followChance = 0; /* Needs To Be Set Via Options */
        likeChance = 0; /* Needs To Be Set Via Options */
    }
    /**************************************************************************
    * Purpose: Resets the graph of network to new graph.
    * IMPORT: none
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void resetGraph()
    { /* Reset The graph. */
        graph = new DSAGraph();
    }
    /**************************************************************************
    * Purpose: Changes graph to newGraph
    * IMPORT: DSAGraph newGraph
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void redirectGraph(DSAGraph newGraph)
    { /* Dereference old graph, use new graph. */
        graph = newGraph;
    }
    /**************************************************************************
    * Purpose: Returns string of UserPosts.
    * IMPORT: String name
    * EXPORT: string UserPosts
    * DATE: 27/10/2019
    **************************************************************************/
    public String getUserPosts(String name)
    {
        return graph.getUserPosts(graph.getVertex(name));
    }
    /**************************************************************************
    * Purpose: Returns string of following users.
    * IMPORT: String name
    * EXPORT: string followingUsers
    * DATE: 27/10/2019
    **************************************************************************/
    public String getFollowing(String name)
    {
        return graph.getFollowing(graph.getVertex(name));
    }
    /**************************************************************************
    * Purpose: Returns string of follwers of user.
    * IMPORT: Sttring name
    * EXPORT: String follwers
    * DATE: 27/10/2019
    **************************************************************************/
    public String getFollowers(String name)
    {
        return graph.getFollowers(graph.getVertex(name));
    }
    /**************************************************************************
    * Purpose: Returns graph.
    * IMPORT: none
    * EXPORT: returns graph
    * DATE: 27/10/2019
    **************************************************************************/
    public DSAGraph getGraph()
    {
        return graph;
    }
    /**************************************************************************
    * Purpose: Displays Graph based off input.
    * IMPORT: char request
    * EXPORT: String outNetwork
    * DATE: 27/10/2019
    **************************************************************************/
    public String displayNetwork(char request)
    {
        String outNetwork = "";
        if (request == 'L')
        {
            outNetwork = graph.displayAsList();
        }
        else if (request == 'M')
        {
            outNetwork = graph.displayAsMatrix();
        }
        return outNetwork;
    }
    /**************************************************************************
    * Purpose: follows from to
    * IMPORT: String from String to
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void follow(String from, String to)
    {
        try
        {
            graph.getVertex(from);
            graph.getVertex(to);
            graph.follow(from, to);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    /**************************************************************************
    * Purpose: Unfollows from to
    * IMPORT: String from, String to
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void unfollow(String from, String to)
    {
        try
        {
            graph.unfollow(from, to);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    /**************************************************************************
    * Purpose: Return String of all vertexs.
    * IMPORT: none
    * EXPORT: String vertexList
    * DATE: 27/10/2019
    **************************************************************************/
    public String getVerticiesList()
    { /* Returns A String 'List' of all the verticies in graph, used in NetworkIO */
        return graph.getVerticiesList();
    }
    /**************************************************************************
    * Purpose: Return String of all edges.
    * IMPORT: none
    * EXPORT: String edgeList
    * DATE: 27/10/2019
    **************************************************************************/
    public String getEdgeList()
    { /* Return A String 'List' of all the edges in graph in the format FROM:TO */
        return graph.getEdgesList();
    }
    /**************************************************************************
    * Purpose: constructs new message in network.
    * IMPORT: String origin, String contents
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void newMessage(String origin, String contents)
    {
        try
        {
            graph.newMessage(graph.getVertex(origin), contents);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    /**************************************************************************
    * Purpose: Constructs new message in network with clickbait
    * IMPORT: String origin, String contents, double clickBait
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void newMessageC(String origin, String contents, double clickBait)
    {
        try
        {
            graph.newMessageC(graph.getVertex(origin), contents, clickBait);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    /**************************************************************************
    * Purpose: Add user to network.
    * IMPORT: String nodeName
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void addUser(String nodeName)
    {
        try
        {
            graph.addVertex(nodeName, new Integer(1)); /* Value isnt important. */
            /* USER HAS BEEN ADDED SUCCESSFULLY */
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    /**************************************************************************
    * Purpose: remove user from network.
    * IMPORT: String nodeName
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void removeUser(String nodeName)
    {
        try
        {
            graph.removeNode(nodeName);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    /**************************************************************************
    * Purpose: returns string of users ranked by popularity.
    * IMPORT: none
    * EXPORT: String orderedUsers
    * DATE: 27/10/2019
    **************************************************************************/
    public String userPopularity()
    {
        return graph.orderedUsers();
    }
    /**************************************************************************
    * Purpose: returns a string of ranked messages.
    * IMPORT: none
    * EXPORT: String rankedMessages
    * DATE: 27/10/2019
    **************************************************************************/
    public String rankMessages()
    {
        return graph.rankMessages();
    }
    /**************************************************************************
    * Purpose: returns followChance
    * IMPORT: none
    * EXPORT: returns followchance.
    * DATE: 27/10/2019
    **************************************************************************/
    public double getFollowChance()
    {
        return graph.getFollowChance();
    }
    /**************************************************************************
    * Purpose: Performs a timestep in the network.
    * IMPORT: none
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void timeStep()
    {
        Calendar timeCurr = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd_hh-mm-ss");
        String uniqueFilename = df.format(timeCurr.getTime()) + "_timestep" + "("
        + (Math.random() * Math.random() * Math.random() * Math.random()) + ")" + ".txt";
        System.out.println("Outputting time step to file: " + uniqueFilename + "\nThis is stored in the timeSteps folder.");
        System.out.println("===================TIMESTEP===================");
        graph.timeStep(uniqueFilename);
    }
    /**************************************************************************
    * Purpose: Returns likeChance
    * IMPORT: none
    * EXPORT: likeChance
    * DATE: 27/10/2019
    **************************************************************************/
    public double getLikeChance()
    {
        return graph.getLikeChance();
    }
    /**************************************************************************
    * Purpose: Returns a int[] of likes.
    * IMPORT: none
    * EXPORT: int[] likes
    * DATE: 27/10/2019
    **************************************************************************/
    public int[] getLikes()
    {
        return graph.getLikes();
    }
    /**************************************************************************
    * Purpose: sets follow chance to inval
    * IMPORT: double inFollowChance
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void setFollowChance(double inFollowChance)
    { /* Expects it as a decimal. */
        if (Math.round(inFollowChance) <= 1 && Math.round(inFollowChance) >= 0)
        {
            graph.setFollowChance(inFollowChance);
        }
        else
        {
            throw new IllegalArgumentException("Can only have a chance between 0.00 and 1.00");
        }
    }
    /**************************************************************************
    * Purpose: sets like chance to inval
    * IMPORT: double inLikeChance
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void setLikeChance(double inLikeChance)
    { /* Expects it as a decimal. */
        if (Math.round(inLikeChance) <= 1 && Math.round(inLikeChance) >= 0)
        {
            graph.setLikeChance(inLikeChance);
        }
        else
        {
            throw new IllegalArgumentException("Can only have a chance between 0.00 and 1.00");
        }
    }
    /**************************************************************************
    * Purpose: Returns count of vertexs.
    * IMPORT: none
    * EXPORT: vertexCount int
    * DATE: 27/10/2019
    **************************************************************************/
    public int amount()
    {
        return graph.getVertexCount();
    }
    /**************************************************************************
    * Purpose: Returns string of all messages.
    * IMPORT: none
    * EXPORT: String messages
    * DATE: 27/10/2019
    **************************************************************************/
    public String displayMessages()
    {
        return graph.getMessages();
    }
}
