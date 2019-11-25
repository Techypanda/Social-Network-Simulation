import java.util.*;
/*******************************************************************************
*
*   AUTHOR: Jonathan Wright
*   PURPOSE: This is a class the deals with the implementation of interactive mode.
*   DATE: 26/10/2019
*
******************************************************************************/
public class Interactive
{
    private static final String MENU =
    "(1) - Load Network\n(2) - Set Probabilities\n(3) - Node operations (find, insert, delete)" +
    "\n(4) - Edge operations (like/follow - add, remove)\n(5) - New post\n(6) - Display network" +
    "\n(7) - Display statistics\n(8) - Update (run a timestep)\n(9) - Save network\n" +
    "(10) - Exit Program";
    /**************************************************************************
    * Purpose: displays menu to user
    * IMPORT: none
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void menu()
    {
        Network network = new Network();
        Scanner sc;
        boolean end = false;
        int selection = -1;
        while (selection != 10) /* Cant Use The Generic Recieve Input method
        as it would end the menu loop. */
        {
            try
            {
                sc = new Scanner(System.in); /* Clear Buffer Every Loop */
                System.out.printf("%s%n",MENU);
                selection = sc.nextInt();
                end = validChoice(selection);
                if (end == true)
                {
                    callSelection(selection, network);
                }
            }
            catch (InputMismatchException e)
            {
                System.out.printf("That input is invalid, please try again%n");
            }
        }
    }
    /**************************************************************************
    * Purpose: calls selection based off userInput
    * IMPORT: int selection, Network network
    * EXPORT: Network network
    * DATE: 27/10/2019
    **************************************************************************/
    private static Network callSelection(int selection, Network network)
    { /* Have To Return network from this context */
        switch (selection)
        {
            case 1:
                selectFormat(network);
                //NetworkIO.readGraph(network);
                break;
            case 2:
                /* Set Probabilities */
                /* Pass object reference to set Probability of that object. */
                setProbabilities(network);
                break;
            case 3:
                /* Node Operations */
                nodeOperations(network.getGraph());
                break;
            case 4:
                /* Edge Operations */
                edgeOperations(network);
                break;
            case 5:
                /* New Post */
                post(network);
                break;
            case 6:
                /* Display Network */
                if (network != null && network.amount() > 0)
                {
                    System.out.printf("%s%n",network.displayNetwork('L'));
                }
                else
                {
                    System.out.printf("There is nothing to display!%n");
                }
                break;
            case 7:
                /* Display Statistics */
                displayStats(network);
                break;
            case 8:
                /* Update (Run Timestep) */
                if (network.getFollowChance() == 0)
                {
                    System.out.println("Please set the follow chance!");
                }
                else if (network.getLikeChance() == 0)
                {
                    System.out.println("Please set the like chance!");
                }
                else
                {
                    network.timeStep();
                }
                break;
            case 9:
                /* Save Network */
                String findMenu = "Enter Filename to save file.";
                String filename = recieveInput(findMenu);
                findMenu = "Select Format: (1) - CSV, (2) - Serialized";
                int format = recieveInput(0, 3, findMenu);
                NetworkIO.saveGraph(network, format, filename);
                break;
        } /* If it's 10 it will gracefully exit. */
        return network;
    }
    /**************************************************************************
    * Purpose: select format of loaded file
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    /* LOADING FILES */
    private static void selectFormat(Network network)
    {
        String searchMenu = "Are you loading a CSV (1) or Serialized Network (2)";
        int format = recieveInput(0, 3, searchMenu);
        NetworkIO.formatSelection(network, format);
    }
    /**************************************************************************
    * Purpose: posts to network based off user input
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    /* POSTING */
    private static void post(Network network)
    {
        String searchMenu = "Enter node to post from.";
        String nodeName = recieveInput(searchMenu);
        String messageToSend = "Enter Message To Post";
        String message = recieveInput(messageToSend);
        try
        {
            network.newMessage(nodeName, message);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    /**************************************************************************
    * Purpose: display stats of the network to user.
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    /* STATS */
    private static void displayStats(Network network)
    {
        int selection = -1;
        String statsMenu = "Which Stats would you like to see?\n"
        + "(1) - Posts in order of popularity\n(2) - People in order of popularity\n"
        + "(3) - A person's record - #posts, #followers, #following etc.\n(4) - Follow/Chance Statistics\n";
        selection = recieveInput(0, 5, statsMenu);
        switch (selection)
        {
            case 1:
                displayPosts(network);
                break;
            case 2:
                displayPeople(network);
                break;
            case 3:
                displayRecord(network);
                break;
            case 4:
                displayFollowChance(network.getFollowChance(), network.getLikeChance());
        }
    }
    /**************************************************************************
    * Purpose: display posts to user (ranked)
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void displayPosts(Network network)
    {
        System.out.println(network.rankMessages());
    }
    /**************************************************************************
    * Purpose: display people to user (ranked)
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void displayPeople(Network network)
    {
        System.out.println(network.userPopularity());
    }
    /**************************************************************************
    * Purpose: display record of selected person to user
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void displayRecord(Network network)
    {
        String findMenu = "Enter User's Name";
        String nodeName = recieveInput(findMenu);
        try
        {
            network.getGraph().getVertex(nodeName); /* Will Continue if It exists. */
            System.out.printf("----Name----%nName: %s%n----Posts----%n%s%n----Followers----%n%s%n----Following----%n%s%n",
            nodeName, network.getUserPosts(nodeName), network.getFollowers(nodeName),
            network.getFollowing(nodeName));
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        } /* Will Return To Menu */

    }
    /**************************************************************************
    * Purpose: display follow chance to user
    * IMPORT: double followChance, double likeChance
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void displayFollowChance(double followChance, double likeChance)
    {
        System.out.printf("Follow Chance: %f%%%nLike Chance: %f%%%n", followChance * 100, likeChance * 100);
    }
    /**************************************************************************
    * Purpose: allow selection of nodeOperations to user
    * IMPORT: DSAGraph graph
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    /* Node Operations */
    private static void nodeOperations(DSAGraph graph) /* find, insert, delete */
    {
        int selection = -1;
        String nodeMenu = "What would you like to do? \n"
        + "(1) - Find Node\n(2) - Insert Node\n(3) - Delete Node";
        selection = recieveInput(0, 4, nodeMenu);
        switch (selection)
        {
            case 1:
                findNode(graph);
                break;
            case 2:
                insertNode(graph);
                break;
            case 3:
                deleteNode(graph);
                break;
        }
    }
    /**************************************************************************
    * Purpose: returns if node exists in graph.
    * IMPORT: DSAGraph graph
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void findNode(DSAGraph graph)
    { /* What Should be returned?, Real Good Question, currently my idea is to
        return this layout: Node LABEL Exists */
        String findMenu = "Enter Node name to find.";
        String nodeName = recieveInput(findMenu);
        try
        {
            graph.getVertex(nodeName); /* Something Else To Do Here */
            System.out.printf("Node %s exists in the graph.%n", nodeName);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        } /* Will Return To Menu */
    }
    /**************************************************************************
    * Purpose: Inserts node into graph.
    * IMPORT: DSAGraph graph
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void insertNode(DSAGraph graph)
    {
        String findMenu = "Enter Node name to insert.";
        String nodeName = recieveInput(findMenu);
        try
        {
            graph.addVertex(nodeName, 0); /* Something Else To Do Here */
            System.out.printf("Node %s added.%n", nodeName);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        } /* Will Return To Menu */
    }
    /**************************************************************************
    * Purpose: Deletes node from graph.
    * IMPORT: DSAGraph graph
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void deleteNode(DSAGraph graph)
    {
        String findMenu = "Enter Node name to remove.";
        String nodeName = recieveInput(findMenu);
        try
        {
            graph.removeNode(nodeName); /* Something Else To Do Here */
            System.out.printf("Node %s removed.%n", nodeName);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        } /* Will Return To Menu */
    }
    /**************************************************************************
    * Purpose: allows selection of edge operations to user.
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    /* EDGE OPERATIONS LIKE/FOLLOW & ADD/REMOVE */
    private static void edgeOperations(Network network)
    {
        String edgeMenu = "(1) - Like\n(2) - Follow\n(3) - Add Edge\n(4) - Remove\n(5) - Unfollow";
        int selection = recieveInput(0,6,edgeMenu);
        switch (selection)
        {
            case 1:
                like(network);
                break;
            case 2:
                follow(network);
                break;
            case 3:
                System.out.println("Following is the same as having a edge in my implementation, as such redirecting to adding a follow.");
                follow(network);
                break;
            case 4:
                removeEdge(network);
                break;
            case 5:
                removeEdge(network);
                break;
        }
    }
    /**************************************************************************
    * Purpose: allows user to change likes of user.
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void like(Network network)
    {
        System.out.println("Here are the users.\n");
        displayPeople(network);
        String findMenu = "Enter the user who you would like to edit like value of.";
        String nodeName = recieveInput(findMenu);
        try
        {
            network.getGraph().getVertex(nodeName); /* check it exists */
            /* Node Exists. */
            String likeMenu = "Enter a new like value";
            int likes = recieveInput(-1, Integer.MAX_VALUE /* Really big int*/,likeMenu);
            network.getGraph().setLike(nodeName, likes);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        } /* Will Return To Menu */
    }
    /**************************************************************************
    * Purpose: allows user to set one to follow other
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void follow(Network network)
    {
        String findMenu = "Enter the user who you would like to have followed.";
        String user1 = recieveInput(findMenu);
        try
        {
            network.getGraph().getVertex(user1); /* Will Continue if It exists. */
            findMenu = "Enter the user who you would like to follow " + user1;
            String user2 = recieveInput(findMenu);
            network.getGraph().getVertex(user2); /* Will Continue if It exists. */
            network.follow(user2, user1);
            System.out.printf("%s now follows %s%n", user2, user1);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        } /* Will Return To Menu */
    }
    /**************************************************************************
    * Purpose: allows user to remove edge from network.
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static void removeEdge(Network network)
    {
        System.out.println("Removing a edge is the same as unfollowing.");
        String findMenu = "Enter the user who you would like to have unfollowed.";
        String user1 = recieveInput(findMenu);
        try
        {
            network.getGraph().getVertex(user1); /* Will Continue if It exists. */
            findMenu = "Enter the user who you would like to unfollow " + user1;
            String user2 = recieveInput(findMenu);
            network.getGraph().getVertex(user2); /* Will Continue if It exists. */
            network.unfollow(user2, user1); /* Throws Exception if edge doesnt exist */
            System.out.printf("%s no longer follows %s%n", user2, user1);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    /**************************************************************************
    * Purpose: sets probabilities of network.
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    /* Set Probabilities */
    private static void setProbabilities(Network network)
    {
        int selection = -1;
        String probMenu = "What would you like to set?\n"
        + "(1) - Follow Chance\n(2) - Like Chance\n(3) - Both\n(4) - None";
        selection = recieveInput(0, 5, probMenu);
        switch (selection)
        {
            case 1: /* Set Follow Chance */
                network.setFollowChance(recieveFollowChance(network));
                break;
            case 2: /* Set Like Chance */
                network.setLikeChance(recieveLikeChance(network));
                break;
            case 3: /* Set Both */
                network.setFollowChance(recieveFollowChance(network));
                network.setLikeChance(recieveLikeChance(network));
                break; /* if 4 Exit */
        }
    }
    /**************************************************************************
    * Purpose: sets the follow chance.
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static double recieveFollowChance(Network network)
    {
        String probMenu = "Enter a follow chance between 0.0 - 100.0";
        double followChance = recieveDoubleInput(0, 100, probMenu);
        System.out.printf("Successfully set follow probability.%n");
        return (followChance / 100);
    }
    /**************************************************************************
    * Purpose: sets the like chance
    * IMPORT: Network network
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private static double recieveLikeChance(Network network)
    {
        String likeMenu = "Enter a like chance between 0.0 - 100.0";
        double likeChance = recieveDoubleInput(0, 100, likeMenu);
        System.out.printf("Successfully set like probability.%n");
        return (likeChance / 100);
    }
    /**************************************************************************
    * Purpose: Verifys if that was a valid choice for menu.
    * IMPORT: int inSelect
    * EXPORT: boolean outBool
    * DATE: 27/10/2019
    **************************************************************************/
    /* VERIFICATION */
    private static boolean validChoice(int inSelect)
    {
        boolean outBool = true;
        if (inSelect > 10 || inSelect < 1)
        {
            outBool = false;
            throw new InputMismatchException("Invalid choice.");
        }
        return outBool;
    }
    /**************************************************************************
    * Purpose: Generic recieveInput (returns string.)
    * IMPORT: String prompt
    * EXPORT: String input
    * DATE: 27/10/2019
    **************************************************************************/
    private static String recieveInput(String prompt)
    {
        String input = "";
        while (input == "")
        {
            try
            {
                Scanner sc = new Scanner(System.in); /* Clear Buffer Every Loop */
                System.out.printf("%s%n",prompt);
                input = sc.nextLine();
            }
            catch (InputMismatchException e)
            {
                System.out.printf("That input is invalid, please try again%n");
            }
        }
        return Utilities.camelCase(input);
    }
    /**************************************************************************
    * Purpose: Generic recieveInput (returns int.) (EXCLUSIVE)
    * IMPORT: int lower, int upper, String menu
    * EXPORT: int selection
    * DATE: 27/10/2019
    **************************************************************************/
    /* EXCLUSIVE */
    private static int recieveInput(int lower, int upper, String menu)
    {
        int selection = -1;
        boolean valid = false;
        while (valid == false)
        {
            try
            {
                Scanner sc = new Scanner(System.in); /* Clear Buffer Every Loop */
                System.out.printf("%s%n",menu);
                selection = sc.nextInt();
                valid = (selection > lower && selection < upper); // within range
                if (valid == false)
                {
                    throw new InputMismatchException(); // No need for message.
                }
            }
            catch (InputMismatchException e)
            {
                System.out.printf("That input is invalid, please try again%n");
            }
        }
        return selection;
    }
    /**************************************************************************
    * Purpose: Generic recieveInput (returns double.) (INCLUSIVE)
    * IMPORT: double lower, double upper, String menu
    * EXPORT: double selection
    * DATE: 27/10/2019
    **************************************************************************/
    /* INCLUSIVE */
    private static double recieveDoubleInput(double lower, double upper, String menu)
    {
        double selection = -1;
        boolean valid = false;
        while (valid == false)
        {
            try
            {
                Scanner sc = new Scanner(System.in); /* Clear Buffer Every Loop */
                System.out.printf("%s%n",menu);
                selection = sc.nextDouble();
                valid = (selection >= lower && selection <= upper); // within range
                if (valid == false)
                {
                    throw new InputMismatchException(); // No need for message.
                }
            }
            catch (InputMismatchException e)
            {
                System.out.printf("That input is invalid, please try again%n");
            }
        }
        return selection;
    }
}
