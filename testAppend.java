import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class testAppend
{
    public static void main(String[] args)
    {
        System.out.println("Testing.");
        NetworkIO.timeStepAppend("Test1", "This is a line.");
        NetworkIO.timeStepAppend("Test1", "This is a line.");
        NetworkIO.timeStepAppend("Test1", "This is a line.");
        NetworkIO.timeStepAppend("Test1", "This is a line.");
        NetworkIO.timeStepAppend("Test1", "This is a line.");
        NetworkIO.timeStepAppend("Test1", "This is a line.");
        System.out.println("Verify that timeSteps/Test1 contains 6 lines of \"This is a line\"");

        Calendar rightNow = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String test = dateFormat.format(rightNow.getTime());
        System.out.println(test);
    }
}
