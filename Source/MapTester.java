package myTest;
//Internal
import myAdapter.*;
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

        assertTrue("La mappa appena creata non è vuota", map1.isEmpty());
        assertTrue("La mappa appena creata non è vuota", map2.isEmpty());
        assertEquals("Due mappe appena create ed identiche sono diverse", map1, map2);
    }    

    @Test
    public void copyConstructorTest(){
        MapAdapter map1 = new MapAdapter();
        
        map1.put("A", 1);
        map1.put("B", 2);
        map1.put("C", 3);
        map1.put("D", 4);
        map1.put("E", 5);
        
        MapAdapter map2 = null;

        try{
            map2 = new MapAdapter(null);
            assertTrue("La mappa è stata inizializzata con una mappa null senza lanciare eccezioni", false);
        }catch(NullPointerException npe){
            assertNull("La mappa è stata inizializzata pur avendo lanciato un eccezione",map2);
        }

        map2 =  new MapAdapter(map1);

        assertEquals("La mappa costruitda con map1 non è identica", map1, map2);
        
        assertThrows("Il costruttore con mappa nulla non lancia NullPointerException", NullPointerException.class, () -> {new MapAdapter(null);});
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
        MapAdapter map1 = new MapAdapter();

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
        MapAdapter map1 = new MapAdapter();
        assertNotNull("Il set ritornato da entrySet è null", map1.entrySet());

        //ulteriori test saranno effettuati nel test della classe entryset
    }

    @Test
    public void equalsTest(){
        MapAdapter map1 = new MapAdapter();
        MapAdapter map2 = new MapAdapter();

        assertEquals("Le due mappe risultano diverse quando sono entrambe vuote", map1, map2);

        map1.put("A", 1);
        map2.put("A", 1);
        assertEquals("Le due mappe risultano diverse pur contenendo gli stessi elementi", map1, map2);

        map1.put("B", 2);
        assertNotEquals("Le due mappe risultano uguali pur contenendo elementi diversi", map1, map2);

        assertNotEquals("La mappa risulta uguale a qualcosa che non implementa HMap", map1, new ListAdapter());
    }


    @Test
    public void getTest(){
        MapAdapter map1 = new MapAdapter();
        assertThrows("La ricerca di una chiave nulla non ritorna NullPointerException", NullPointerException.class, () -> {map1.get(null);});
        assertNull("get torna null se non viene trovato l'oggetto riferito alla chiave", map1.get("A"));

        map1.put("A",1);
        assertEquals("get non trova l'elemento appena inserito (A,1)", 1, map1.get("A"));

        map1.put("B", 2);
        assertEquals("get non trova l'elemento appena inserito (B,2)", 2, map1.get("B"));
        assertEquals("get non trova l'elemento inserito precedentemente (A,1)", 1, map1.get("A"));

        map1.remove("A");
        assertNull("get trova il riferimento ad una chiave eliminata", map1.get("A"));

    }

    @Test
    public void hashTest(){
        MapAdapter map1 = new MapAdapter();

        map1.put("A", 1);
        assertEquals("La funzione hash non ritorna il valore atteso (64)", 64, map1.hashCode());

        map1.put("B", 2);
        assertEquals("La funzione hash non ritorna il valore atteso (128)", 128, map1.hashCode());

        map1.put(5, "E");
        assertEquals("La funzione hash non ritorna il valore atteso (192)", 192, map1.hashCode());

    }


    @Test
    public void isEmptyTest(){
        MapAdapter map1 = new MapAdapter();

        assertTrue("La mappa appena creata non ha dimensione 0", map1.isEmpty());

        map1.put("key", "value");
        assertFalse("La mappa con un valore non ha dimensione diversa da 0", map1.isEmpty());

        map1.clear();
        assertTrue("La mappa appena svuotata non ha dimensione 0", map1.isEmpty());

    }


    @Test
    public void keySetTest(){
        MapAdapter map1 = new MapAdapter();
        assertNotNull("Il set ritornato da keySet è null", map1.keySet());

        //ulteriori test saranno effettuati nel test della classe keyset
        
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



        assertThrows("map non ha lanciato eccezioni quando ha ricevuto chiave nulla", NullPointerException.class, () -> {map1.put(null,100);});
        assertFalse("È stato trovato il valore 100 pur essendo stata lanciata l'eccezione per chiave nulla", map1.containsValue(100));

        assertThrows("map non ha lanciato eccezioni quando ha ricevuto valore nullo", NullPointerException.class, () -> {map1.put("Z",null);});
        assertFalse("È stato trovata la chiave Z pur essendo stata lanciata l'eccezione per valore nullo", map1.containsKey("Z"));

        map1.put(1.5, "C");
        assertTrue("L'inserimento di valori di tipo diverso non funziona", map1.containsValue("C"));
        assertTrue("L'inserimento di chiavi di tipo diverso non funziona", map1.containsKey(1.5));
        
    }

    @Test
    public void putAllTest(){
        MapAdapter map1 = new MapAdapter();
        assertThrows("putAll non lancia NullPointerException quando viene passata una HMap nulla", NullPointerException.class, () -> {map1.putAll(null);});
        
        MapAdapter map2 = new MapAdapter();
        map1.put("A", 1);
        map2.put("A", 1);
        map1.putAll(map1);
        assertEquals("La mappa 1 e 2 non sono uguali dopo l'inserimento di entry già presenti", map2, map1);

        map2.clear();
        map1.putAll(map2);
        assertEquals("La mappa non rimane della stessa dimensione dopo l'aggiunta di una mappa vuota", 1, map1.size());
        assertEquals("Il contenuto della mappa non rimane lo stesso dopo l'aggiunta di una mappa vuota", 1, map1.get("A"));

        map2.put("B", 2);
        map1.putAll(map2);
        assertEquals("La dimensione della mappa è diversa da quella attesa dopo l'aggiunta di una mappa con entry diverse ", 2, map1.size());
        assertEquals("Il contenuto della mappa non è rimasto lo stesso dopo l'aggiunta di una mappa vuota", 1, map1.get("A"));
        assertEquals("Il contenuto della mappa non è rimasto lo stesso dopo l'aggiunta di una mappa vuota", 2, map1.get("B"));
        

    }


    @Test
    public void removeTest(){
        MapAdapter map1 = new MapAdapter();
        assertThrows("Map non ha lanciato eccezioni sulla rimozione di chiave nulla", NullPointerException.class, () -> {map1.remove(null);});

        MapAdapter map2 = new MapAdapter();
        map1.put("A", 1);
        map2.put("A", 1);

        assertEquals("Le due mappe non sono uguali dopo l'inserimento di valori uguali", map1, map2);
        
        map1.clear();
        assertEquals("L'oggetto ritornato da remove non è quello atteso (1)", 1, map2.remove("A")); 
        assertEquals("La mappa pulita e quella a cui è stato rimosso l'unico valore non sono uguali", map1, map2);

        assertNull("La rimozione di chiavi non presenti non fornisce null come valore di ritorno", map2.remove("SOS"));
    }


    @Test
    public void sizeTest(){
        MapAdapter map1 = new MapAdapter();

        assertEquals("La dimensione della mappa vuota non corrisponde a quella attesa", 0, map1.size());
        map1.put(1, 1);
        assertEquals("La dimensione della mappa non corrisponde a quella attesa (1)", 1, map1.size());
        map1.put(2, 2);
        assertEquals("La dimensione della mappa non corrisponde a quella attesa (2)", 2, map1.size());
        map1.remove(1);
        assertEquals("La dimensione della mappa non corrisponde a quella attesa dopo la rimozione", 1, map1.size());

    }

    @Test
    public void valuesTest(){
        MapAdapter map1 = new MapAdapter();
        assertNotNull("La collection ritornata da valued è null", map1.values());
    }


    


    
    

    


    
}
