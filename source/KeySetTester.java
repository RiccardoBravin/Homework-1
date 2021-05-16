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


public class KeySetTester {
    @Test
    public void constructorTest() {
        MapAdapter map = new MapAdapter();
        HSet keySet = new MapAdapter().new KeySetAdapter(map);

        assertNotNull("il construttore crea un oggetto null", keySet);
        assertEquals("il costruttore costruisce un keySet di dimensioni diverse dalla mappa", 0, keySet.size());
    }

    @Test
    public void addTest() {
        MapAdapter map = new MapAdapter();
        HSet keySet = map.keySet();

        assertThrows("l'operazione non è supportata ma non viene lanciata un eccezione", UnsupportedOperationException.class, ()->{keySet.add(new Object());});
    }

    @Test
    public void addAllTest() {
        MapAdapter map = new MapAdapter();
        HSet keySet = map.keySet();

        assertThrows("l'operazione non è supportata ma non viene lanciata un eccezione", UnsupportedOperationException.class, ()->{keySet.addAll(new ListAdapter());});
    }

    @Test
    public void clearTest() {
        MapAdapter map = new MapAdapter();
        HSet keySet = map.keySet();
        Object key = new Object();
        Object value = new Object();

        keySet.clear();
        assertTrue("clear() non funziona su un keySet vuoto", keySet.size()==0 && keySet.size()==0);
        map.put(key, value);
        keySet.clear();
        assertTrue("clear() non funziona su un keySet non vuoto", keySet.size()==0 && keySet.size()==0);
    }

    @Test
    public void containsTest() {
        MapAdapter map = new MapAdapter();
        HSet keySet = map.keySet();
        Object key = new Object();
        Object key1 = new Object();
        Object value = new Object();
        
        assertThrows("contains(null) non lancia NullPointerexception", NullPointerException.class, ()->{keySet.contains(null);});
        assertFalse("contains(Object) trova qualcosa in un keySet vuoto", keySet.contains(key));
        map.put(key, value);
        map.put(new Object(), new Object());
        map.put(new Object(), new Object());
        assertFalse("contains(Object) trova una chiave non presente", keySet.contains(key1));
        assertTrue("contains(Object) non trova una chiave presente", keySet.contains(key));
    }

    @Test
    public void containsAllTest() {                                              
        MapAdapter map = new MapAdapter();
        HSet keySet = map.keySet();
        Object key = new Object();
        Object key1 = new Object();
        Object value = new Object();
        Object value1 = new Object();

        HCollection emptyCollection = new ListAdapter();
        HCollection collection = new ListAdapter();
        collection.add(key);
        collection.add(key1);
        
        assertThrows("containsAll(null) non lancia NullPointerexception", NullPointerException.class, ()->{keySet.containsAll(null);});

        assertTrue("containsAll(HCollection) a quanto pare l'insieme vuoto è contenuto nell'insime vuoto...", keySet.containsAll(emptyCollection));
        
        assertFalse("containsAll(HCollection) trova qualcosa con una collection non vuota su un keySet vuoto", keySet.containsAll(collection));
        
        map.put(key, value);
        assertTrue("containsAll(HCollection) non trova una collection vuota su un keySet non vuoto", keySet.containsAll(emptyCollection));
        
        assertFalse("containsAll(HCollection) trova una HCollection non vuota non presente su un keySet non vuoto", keySet.containsAll(collection));
        
        map.put(key1, value1);
        assertTrue("containsAll(HCollection) non trova una HCollection non vuota presente su un keySet non vuoto", keySet.containsAll(collection));
    }

    @Test
    public void isEmptyTest(){
        MapAdapter map = new MapAdapter();
        HSet keySet = map.keySet();
        Object key = new Object();
        Object value = new Object();

        assertTrue("isEmpty() restituisce false su un entrtyset vuoto", keySet.isEmpty());
        map.put(key, value);
        assertFalse("isEmpty() restituisce true su un entrtyset non vuoto", keySet.isEmpty());
    }

    @Test
    public void iteratorTest(){
        MapAdapter map = new MapAdapter();
        HSet keySet = map.keySet();
        Object key = new Object();
        Object value = new Object();

        assertNotNull("iterator() costruisce oggetto null su keySet vuoto", keySet.iterator());
        map.put(key, value);
        assertNotNull("iterator() costruisce oggetto null su keySet non vuoto", keySet.iterator());
    }

    @Test
    public void removeTest() {
        MapAdapter map = new MapAdapter();
        HSet keySet = map.keySet();
        Object key = new Object();
        Object value = new Object();
        Object key1 = new Object();
        Object value1 = new Object();

        assertThrows("remove(null) non lancia NullPointerException", NullPointerException.class, ()->{keySet.remove(null);});

        assertFalse("remove(Object) restituisce true su un keySet vuoto", keySet.remove(key));
        map.put(key, value);
        assertTrue("remove(Object) rimuove una chiave non presente", keySet.remove(key1)==false && keySet.size()==1);
        assertTrue("remove(Object) non rimuove una chiave presente", keySet.remove(key)==true && keySet.size()==0);
    }

