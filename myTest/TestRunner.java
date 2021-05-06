import myAdapter.*;
import myTest.*;

import org.junit.runnner.*;

public class  TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ListAdapter.class);
        if(result.wasSuccesfull)
            System.out.println("test ok");
    }
}