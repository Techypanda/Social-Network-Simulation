import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;
/*******************************************************************************
*
*   AUTHOR: Jonathan Wright
*   PURPOSE: This is a class the deals with the implementation of simulation mode.
*   DATE: 26/10/2019
*
******************************************************************************/
public class SimulationMode
{
    /**************************************************************************
    * Purpose: Begins simulation mode.
    * IMPORT: String[] args
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public static void begin(String[] args)
    {
        if (args.length != 5)
        {
            throw new IllegalStateException(String.format("Incorrect Usage!%nUsage information:%n\"-i\" : interactive testing environment"
            + "%n\"-s\" : simulation mode (usage: SocialSim s netfile eventfile prob_like prob_foll)%n"));
        }
        Network network = new Network();
        try
        {
            NetworkIO.readGraph(network, args[1]);
            network.setLikeChance(Double.parseDouble(args[3]));
            network.setFollowChance(Double.parseDouble(args[4]));
            NetworkIO.readEventFile(network, args[2]);
            System.out.println("Finished Reading All Files In.");

            boolean end = false;
            int timeStepCount = 0;
            while (end == false)
            {
                System.out.println("Timestep " + timeStepCount + "\nEnter an integer to timeStep or anything else to exit");
                try
                {
                    Scanner sc = new Scanner(System.in);
                    int timeStep = sc.nextInt();
                    network.timeStep();
                    timeStepCount++;
                }
                catch (InputMismatchException e)
                {
                    end = true;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("ERROR: " + e.getMessage());
        }
        catch (NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        catch (NumberFormatException e)
        {
            System.out.println("Expected a double value between 0.0 and 1.0 for likeChance and followChance: ErrorMsg - " + e.getMessage());
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
