import myAdapter.*;
import org.hamcrest.*;
import org.hamcrest.core.IsEqual;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.runner.*;
import org.junit.Test;

public class HashtableIteratorTester {
    @Test
    public void contructorTest() {
        Hashtable hasht = new Hashtable();

        assertNotNull("il construttore restituisce null con un hashtable vuoto", new HashtableIterator(hasht));
        hasht.put(new Object(), new Object());
        assertNotNull("il construttore restituisce null con un hashtable non vuoto", new HashtableIterator(hasht));
    }

    @Test
    public void hasNextTest() {
        Hashtable hasht = new Hashtable();

        assertFalse("hasNext restituisce true su una hashtable vuota", new HashtableIterator(hasht).hasNext());
        hasht.put(new Object(), new Object());
        HIterator iter = new HashtableIterator(hasht);
        assertTrue("hasNext restituisce false su una hashtable non vuota", iter.hasNext());
        iter.next();
        assertFalse("hasNext restituisce false al termine di una hashtable", iter.hasNext());
    }

    @Test 
    public void nextTest() {
        Hashtable hasht = new Hashtable();
        Object key1 =   new Object();
        Object key2 =   new Object();
        Object key3 =   new Object();
        Object value1 = new Object();
        Object value2 = new Object();
        Object value3 = new Object();
        hasht.put(key1, value1);
        hasht.put(key2, value2);
        hasht.put(key3, value3);
        HMap.HEntry entry1 = new MapAdapter().new EntryAdapter(key1, value1);
        HMap.HEntry entry2 = new MapAdapter().new EntryAdapter(key2, value2);
        HMap.HEntry entry3 = new MapAdapter().new EntryAdapter(key3, value3);

        HIterator iter = new HashtableIterator(hasht);

        HMap.HEntry aux = (HMap.HEntry)iter.next();
        assertTrue("next() non funziona1", aux.equals(entry1) || aux.equals(entry2) || aux.equals(entry3));
        aux = (HMap.HEntry)iter.next();
        assertTrue("next() non funziona2", aux.equals(entry1) || aux.equals(entry2) || aux.equals(entry3));
        aux = (HMap.HEntry)iter.next();
        assertTrue("next() non funziona3", aux.equals(entry1) || aux.equals(entry2) || aux.equals(entry3));
        assertThrows("next() non lancia NoSuchElementException al termine dell'iteratore", NoSuchElementException.class, ()->{iter.next();});
    }

    @Test
    public void removeTest(){
        Hashtable hasht = new Hashtable();
        Object key1 =   new Object();
        Object key2 =   new Object();
        Object key3 =   new Object();
        Object value1 = new Object();
        Object value2 = new Object();
        Object value3 = new Object();
        hasht.put(key1, value1);
        hasht.put(key2, value2);
        hasht.put(key3, value3);

        HIterator iter = new HashtableIterator(hasht);

        HMap.HEntry aux = (HMap.HEntry)iter.next();
        iter.remove();
        assertTrue("remove non funziona1", !hasht.containsKey(aux.getKey()));
        aux = (HMap.HEntry)iter.next();
        iter.remove();
        assertTrue("next() non funziona2", !hasht.containsKey(aux.getKey()));
        aux = (HMap.HEntry)iter.next();
        iter.remove();
        assertTrue("next() non funziona3", !hasht.containsKey(aux.getKey()));
    }
}
