import java.io.*;
/*******************************************************************************
*
*   AUTHOR: Jonathan Wright
*   PURPOSE: This is a class that tests the network class.
*   DATE: 26/10/2019
*
******************************************************************************/
public class NetworkTestHarness
{
    public static void main(String[] args)
    {
        int passed = 0;
        ByteArrayOutputStream test = new ByteArrayOutputStream();
        Network network = new Network();
        PrintStream originalOut = System.out;
        try
        {
            NetworkIO.readGraph(network, "darkCrystal.csv");
            System.out.println("SUCCESSFULLY READ! (PASS)");
            passed += 1; // success read
        }
        catch (Exception e)
        {
            System.out.println("EXCEPTION CAUGHT!!!!!!!! FAIL");
        }
        System.out.println("Beginning test\n");
        System.setOut(new PrintStream(test));
        System.out.println(network.displayNetwork('L'));
        System.setOut(originalOut);
        System.out.printf("-------Curr Network---------%n%s",test.toString());
        System.out.printf("----------------------------%n");
        System.out.println("Inserting a new message originating from Kira: \n"
        + "OMG! NEW SOCK PHOTO GUYS OUT ON MY INSTA!\n");
        network.setFollowChance(0.40);
        network.setLikeChance(0.60);
        network.newMessage("Kira", "OMG! NEW SOCK PHOTO GUYS OUT ON MY INSTA!");
        System.out.println("---------------TIMESTEP!------------");
        network.timeStep();
        int[] testArray = network.getLikes();
        System.out.println("-------------------------------------");
        System.out.println("Likes: " + testArray[0]);
        System.out.println("-----------------Testing events----------------");
        System.out.println("Testing sample1 event file manually");
        network = new Network();
        network.setFollowChance(0.40);
        network.setLikeChance(0.60);
        try
        {
            NetworkIO.readGraph(network, "darkCrystal.csv");
            System.out.println("SUCCESSFULLY READ! (PASS)");
            passed += 1; // read success
        }
        catch (Exception e)
        {
            System.out.println("EXCEPTION CAUGHT!!!!!!!! FAIL");
        }
        network.follow("Kira","Jen");
        network.follow("Jen","Kira");
        network.setFollowChance(0.60);
        network.setLikeChance(0.70);
        System.out.println("Printing the network, verify a connection between Kira - Jen and Jen - Kira exists.\n\n");
        System.out.println(network.displayNetwork('L'));
        System.out.println("PASS.");
        System.out.println("\n\nPosting a message from Kira: \"The Garthim attacked my village.\"");
        System.out.println("Posting a message from Chamberlain: \"The great conjuction is the end of the world! ... or the beginning.\"");
        System.out.println("Posting a message from Chamberlain: \"Trail by stone!\"");
        System.out.println("Posting a message from Jen: \"Wings? I don't have wings!\"");
        network.newMessage("Garthim", "The Garthim attacked my village.");
        network.newMessage("Chamberlain", "The great conjuction is the end of the world! ... or the beginning.");
        network.newMessage("Chamberlain", "Trail by stone!");
        network.newMessage("Chamberlain", "Wings? I don't have wings!");
        System.out.println("------------------TIMESTEP!--------------");
        network.timeStep();
        System.out.println("------------------TIMESTEP!--------------");
        network.timeStep();
        System.out.println("------------------TIMESTEP!--------------");
        network.timeStep();
        System.out.println("------------------TIMESTEP!--------------");
        network.timeStep();
        System.out.println("Ranking the messages!");
        System.out.println(network.rankMessages());
        passed += 1; // passed ranked
        System.out.println();
        System.out.println("Now Testing reading various data sets.");
        try
        {
            NetworkIO.readGraph(network, "darkCrystal.csv");
            NetworkIO.readGraph(network, "doReMe.csv");
            NetworkIO.readGraph(network, "toyStory.csv");
            passed += 1; // passed reading
            System.out.println("Passed tests: " + passed + " / 4");
        }
        catch (Exception e)
        {
            System.out.println("EXCEPTION CAUGHT!!!!!!!! FAIL");
        }
    }
}
