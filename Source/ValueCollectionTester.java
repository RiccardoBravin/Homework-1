//package myTest;
//Internal
import myAdapter.*;
import myAdapter.MapAdapter.ValueCollection;
//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;
import org.junit.runner.*;
import org.junit.Test;
//Extra
import java.util.Hashtable;

public class ValueCollectionTester {
    @Test
    public void constructorTester(){
        Hashtable ht =  new Hashtable();
        assertNotNull("Il costruttore di ValueCollection ritorna un valore nullo", new MapAdapter().new ValueCollection(ht));
        assertThrows("Il costruttore non lancia NullPointerException se viene passata una hashtable nulla", NullPointerException.class, () -> {new MapAdapter().new ValueCollection(null);});
    }

    @Test
    public void addTest(){
        Hashtable ht =  new Hashtable();
        MapAdapter.ValueCollection vc = new MapAdapter().new ValueCollection(ht);
        assertThrows("È permesso l'utilizzo di add quando dovrebbe lanciare UnsupportedOperationException", UnsupportedOperationException.class, () -> {vc.add(null);});

    }

    @Test
    public void addAllTest(){
        Hashtable ht =  new Hashtable();
        MapAdapter.ValueCollection vc = new MapAdapter().new ValueCollection(ht);
        assertThrows("È permesso l'utilizzo di addAll quando dovrebbe lanciare UnsupportedOperationException", UnsupportedOperationException.class, () -> {vc.addAll(null);});

    }

    @Test
    public void clearTest(){
        Hashtable ht =  new Hashtable();
        ht.put("A", 1);
        MapAdapter.ValueCollection vc = new MapAdapter().new ValueCollection(ht);
        vc.clear();
        assertTrue("clear non cancella tutti gli elementi del ValueCollection", vc.isEmpty());
        assertTrue("clear su ValueCollection non cancella tutti gli elementi di Hashtable", ht.isEmpty());
    }

    @Test
    public void containsTest(){
        Hashtable ht =  new Hashtable();
        ht.put("A", 1);
        MapAdapter.ValueCollection vc = new MapAdapter().new ValueCollection(ht);
        assertTrue("ValueCollection sembra non contenere il valore contenuto nella hashtable", vc.contains(1));
        assertFalse("ValueCollection sembra contenere valori non presenti nella hashtable", vc.contains("A"));
    }

    @Test
    public void containsAllTest(){
        Hashtable ht =  new Hashtable();
        ht.put("A", 1);
        MapAdapter.ValueCollection vc = new MapAdapter().new ValueCollection(ht);
        ListAdapter l1 = new ListAdapter();
        l1.add(1);
        assertTrue("ValueCollection sembra non contenere la lista di valori contenuta nella hashtable", vc.containsAll(l1));
        l1.add("A");
        assertFalse("ValueCollection sembra contenere valori non presenti nella hashtable", vc.containsAll(l1));

        assertTrue("ValueCollection sembra non contenere la lista di chiavi vuota ", vc.containsAll(new ListAdapter()));
        assertThrows("AddAll non lancia NullPointerException passando una lista nulla", NullPointerException.class, () -> {vc.containsAll(null);});

    }


    @Test
    public void equalsTest(){
        Hashtable ht1 =  new Hashtable();
        ht1.put("A", 1);
        MapAdapter.ValueCollection vc1 = new MapAdapter().new ValueCollection(ht1);
        Hashtable ht2 =  new Hashtable();
        ht2.put("A", 1);
        MapAdapter.ValueCollection vc2 = new MapAdapter().new ValueCollection(ht2);

        assertTrue("Due ValueCollection contenenti le stesse chiavi non sono uguali", vc1.equals(vc2));

        ht2.remove("A");

        assertFalse("Due ValueCollection contenenti le stesse chiavi diverse sono uguali", vc1.equals(vc2));

        ht1.remove("A");

        assertTrue("Due ValueCollection vuoti non sono uguali", vc1.equals(vc2));

        ht1.put("B", 2);
        ht1.put("C", 3);
        
        ht2.put("B", 2);
        ht2.put("C", 3);
        

        assertTrue("Due ValueCollection contenenti le stesse chiavi non sono uguali", vc1.equals(vc2));


    }

    @Test
    public void hashTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.ValueCollection vc1 = new MapAdapter().new ValueCollection(ht1);
        assertEquals("La chiave contenuta nel ValueCollection non ha l'hashcode corretto (0)", 0, vc1.hashCode());

        ht1.put("A", 1);
        assertEquals("La chiave contenuta nel ValueCollection non ha l'hashcode corretto (1)", 1, vc1.hashCode());

