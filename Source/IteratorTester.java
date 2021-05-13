/package myTest;
//Internal
import myAdapter.*;
import myAdapter.MapAdapter.EntryAdapter;
//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.*;
//Extra
import java.util.NoSuchElementException;


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

        Object obj = null;
        try{
            obj = ik.next();
            assertTrue("L'iteratore di chiavi su set vuoto non lancia NoSuchElementException con next", false);
        }catch (NoSuchElementException nsee){
            assertNull("Il valore di obj è stato sovrascritto pur avendo next lanciato un'eccezione", obj);
        }

        try{
            obj = ie.next();
            assertTrue("L'iteratore di entry su set vuoto non lancia NoSuchElementException con next", false);
        }catch (NoSuchElementException nsee){
            assertNull("Il valore di obj è stato sovrascritto pur avendo next lanciato un'eccezione", obj);
        }

        try{
            obj = iv.next();
            assertTrue("L'iteratore di valori su collection vuota non lancia NoSuchElementException con next", false);
        }catch (NoSuchElementException nsee){
            assertNull("Il valore di obj è stato sovrascritto pur avendo next lanciato un'eccezione", obj);
        }
        

        map1.put("A", 1);
        map1.put("B", 2);
        map1.put(3, "C");

        keys = map1.keySet();
        entrys = map1.entrySet();
        vals = map1.values();

        ik = new IteratorAdapter(keys);
        ie = new IteratorAdapter(entrys);
        iv = new IteratorAdapter(vals);

        assertEquals("L'iteratore di chiavi non ritorna la chiave corretta (A)", "A", ik.next());
        assertEquals("L'iteratore di chiavi non ritorna la chiave corretta (A,1)", new MapAdapter().new EntryAdapter("A",1) , ie.next());
        assertEquals("L'iteratore di chiavi non ritorna la chiave corretta (1)", 1, iv.next());

        assertEquals("L'iteratore di chiavi non ritorna la chiave corretta (3)", 3, ik.next());
        assertEquals("L'iteratore di chiavi non ritorna la chiave corretta (3,c)", new MapAdapter().new EntryAdapter(3,"C") , ie.next());
        assertEquals("L'iteratore di chiavi non ritorna la chiave corretta (c)", "C", iv.next());

        assertEquals("L'iteratore di chiavi non ritorna la chiave corretta (B)", "B", ik.next());
        assertEquals("L'iteratore di chiavi non ritorna la chiave corretta (B,2)", new MapAdapter().new EntryAdapter("B",2) , ie.next());
        assertEquals("L'iteratore di chiavi non ritorna la chiave corretta (2)", 2, iv.next());

        try{
            obj = ik.next();
            assertTrue("L'iteratore di chiavi in ultima posizione su set pieno non lancia NoSuchElementException con next", false);
        }catch (NoSuchElementException nsee){
            assertNull("Il valore di obj è stato sovrascritto pur avendo next lanciato un'eccezione", obj);
        }

        try{
            obj = ie.next();
            assertTrue("L'iteratore di entry in ultima posizione su set pieno non lancia NoSuchElementException con next", false);
        }catch (NoSuchElementException nsee){
            assertNull("Il valore di obj è stato sovrascritto pur avendo next lanciato un'eccezione", obj);
        }

        try{
            obj = iv.next();
            assertTrue("L'iteratore di valori in ultima posizione su collection piena non lancia NoSuchElementException con next", false);
        }catch (NoSuchElementException nsee){
            assertNull("Il valore di obj è stato sovrascritto pur avendo next lanciato un'eccezione", obj);
        }
        
    }

    @Test
    public void removeTest(){
        MapAdapter map1 = new MapAdapter();
        
        HSet keys = map1.keySet();
        HSet entrys = map1.entrySet();
        HCollection vals = map1.values();

        HIterator ikk = new IteratorAdapter(keys);
        HIterator iee = new IteratorAdapter(entrys);
        HIterator ivv = new IteratorAdapter(vals);

        assertThrows("L'iteratore di chiavi su set vuoto non lancia NoSuchElementException pur non avendo mai fatto next" ,IllegalStateException.class , () -> {ikk.remove();});
        assertThrows("L'iteratore di entry su set vuoto non lancia NoSuchElementException pur non avendo mai fatto next" ,IllegalStateException.class , () -> {iee.remove();});
        assertThrows("L'iteratore di valori su collection vuota non lancia NoSuchElementException pur non avendo mai fatto next" ,IllegalStateException.class , () -> {ivv.remove();});


        map1.put("A", 1);
        map1.put("B", 2);
        map1.put(3, "C");

        keys = map1.keySet();
        entrys = map1.entrySet();
        vals = map1.values();

        HIterator ik = new IteratorAdapter(keys);
        HIterator ie = new IteratorAdapter(entrys);
        HIterator iv = new IteratorAdapter(vals);

        Object key = ik.next();
        ik.remove();
        assertFalse("La chiave rimossa da remove è ancora presente nella mappa", map1.containsKey(key));
        
        MapAdapter.EntryAdapter entry = (MapAdapter.EntryAdapter)ie.next();
        ie.remove();
        assertFalse("La entry rimossa da remove è ancora presente nella mappa", map1.get(key) == entry.getValue());

        Object val = iv.next();
        iv.remove();
        //Questo assert non ha alcun senso se nella mappa ci sono più chiavi cono lo stesso valore ma è di per sè insensata la cosa di rimuovere un valore da una mappa...
        assertFalse("La chiave rimossa da remove è ancora presente nella mappa", map1.containsValue(val));


        assertThrows("L'iteratore di chiavi su set pieno non lancia NoSuchElementException pur non avendolo eseguito dopo next" ,IllegalStateException.class , () -> {ik.remove();});
        assertThrows("L'iteratore di entry su set pieno non lancia NoSuchElementException pur non avendolo eseguito dopo next" ,IllegalStateException.class , () -> {ie.remove();});
        assertThrows("L'iteratore di valori su collection piena non lancia NoSuchElementException pur non avendolo eseguito dopo next" ,IllegalStateException.class , () -> {iv.remove();});

    }

}
