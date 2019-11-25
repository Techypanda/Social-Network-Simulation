import java.lang.Math;
import java.util.Random;
import java.io.Serializable;
/*******************************************************************************
*
*   AUTHOR: Jonathan Wright
*   PURPOSE: This is a message class that retains information relating to messages
*   sent in a network.
*   DATE: 26/10/2019
*
******************************************************************************/
public class Message implements Serializable
{
    private String contents;
    private Object origin; /* original start */
    private int likes;
    private double clickBait;
    private DSALinkedList visited;
    private DSALinkedList rolled;
    private DSALinkedList rolledFollow;
    private static final String[] likeResponses = {" was very fond of that post.", " appreciated that post.",
    " thought that was a very insightful post.", " liked that post.", " loved that post."};
    private static final String[] dislikeResponses = {" thought that post was offensive.", " hated that post.",
    " did not appreciate that post", " thought that was a bad post, very unfunny."};

    /**************************************************************************
    * Purpose: Alternate Constructor
    * IMPORT: String inContents, Object inOrigin
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public Message(String inContents, Object inOrigin)
    {
        clickBait = 1;
        contents = inContents;
        origin = inOrigin;
        likes = 0;
        visited = new DSALinkedList();
        rolled = new DSALinkedList();
        rolledFollow = new DSALinkedList();
    }
    /**************************************************************************
    * Purpose: Alternate Constructor
    * IMPORT: String inContents, Object inOrigin, double inBait
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public Message(String inContents, Object inOrigin, double inBait)
    { /* CLICK BAIT VERSION */
        clickBait = inBait;
        contents = inContents;
        origin = inOrigin;
        likes = 0;
        visited = new DSALinkedList();
        rolled = new DSALinkedList();
        rolledFollow = new DSALinkedList();
    }
    /**************************************************************************
    * Purpose: sets clickbait to inval
    * IMPORT: int inBait
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void setClickBait(int inBait)
    {
        clickBait = inBait;
    }
    /**************************************************************************
    * Purpose: Increments like
    * IMPORT: none
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void liked()
    {
        likes += 1;
    }
    /**************************************************************************
    * Purpose: return contents of message.
    * IMPORT: none
    * EXPORT: contents String
    * DATE: 27/10/2019
    **************************************************************************/
    public String getContents()
    {
        return contents;
    }
    /**************************************************************************
    * Purpose: returns likes of message
    * IMPORT: none
    * EXPORT: int likes
    * DATE: 27/10/2019
    **************************************************************************/
    public int getLikes()
    {
        return likes;
    }
    /**************************************************************************
    * Purpose: returns origin of message.
    * IMPORT: none
    * EXPORT: Object origin
    * DATE: 27/10/2019
    **************************************************************************/
    public Object getOrigin()
    {
        return origin;
    }
    /**************************************************************************
    * Purpose: Rolls dice for likeChance
    * IMPORT: Object rolling, double likeChance, String uniqueFilename
    * EXPORT: boolean pf (Pass or Fail)
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean rollLike(Object rolling, double likeChance, String uniqueFilename)
    {
        boolean pf = false; /* Pass Fail */
        double dice;
        dice = Math.random();
        rolled.insertLast(rolling);
        Random random = new Random();
        if (dice <= (likeChance * clickBait))
        {
            NetworkIO.timeStepAppend(uniqueFilename, rolling.toString() + likeResponses[(int)Math.random() * 4] + " (Roll: " + dice + ")" + ", Like Chance: " + (likeChance * clickBait) + ")" + " (Post: " + this.contents + ", currLikes: " + this.likes + ")");
            System.out.println(rolling.toString() + likeResponses[random.nextInt(5)] + " (Roll: " + dice + ")");
            pf = true;
        }
        else
        {
            NetworkIO.timeStepAppend(uniqueFilename, rolling.toString() + dislikeResponses[(int)Math.random() * 3] + " (Roll: " + dice + ", Like Chance: " + (likeChance * clickBait) +  ")" + " (Post: " + this.contents + ", currLikes: " + this.likes + ")");
            System.out.println(rolling.toString() + dislikeResponses[random.nextInt(4)] + " (Roll: " + dice + ")" + " (Post: " + this.contents + ")");
            pf = false;
        }
        return pf;
    }
    /**************************************************************************
    * Purpose: Rolls dice for followChance
    * IMPORT: Object rolling, double followChance, String uniqueFilename
    * EXPORT: boolean pf (Pass or Fail)
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean rollFollow(Object rolling, double followChance, String uniqueFilename)
    {
        boolean pf = false; /* Pass Fail */
        double dice;
        dice = Math.random();
        rolled.insertLast(rolling);
        if (dice <= followChance)
        {
            pf = true;
        }
        else
        {
            System.out.println(rolling.toString() + " liked the post but didn't want to follow "
            + this.getOrigin().toString() + " (Roll: " + dice + ")");
            pf = false;
        }
        return pf;
    }
    /**************************************************************************
    * Purpose: returns if object hasnt rolled.
    * IMPORT: Object check
    * EXPORT: Boolean rolled
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean hasntRolled(Object check)
    {
        return !rolled.contains(check);
    }
    /**************************************************************************
    * Purpose: returns if hasntRolled followChance
    * IMPORT: Object check
    * EXPORT: Boolean rolledFollow
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean hasntRolledFollow(Object check)
    {
        return !rolledFollow.contains(check);
    }
    /**************************************************************************
    * Purpose: Adds object to visited once its been tehre.
    * IMPORT: Object o
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void visit(Object o)
    {
        visited.insertLast(o); /* The places its been */
    }
    /**************************************************************************
    * Purpose: returns whether it has been to object.
    * IMPORT: Object o
    * EXPORT: Boolean visited.
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean contains(Object o)
    {
        return visited.contains(o);
    }
}
