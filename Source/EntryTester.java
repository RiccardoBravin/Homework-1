//package myTest;
//Internal
import myAdapter.*;
import myAdapter.MapAdapter.EntryAdapter;
//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;
import org.junit.runner.*;
import org.junit.Test;


public class EntryTester {

    @Test
    public void constructorTest(){
        MapAdapter.EntryAdapter e1 = new MapAdapter().new EntryAdapter(null, null);
        assertNotNull("Il costruttore di entry crea oggetti nulli anche con parametri nulli", e1);

        MapAdapter.EntryAdapter e2 = new MapAdapter().new EntryAdapter("A", 1);
        assertNotNull("Il costruttore di entry crea oggetti nulli", e2);
    }

    @Test
    public void equalsTest(){
        MapAdapter.EntryAdapter e1 = new MapAdapter().new EntryAdapter(null, null);
        MapAdapter.EntryAdapter e2 = new MapAdapter().new EntryAdapter("A", 1);
        assertFalse("Due entry diverse sono ritenute uguali", e1.equals(e2));
        assertTrue("Una entry è diversa da se stessa", e1.equals(e1));

        e1 = new MapAdapter().new EntryAdapter("A", 1);
        assertTrue("Due entry uguali sono ritenute diverse", e1.equals(e2));

        assertFalse("Una entry e un oggetto non entry sono ritenuti uguali", e1.equals(null));
    }

    @Test
    public void getKeyTest(){
        MapAdapter.EntryAdapter e1 = new MapAdapter().new EntryAdapter(null, null);
        MapAdapter.EntryAdapter e2 = new MapAdapter().new EntryAdapter("A", 1);

        assertEquals("Il valore ritornato da getKey non è quello atteso (null)", null, e1.getKey());
        assertEquals("Il valore ritornato da getKey non è quello atteso (null)", "A", e2.getKey());
    }

    @Test
    public void getValueTest(){
        MapAdapter.EntryAdapter e1 = new MapAdapter().new EntryAdapter(null, null);
        MapAdapter.EntryAdapter e2 = new MapAdapter().new EntryAdapter("A", 1);

        assertEquals("Il valore ritornato da getKey non è quello atteso (null)", null, e1.getValue());
        assertEquals("Il valore ritornato da getKey non è quello atteso (null)", 1, e2.getValue());
    }


    @Test 
    public void setValueTest(){
        MapAdapter map1 = new MapAdapter();
        map1.put("A", 1);

        MapAdapter.EntryAdapter e1 = map1.new EntryAdapter("A", 1);
        e1.setValue(2);

        assertEquals("Il valore non è stato correttamente sostituito nella mappa", 2, map1.get("A"));
        assertEquals("Il valore non è stato correttamente sostituito nella entry", 2, e1.getValue());

        assertThrows("La entry non lancia NullPointerException quando viene tentata la sostituzione con valori nulli",NullPointerException.class, () ->{e1.setValue(null);});
        //System.out.println(map1);
    }


    
}
