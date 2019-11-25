/*******************************************************************************
*
*   AUTHOR: Jonathan Wright
*   PURPOSE: This is where the program starts and this specific class only holds
*   the main method.
*   DATE: 26/10/2019
*
******************************************************************************/
public class SocialSim
{
    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            System.out.printf("Usage information:%n\"-i\" : interactive testing environment"
            + "%n\"-s\" : simulation mode (usage: SocialSim -s netfile eventfile prob_like prob_foll)%n");
        }
        else
        {
            if (args[0].toLowerCase().equals("-i"))
            {
                Interactive.menu();
            }
            else if (args[0].toLowerCase().equals("-s"))
            {
                try
                {
                    SimulationMode.begin(args);
                }
                catch (IllegalStateException e)
                {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                System.out.printf("Incorrect Usage!%nUsage information:%n\"-i\" : interactive testing environment"
                + "%n\"-s\" : simulation mode (usage: SocialSim –s netfile eventfile prob_like prob_foll)%n");
            }
        }
    }
}