        ht1.put("B", 2);
        assertEquals("La chiave contenuta nel ValueCollection non ha l'hashcode corretto (3)", 3, vc1.hashCode());
    }

    @Test
    public void isEmptyTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.ValueCollection vc1 = new MapAdapter().new ValueCollection(ht1);

        assertTrue("isEmpty su un ValueCollection vuoto torna false", vc1.isEmpty());
        ht1.put("A", 1);
        assertFalse("isEmpty su un ValueCollection pieno torna true", vc1.isEmpty());

    }

    @Test
    public void iteratorTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.ValueCollection vc1 = new MapAdapter().new ValueCollection(ht1);

        assertNotNull("iterator su ValueCollection torna un oggetto nullo", vc1.iterator());
        
    }

    @Test
    public void removeTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.ValueCollection vc1 = new MapAdapter().new ValueCollection(ht1);
        assertFalse("Il ValueCollection ritorna true alla richiesta di rimuovere null, impossibile",vc1.remove(null));

        assertFalse("Il remove dice di aver rimosso elementi da una hashtable vuota", vc1.remove("A"));

        ht1.put("A", 1);
        ht1.put("B", 1);
        ht1.put("C", 1);

        assertTrue("Il remove dice di non aver rimosso elementi da una hashtable con valori", vc1.remove(1));
        assertEquals("La hashtable non il numero corretto di elementi dopo il remove", 2, ht1.size());
    }

    @Test
    public void removeAllTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.ValueCollection vc1 = new MapAdapter().new ValueCollection(ht1);
        assertThrows("Il ValueCollection non lancia NullPointerException alla richiesta di rimuovere una collection null", NullPointerException.class, () ->{vc1.removeAll(null);});

        ListAdapter l = new ListAdapter();
        
        ht1.put("A", 1);
        assertFalse("il removeall dice di aver rimosso elementi passando una collection vuota", vc1.removeAll(new ListAdapter()));
        ht1.clear();

        l.add(1);

        assertFalse("Il removeall dice di aver rimosso elementi da una hashtable vuota", vc1.removeAll(l));

        ht1.put("A", 1);

        assertTrue("Removeall di una lista sembra non apportare modifiche alla hashtable", vc1.removeAll(l));
        assertTrue("La hashtable non è vuota dopo il remove", ht1.isEmpty());
    }

    @Test
    public void retainAllTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.ValueCollection vc1 = new MapAdapter().new ValueCollection(ht1);
        assertThrows("Il ValueCollection non lancia NullPointerException alla richiesta di rimuovere una collection null", NullPointerException.class, () ->{vc1.retainAll(null);});

        ListAdapter l = new ListAdapter();
        
        ht1.put("A", 1);
        assertTrue("il removeall dice di aver rimosso elementi passando una collection vuota", vc1.retainAll(new ListAdapter()));
        assertTrue("La hashtable non è vuota dopo il remove", ht1.isEmpty());

        l.add(1);

        assertFalse("Il removeall dice di aver rimosso elementi da una hashtable vuota", vc1.retainAll(l));

        ht1.put("A", 1);
        ht1.put("B", 2);

        assertTrue("retainAll di una lista sembra non apportare modifiche alla hashtable", vc1.retainAll(l));
        assertTrue("La hashtable non contiene gli elementi corretti dopo retainall", ht1.contains(1) && ht1.size() == 1);
    }

    @Test
    public void sizeTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.ValueCollection vc1 = new MapAdapter().new ValueCollection(ht1);
        assertEquals("La dimensione di una ValueCollection vuoto non è 0", 0, vc1.size());
        
        ht1.put("A", 1);
        assertEquals("La dimensione di una ValueCollection con un elemento non è 1", 1, vc1.size());

    }

    @Test
    public void toArrayTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.ValueCollection vc1 = new MapAdapter().new ValueCollection(ht1);

        assertArrayEquals("Il ValueCollection vuoto non torna un array vuoto ", new Object[0], vc1.toArray());
        
        ht1.put("A", 1);
        ht1.put("B", 1);

        assertArrayEquals("Il ValueCollection [A,B] non torna un array con i corretti elementi ", new Object[] {1,1}, vc1.toArray());
    }

    @Test
    public void toArrayObjectTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.ValueCollection vc1 = new MapAdapter().new ValueCollection(ht1);

        assertArrayEquals("Il ValueCollection vuoto non torna un array vuoto dato un array vuoto", new Object[0], vc1.toArray(new Object[0]));
        
        ht1.put("A", 1);
        ht1.put("B", 1);

        assertArrayEquals("Il ValueCollection [A,B] non torna un array con i corretti elementi nell'array passato ", new Object[] {1,1,null,null}, vc1.toArray(new Object[4]));

        assertArrayEquals("Il ValueCollection [A,B] non torna un array con i corretti elementi dato un array di dimensione corretta", new Object[] {1,1}, vc1.toArray(new Object[2]));
        
        assertArrayEquals("Il ValueCollection [A,B] non torna un array con i corretti elementi dato un array di dimensione inferiore", new Object[] {1, 1}, vc1.toArray(new Object[0]));
    }
}
