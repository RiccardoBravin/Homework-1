//package myTest;

//Adapter
import myAdapter.MapAdapter;
//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;
import org.junit.runner.*;
import org.junit.Test;



public class MapTester {
    
    @Test
    public void constructorTester(){
        MapAdapter map1 = new MapAdapter();
        assertNotNull("Il costruttore di default di map ritorna un oggetto null", map1);
        
        MapAdapter map2 =  new MapAdapter(5);
        assertNotNull("Il costruttore di default di map ritorna un oggetto null", map2);

        assertEquals("Due mappe appena create ed identiche sono diverse", map1, map2);
    }    

    @Test
    public void copyConstructorTest(){
        //TODO
        assertTrue(true);
    }

    @Test
    public void putTest(){
        
        MapAdapter map1 = new MapAdapter();


        map1.put("A", 1); //primo inserimento

        assertEquals("La mappa non è della dimensione corretta dopo gli inserimenti", 1, map1.size());
        
        assertTrue("La mappa in realtà sembra non contenere effettivamente 1", map1.containsValue(1));
        assertFalse("La mappa in realtà sembra contenere A quando era stata inserita come chiave", map1.containsValue("A"));

        assertTrue("La mappa in realtà sembra non contenere effettivamente A come chiave", map1.containsKey("A"));
        assertFalse("La mappa in realtà sembra contenere 1 come chiave quando era stata inserito come elemento", map1.containsKey(1));


        map1.put("A", 2); //inserimento con chiave uguale
        
        assertEquals("La mappa non è della dimensione corretta dopo gli inserimenti", 1, map1.size());

        assertTrue("La mappa in realtà sembra non contenere effettivamente 2", map1.containsValue(2));
        assertFalse("La mappa in realtà sembra contenere A quando era stata inserita come chiave", map1.containsValue("A"));

        assertTrue("La mappa in realtà sembra non contenere effettivamente A come chiave", map1.containsKey("A"));
        assertFalse("La mappa in realtà sembra contenere 2 come chiave quando era stata inserito come elemento", map1.containsKey(2));


        map1.put("B", 2); // secondo inserimento

        assertEquals("La mappa non è della dimensione corretta dopo gli inserimenti", 2, map1.size());

        assertTrue("La mappa non contiene il valore 2 inserito due volte", map1.containsValue(2));
        assertEquals(2, map1.size());




        try{
            map1.put(null, 100);
            assertTrue("map non ha lanciato eccezioni quando ha ricevuto chiave nulla", false);
        }catch (NullPointerException npe){
            assertFalse("È stato trovato il valore 100 pur essendo stata lanciata l'eccezione per chiave nulla", map1.containsValue(100));
        }

        try{
            map1.put("Z", null);
            assertTrue("map non ha lanciato eccezioni quando ha ricevuto valore nullo", false);
        }catch (NullPointerException npe){
            assertFalse("È stato trovata la chiave Z pur essendo stata lanciata l'eccezione per valore nullo", map1.containsKey("Z"));
        }

        map1.put(1.5, "C");
        assertTrue("L'inserimento di valori di tipo diverso non funziona", map1.containsValue("C"));
        assertTrue("L'inserimento di chiavi di tipo diverso non funziona", map1.containsKey(1.5));
        
    }

    @Test
    public void sizeTest(){
        MapAdapter map1 = new MapAdapter();

        assertEquals("La dimensione della mappa non corrisponde a quella attesa", 0, map1.size());
        map1.put(1, 1);
        assertEquals("La dimensione della mappa non corrisponde a quella attesa", 1, map1.size());
        map1.put(2, 2);
        assertEquals("La dimensione della mappa non corrisponde a quella attesa", 2, map1.size());
        map1.put(3, 3);
        assertEquals("La dimensione della mappa non corrisponde a quella attesa", 3, map1.size());


    }


    @Test
    public void clearTest(){
        MapAdapter map1 = new MapAdapter(5);

        map1.put(1, 1);
        map1.put(2, 2);
        map1.put(3, 3);
        map1.put(4, 4);
        map1.put(5, 5);

        assertEquals("La mappa non è della dimensione corretta dopo gli inserimenti", 5, map1.size());

        map1.clear();

        assertEquals("La mappa non è della dimensione corretta dopo il clear", 0, map1.size());
    }

    @Test
    public void containsKeyTest(){
        MapAdapter map1 = new MapAdapter(5);

        map1.put("A", 1);
        map1.put("B", 2);
        map1.put("C", 3);
        map1.put("D", 4);
        map1.put(5, "E");

        assertTrue("La chiave A non è stata trovata", map1.containsKey("A"));
        assertTrue("La chiave 5 non è stata trovata", map1.containsKey(5));
        assertFalse("Trovata una chiave non esistente", map1.containsKey("b"));
        assertFalse("Trovata una chiave non esistente", map1.containsKey("E"));
        assertFalse("Trovata una chiave non esistente", map1.containsKey("100"));

        map1.clear();

        assertFalse("La chiave A è stata trovata nonostante la mappa fosse vuota", map1.containsKey("A"));

        map1.put("A", 1);

        assertTrue("La chiave A non è stata trovata", map1.containsKey("A"));

    }

    @Test
    public void containsValueTest(){
        MapAdapter map1 = new MapAdapter(5);

        map1.put("A", 1);
        map1.put("B", 2);
        map1.put("C", 3);
        map1.put("D", 4);
        map1.put(5, "E");

        assertEquals("La mappa non è della dimensione corretta", 5, map1.size());

        assertTrue("Il valore 1 non è stato trovato", map1.containsValue(1));
        assertTrue("Il valore E non è stato trovato", map1.containsValue("E"));
        assertFalse("Trovato un valore non esistente", map1.containsValue("1"));
        assertFalse("Trovato un valore non esistente", map1.containsValue(5));
        assertFalse("Trovato un valore non esistente", map1.containsValue(100));


        map1.clear();
        assertEquals("La mappa non è della dimensione corretta", 0, map1.size());
        assertFalse("Il valore 1 è stato trovato nonostante la mappa fosse vuota", map1.containsValue(1));

        map1.put("A", 1);
        assertEquals("La mappa non è della dimensione corretta", 1, map1.size());
        assertTrue("Il valore 1 non è stato trovato", map1.containsValue(1));
    }


    
    @Test
    public void entrySetTest(){

    }

    @Test
    public void hashTest(){
        MapAdapter map1 = new MapAdapter();

        map1.put("A", 1);
        assertEquals("La funzione hash non ritorna il valore atteso", 1, map1.hashCode());

        map1.put("B", 2);
        map1.put("C", 3);
        map1.put("D", 4);
        map1.put(5, "E");
    }


    
}
