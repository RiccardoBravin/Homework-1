import myAdapter.*;
import org.hamcrest.*;
import org.hamcrest.core.IsEqual;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import org.junit.runner.*;
import org.junit.Test;

public class EntrySetTester {
    @Test
    public void constructorTest() {
        MapAdapter map = new MapAdapter();
        HSet entryset = new MapAdapter().new EntrySetAdapter(map);

        assertNotNull("il construttore crea un oggetto null", entryset);
        assertEquals("il costruttore costruisce un entryset di dimensioni diverse dalla mappa", 0, entryset.size());
    }

    @Test
    public void addTest() {
        MapAdapter map = new MapAdapter();
        HSet entryset = map.entrySet();

        assertThrows("l'operazione non è supportata ma non viene lanciata un eccezione", UnsupportedOperationException.class, ()->{entryset.add(new Object());});
    }

    @Test
    public void addAllTest() {
        MapAdapter map = new MapAdapter();
        HSet entryset = map.entrySet();

        assertThrows("l'operazione non è supportata ma non viene lanciata un eccezione", UnsupportedOperationException.class, ()->{entryset.addAll(new ListAdapter());});
    }

    @Test
    public void clearTest() {
        MapAdapter map = new MapAdapter();
        HSet entryset = map.entrySet();
        Object key = new Object();
        Object value = new Object();

        entryset.clear();
        assertTrue("clear() non funziona su un entryset vuoto", entryset.size()==0 && entryset.size()==0);
        map.put(key, value);
        entryset.clear();
        assertTrue("clear() non funziona su un entryset non vuoto", entryset.size()==0 && entryset.size()==0);
    }

    @Test
    public void containsTest() {
        MapAdapter map = new MapAdapter();
        HSet entryset = map.entrySet();
        Object key = new Object();
        Object key1 = new Object();
        Object value = new Object();
        Object value1 = new Object();
        HMap.HEntry entry = new MapAdapter().new EntryAdapter(key, value);
        HMap.HEntry entry1 = new MapAdapter().new EntryAdapter(key, value1);
        HMap.HEntry entry2 = new MapAdapter().new EntryAdapter(key1, value);
        HMap.HEntry entry3 = new MapAdapter().new EntryAdapter(key1, value1);
        
        assertThrows("contains(null) non lancia NullPointerexception", NullPointerException.class, ()->{entryset.contains(null);});
        assertFalse("contains(Object) trova qualcosa in un entryset vuoto", entryset.contains(entry));
        map.put(key, value);
        map.put(new Object(), new Object());
        map.put(new Object(), new Object());
        assertFalse("contains(Object) trova una entry con stessa chiave e valore diverso", entryset.contains(entry1));
        assertFalse("contains(Object) trova una entry con chiave diversa e stesso valore", entryset.contains(entry2));
        assertFalse("contains(Object) trova una entry con chiave diversa e valore diverso", entryset.contains(entry3));
        assertTrue("contains(Object) non trova una entry presente", entryset.contains(entry));
    }

    @Test
    public void containsAllTest() {                                              
        MapAdapter map = new MapAdapter();
        HSet entryset = map.entrySet();
        Object key = new Object();
        Object key1 = new Object();
        Object value = new Object();
        Object value1 = new Object();
        HMap.HEntry entry = new MapAdapter().new EntryAdapter(key, value);
        HMap.HEntry entry3 = new MapAdapter().new EntryAdapter(key1, value1);

        HCollection emptyCollection = new ListAdapter();
        HCollection collection = new ListAdapter();
        collection.add(entry3);
        collection.add(entry);
        
        assertThrows("containsAll(null) non lancia NullPointerexception", NullPointerException.class, ()->{entryset.containsAll(null);});

        assertTrue("containsAll(HCollection) a quanto pare l'insieme vuoto è contenuto nell'insime vuoto...", entryset.containsAll(emptyCollection));
        
        assertFalse("containsAll(HCollection) trova qualcosa con una collection non vuota su un entryset vuoto", entryset.containsAll(collection));
        
        map.put(key, value);
        assertTrue("containsAll(HCollection) non trova una collection vuota su un entryset non vuoto", entryset.containsAll(emptyCollection));
        
        assertFalse("containsAll(HCollection) trova una HCollection non vuota non presente su un entryset non vuoto", entryset.containsAll(collection));
        
        map.put(key1, value1);
        assertTrue("containsAll(HCollection) non trova una HCollection non vuota presente su un entryset non vuoto", entryset.containsAll(collection));
    }

    @Test
    public void isEmptyTest(){
        MapAdapter map = new MapAdapter();
        HSet entryset = map.entrySet();
        Object key = new Object();
        Object value = new Object();

        assertTrue("isEmpty() restituisce false su un entrtyset vuoto", entryset.isEmpty());
        map.put(key, value);
        assertFalse("isEmpty() restituisce true su un entrtyset non vuoto", entryset.isEmpty());
    }

    @Test
    public void iteratorTest(){
        MapAdapter map = new MapAdapter();
        HSet entryset = map.entrySet();
        Object key = new Object();
        Object value = new Object();

        assertNotNull("iterator() costruisce oggetto null su entryset vuoto", entryset.iterator());
        map.put(key, value);
        assertNotNull("iterator() costruisce oggetto null su entryset non vuoto", entryset.iterator());
    }

