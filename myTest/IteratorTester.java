//package myTest;

//Adapter
import myAdapter.IteratorAdapter;
//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.runner.*;
import org.junit.Test;

public class IteratorTester {
    
    @Test
    public void constructorTest(){
        MapAdapter map1 = new MapAdapter();
        map1.put("A", 1);
        map1.put("B", 2);
        map1.put("C", 3);
        map1.put("D", 4);
        map1.put("E", 5);
        HSet keys = map1.keySet();
        HSet entrys = map1.entrySet();
        HCollection vals = map1.values();
        
        HIterator i1 = null;

        try{
            i1 = new IteratorAdapter(null);
            assertTrue("L'iteratore senza indice è stato inizializzato con null senza lanciare eccezioni", false);
        }catch(NullPointerException npe){
            assertNull("L'iteratore senza indice è stato inizializzato pur avendo lanciato un eccezione", i1);
        }

        try{
            i1 = new IteratorAdapter(null, 0);
            assertTrue("L'iteratore con indice è stato inizializzato con null senza lanciare eccezioni", false);
        }catch(NullPointerException npe){
            assertNull("L'iteratore con indice è stato inizializzato pur avendo lanciato un eccezione", i1);
        }

        try{
            i1 = new IteratorAdapter(keys, -1);
            assertTrue("L'iteratore è stato inizializzato con un indice non valido (-1) senza lanciare eccezioni", false);
        }catch(IndexOutOfBoundsException npe){
            assertNull("L'iteratore è stato inizializzato con un indice non valido (-1) pur avendo lanciato un eccezione", i1);
        }

        try{
            i1 = new IteratorAdapter(keys, 5);
            assertTrue("L'iteratore è stato inizializzato con un indice non valido (5) senza lanciare eccezioni", false);
        }catch(IndexOutOfBoundsException npe){
            assertNull("L'iteratore è stato inizializzato con un indice non valido (5) pur avendo lanciato un eccezione", i1);
        }


        IteratorAdapter ik = new IteratorAdapter(keys);
        IteratorAdapter ie = new IteratorAdapter(entrys,2);
        IteratorAdapter iv = new IteratorAdapter(vals, 4);

        assertNotNull("Il costruttore dell'iteratore senza indice ritorna oggetti nulli", ik);
        assertNotNull("Il costruttore dell'iteratore con indice ritorna oggetti nulli", ie);
        assertNotNull("Il costruttore dell'iteratore con indice ritorna oggetti nulli", iv);
    }

    @Test
    public void hasNextTest(){
        MapAdapter map1 = new MapAdapter();
        
        HSet keys = map1.keySet();
        HSet entrys = map1.entrySet();
        HCollection vals = map1.values();

        HIterator ik = new IteratorAdapter(keys);
        HIterator ie = new IteratorAdapter(entrys);
        HIterator iv = new IteratorAdapter(vals);

        assertFalse("L'iteratore di chiavi su set vuoto ritorna vero su hasNext", ik.hasNext());
        assertFalse("L'iteratore di entry su set vuoto ritorna vero su hasNext", ie.hasNext());
        assertFalse("L'iteratore di valori su collection vuota ritorna vero su hasNext", iv.hasNext());


        map1.put("A", 1);
        map1.put("B", 2);
        map1.put("C", 3);
        map1.put("D", 4);
        map1.put("E", 5);

        ik = map1.keySet().iterator();
        ie = map1.entrySet().iterator();
        iv = map1.values().iterator();

        assertTrue("L'iteratore di chiavi in prima posizione su set pieno ritorna falso su hasNext", ik.hasNext());
        assertTrue("L'iteratore di entry in prima posizione su set pieno ritorna falso su hasNext", ie.hasNext());
        assertTrue("L'iteratore di valori in prima posizione su collection piena ritorna falso su hasNext", iv.hasNext());


        keys = map1.keySet();
        entrys = map1.entrySet();
        vals = map1.values();

        ik = new IteratorAdapter(keys,4);
        ie = new IteratorAdapter(entrys,4);
        iv = new IteratorAdapter(vals,4);

        ik.next();
        ie.next();
        iv.next();    
    
        assertFalse("L'iteratore di chiavi in ultima posizione su set pieno ritorna vero su hasNext", ik.hasNext());
        assertFalse("L'iteratore di entry in ultima posizione su set pieno ritorna vero su hasNext", ie.hasNext());
        assertFalse("L'iteratore di valori in ultima posizione su collection piena ritorna vero su hasNext", iv.hasNext());

    }

    @Test
    public void nextTest(){
        MapAdapter map1 = new MapAdapter();
        
        HSet keys = map1.keySet();
        HSet entrys = map1.entrySet();
        HCollection vals = map1.values();

        HIterator ik = new IteratorAdapter(keys);
        HIterator ie = new IteratorAdapter(entrys);
        HIterator iv = new IteratorAdapter(vals);

        assertThrows("L'iteratore di chiavi su set vuoto non lancia NoSuchElementException con next ", NoSuchElementException.class, () -> {ik.next();});
        assertThrows("L'iteratore di entry su set vuoto non lancia NoSuchElementException con next ", NoSuchElementException.class, () -> {ie.next();});
        assertThrows("L'iteratore di valori su collection vuota non lancia NoSuchElementException con next ", NoSuchElementException.class, () -> {iv.next();});


        map1.put("A", 1);
        map1.put("B", 2);
        map1.put("C", 3);
        map1.put("D", 4);
        map1.put("E", 5);

        keys = map1.keySet();
        entrys = map1.entrySet();
        vals = map1.values();

        ik = new IteratorAdapter(keys);
        ie = new IteratorAdapter(entrys);
        iv = new IteratorAdapter(vals);

        System.out.println(map1);
    }

}
