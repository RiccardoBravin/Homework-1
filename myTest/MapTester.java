import myAdapter.*;
import org.hamcrest.*;
import org.hamcrest.core.IsEqual;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.plaf.BorderUIResource.EmptyBorderUIResource;

import org.junit.runner.*;
import org.junit.Test;

public class MapTester {
    @Test
    public void constructorTest() {
        assertNotNull("Il costruttore ritorna null", new MapAdapter());
    }

    @Test
    public void constructorWithInitialCapacityTest() {
        assertNotNull("Il costruttore con InitialCapacity ritorna null", new MapAdapter(10));
        assertThrows("il costruttore con map non lancia IllegalArgumentException con initialCapacity < 0", NullPointerException.class, ()->{new MapAdapter(null);});
    }

    
    //prima deve funzionare putAll
    @Test
    public void constructorWithMapTest() {
        HMap map = new MapAdapter();

        assertNotNull("Il costruttore con map ritorna null", new MapAdapter(map));
        
        assertThrows("il costruttore con map non lancia NullPointerException", NullPointerException.class, ()->{new MapAdapter(null);});
    }

    @Test
    public void clearTest() {
        MapAdapter map = new MapAdapter();

        map.clear();
        assertTrue("clear() non pulisce una mappa vuota", map.size()==0);

        map.put(new Object(), new Object());
        map.clear();
        assertTrue("clear() non pulisce una mappa non vuota", map.size()==0);
    }

    @Test
    public void containsKeyTest() {
        MapAdapter map = new MapAdapter();
        Object key = new Object();
        Object value = new Object();
        Object notContained = new Object();
        
        assertFalse("containsKey(Object) trova una chiave su una mappa vuota", map.containsKey(key));

        map.put(key, value);
        assertFalse("containsKey(Object) trova una chiave non contenuta in una mappa non vuota", map.containsKey(notContained));        
        
        assertTrue("containsKey(Object) non trova una chiave presente in una mappa non vuota", map.containsKey(key));

        assertThrows("containsKey(null) non lancia NullPointerException", NullPointerException.class, ()->{map.containsKey(null);});
    }

    @Test
    public void containsValuesTest() {
        MapAdapter map = new MapAdapter();
        Object key = new Object();
        Object value = new Object();
        Object notContained = new Object();
        
        assertFalse("containsValue(Object) trova un valore su una mappa vuota", map.containsValue(value));

        map.put(key, value);
        assertFalse("containsValue(Object) trova un valore non contenuto in una mappa non vuota", map.containsValue(notContained));        
        
        assertTrue("containsValue(Object) non trova un valore presente in una mappa nonvuota", map.containsValue(value));

        assertThrows("containsValue(null) non lancia NullPointerException", NullPointerException.class, ()->{map.containsValue(null);});
    }

    @Test
    public void entrySetTest() {
        MapAdapter map = new MapAdapter();
        Object key = new Object();
        Object value = new Object();

        map.put(key, value);
        HSet set = map.entrySet();
        assertNotNull("entrySet() crea un oggetto null", set);
        assertEquals("entrySet() crea un set didimensioni diverse da quelle della mappa", map.size(), set.size());
    }

    @Test
    public void getTest(){
        MapAdapter map = new MapAdapter();
        Object key = new Object();
        Object value = new Object();
        Object notContained = new Object();

        assertNull("get(Object) restituisce qualcosa da una mappa vuota", map.get(key));

        map.put(key, value);
        assertNull("get(Object) restituisce !=null con una chiave non presente in una mappa non vuota", map.get(notContained));
        
        assertEquals("get(Object) non restituisce un valore presente", value, map.get(key));

        assertThrows("get(null) non lancia NullPointerException", NullPointerException.class, ()-> {map.get(null);});
    }

    @Test
    public void isEmptyTest() {
        MapAdapter map = new MapAdapter();
        Object key = new Object();
        Object value = new Object();

        assertTrue("isEmpty() non restituisce true su una mappa vuota", map.isEmpty());

        map.put(key, value);
        assertFalse("isEmpty() non restituisce false su una mappa non vuota", map.isEmpty());
    }

    @Test
    public void KeySetTest() {
        MapAdapter map = new MapAdapter();
        Object key = new Object();
        Object value = new Object();
        map.put(key, value);

        HSet set = map.keySet();
        assertNotNull("keySet() restituisce un oggetto null", set);
        assertEquals("keySet() crea un set con dimensione diversa da quella della mappa", map.size(), set.size());
    }

    @Test
    public void putTest() {
        MapAdapter map = new MapAdapter();
        Object key = new Object();
        Object value = new Object();
        Object value1 = new Object();

        assertTrue("put(Object, Object) non funziona su una mappa vuota", map.put(key, value)==null && map.size()==1);
        assertTrue("put(Object, Object) non funziona su una mappa non vuota", map.put(key, value1).equals(value) && map.size()==1);

        assertThrows("put(null,  object) non lancia NullPointerException", NullPointerException.class, ()->{map.put(null, value);});
        assertThrows("put(object,  null) non lancia NullPointerException", NullPointerException.class, ()->{map.put(key, null);});
    }

    @Test
    public void putAllTest() {                      //debugga entry set prima
        MapAdapter map1 = new MapAdapter();
        MapAdapter map2 = new MapAdapter();
        MapAdapter map3 = new MapAdapter();
        Object key1 = new Object();
        Object value1 = new Object();
        Object key2 = new Object();
        Object value2 = new Object();

        assertThrows("putAll(null) non lancia NullPointerException", NullPointerException.class, ()->{map1.putAll(null);});
        
        map1.putAll(map2);
        assertEquals("putAll(HMap) non funziona con una mappa vuota su una mappa vuota", 0, map1.size());

        map1.put(key1, value1);
        map2.putAll(map1);
        assertEquals("putAll(HMap) non funziona con una mappa non vuota su una mappa vuota", value1, map2.get(key1));

        map3.put(key2, value2);
        map2.putAll(map3);
        assertEquals("putAll(HMap) non funziona con una mappa non vuota su una mappa non vuota", value2, map2.get(key2));
    }

    @Test
    public void removeTest() {
        MapAdapter map = new MapAdapter();
        Object key = new Object();
        Object value = new Object();
        Object notContained = new Object();

        assertThrows("remove(null) non lancia NullPointerException", NullPointerException.class, ()->{map.remove(null);});

        assertNull("remove(Object) non restituisce null su una mappa vuota", map.remove(key));
        
        map.put(key, value);
        assertNull("remove(Object) non restituisce null per una chiave non presente", map.remove(notContained));

        assertTrue("remove(Object) non rimuove o non restituice l'oggetto rimosso", map.remove(key).equals(value) && map.size()==0);
    }

    @Test
    public void sizeTest() {
        MapAdapter map = new MapAdapter();
        Object key = new Object();
        Object value = new Object();

        assertEquals("size() no restituisce 0 su una mappa vuota", 0, map.size());

        map.put(key, value);
        assertEquals("size() no restituisce la dimensione corretta", 1, map.size());
    }

    @Test
    public void valuesTest(){
        MapAdapter map = new MapAdapter();
        Object key = new Object();
        Object value = new Object();
        map.put(key, value);

        HCollection coll = map.values();
        assertNotNull("values() restituisce un oggetto null", coll);
        assertEquals("HCollection creata non ha la stessa dimensione della mappa", map.size(), coll.size());
    }
}

