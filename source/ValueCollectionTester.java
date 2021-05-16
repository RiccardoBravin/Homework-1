import myAdapter.*;
import org.hamcrest.*;
import org.hamcrest.core.IsEqual;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.runner.*;
import org.junit.Test;

public class ValueCollectionTester {
    @Test
    public void constructorTest(){
        Hashtable hashT = new Hashtable();
        HCollection valuec = new MapAdapter().new ValueCollection(hashT);

        assertNotNull("il costruttore restituisce null", valuec);
        assertThrows("ValueCollection(null) non lancia NullPointerException", NullPointerException.class, ()->{new MapAdapter().new ValueCollection(null);});
    }

    @Test
    public void addTest() {
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();

        assertThrows("l'operazione non è supportata ma non viene lanciata un'eccezione", UnsupportedOperationException.class, ()->{valuec.add(new Object());});
    }

    @Test
    public void addAllTest() {
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();

        assertThrows("l'operazione non è supportata ma non viene lanciata un'eccezione", UnsupportedOperationException.class, ()->{valuec.addAll(new ListAdapter());});
    }

    @Test
    public void clearTest() {
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();
        Object key = new Object();
        Object value = new Object();

        valuec.clear();
        assertTrue("clear() non funziona su un valuec vuoto", valuec.size()==0 && valuec.size()==0);
        map.put(key, value);
        valuec.clear();
        assertTrue("clear() non funziona su un valuec non vuoto", valuec.size()==0 && valuec.size()==0);
    }

    @Test
    public void containsTest() {
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();
        Object key = new Object();
        Object key1 = new Object();
        Object value = new Object();
        
        assertThrows("contains(null) non lancia NullPointerexception", NullPointerException.class, ()->{valuec.contains(null);});
        assertFalse("contains(Object) trova qualcosa in un valuec vuoto", valuec.contains(value));
        map.put(key, value);
        map.put(new Object(), new Object());
        map.put(new Object(), new Object());
        assertFalse("contains(Object) trova un valore non presente", valuec.contains(key1));
        assertTrue("contains(Object) non trova un valore presente", valuec.contains(value));
    }

    @Test
    public void containsAllTest() {                                              
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();
        Object key = new Object();
        Object key1 = new Object();
        Object value = new Object();
        Object value1 = new Object();

        HCollection emptyCollection = new ListAdapter();
        HCollection collection = new ListAdapter();
        collection.add(value);
        collection.add(value1);
        
        assertThrows("containsAll(null) non lancia NullPointerexception", NullPointerException.class, ()->{valuec.containsAll(null);});

        assertTrue("containsAll(HCollection) a quanto pare l'insieme vuoto è contenuto nell'insieme vuoto...", valuec.containsAll(emptyCollection));
        
        assertFalse("containsAll(HCollection) trova qualcosa con una collection non vuota su un valuec vuoto", valuec.containsAll(collection));
        
        map.put(key, value);
        assertTrue("containsAll(HCollection) non trova una collection vuota su un valuec non vuoto", valuec.containsAll(emptyCollection));
        
        assertFalse("containsAll(HCollection) trova una HCollection non vuota non presente su un valuec non vuoto", valuec.containsAll(collection));
        
        map.put(key1, value1);
        assertTrue("containsAll(HCollection) non trova una HCollection non vuota presente su un valuec non vuoto", valuec.containsAll(collection));
    }

    @Test
    public void equalsTest() {
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();
        HCollection list = new ListAdapter();
        Object key = new Object();
        Object value = new Object();
        Object value1 = new Object();

        assertFalse("equals(Object) restituisce true con un oggeto non istanza di HCollection", valuec.equals(new Object()));

        assertTrue("equals(Object) restituisce false fra una valueCollection vuota e un HCollection vuota", valuec.equals(list));

        list.add(value);
        assertFalse("equals(Object) restituisce true fra una valueCollection vuota e una HColletion non vuota", valuec.equals(list));

        list.clear();
        map.put(key, value);
        assertFalse("equals(Object) restituisce true fra una valueCollection non vuota e una HColletion vuota", valuec.equals(list));

        list.add(value1);
        assertFalse("equals(Object) restituisce true fra una valueCollection non vuota e una HColletion non vuota non contenente i valori di valueCollection", valuec.equals(list));

        list.clear();
        map.clear();
        list.add(value);
        map.put(key, value);
        assertTrue("equals(Object) restituisce false fra una valueCollection non vuota e una HColletion non vuota contenente i valori di valueCollection(1)", valuec.equals(list));
    }

    @Test
    public void hashCodeTest(){
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();
        Object key = 1;
        Object key1 = 'a';
        Object value = "sos";
        Object value1 = 3.5;

        assertEquals("hashCode non funziona (1)", 0, valuec.hashCode());
        
        map.put(key, value);
        assertEquals("hashCode non funziona (2)", 114071, valuec.hashCode());

        map.put(key1, value1);
        assertEquals("hashCode non funziona (3)", 1074642327, valuec.hashCode());
    }

