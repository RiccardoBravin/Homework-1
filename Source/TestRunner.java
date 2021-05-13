//package myTest;

import myAdapter.*;
import myTest.*;
import org.junit.runner.*;

public class TestRunner {
    public static void main(String[] args) {

        int total_times = 0;
        int total_tests = 0;

        System.out.println("Inizializzazione esecuzione test");

        System.out.println("Esecuzione test MapTester.class");
        Result mt = JUnitCore.runClasses(MapTester.class);
        if(mt.wasSuccessful()){
            System.out.println("Esecuzione completata con successo");    
        }else{
            System.out.println("Esecuzione completata senza successo");
            System.out.println("I metodi che hanno fallito sono" + mt.getFailures().toString());
        }
        System.out.println("Sono stati eseguiti correttamente " + (mt.getRunCount() - mt.getFailureCount()) + " test su " + mt.getRunCount() + " in un tempo di " + mt.getRunTime() + "ms");
        total_tests += mt.getRunCount();
        total_times += mt.getRunTime();


        System.out.println("Esecuzione test EntryTester.class");
        Result et = JUnitCore.runClasses(EntryTester.class);
        if(mt.wasSuccessful()){
            System.out.println("Esecuzione completata con successo");    
        }else{
            System.out.println("Esecuzione completata senza successo");
            System.out.println("I metodi che hanno fallito sono" + et.getFailures().toString());
        }
        System.out.println("Sono stati eseguiti correttamente " + (et.getRunCount() - et.getFailureCount()) + " test su " + et.getRunCount() + " in un tempo di " + et.getRunTime() + "ms");
        total_tests += et.getRunCount();
        total_times += et.getRunTime();


        System.out.println("Esecuzione test IteratorTester.class");
        Result it = JUnitCore.runClasses(IteratorTester.class);
        if(mt.wasSuccessful()){
            System.out.println("Esecuzione completata con successo");    
        }else{
            System.out.println("Esecuzione completata senza successo");
            System.out.println("I metodi che hanno fallito sono" + it.getFailures().toString());
        }
        System.out.println("Sono stati eseguiti correttamente " + (it.getRunCount() - it.getFailureCount()) + " test su " + it.getRunCount() + " in un tempo di " + it.getRunTime() + "ms");
        total_tests += it.getRunCount();
        total_times += it.getRunTime();


        System.out.println("Esecuzione test EntrySetTester.class");
        Result est = JUnitCore.runClasses(EntrySetTester.class);
        if(mt.wasSuccessful()){
            System.out.println("Esecuzione completata con successo");    
        }else{
            System.out.println("Esecuzione completata senza successo");
            System.out.println("I metodi che hanno fallito sono" + est.getFailures().toString());
        }
        System.out.println("Sono stati eseguiti correttamente " + (est.getRunCount() - est.getFailureCount()) + " test su " + est.getRunCount() + " in un tempo di " + est.getRunTime() + "ms");
        total_tests += est.getRunCount();
        total_times += est.getRunTime();


        System.out.println("Esecuzione test KeySetTester.class");
        Result kst = JUnitCore.runClasses(KeySetTester.class);
        if(mt.wasSuccessful()){
            System.out.println("Esecuzione completata con successo");    
        }else{
            System.out.println("Esecuzione completata senza successo");
            System.out.println("I metodi che hanno fallito sono" + kst.getFailures().toString());
        }
        System.out.println("Sono stati eseguiti correttamente " + (kst.getRunCount() - kst.getFailureCount()) + " test su " + kst.getRunCount() + " in un tempo di " + kst.getRunTime() + "ms");
        total_tests += kst.getRunCount();
        total_times += kst.getRunTime();


        System.out.println("Esecuzione test ValueCollectionTester.class");
        Result vct = JUnitCore.runClasses(ValueCollectionTester.class);
        if(mt.wasSuccessful()){
            System.out.println("Esecuzione completata con successo");    
        }else{
            System.out.println("Esecuzione completata senza successo");
            System.out.println("I metodi che hanno fallito sono" + vct.getFailures().toString());
        }
        System.out.println("Sono stati eseguiti correttamente " + (vct.getRunCount() - vct.getFailureCount()) + " test su " + vct.getRunCount() + " in un tempo di " + vct.getRunTime() + "ms");
        total_tests += vct.getRunCount();
        total_times += vct.getRunTime();


        System.out.println("Esecuzione test ListTester.class");
        Result lt = JUnitCore.runClasses(ListTester.class);
        if(mt.wasSuccessful()){
            System.out.println("Esecuzione completata con successo");    
        }else{
            System.out.println("Esecuzione completata senza successo");
            System.out.println("I metodi che hanno fallito sono" + lt.getFailures().toString());
        }
        System.out.println("Sono stati eseguiti correttamente " + (lt.getRunCount() - lt.getFailureCount()) + " test su " + lt.getRunCount() + " in un tempo di " + lt.getRunTime() + "ms");
        total_tests += lt.getRunCount();
        total_times += lt.getRunTime();


        System.out.println("Esecuzione test LimitedListTester.class");
        Result llt = JUnitCore.runClasses(LimitedListTester.class);
        if(mt.wasSuccessful()){
            System.out.println("Esecuzione completata con successo");    
        }else{
            System.out.println("Esecuzione completata senza successo");
            System.out.println("I metodi che hanno fallito sono" + llt.getFailures().toString());
        }
        System.out.println("Sono stati eseguiti correttamente " + (llt.getRunCount() - llt.getFailureCount()) + " test su " + llt.getRunCount() + " in un tempo di " + llt.getRunTime() + "ms");
        total_tests += llt.getRunCount();
        total_times += llt.getRunTime();


        System.out.println("Esecuzione test ListIteratorTester.class");
        Result lit = JUnitCore.runClasses(ListIteratorTester.class);
        if(mt.wasSuccessful()){
            System.out.println("Esecuzione completata con successo");    
        }else{
            System.out.println("Esecuzione completata senza successo");
            System.out.println("I metodi che hanno fallito sono" + lit.getFailures().toString());
        }
        System.out.println("Sono stati eseguiti correttamente " + (lit.getRunCount() - lit.getFailureCount()) + " test su " + lit.getRunCount() + " in un tempo di " + lit.getRunTime() + "ms");
        total_tests += lit.getRunCount();
        total_times += lit.getRunTime();

        System.out.println("\n\nSono stati eseguiti un totale di " + total_tests + " in un tempo pari a " + total_times + "ms\n\n");

        System.out.println("Che la forza sia con TEst");

    }
}
