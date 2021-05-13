//package myTest;
//internal
import myAdapter.*;
import myAdapter.MapAdapter.EntrySet;
import myAdapter.MapAdapter.EntryAdapter;
//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;
import org.junit.runner.*;
import org.junit.Test;
//extra
import java.util.Hashtable;


public class EntrySetTester {
    
    @Test
    public void constructorTester(){
        Hashtable ht =  new Hashtable();
        assertNotNull("Il costruttore di EntrySet ritorna un valore nullo", new MapAdapter().new EntrySet(ht));
        assertThrows("Il costruttore non lancia NullPointerException se viene passata una hashtable nulla", NullPointerException.class, () -> {new MapAdapter().new EntrySet(null);});
    }

    @Test
    public void addTest(){
        Hashtable ht =  new Hashtable();
        MapAdapter.EntrySet es = new MapAdapter().new EntrySet(ht);
        assertThrows("È permesso l'utilizzo di add quando dovrebbe lanciare UnsupportedOperationException", UnsupportedOperationException.class, () -> {es.add(null);});

    }

    @Test
    public void addAllTest(){
        Hashtable ht =  new Hashtable();
        MapAdapter.EntrySet es = new MapAdapter().new EntrySet(ht);
        assertThrows("È permesso l'utilizzo di addAll quando dovrebbe lanciare UnsupportedOperationException", UnsupportedOperationException.class, () -> {es.addAll(null);});

    }

    @Test
    public void clearTest(){
        Hashtable ht =  new Hashtable();
        ht.put("A", 1);
        MapAdapter.EntrySet es = new MapAdapter().new EntrySet(ht);
        es.clear();
        assertTrue("clear non cancella tutti gli elementi del EntrySet", es.isEmpty());
        assertTrue("clear su EntrySet non cancella tutti gli elementi di Hashtable", ht.isEmpty());
    }

    @Test
    public void containsTest(){
        Hashtable ht =  new Hashtable();
        ht.put("A", 1);
        MapAdapter.EntrySet es = new MapAdapter().new EntrySet(ht);
        assertTrue("EntrySet sembra non contenere la entry contenuta nella hashtable", es.contains(new MapAdapter().new EntryAdapter("A", 1)));
        assertFalse("EntrySet sembra contenere elementi diversi da entry", es.contains("Test"));
        assertFalse("EntrySet sembra contenere chiavi non presenti nella hashtable", es.contains(new MapAdapter().new EntryAdapter("B", 1)));
    }

    @Test
    public void containsAllTest(){
        Hashtable ht =  new Hashtable();
        ht.put("A", 1);
        MapAdapter.EntrySet es = new MapAdapter().new EntrySet(ht);

        ListAdapter l1 = new ListAdapter();
        l1.add(new MapAdapter().new EntryAdapter("A", 1));
        assertTrue("EntrySet sembra non contenere la lista di entry contenuta nella hashtable", es.containsAll(l1));
        
        l1.add(new MapAdapter().new EntryAdapter("A", 2));
        assertFalse("EntrySet sembra contenere valori di entry non presenti nella hashtable", es.containsAll(l1));

        assertTrue("EntrySet sembra non contenere la lista di entry vuota ", es.containsAll(new ListAdapter()));
        assertThrows("AddAll non lancia NullPointerException passando una lista nulla", NullPointerException.class, () -> {es.containsAll(null);});
    }


    @Test
    public void equalsTest(){
        Hashtable ht1 =  new Hashtable();
        ht1.put("A", 1);
        MapAdapter.EntrySet es1 = new MapAdapter().new EntrySet(ht1);
        Hashtable ht2 =  new Hashtable();
        ht2.put("A", 1);
        MapAdapter.EntrySet es2 = new MapAdapter().new EntrySet(ht2);

        assertTrue("Due EntrySet contenenti le stesse entry non sono uguali", es1.equals(es2));

        ht2.remove("A");

        assertFalse("Due EntrySet contenenti le stesse chiavi diverse sono uguali", es1.equals(es2));

        ht1.remove("A");

        assertTrue("Due EntrySet vuoti non sono uguali", es1.equals(es2));

        ht1.put("B", 2);
        ht1.put("C", 3);
        
        ht2.put("B", 2);
        ht2.put("C", 3);

        assertTrue("Due EntrySet contenenti le stesse chiavi non sono uguali", es1.equals(es2));
    }

    @Test
    public void hashTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.EntrySet es1 = new MapAdapter().new EntrySet(ht1);
        assertEquals("La chiave contenuta nel EntrySet non ha l'hashcode corretto", 0, es1.hashCode());

        ht1.put("A", 1);
        assertEquals("La chiave contenuta nel EntrySet non ha l'hashcode corretto", 64, es1.hashCode());

