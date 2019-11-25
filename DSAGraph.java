import java.lang.StringBuilder; // reverse
import java.lang.Math;
import java.io.Serializable;
/*
    ORIGINALLY SUBMITTED FOR PRAC 5 BY JONATHAN WRIGHT
    AUTHOR: Jonathan Wright
    DATE: 19/06/2019 Unknown Origin Date
    PURPOSE: Graph ADT implemented in java.
    MODIFIED BY: Jonathan Wright
    DATE OF MODIFICATION: 15/10/2019
*/
public class DSAGraph implements Serializable
{
    private class DSAGraphVertex implements Serializable
    {
        /* Classfields */
        private String label;
        private int likes;
        private Object value;
        private DSALinkedList links; /* Edges */
        private DSALinkedList recievedMessages;
        private boolean visited;
        /* Constructor */
        /**************************************************************************
        * Purpose: alternate constructor
        * IMPORT: String inLabel, Object inValue
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public DSAGraphVertex(String inLabel, Object inValue)
        {
            label = inLabel;
            value = inValue;
            likes = 0;
            links = new DSALinkedList<DSAGraphEdge>();
            recievedMessages = new DSALinkedList();
            visited = false;
        }
        /**************************************************************************
        * Purpose: get the likes of message passed in (OLD, UNUSED BUT PERHAPS
        * USED AGAIN)
        * IMPORT: Object inMessage
        * EXPORT: Contents of Message
        * DATE: 27/10/2019
        **************************************************************************/
        public int getLikeVert(Object inMessage)
        { /* Required To Get Likes of A Message. */
            return ((Message)inMessage).getLikes();
        }
        /**************************************************************************
        * Purpose: Get formatted version of each edge the vertex has.
        * IMPORT: none
        * EXPORT: String of formatted edges.
        * DATE: 27/10/2019
        **************************************************************************/
        public String getFormattedEdges()
        {
            String formatted = "";
            for (Object o: links)
            {
                formatted += ((DSAGraphEdge)o).getFrom() + ":" + ((DSAGraphEdge)o).getTo() + "\n";
            }
            formatted = formatted.substring(0, formatted.length() - 1); /* REMOVE FINAL \n */
            return formatted;
        }
        /**************************************************************************
        * Purpose: Removes a edge between vertex and target.
        * IMPORT: DSAGraphVertex selectedVertex
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void unfollow(DSAGraphVertex selectedUser)
        {
            for (Object o: links)
            {
                if (((DSAGraphEdge)o).getTo().equals(selectedUser))
                {
                    links.remove(o); /* REMOVE THE EDGE */
                }
            }
        }
        /**************************************************************************
        * Purpose: returns a string filled with all edges to value.
        * IMPORT: none
        * EXPORT: String followers.
        * DATE: 27/10/2019
        **************************************************************************/
        public String getFollowing()
        {
            String followers = "";
            for (Object o: links)
            {
                followers += ((DSAGraphEdge)o).getTo().getLabel() + "\n";
            }
            if (followers == "")
            {
                followers = "Not Following Anyone";
            }
            return followers;
        }
        /**************************************************************************
        * Purpose: return whether or not this vertex follows selectedvertex.
        * IMPORT: DSAGraphVertex selectedUser
        * EXPORT: boolean isFollowing
        * DATE: 27/10/2019
        **************************************************************************/
        public boolean follows(DSAGraphVertex selectedUser)
        {
            boolean isFollowing = false;
            for (Object o: links)
            {
                if ( ((DSAGraphEdge)o).getTo().equals(selectedUser) )
                {
                    isFollowing = true;
                }
            }
            return isFollowing;
        }
        /**************************************************************************
        * Purpose: will perform a singular timestep.
        * IMPORT: String uniqueFilename (For saving details to unique file.)
        * EXPORT: boolean testing (Used for a test harness)
        * DATE: 27/10/2019
        **************************************************************************/
        public boolean timeStep(String uniqueFilename)
        {
            boolean testing = false; /* FOR TEST HARNESS PURPOSES */
            /* Need To Store all the current Messages Before the timestep happens */
            Message[] timeMessages = new Message[recievedMessages.size()];
            for (int i = 0; i < timeMessages.length; i++)
            {
                timeMessages[i] = (Message)recievedMessages.removeFirst();
            }
            /* LIST SHOULD NOW BE EMPTY TO RECIEVE MORE FOR NEXT TIME STEP */
            for (Message curr: timeMessages)
            {
                if (!curr.getOrigin().equals(this) && curr.hasntRolled(this) && curr.rollLike(this, likeChance, uniqueFilename))
                {
                    ((DSAGraphVertex)curr.getOrigin()).liked();
                    testing = true;
                    curr.liked();
                    boolean exists = false;
                    for (Object o: links)
                    {
                        if (((DSAGraphEdge)o).getTo().equals(curr.getOrigin()))
                        {
                            exists = true;
                        }
                    }
                    if (exists == false)
                    {
                        if (curr.hasntRolledFollow(this) && curr.rollFollow(this, followChance, uniqueFilename))
                        {
                            /* FOLLOW */
                            NetworkIO.timeStepAppend(uniqueFilename, this.getLabel() + " decided to follow "
                            + curr.getOrigin().toString());
                            System.out.println(this.getLabel() + " decided to follow "
                            + curr.getOrigin().toString());
                            this.follow((DSAGraphVertex)curr.getOrigin());
                        }
                    }
                    sendMessageOut(curr);
                }
            }
            return testing;
        }
        /**************************************************************************
        * Purpose: will increment like count by one for vertex
        * IMPORT: none
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void liked()
        { /* Called when a message from user is liked. */
            likes += 1;
        }
        /**************************************************************************
        * Purpose: will make vertex follow target vertex.
        * IMPORT: DSAGraphVertex to
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void follow(DSAGraphVertex to)
        {
            this.addEdge(this.getLabel() + ":" + to.getLabel(), new
            Integer(0), to);
        }
        /**************************************************************************
        * Purpose: sends a message from origin point to all followers.
        * IMPORT: DSAGraphVertex origin, String contents, double inLike, double inFollow
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void sendMessage(DSAGraphVertex origin, String contents, double inLike,
        double inFollow)
        { /* THIS IS DESIGNED FOR THE ORIGIN POINT! */
            /* If Its My message! */
            if (origin.equals(this))
            {
                /* Forward It Out */
                System.out.println(this.getLabel() + " just posted: " + contents);
                Message outMessage = new Message(contents, this);
                outMessage.visit(this);
                messages.insertLast(outMessage);
                sendMessageOut(outMessage);
            }
            else
            {
                throw new IllegalArgumentException("Node " + this.getLabel() + " recieved a message to send from " + origin.getLabel() + " this isn't supposed to happen!");
            }
        }
        /**************************************************************************
        * Purpose: sends a message out from origin point to all followers with
        * clickBait attached.
        * IMPORT: DSAGraphVertex origin, String contents, double inLike, double inFollow,
        * double clickBait
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void sendMessageC(DSAGraphVertex origin, String contents, double inLike,
        double inFollow, double clickBait) /* CLICKBAIT VERSION */
        { /* THIS IS DESIGNED FOR THE ORIGIN POINT! */
            /* If Its My message! */
            if (origin.equals(this))
            {
                /* Forward It Out */
                System.out.println(this.getLabel() + " just posted: " + contents);
                Message outMessage = new Message(contents, this, clickBait);
                outMessage.visit(this);
                messages.insertLast(outMessage);
                sendMessageOut(outMessage);
            }
            else
            {
                throw new IllegalArgumentException("Node " + this.getLabel() + " recieved a message to send from " + origin.getLabel() + " this isn't supposed to happen!");
            }
        }
        /**************************************************************************
        * Purpose: Vertex will recieve message this way.
        * IMPORT: Message message
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void recieveMessage(Message message)
        { /* THIS IS DESIGNED FOR THE ORIGIN POINT! */
            if (message.getOrigin() != this && !message.contains(this))
            {
                /* Chance To Like & Follow */
                System.out.println(this.getLabel() + " just read their timeline and saw a post from " + message.getOrigin() + ". It said: \"" + message.getContents() + "\"");
                recievedMessages.insertLast(message);
                message.visit(this);
            }
        }
        /**************************************************************************
        * Purpose: default constructor
        * IMPORT: none
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void sendMessageOut(Message message)
        {
            for (Object o: links)
            {
                if (!message.contains(o))
                {
                    ((DSAGraphEdge)o).getTo().recieveMessage(message);
                }
            }
        }
        /**************************************************************************
        * Purpose: returns label.
        * IMPORT: none
        * EXPORT: label
        * DATE: 27/10/2019
        **************************************************************************/
        public String getLabel() {return label;}
        /**************************************************************************
        * Purpose: returns value
        * IMPORT: none
        * EXPORT: value
        * DATE: 27/10/2019
        **************************************************************************/
        public Object getValue() {return value;}
        /**************************************************************************
        * Purpose: returns adjacent edges;
        * IMPORT: none
        * EXPORT: links
        * DATE: 27/10/2019
        **************************************************************************/
        public DSALinkedList getAdjacent() {return links;}
        /**************************************************************************
        * Purpose: addEdge to vertex.
        * IMPORT: String label, Object value, DSAGraphVertex connection
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void addEdge(String label, Object value, DSAGraphVertex connection)
        {
            links.insertLast(new DSAGraphEdge(label, value, this, connection));
        }
        /**************************************************************************
        * Purpose: sets visited to true.
        * IMPORT: none
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void setVisited()
        {
            // None yet
            visited = true;
        }
        /**************************************************************************
        * Purpose: sets visited to false.
        * IMPORT: none
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void clearVisited()
        {
            visited = false;
        }
        /**************************************************************************
        * Purpose: remove all edges that reference in vertex.
        * IMPORT: DSAGraphVertex in
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void removeReference(DSAGraphVertex in)
        {
            for (Object o: links)
            {
                if (((DSAGraphEdge)o).getTo() == in)
                {
                    links.remove(o);
                }
            }
        }
        /**************************************************************************
        * Purpose: return visited.
        * IMPORT: none
        * EXPORT: boolean visited
        * DATE: 27/10/2019
        **************************************************************************/
        public boolean getVisited() {return visited;}
        /**************************************************************************
        * Purpose: will return the label of the vertex.
        * IMPORT: none
        * EXPORT: String label
        * DATE: 27/10/2019
        **************************************************************************/
        public String toString()
        {
            return label; /* For This Implementation it made sense for the toString to just be the title of the node. */
            /*return String.format("L:%s, V:%s, VL:%s, Vis:%s", label, value.toString(), displayList(), Boolean.toString(visited));*/
        }
        /**************************************************************************
        * Purpose: Will display the vertex's links in a string.
        * IMPORT: none
        * EXPORT: displayString
        * DATE: 27/10/2019
        **************************************************************************/
        public String displayList()
        {
            String displayString = new String();
            for(Object ele: links)
            {
                displayString += ((DSAGraphEdge)ele).getTo().getLabel() + ", ";
            }
            if (displayString.length() > 0)
            {
                displayString = displayString.substring(0, displayString.length() - 1); // remove the final ' '
            }
            /* SORTING
            char[] sorted = displayString.toUpperCase().toCharArray();
            int[] sortedI = new int[sorted.length];
            for (int i = 0; i < sorted.length; i++)*/
            //{ /* copy char values into int array of ascii values */
            /*    sortedI[i] = (int)sorted[i];
            }
            Sorts.quickThreeWaySort(sortedI);*/ /* Dutch Flag best with random */
            /*displayString = "";
            for (int x: sortedI)
            {
                if (x != (int)' ')
                {
                    displayString += (char)x + " ";
                }   CURRENTLY DOES NOT WORK.
            }*/
            return displayString;
        }
        /**************************************************************************
        * Purpose: getMessageDetails returns values of message. This is a
        * remenant of when it was a private innerclass but I decided to keep this
        * as it is quicker access to a messages details from a vertex.
        * IMPORT: Object inMessage
        * EXPORT: outString
        * DATE: 27/10/2019
        **************************************************************************/
        public String getMessageDetails(Object inMessage)
        {
            String outString = "";
            outString = ((DSAGraphVertex)((Message)inMessage).getOrigin()).getLabel() + " said: "
            + ((Message)inMessage).getContents();
            return outString;
        }
        /**************************************************************************
        * Purpose: returns likes of vertex.
        * IMPORT: none
        * EXPORT: likes (int)
        * DATE: 27/10/2019
        **************************************************************************/
        public int getLikes()
        {
            return likes;
        }
        /**************************************************************************
        * Purpose: sets like to value
        * IMPORT: int value
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public void setLike(int value)
        {
            this.likes = value;
        }
    }

    private class DSAGraphEdge implements Serializable
    {
        private String label;
        private Object value;
        private DSAGraphVertex from;
        private DSAGraphVertex to;

        /**************************************************************************
        * Purpose: alternate constructor
        * IMPORT: String inLabel, Object inValue, DSAGraphVertex inFrom, DSAGraphVertex inTo
        * EXPORT: none
        * DATE: 27/10/2019
        **************************************************************************/
        public DSAGraphEdge(String inLabel, Object inValue, DSAGraphVertex inFrom, DSAGraphVertex inTo)
        {
            value = inValue;
            label = inLabel;
            from = inFrom;
            to = inTo;
        }
        /**************************************************************************
        * Purpose: returns label
        * IMPORT: none
        * EXPORT: String label
        * DATE: 27/10/2019
        **************************************************************************/
        public String getLabel()
        {
            return label;
        }
        /**************************************************************************
        * Purpose: returns value
        * IMPORT: none
        * EXPORT: value
        * DATE: 27/10/2019
        **************************************************************************/
        public Object getValue()
        {
            return value;
        }
        /**************************************************************************
        * Purpose: returns from
        * IMPORT: none
        * EXPORT: from
        * DATE: 27/10/2019
        **************************************************************************/
        public DSAGraphVertex getFrom()
        {
            return from;
        }
        /**************************************************************************
        * Purpose: returns to
        * IMPORT: none
        * EXPORT: to
        * DATE: 27/10/2019
        **************************************************************************/
        public DSAGraphVertex getTo()
        {
            return to;
        }
        /**************************************************************************
        * Purpose: returns a formatted string of each detail of edge.
        * IMPORT: none
        * EXPORT: formattedString
        * DATE: 27/10/2019
        **************************************************************************/
        public String toString()
        {
            return String.format("%s, %s, %s, %s", label, value, from.getLabel(), to.getLabel());
        }
    }
    /* Classfields */
    private double likeChance;
    private double followChance;
    private DSALinkedList verticies; /* try a hash table. : DIDNT WORK.*/
    private DSALinkedList messages;
    private boolean directed;
    /* Constructor */
    /**************************************************************************
    * Purpose: default constructor
    * IMPORT: none
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public DSAGraph()
    {
        likeChance = 0;
        followChance = 0;
        directed = false;
        verticies = new DSALinkedList();
        messages = new DSALinkedList();
    }
    /**************************************************************************
    * Purpose: alternate constructor for directed graphs
    * IMPORT: boolean direction
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public DSAGraph(boolean direct)
    {
        likeChance = 0;
        followChance = 0;
        if (direct == true)
        {
            directed = true;
        }
        else
        {
            directed = false;
        }
        verticies = new DSALinkedList();
    }
    /**************************************************************************
    * Purpose: returns all posts by user as a string.
    * IMPORT: none
    * EXPORT: String userPosts
    * DATE: 27/10/2019
    **************************************************************************/
    /* Methods */
    public String getUserPosts(DSAGraphVertex selectedUser)
    {
        String userPosts = "";
        for (Object m: messages)
        {
            if (((Message)m).getOrigin().equals(selectedUser))
            { /* If its their message add it. */
                userPosts += ((Message)m).getContents();
                userPosts += "\n";
            }
        }
        if (userPosts == "")
        { /* If there were no posts in graph from user. */
            userPosts = "No Posts.";
        }
        return userPosts;
    }
    /**************************************************************************
    * Purpose: returns string of all following
    * IMPORT: DSAGraphVertex selectedUser
    * EXPORT: String of following
    * DATE: 27/10/2019
    **************************************************************************/
    public String getFollowing(DSAGraphVertex selectedUser)
    {
        return selectedUser.getFollowing();
    }
    /**************************************************************************
    * Purpose: get string of followers of user.
    * IMPORT: DSAGraphVertex selectedUser
    * EXPORT: follows
    * DATE: 27/10/2019
    **************************************************************************/
    public String getFollowers(DSAGraphVertex selectedUser)
    {
        String follows = "";
        for (Object o: verticies)
        {
            if (((DSAGraphVertex)o).follows(selectedUser))
            {
                follows += o.toString() + "\n";
            }
        }
        if (follows == "")
        {
            follows = "No Followers.";
        }
        return follows;
    }
    /**************************************************************************
    * Purpose: Returns a string of sorted users by popularity.
    * IMPORT: none
    * EXPORT: String finalOutput
    * DATE: 27/10/2019
    **************************************************************************/
    public String orderedUsers()
    {
        String finalOutput = "";
        int[] temp = new int[verticies.size()];
        String[] users = new String[temp.length];
        int i = 0;
        for (Object o: verticies)
        {
            users[i] = ((DSAGraphVertex)o).getLabel() + " (Likes):" + ((DSAGraphVertex)o).getLikes();
            temp[i] = ((DSAGraphVertex)o).getLikes();
            i++;
        }
        Sorts.quickThreeWaySort(temp); /* Fastest With random data. */
        for (i = 0; i < temp.length; i++)
        {
            for (int ik = 0; ik < users.length; ik++)
            {
                if (users[ik] != null)
                {
                    int tempD = Integer.valueOf(((users[ik].split(":"))[1]));
                    if (tempD == temp[i] && users[i] != null)
                    {
                        finalOutput += users[i] + "\n";
                        users[i] = null;
                    }
                }
            }
        }
        return finalOutput;
    } /* Orders Users in Popularity. */
    /**************************************************************************
    * Purpose: Returned a string of sorted messages by likes.
    * IMPORT: none
    * EXPORT: String ranked.
    * DATE: 27/10/2019
    **************************************************************************/
    public String rankMessages()
    {
        /*int[] rankedLikes = getLikes();
        Sorts.quickThreeWaySort(rankedLikes);
        int[] temp = new int[rankedLikes.length];
        for (int j = 0; j < (rankedLikes.length - 1); j++)
        {
            temp[j] = rankedLikes[(rankedLikes.length - 1) - j];
        }
        return temp;*/
        Message[] unordered = new Message[messages.size()];
        int i = 0;
        for (Object o: messages)
        {
            unordered[i] = (Message)o;
            i++;
        }
        Sorts.messageSort(unordered);
        /* Messages Now Sorted. */
        String ranked = "";
        for (Message m: unordered)
        {
            ranked += m.getContents() + " (Likes: " + m.getLikes() + ")" + "\n";
        }
        return ranked;
    } /* Returns Ranked Liked Messages */
    /**************************************************************************
    * Purpose: perform 1 timestep of all messages.
    * IMPORT: String uniqueFilename
    * EXPORT: boolean timeStep (This was unimplemented by was going to be a way
    * to say it has reached the final vertex.)
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean timeStep(String uniqueFilename)
    {
        boolean timeStep = false;
        for (Object o: verticies)
        {
            boolean x = ((DSAGraphVertex)o).timeStep(uniqueFilename);
            if (x == true)
            {
                timeStep = x;
            }
        }
        return timeStep;
    }
    /**************************************************************************
    * Purpose: returns a string of all verticies.
    * IMPORT: none
    * EXPORT: String vertexs
    * DATE: 27/10/2019
    **************************************************************************/
    public String getVerticiesList()
    {
        String vertexs = "";
        for (Object o: verticies)
        {
            vertexs += ((DSAGraphVertex)o).getLabel() + "\n";
        }
        return vertexs;
    }
    /**************************************************************************
    * Purpose: returns a string of all edges.
    * IMPORT: none
    * EXPORT: String edges.
    * DATE: 27/10/2019
    **************************************************************************/
    public String getEdgesList()
    {
        String edges = "";
        for (Object o: verticies)
        {
            String currEdge = ((DSAGraphVertex)o).getFormattedEdges();
            if (currEdge != "")
            {
                edges += ((DSAGraphVertex)o).getFormattedEdges() + "\n";
            }
        }
        return edges;
    }
    /**************************************************************************
    * Purpose: from vertex will follow to vertex.
    * IMPORT: String from, String to
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void follow(String from, String to)
    {
        getVertex(from).follow(getVertex(to));
    }
    /**************************************************************************
    * Purpose: from vertex will unfollow to vertex.
    * IMPORT: String from, String to
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void unfollow(String from, String to)
    {
        if (getVertex(from).follows(getVertex(to)))
        {
            getVertex(from).unfollow(getVertex(to));
        }
        else
        {
            throw new IllegalArgumentException("Vertex " + from + " does not follow " + to);
        }
    }
    /**************************************************************************
    * Purpose: return a int[] of likes.
    * IMPORT: none
    * EXPORT: likez (array of int)
    * DATE: 27/10/2019
    **************************************************************************/
    public int[] getLikes()
    {
        int[] likez = new int[messages.size()];
        int i = 0;
        for (Object o: messages)
        {
            likez[i] = ((Message)o).getLikes();
            i++;
        }
        return likez;
    }
    /**************************************************************************
    * Purpose: sets like of vertex to likeVal.
    * IMPORT: String node, int likeVal
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void setLike(String node, int likeVal)
    {
        getVertex(node).setLike(likeVal);
    }
    /**************************************************************************
    * Purpose: returns likechance.
    * IMPORT: none
    * EXPORT: likechance
    * DATE: 27/10/2019
    **************************************************************************/
    public double getLikeChance()
    {
        return likeChance;
    }
    /**************************************************************************
    * Purpose: return followchance
    * IMPORT: none
    * EXPORT: followchance
    * DATE: 27/10/2019
    **************************************************************************/
    public double getFollowChance()
    {
        return followChance;
    }
    /**************************************************************************
    * Purpose: sets likechance to inval
    * IMPORT: double inlikechance
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void setLikeChance(double inLikeChance)
    {
        likeChance = inLikeChance;
    }
    /**************************************************************************
    * Purpose: sets followchance to inval
    * IMPORT: double infollowchance
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void setFollowChance(double inFollowChance)
    {
        followChance = inFollowChance;
    }
    /**************************************************************************
    * Purpose: returns a string of all messages.
    * IMPORT: none
    * EXPORT: finalString
    * DATE: 27/10/2019
    **************************************************************************/
    public String getMessages()
    {
        String finalString = "";
        for (Object o: messages)
        {
            finalString += ((Message)o).getOrigin() + " said: " + ((Message)o).getContents() + " (" + ((Message)o).getLikes() + " Likes)\n";
        }
        return finalString;
    }
    /**************************************************************************
    * Purpose: adds vertex to graph.
    * IMPORT: String inLabel, Object inVal
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void addVertex(String inLabel, Object inValue)
    {
        if (hasVertex(inLabel))
        {
            throw new IllegalArgumentException("Vertex " + inLabel + " already exists in the graph!");
        }
        else
        {
            verticies.insertLast(new DSAGraphVertex(inLabel, inValue));
        }
    }
    /**************************************************************************
    * Purpose: add edge to graph.
    * IMPORT: String from, String to, String edgeLabel, Object weight
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void addEdge(String from, String to, String edgeLabel, Object weight)
    {
        if (directed == false)
        {
            getVertex(from).addEdge(edgeLabel, weight, getVertex(to));
            getVertex(to).addEdge(edgeLabel, weight, getVertex(from));
        }
        else
        {
            getVertex(from).addEdge(edgeLabel, weight, getVertex(to));
        }
    }
    /**************************************************************************
    * Purpose: creates a new message in graph.
    * IMPORT: DSAGraphVertex origin, String contents
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void newMessage(DSAGraphVertex origin, String contents)
    {
        origin.sendMessage(origin, contents, this.likeChance, this.followChance);
    }
    /**************************************************************************
    * Purpose: creates a new message in graph with clickbait factor.
    * IMPORT: DSAGraphVertex origin, String contents, double clickBait
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void newMessageC(DSAGraphVertex origin, String contents, double clickBait)
    {
        origin.sendMessageC(origin, contents, this.likeChance, this.followChance, clickBait);
    }
    /**************************************************************************
    * Purpose: removes a node from the graph.
    * IMPORT: String inLabel
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    public void removeNode(String inLabel)
    {
        try
        {
            DSAGraphVertex removed = this.getVertex(inLabel);
            verticies.remove(removed);
            /* Out of verticies list, now have to remove references to the removed vertex. */
            for (Object o: verticies)
            {
                ((DSAGraphVertex)o).removeReference(removed);
            }
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException("Cannot remove a node that does not exist!");
        }
    }
    /**************************************************************************
    * Purpose: checks if graph has vertex in it.
    * IMPORT: String inLabel
    * EXPORT: boolean hasVertex
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean hasVertex(String inLabel)
    {
        boolean hVertex = false; //assume worst
        for(Object vert: verticies)
        {
            try
            {
                if (getVertex(inLabel).equals(vert)) {hVertex = true;}
            }
            catch (IllegalArgumentException e)
            {
                // Its not in graph.
            }
        }
        return hVertex;
    }
    /**************************************************************************
    * Purpose: gets count of vertexs.
    * IMPORT: none
    * EXPORT: count
    * DATE: 27/10/2019
    **************************************************************************/
    public int getVertexCount()
    {
        int count = 0;
        for (Object vert: verticies)
        {
            count++;
        }
        return count;
    }
    /**************************************************************************
    * Purpose: gets count of edges.
    * IMPORT: none
    * EXPORT: count
    * DATE: 27/10/2019
    **************************************************************************/
    public int getEdgeCount()
    {
        int count = 0;
        DSALinkedList<String> edges = new DSALinkedList();
        for (Object vert: verticies)
        {
            DSAGraphVertex x = (DSAGraphVertex)vert;
            if (!contains(edges, x.displayList()))
            {
                edges.insertLast(x.displayList());
                count++;
            }
        }
        return count;
    }
    /**************************************************************************
    * Purpose: checks if graph contains edge.
    * IMPORT: DSALinkedList<String> edges, String inString
    * EXPORT: exists.
    * DATE: 27/10/2019
    **************************************************************************/
    private boolean contains(DSALinkedList<String> edges, String inString)
    {
        boolean exists = false; // assume best
        StringBuilder reverser = new StringBuilder(inString);
        String compareString = reverser.reverse().toString();
        for(Object edge: edges)
        {
            if (((String)edge).equals(compareString)) {exists = true;}
        }
        return exists;
    }
    /**************************************************************************
    * Purpose: getVertex from graph.
    * IMPORT: String inLabel
    * EXPORT: DSAGraphVertex outVertex
    * DATE: 27/10/2019
    **************************************************************************/
    public DSAGraphVertex getVertex(String inLabel)
    {
        DSAGraphVertex outVertex = null;
        boolean inGraph = false; // start by assuming not in
        for(Object vert: verticies)
        {
            if (((DSAGraphVertex)vert).getLabel().equals(inLabel))
            {
                outVertex = (DSAGraphVertex)vert;
                inGraph = true;
            }
        }
        if (inGraph == false)
        {
            throw new IllegalArgumentException(inLabel + " is not in Graph!");
        }
        return outVertex;
    }
    /**************************************************************************
    * Purpose: returns string of adjacent vertexs.
    * IMPORT: String inLabel
    * EXPORT: String adjacent
    * DATE: 27/10/2019
    **************************************************************************/
    public DSALinkedList getAdjacent(String inLabel)
    {
        DSAGraphVertex requestedVertex = getVertex(inLabel);
        return requestedVertex.getAdjacent();
    }
    /**************************************************************************
    * Purpose: checks if it is adjacent
    * IMPORT: String label1, String label2
    * EXPORT: isInGraph (Boolean)
    * DATE: 27/10/2019
    **************************************************************************/
    public boolean isAdjacent(String label1, String label2)
    {
        boolean inGraph = false;
        try
        {
            DSAGraphVertex vertex1 = getVertex(label1);
            DSAGraphVertex vertex2 = getVertex(label2);
            for(Object vert: vertex1.getAdjacent())
            {
                if (((DSAGraphEdge)vert).getTo().equals(vertex2)) {inGraph = true;}
            }
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        return inGraph;
    }
    /**************************************************************************
    * Purpose: Returns string of graph as a adj list.
    * IMPORT: none
    * EXPORT: finalString
    * DATE: 27/10/2019
    **************************************************************************/
    public String displayAsList()
    {
        int i = 0;
        String[] lines = new String[getVertexCount()];
        for(Object vert: verticies)
        {
            lines[i] = ((DSAGraphVertex)vert).getLabel() + ": " + ((DSAGraphVertex)vert).displayList();
            //System.out.printf("");
            i++;
        }
        String finalString = "";
        for(String line: lines)
        {
            finalString += line + "\n";
        }
        return finalString;
    }
    /**************************************************************************
    * Purpose: Returns a string of graph as a matrix.
    * IMPORT: none
    * EXPORT: finalString
    * DATE: 27/10/2019
    **************************************************************************/
    public String displayAsMatrix()
    {
        int i = 0;
        String[] lines = new String[getVertexCount()];
        for (Object vert: verticies)
        {
            lines[i] = ((DSAGraphVertex)vert).getLabel();
            for (Object vertinner: verticies)
            {
                if (isAdjacent(((DSAGraphVertex)vert).getLabel(), ((DSAGraphVertex)vertinner).getLabel()))
                {
                    lines[i] += " 1";
                }
                else
                {
                    lines[i] += " 0";
                }
            }
            i++;
        }
        String firstLine = "  ";
        for (Object verts: verticies)
        {
            firstLine += ((DSAGraphVertex)verts).getLabel() + " ";
        }
        String finalString = "";
        finalString += firstLine + "\n";
        for (String line: lines)
        {
            finalString += line + "\n";
        }
        return finalString;
    }
    /**************************************************************************
    * Purpose: Performs a depth first search on graph.
    * IMPORT: none
    * EXPORT: DSAGraph dfsTree.
    * DATE: 27/10/2019
    **************************************************************************/
    public DSAGraph depthFirstSearch()
    {
        DSAGraph dfsTree = new DSAGraph();
        resetVertexs();
        DSAGraphVertex v = getFirstVertex();
        DSAGraphVertex w;
        v.setVisited();
        DSAStack s = new DSAStack();
        s.push(v);
        dfsTree.addVertex(v.getLabel(),v.getLabel());
        while (!s.isEmpty())
        {
            while (v != null && unvisited(v.getAdjacent()) != null)
            {
                w = unvisited(v.getAdjacent());
                try
                {
                    dfsTree.addVertex(w.getLabel(),w.getLabel());
                }
                catch (IllegalArgumentException e)
                {
                    // already in tree
                }
                System.out.println("Adding edge between " + v.getLabel() + " and " + w.getLabel());
                dfsTree.addEdge(v.getLabel(), w.getLabel(), "Edge", new Integer(0));
                //System.out.println("Loop");
                w.setVisited();
                s.push(w);
                v = (DSAGraphVertex)s.top();
            }
            try
            {
                v = (DSAGraphVertex)s.pop();
            }
            catch (NullPointerException e)
            {
                // stack is empty.
            }
        }
        return dfsTree;
    }
    /**************************************************************************
    * Purpose: returns a unvisited node.
    * IMPORT: none
    * EXPORT: curr (DSAGraphVertex)
    * DATE: 27/10/2019
    **************************************************************************/
    private DSAGraphVertex unvisited(DSALinkedList adj)
    {
        DSAGraphVertex curr = null;
        for(Object e: adj)
        {
            if (curr == null && !((DSAGraphEdge)e).getTo().getVisited())
            {
                curr = ((DSAGraphEdge)e).getTo();
            }
            else if (curr != null && ((DSAGraphEdge)e).getTo().getLabel().compareTo(curr.getLabel()) < 0 && !((DSAGraphEdge)e).getTo().getVisited())
            {
                curr = ((DSAGraphEdge)e).getTo();
            }
        }
        return curr;
    }
    /**************************************************************************
    * Purpose: set all vertexs to unvisited.
    * IMPORT: none
    * EXPORT: none
    * DATE: 27/10/2019
    **************************************************************************/
    private void resetVertexs()
    {
        for (Object v: verticies)
        {
            ((DSAGraphVertex)v).clearVisited();
        }
    }
    /**************************************************************************
    * Purpose: get first vertex in list
    * IMPORT: none
    * EXPORT: curr (DSAGraphvertex)
    * DATE: 27/10/2019
    **************************************************************************/
    private DSAGraphVertex getFirstVertex()
    {
        DSAGraphVertex curr = null;
        for (Object x: verticies)
        {
            if (curr == null)
            {
                curr = (DSAGraphVertex)x;
            }
            else if (((DSAGraphVertex)x).getLabel().compareTo(curr.getLabel()) < 0)
            {
                curr = (DSAGraphVertex)x;
            }
        }
        return curr;
    }
    /**************************************************************************
    * Purpose: performs breadthFirstSearch on graph.
    * IMPORT: none
    * EXPORT: bfsTree (DSAGraph)
    * DATE: 27/10/2019
    **************************************************************************/
    public DSAGraph breadthFirstSearch()
    {
        DSAGraph bfsTree = new DSAGraph();
        DSAGraphVertex v = getFirstVertex();
        resetVertexs();
        DSACircularQueue queue = new DSACircularQueue();
        queue.enqueue(v);
        v.setVisited();
        while(!queue.isEmpty())
        {
            v = (DSAGraphVertex)queue.dequeue();
            try
            {
                bfsTree.addVertex(((DSAGraphVertex)v).getLabel(), ((DSAGraphVertex)v).getLabel());
            }
            catch (IllegalArgumentException e)
            {
                // already in tree.
            }
            for(Object w: unvisitedBFS(v.getAdjacent()))
            {
                try
                {
                    bfsTree.addVertex(((DSAGraphEdge)w).getTo().getLabel(),((DSAGraphEdge)w).getTo().getLabel());
                }
                catch (IllegalArgumentException e)
                {
                    //already in tree.
                }
                bfsTree.addEdge(((DSAGraphVertex)v).getLabel(),((DSAGraphEdge)w).getTo().getLabel(), "Edge", new Integer(0));
                ((DSAGraphEdge)w).getTo().setVisited();
                queue.enqueue(((DSAGraphEdge)w).getTo());
            }
        }
        return bfsTree;
    }
    /**************************************************************************
    * Purpose: return unvisited node list.
    * IMPORT: none
    * EXPORT: outList (DSALinkedList)
    * DATE: 27/10/2019
    **************************************************************************/
    private DSALinkedList unvisitedBFS(DSALinkedList inList)
    {
        DSALinkedList outList = new DSALinkedList();
        for(Object z: inList)
        {
            if (!((DSAGraphEdge)z).getTo().getVisited())
            {
                outList.insertLast(z);
            }
        }
        return outList;
    }
}