    @Test
    public void removeAllTest() {
        MapAdapter map = new MapAdapter();
        HSet keySet = map.keySet();
        Object key =  new Object();
        Object key1 =  new Object();
        Object value = new Object();
        Object value1 = new Object();

        HCollection collection = new ListAdapter();
        
        assertThrows("removeAll(null) non lancia NullPointerException", NullPointerException.class, ()->{keySet.removeAll(null);});

        assertFalse("removeAll(HCollection) rimuove una collection vuota da un keySet vuoto", keySet.removeAll(collection));
        map.put(key, value);
        assertFalse("removeAll(HCollection) rimuove una collection vuota da un keySet non vuoto", keySet.removeAll(collection));

        collection.add(key1);
        collection.add(key);
        map.clear();
        assertFalse("removeAll(HCollection) rimuove una collection non vuota da un keySet vuoto", keySet.removeAll(collection));

        map.put(key, value);
        assertTrue("removeAll(HCollection) non rimuove una collection non vuota da un keySet non vuoto", keySet.removeAll(collection));
    }

    @Test
    public void retainAllTest() {                
        MapAdapter map = new MapAdapter();
        HSet keySet = map.keySet();
        Object key =  new Object();
        Object key1 =  new Object();
        Object value = new Object();
        Object value1 = new Object();

        HCollection collection = new ListAdapter();

        assertThrows("retainAll(null) non lancia NullPointerException", NullPointerException.class, ()->{keySet.retainAll(null);});

        assertFalse("retainAll(HCollection) rimuove qualcosa da un keySet vuoto con un HCollection vuota", keySet.retainAll(collection));

        collection.add(key1);
        collection.add(key);
        assertFalse("retainAll(HCollection) rimuove qualcosa da un keySet vuoto con un HCollection non vuota", keySet.retainAll(collection));

        map.put(key, value);
        map.put(key1, value1);
        collection.clear();
        assertTrue("retainAll(HCollection) non rimuove tutto da un keySet non vuoto con un HCollection vuota", keySet.retainAll(collection));
        assertEquals("retainAll(HCollection) non rimuove correttamente dal vuoto", 0, keySet.size());
        
        collection.add(key);
        collection.add(key1);
        map.put(key, value);
        map.put(key1, value1);
        map.put(1, 2);
        assertTrue("retainAll(HCollection) non rimuove correttamente", keySet.retainAll(collection));
        
        assertEquals("retainAll(HCollection) non rimuove correttamente", 2, keySet.size());
    }

    @Test
    public void sizeTest() {
        MapAdapter map = new MapAdapter();
        HSet keySet = map.keySet();
        Object key =  new Object();
        Object key1 =  new Object();
        Object value = new Object();
        Object value1 = new Object();

        assertEquals("size() non ritorna 0 su un keySet vuoto", 0, keySet.size());

        map.put(key, value);
        assertEquals("size() non ritorna 1 su un keySet con un elemento", 1, keySet.size());

        map.put(key1, value1);
        assertEquals("size() non ritorna 2 su un keySet con due elementi", 2, keySet.size());
    }

    @Test
    public void toArrayTest() {
        MapAdapter map = new MapAdapter();
        HSet keySet = map.keySet();
        Object key =  new Object();
        Object key1 =  new Object();
        Object value = new Object();
        Object value1 = new Object();

        Object[] emptyArr = new Object[0];

        assertArrayEquals("toArray() non funziona con keySet vuoti", emptyArr, keySet.toArray());
        
        map.put(key, value);
        map.put(key1, value1);
        assertTrue("toArray() non funziona con keySet non vuoti", keySet.toArray().length == 2);
    }

    @Test
    public void toArrayWithArrayTest() {
        MapAdapter map = new MapAdapter();
        HSet keySet = map.keySet();
        Object key =  new Object();
        Object key1 =  new Object();
        Object value = new Object();
        Object value1 = new Object();
        map.put(key, value);
        map.put(key1, value1);

        Object[] emptyArr = new Object[0];
        Object[] smallArr = new Object[1];
        Object[] correctArr = new Object[2];
        Object[] largeArr = new Object[3];

        assertEquals("toArray(Object[]) non funziona con un array vuoto", 2, keySet.toArray(emptyArr).length);
        assertEquals("toArray(Object[]) non funziona con un array più piccolo", 2, keySet.toArray(smallArr).length);
        assertEquals("toArray(Object[]) non funziona con un array di dimensioni corrette", 2, keySet.toArray(correctArr).length);
        assertEquals("toArray(Object[]) non funziona con un array più grande", 3, keySet.toArray(largeArr).length);
        assertThrows("toArray(null) non lancia NullPointerException", NullPointerException.class, ()->{keySet.toArray(null);});
    }
}
