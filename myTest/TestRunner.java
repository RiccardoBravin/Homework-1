//package myTest;

import myAdapter.*;
import myTest.*;

//import org.hamcrest.*;
import org.junit.runner.*;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MapTester.class);
        if(result.wasSuccessful())
		{
			System.out.println("Test OK");
		}
    }
}