    @Test
    public void removeTest() {
        MapAdapter map = new MapAdapter();
        HSet entryset = map.entrySet();
        Object key = new Object();
        Object value = new Object();
        Object key1 = new Object();
        Object value1 = new Object();
        HMap.HEntry entry = new MapAdapter().new EntryAdapter(key, value);
        HMap.HEntry entry1 = new MapAdapter().new EntryAdapter(key1, value1);

        assertThrows("remove(null) non lancia NullPointerException", NullPointerException.class, ()->{entryset.remove(null);});
        assertThrows("remove(non una HEntry) non lancia ClassCastException", ClassCastException.class, ()->{entryset.remove(new ListAdapter());});

        assertFalse("remove(Object) restituisce true su un entryset vuoto", entryset.remove(entry));
        map.put(key, value);
        assertTrue("remove(Object) rimuove una entry non presente", entryset.remove(entry1)==false && entryset.size()==1);
        assertTrue("remove(Object) non rimuove una entry presente", entryset.remove(entry)==true && entryset.size()==0);
    }

    @Test
    public void removeAllTest() {
        MapAdapter map = new MapAdapter();
        HSet entryset = map.entrySet();
        Object key =  new Object();
        Object key1 =  new Object();
        Object value = new Object();
        Object value1 = new Object();
        HMap.HEntry entry = new MapAdapter().new EntryAdapter(key, value);
        HMap.HEntry entry3 = new MapAdapter().new EntryAdapter(key1, value1);

        HCollection collection = new ListAdapter();
        
        assertThrows("removeAll(null) non lancia NullPointerException", NullPointerException.class, ()->{entryset.removeAll(null);});

        assertFalse("removeAll(HCollection) rimuove una collection vuota da un entryset vuoto", entryset.removeAll(collection));
        map.put(key, value);
        assertFalse("removeAll(HCollection) rimuove una collection vuota da un entryset non vuoto", entryset.removeAll(collection));

        collection.add(entry3);
        collection.add(entry);
        map.clear();
        assertFalse("removeAll(HCollection) rimuove una collection non vuota da un entryset vuoto", entryset.removeAll(collection));

        map.put(key, value);
        assertTrue("removeAll(HCollection) non rimuove una collection non vuota da un entryset non vuoto", entryset.removeAll(collection));
    }

    @Test
    public void retainAllTest() {                
        MapAdapter map = new MapAdapter();
        HSet entryset = map.entrySet();
        Object key =  new Object();
        Object key1 =  new Object();
        Object value = new Object();
        Object value1 = new Object();
        HMap.HEntry entry = new MapAdapter().new EntryAdapter(key, value);
        HMap.HEntry entry3 = new MapAdapter().new EntryAdapter(key1, value1);

        HCollection collection = new ListAdapter();

        assertThrows("retainAll(null) non lancia NullPointerException", NullPointerException.class, ()->{entryset.retainAll(null);});

        assertFalse("retainAll(HCollection) rimuove qualcosa da un entryset vuoto con un HCollection vuota", entryset.retainAll(collection));

        collection.add(entry3);
        collection.add(entry);
        assertFalse("retainAll(HCollection) rimuove qualcosa da un entryset vuoto con un HCollection non vuota", entryset.retainAll(collection));

        map.put(key, value);
        map.put(key1, value1);
        collection.clear();
        assertTrue("retainAll(HCollection) non rimuove tutto da un entryset non vuoto con un HCollection vuota", entryset.retainAll(collection) && entryset.size()==0);
        
        collection.add(entry);
        collection.add(entry3);
        map.put(key, value);
        map.put(key1, value1);
        map.put(1, 2);
        assertTrue("retainAll(HCollection) non rimuove correttamente", entryset.retainAll(collection));
        
        assertEquals("retainAll(HCollection) non rimuove correttamente", 2, entryset.size());
    }

    @Test
    public void sizeTest() {
        MapAdapter map = new MapAdapter();
        HSet entryset = map.entrySet();
        Object key =  new Object();
        Object key1 =  new Object();
        Object value = new Object();
        Object value1 = new Object();

        assertEquals("size() non ritorna 0 su un entryset vuoto", 0, entryset.size());

        map.put(key, value);
        assertEquals("size() non ritorna 1 su un entryset con un elemento", 1, entryset.size());

        map.put(key1, value1);
        assertEquals("size() non ritorna 2 su un entryset con due elementi", 2, entryset.size());
    }

    @Test
    public void toArrayTest() {
        MapAdapter map = new MapAdapter();
        HSet entryset = map.entrySet();
        Object key =  new Object();
        Object key1 =  new Object();
        Object value = new Object();
        Object value1 = new Object();

        Object[] emptyArr = new Object[0];

        assertArrayEquals("toArray() non funziona con entryset vuoti", emptyArr, entryset.toArray());
        
        map.put(key, value);
        map.put(key1, value1);
        assertTrue("toArray() non funziona con entryset non vuoti", entryset.toArray().length == 2);
    }

    @Test
    public void toArrayWithArrayTest() {
        MapAdapter map = new MapAdapter();
        HSet entryset = map.entrySet();
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

        assertEquals("toArray(Object[]) non funziona con un array vuoto", 2, entryset.toArray(emptyArr).length);
        assertEquals("toArray(Object[]) non funziona con un array più piccolo", 2, entryset.toArray(smallArr).length);
        assertEquals("toArray(Object[]) non funziona con un array di dimensioni corrette", 2, entryset.toArray(correctArr).length);
        assertEquals("toArray(Object[]) non funziona con un array più grande", 3, entryset.toArray(largeArr).length);
        assertThrows("toArray(null) non lancia NullPointerException", NullPointerException.class, ()->{entryset.toArray(null);});
    }
}
