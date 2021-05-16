import myAdapter.*;
import myTest.*;
import org.junit.runner.*;

public class TestRunner {
    public static void main(String[] args) {
        int time = 0;
        int tests = 0;
        int unsuccessfull = 0;

        System.out.println("Inizio esecuzione test suite");

        System.out.println("Esecuzione test <<MapTester>>");
        Result results = JUnitCore.runClasses(MapTester.class);
        System.out.println("  Test superati: " + (results.getRunCount() - results.getFailureCount()) + "/" + results.getRunCount());
        if(!results.wasSuccessful()) {
            System.out.println("problemi riscontrati: " + results.getFailures());
        }
        time += results.getRunTime();
        tests += results.getRunCount();
        unsuccessfull += results.getFailureCount();

        System.out.println("\nAAEsecuzione test <<EntryAdapter>>");
        results = JUnitCore.runClasses(EntryAdapterTester.class);
        System.out.println("  Test superati: " + (results.getRunCount() - results.getFailureCount()) + "/" + results.getRunCount());
        if(!results.wasSuccessful()) {
            System.out.println("problemi riscontrati: " + results.getFailures());
        }
        time += results.getRunTime();
        tests += results.getRunCount();
        unsuccessfull += results.getFailureCount();

        System.out.println("\nEsecuzione test <<EntrySetTester>>");
        results = JUnitCore.runClasses(EntrySetTester.class);
        System.out.println("  Test superati: " + (results.getRunCount() - results.getFailureCount()) + "/" + results.getRunCount());
        if(!results.wasSuccessful()) {
            System.out.println("problemi riscontrati: " + results.getFailures());
        }
        time += results.getRunTime();
        tests += results.getRunCount();
        unsuccessfull += results.getFailureCount();

        System.out.println("\nEsecuzione test <<KeySetTester>>");
        results = JUnitCore.runClasses(KeySetTester.class);
        System.out.println("  Test superati: " + (results.getRunCount() - results.getFailureCount()) + "/" + results.getRunCount());
        if(!results.wasSuccessful()) {
            System.out.println("problemi riscontrati: " + results.getFailures());
        }
        time += results.getRunTime();
        tests += results.getRunCount();
        unsuccessfull += results.getFailureCount();

        
        System.out.println("\nEsecuzione test <<ValueCollectionTester>>");
        results = JUnitCore.runClasses(ValueCollectionTester.class);
        System.out.println("  Test superati: " + (results.getRunCount() - results.getFailureCount()) + "/" + results.getRunCount());
        if(!results.wasSuccessful()) {
            System.out.println("problemi riscontrati: " + results.getFailures());
        }
        time += results.getRunTime();
        tests += results.getRunCount();
        unsuccessfull += results.getFailureCount();


        System.out.println("\nEsecuzione test <<HashtableIteratorTester>>");
        results = JUnitCore.runClasses(HashtableIteratorTester.class);
        System.out.println("  Test superati: " + (results.getRunCount() - results.getFailureCount()) + "/" + results.getRunCount());
        if(!results.wasSuccessful()) {
            System.out.println("problemi riscontrati: " + results.getFailures());
        }
        time += results.getRunTime();
        tests += results.getRunCount();
        unsuccessfull += results.getFailureCount();

        System.out.println("\nEsecuzione test <<HashtableKeyIteratorTester>>");
        results = JUnitCore.runClasses(HashtableKeyIteratorTester.class);
        System.out.println("  Test superati: " + (results.getRunCount() - results.getFailureCount()) + "/" + results.getRunCount());
        if(!results.wasSuccessful()) {
            System.out.println("problemi riscontrati: " + results.getFailures());
        }
        time += results.getRunTime();
        tests += results.getRunCount();
        unsuccessfull += results.getFailureCount();


        System.out.println("\nEsecuzione test <<HashtableValueIteratorTester>>");
        results = JUnitCore.runClasses(HashtableValueIteratorTester.class);
        System.out.println("  Test superati: " + (results.getRunCount() - results.getFailureCount()) + "/" + results.getRunCount());
        if(!results.wasSuccessful()) {
            System.out.println("problemi riscontrati: " + results.getFailures());
        }
        time += results.getRunTime();
        tests += results.getRunCount();
        unsuccessfull += results.getFailureCount();
        
        
        System.out.println("\nEsecuzione test <<ListTester>>");
        results = JUnitCore.runClasses(ListTester.class);
        System.out.println("  Test superati: " + (results.getRunCount() - results.getFailureCount()) + "/" + results.getRunCount());
        if(!results.wasSuccessful()) {
            System.out.println("problemi riscontrati: " + results.getFailures());
        }
        time += results.getRunTime();
        tests += results.getRunCount();
        unsuccessfull += results.getFailureCount();


        System.out.println("\nEsecuzione test <<ListIteratorTester>>");
        results = JUnitCore.runClasses(ListIteratorTester.class);
        System.out.println("  Test superati: " + (results.getRunCount() - results.getFailureCount()) + "/" + results.getRunCount());
        if(!results.wasSuccessful()) {
            System.out.println("problemi riscontrati: " + results.getFailures());
        }
        time += results.getRunTime();
        tests += results.getRunCount();
        unsuccessfull += results.getFailureCount();

        System.out.println("\nEsecuzione test <<RangedListTester>>");
        results = JUnitCore.runClasses(RangedListTester.class);
        System.out.println("  Test superati: " + (results.getRunCount() - results.getFailureCount()) + "/" + results.getRunCount());
        if(!results.wasSuccessful()) {
            System.out.println("problemi riscontrati: " + results.getFailures());
        }
        time += results.getRunTime();
        tests += results.getRunCount();
        unsuccessfull += results.getFailureCount();

        System.out.println("\n\nTOTALE TEST ESEGUITI: " + tests);
        System.out.println("TOTALE TEST SUPERATI: " + (tests - unsuccessfull));
        System.out.println("TOTALE TEST NON SUPERATI: " + unsuccessfull);
        System.out.println("TEMPO DI ESECUZIONE: " + time + "ms");

    }
}
