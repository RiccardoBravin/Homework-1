//package myTest;

import myAdapter.*;
import myTest.*;


import org.junit.runner.*;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MapTester.class, ListTester.class, IteratorTester.class );
        if(result.wasSuccessful())
		{
			System.out.println("Test OK");
		}

        System.out.println("Che la forza sia con TEst");
    }
}