        ht1.put("B", 2);
        assertEquals("La chiave contenuta nel EntrySet non ha l'hashcode corretto", 64+64, es1.hashCode());
    }

    @Test
    public void isEmptyTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.EntrySet es1 = new MapAdapter().new EntrySet(ht1);

        assertTrue("isEmpty su un EntrySet vuoto torna false", es1.isEmpty());
        ht1.put("A", 1);
        assertFalse("isEmpty su un EntrySet pieno torna true", es1.isEmpty());

    }

    @Test
    public void iteratorTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.EntrySet es1 = new MapAdapter().new EntrySet(ht1);

        assertNotNull("iterator su EntrySet torna un oggetto nullo", es1.iterator());
        
    }

    @Test
    public void removeTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.EntrySet es1 = new MapAdapter().new EntrySet(ht1);
        assertThrows("Il EntrySet non lancia NullPointerException alla richiesta di rimuovere entry con chiave null", NullPointerException.class, () ->{es1.remove(new MapAdapter().new EntryAdapter(null, 1));});

        assertFalse("Il remove dice di aver rimosso elementi da una hashtable vuota", es1.remove(new MapAdapter().new EntryAdapter("A", 1)));

        ht1.put("A", 1);

        assertTrue("Il remove dice di non aver rimosso elementi da una hashtable con valori", es1.remove(new MapAdapter().new EntryAdapter("A", 1)));
        assertTrue("La hashtable non è vuota dopo il remove", ht1.isEmpty());

        ht1.put("A", 1);
        assertFalse("Il remove dice di aver rimosso una entry con valore non presente", es1.remove(new MapAdapter().new EntryAdapter("A", 2)));
        assertFalse("Il remove dice di aver rimosso una entry con chiave non presente", es1.remove(new MapAdapter().new EntryAdapter("B", 1)));


    }

    @Test
    public void removeAllTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.EntrySet es1 = new MapAdapter().new EntrySet(ht1);
        assertThrows("Il EntrySet non lancia NullPointerException alla richiesta di rimuovere una collection null", NullPointerException.class, () ->{es1.removeAll(null);});

        ListAdapter l = new ListAdapter();
        
        ht1.put("A", 1);
        assertFalse("il removeall dice di aver rimosso elementi passando una collection vuota", es1.removeAll(new ListAdapter()));
        ht1.clear();

        l.add(new MapAdapter().new EntryAdapter("A", 1));

        assertFalse("Il removeall dice di aver rimosso elementi da una hashtable vuota", es1.removeAll(l));

        ht1.put("A", 1);

        assertTrue("Removeall di una lista sembra non apportare modifiche alla hashtable", es1.removeAll(l));
        assertTrue("La hashtable non è vuota dopo il remove", ht1.isEmpty());
    }


    @Test
    public void retainAllTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.EntrySet es1 = new MapAdapter().new EntrySet(ht1);
        assertThrows("Il EntrySet non lancia NullPointerException alla richiesta di rimuovere una collection null", NullPointerException.class, () ->{es1.retainAll(null);});

        ListAdapter l = new ListAdapter();
        
        ht1.put("A", 1);
        assertTrue("il removeall dice di aver rimosso elementi passando una collection vuota", es1.retainAll(new ListAdapter()));
        assertTrue("La hashtable non è vuota dopo il remove", ht1.isEmpty());

        l.add(new MapAdapter().new EntryAdapter("A", 1));

        assertFalse("Il removeall dice di aver rimosso elementi da una hashtable vuota", es1.retainAll(l));

        ht1.put("A", 1);
        ht1.put("B", 2);

        assertTrue("retainAll di una lista sembra non apportare modifiche alla hashtable", es1.retainAll(l));
        assertTrue("La hashtable non contiene gli elementi corretti dopo retainall", ht1.containsKey("A") && ht1.contains(1) && ht1.size() == 1);
    }

    @Test
    public void sizeTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.EntrySet es1 = new MapAdapter().new EntrySet(ht1);
        assertEquals("La dimensione di una EntrySet vuoto non è 0", 0, es1.size());
        
        ht1.put("A", 1);
        assertEquals("La dimensione di una EntrySet con un elemento non è 1", 1, es1.size());

    }

    @Test
    public void toArrayTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.EntrySet es1 = new MapAdapter().new EntrySet(ht1);

        assertArrayEquals("Il EntrySet vuoto non torna un array vuoto ", new Object[0], es1.toArray());
        
        ht1.put("A", 1);
        ht1.put("B", 1);

        assertArrayEquals("Il EntrySet [A,B] non torna un array con i corretti elementi ", new Object[] {new MapAdapter().new EntryAdapter("A", 1),new MapAdapter().new EntryAdapter("B", 1)}, es1.toArray());
    }

    @Test
    public void toArrayObjectTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.EntrySet es1 = new MapAdapter().new EntrySet(ht1);

        assertArrayEquals("Il EntrySet vuoto non torna un array vuoto dato un array vuoto", new Object[0], es1.toArray(new Object[0]));
        
        ht1.put("A", 1);
        ht1.put("B", 1);

        assertArrayEquals("Il EntrySet [A,B] non torna un array con i corretti elementi nell'array passato ", new Object[] {new MapAdapter().new EntryAdapter("A", 1),new MapAdapter().new EntryAdapter("B", 1),null,null}, es1.toArray(new Object[4]));

        assertArrayEquals("Il EntrySet [A,B] non torna un array con i corretti elementi dato un array di dimensione corretta", new Object[] {new MapAdapter().new EntryAdapter("A", 1),new MapAdapter().new EntryAdapter("B", 1)}, es1.toArray(new Object[2]));
        
        assertArrayEquals("Il EntrySet [A,B] non torna un array con i corretti elementi dato un array di dimensione inferiore", new Object[] {new MapAdapter().new EntryAdapter("A", 1),new MapAdapter().new EntryAdapter("B", 1)}, es1.toArray(new Object[0]));
    }

}
