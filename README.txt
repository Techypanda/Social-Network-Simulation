Dependencies:
    JDK (Version 7 or Higher)
    Windows CMD, Linux Terminal or Mac Terminal
    CSV Files to load into the network (Not Necessarily required but recommended)

How To Run:
    1) First you must compile all the classes using command:
        javac *.java
    2) Then you run the program like this:
        java SocialSim
    3) It will present you with different ways to run the program:
        (-i for interactive)
        (-s for simulation)
    4) Run the program like this once decided:
        java SocialSim -(CHOSENFLAG) (ADDITIONAL PARAMETERS)
    5) Program should run fine.

Files:
    - DSACircularQueue.java
        Purpose: Circular Queue Abstract DataType.
    - DSAGraph.java
        Purpose: Graph Abstract Datatype.
    - DSALinkedList.java
        Purpose: LinkedList Abstract Datatype.
    - DSAQueue.java
        Purpose: Queue Abstract Datatype
    - DSAStack.java
        Purpose: Stack Abstract Datatype
    - FakeJUnit.java
        Purpose: Custom Testing Harness Tools (Basically JUnit)
    - FileIOGraph.java
        Purpose: Reads in graphs from file.
    - GraphTest.java
        Purpose: Graph Test harness.
    - Interactive.java
        Purpose: Contains the code for how the Interactive Mode will perform.
    - Message.java
        Purpose: Contains class Message which contains details about messages posted in network.
    - Network.java
        Purpose: Contains network class which contains graph for social network and other details.
    - NetworkIO.java
        Purpose: Contains code for how networks are read from file and saved to file.
    - NetworkTestHarness.java
        Purpose: Tests network class.
    - SimulationMode.java
        Purpose: Contains implementation of Simulation Mode
    - SocialSim.java
        Purpose: Starting point of the program and is used to run the Social Simulation
    - Sorts.java
        Purpose: Contains Various Sorting Algorithms.
    - testAppend.java
        Purpose: Tested my method of appending to file.
    - Utilities.java
        Purpose: Contains general purpose code used in various classes.