    @Test
    public void isEmptyTest(){
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();
        Object key = new Object();
        Object value = new Object();

        assertTrue("isEmpty() restituisce false su un entrtyset vuoto", valuec.isEmpty());
        map.put(key, value);
        assertFalse("isEmpty() restituisce true su un entrtyset non vuoto", valuec.isEmpty());
    }

    @Test
    public void iteratorTest(){
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();
        Object key = new Object();
        Object value = new Object();

        assertNotNull("iterator() costruisce oggetto null su valuec vuoto", valuec.iterator());
        map.put(key, value);
        assertNotNull("iterator() costruisce oggetto null su valuec non vuoto", valuec.iterator());
    }

    @Test
    public void removeTest() {
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();
        Object key = new Object();
        Object value = new Object();
        Object value1 = new Object();

        assertFalse("remove(Object) restituisce true su un valuec vuoto", valuec.remove(value));
        map.put(key, value);
        assertTrue("remove(Object) rimuove una chiave non presente", valuec.remove(value1)==false && valuec.size()==1);
        assertTrue("remove(Object) non rimuove una chiave presente", valuec.remove(value)==true && valuec.size()==0);
    }

    @Test
    public void removeAllTest() {
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();
        Object key =  new Object();
        Object value = new Object();
        Object value1 = new Object();

        HCollection collection = new ListAdapter();
        
        assertThrows("removeAll(null) non lancia NullPointerException", NullPointerException.class, ()->{valuec.removeAll(null);});

        assertFalse("removeAll(HCollection) rimuove una collection vuota da un valuec vuoto", valuec.removeAll(collection));
        map.put(key, value);
        assertFalse("removeAll(HCollection) rimuove una collection vuota da un valuec non vuoto", valuec.removeAll(collection));

        collection.add(value1);
        collection.add(value);
        map.clear();
        assertFalse("removeAll(HCollection) rimuove una collection non vuota da un valuec vuoto", valuec.removeAll(collection));

        map.put(key, value);
        assertTrue("removeAll(HCollection) non rimuove una collection non vuota da un valuec non vuoto", valuec.removeAll(collection));
    }

    @Test
    public void retainAllTest() {                
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();
        Object key =  new Object();
        Object key1 =  new Object();
        Object value = new Object();
        Object value1 = new Object();

        HCollection collection = new ListAdapter();

        assertThrows("retainAll(null) non lancia NullPointerException", NullPointerException.class, ()->{valuec.retainAll(null);});

        assertFalse("retainAll(HCollection) rimuove qualcosa da un valuec vuoto con un HCollection vuota", valuec.retainAll(collection));

        collection.add(value1);
        collection.add(value);
        assertFalse("retainAll(HCollection) rimuove qualcosa da un valuec vuoto con un HCollection non vuota", valuec.retainAll(collection));

        map.put(key, value);
        map.put(key1, value1);
        collection.clear();
        assertTrue("retainAll(HCollection) non rimuove tutto da un valuec non vuoto con un HCollection vuota", valuec.retainAll(collection) && valuec.size()==0);
        
        collection.add(value);
        collection.add(value1);
        map.put(key, value);
        map.put(key1, value1);
        map.put(1, 2);
        assertTrue("retainAll(HCollection) non rimuove correttamente", valuec.retainAll(collection));
        
        assertEquals("retainAll(HCollection) non rimuove correttamente", 2, valuec.size());
    }

    @Test
    public void sizeTest() {
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();
        Object key =  new Object();
        Object key1 =  new Object();
        Object value = new Object();
        Object value1 = new Object();

        assertEquals("size() non ritorna 0 su un valuec vuoto", 0, valuec.size());

        map.put(key, value);
        assertEquals("size() non ritorna 1 su un valuec con un elemento", 1, valuec.size());

        map.put(key1, value1);
        assertEquals("size() non ritorna 2 su un valuec con due elementi", 2, valuec.size());
    }

    @Test
    public void toArrayTest() {
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();
        Object key =  new Object();
        Object key1 =  new Object();
        Object value = new Object();
        Object value1 = new Object();

        Object[] emptyArr = new Object[0];

        assertArrayEquals("toArray() non funziona con valuec vuoti", emptyArr, valuec.toArray());
        
        map.put(key, value);
        map.put(key1, value1);
        assertTrue("toArray() non funziona con valuec non vuoti", valuec.toArray().length == 2);
    }

    @Test
    public void toArrayWithArrayTest() {
        MapAdapter map = new MapAdapter();
        HCollection valuec = map.values();
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

        assertEquals("toArray(Object[]) non funziona con un array vuoto", 2, valuec.toArray(emptyArr).length);
        assertEquals("toArray(Object[]) non funziona con un array più piccolo", 2, valuec.toArray(smallArr).length);
        assertEquals("toArray(Object[]) non funziona con un array di dimensioni corrette", 2, valuec.toArray(correctArr).length);
        assertEquals("toArray(Object[]) non funziona con un array più grande", 3, valuec.toArray(largeArr).length);
        assertThrows("toArray(null) non lancia NullPointerException", NullPointerException.class, ()->{valuec.toArray(null);});
    }


}
