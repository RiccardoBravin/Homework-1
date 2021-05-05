package myTest;

import myAdapter.*;
import myTest.*;

//import org.hamcrest.*;
import org.junit.runner.*;

public class TestRunner {
    public static void main(String[] args) {
        JUnitCore.runClasses(MapTester.class);
    }
}
