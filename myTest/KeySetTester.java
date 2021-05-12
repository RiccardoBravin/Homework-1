import myAdapter.*;
import myAdapter.MapAdapter.KeySet;
//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;
import org.junit.runner.*;
import org.junit.Test;

import java.util.Hashtable;
import java.util.List;

public class KeySetTester {
    
    @Test
    public void constructorTester(){
        Hashtable ht =  new Hashtable();
        assertNotNull("Il costruttore di KeySet ritorna un valore nullo", new MapAdapter().new KeySet(ht));
        assertThrows("Il costruttore non lancia NullPointerException se viene passata una hashtable nulla", NullPointerException.class, () -> {new MapAdapter().new KeySet(null);});
    }

    @Test
    public void addTest(){
        Hashtable ht =  new Hashtable();
        MapAdapter.KeySet ks = new MapAdapter().new KeySet(ht);
        assertThrows("È permesso l'utilizzo di add quando dovrebbe lanciare UnsupportedOperationException", UnsupportedOperationException.class, () -> {ks.add(null);});

    }

    @Test
    public void addAllTest(){
        Hashtable ht =  new Hashtable();
        MapAdapter.KeySet ks = new MapAdapter().new KeySet(ht);
        assertThrows("È permesso l'utilizzo di addAll quando dovrebbe lanciare UnsupportedOperationException", UnsupportedOperationException.class, () -> {ks.addAll(null);});

    }

    @Test
    public void clearTest(){
        Hashtable ht =  new Hashtable();
        ht.put("A", 1);
        MapAdapter.KeySet ks = new MapAdapter().new KeySet(ht);
        ks.clear();
        assertTrue("clear non cancella tutti gli elementi del keySet", ks.isEmpty());
        assertTrue("clear su keyset non cancella tutti gli elementi di Hashtable", ht.isEmpty());
    }

    @Test
    public void containsTest(){
        Hashtable ht =  new Hashtable();
        ht.put("A", 1);
        MapAdapter.KeySet ks = new MapAdapter().new KeySet(ht);
        assertTrue("keySet sembra non contenere la chiave contenuta nella hashtable", ks.contains("A"));
        assertFalse("KeySet sembra contenere chiavi non presenti nella hashtable", ks.contains(1));
    }

    @Test
    public void containsAllTest(){
        Hashtable ht =  new Hashtable();
        ht.put("A", 1);
        MapAdapter.KeySet ks = new MapAdapter().new KeySet(ht);
        ListAdapter l1 = new ListAdapter();
        l1.add("A");
        assertTrue("keySet sembra non contenere la lista di chiavi contenuta nella hashtable", ks.containsAll(l1));
        l1.add(1);
        assertFalse("KeySet sembra contenere chiavi non presenti nella hashtable", ks.containsAll(l1));

        assertTrue("keySet sembra non contenere la lista di chiavi vuota ", ks.containsAll(new ListAdapter()));
        assertThrows("AddAll non lancia NullPointerException passando una lista nulla", NullPointerException.class, () -> {ks.containsAll(null);});

    }


    @Test
    public void equalsTest(){
        Hashtable ht1 =  new Hashtable();
        ht1.put("A", 1);
        MapAdapter.KeySet ks1 = new MapAdapter().new KeySet(ht1);
        Hashtable ht2 =  new Hashtable();
        ht2.put("A", 1);
        MapAdapter.KeySet ks2 = new MapAdapter().new KeySet(ht2);

        assertTrue("Due keyset contenenti le stesse chiavi non sono uguali", ks1.equals(ks2));

        ht2.remove("A");

        assertFalse("Due keyset contenenti le stesse chiavi diverse sono uguali", ks1.equals(ks2));

        ht1.remove("A");

        assertTrue("Due keyset vuoti non sono uguali", ks1.equals(ks2));

        ht1.put("B", 2);
        ht1.put("C", 3);
        
        ht2.put("B", 2);
        ht2.put("C", 3);
        

        assertTrue("Due keyset contenenti le stesse chiavi non sono uguali", ks1.equals(ks2));


    }

    @Test
    public void hashTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.KeySet ks1 = new MapAdapter().new KeySet(ht1);
        assertEquals("La chiave contenuta nel keyset non ha l'hashcode corretto", 0, ks1.hashCode());

        ht1.put("A", 1);
        assertEquals("La chiave contenuta nel keyset non ha l'hashcode corretto", 65, ks1.hashCode());

        ht1.put("B", 2);
        assertEquals("La chiave contenuta nel keyset non ha l'hashcode corretto", 65+66, ks1.hashCode());
    }

    @Test
    public void isEmptyTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.KeySet ks1 = new MapAdapter().new KeySet(ht1);

        assertTrue("isEmpty su un keyset vuoto torna false", ks1.isEmpty());
        ht1.put("A", 1);
        assertFalse("isEmpty su un keyset pieno torna true", ks1.isEmpty());

    }

    @Test
    public void iteratorTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.KeySet ks1 = new MapAdapter().new KeySet(ht1);

        assertNotNull("iterator su keyset torna un oggetto nullo", ks1.iterator());
        
    }

    @Test
    public void removeTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.KeySet ks1 = new MapAdapter().new KeySet(ht1);
        assertThrows("Il keyset non lancia NullPointerException alla richiesta di rimuovere null", NullPointerException.class, () ->{ks1.remove(null);});

        assertFalse("Il remove dice di aver rimosso elementi da una hashtable vuota", ks1.remove("A"));

        ht1.put("A", 1);

        assertTrue("Il remove dice di non aver rimosso elementi da una hashtable con valori", ks1.remove("A"));
        assertTrue("La hashtable non è vuota dopo il remove", ht1.isEmpty());
    }

    @Test
    public void removeAllTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.KeySet ks1 = new MapAdapter().new KeySet(ht1);
        assertThrows("Il keyset non lancia NullPointerException alla richiesta di rimuovere una collection null", NullPointerException.class, () ->{ks1.removeAll(null);});

        ListAdapter l = new ListAdapter();
        
        ht1.put("A", 1);
        assertFalse("il removeall dice di aver rimosso elementi passando una collection vuota", ks1.removeAll(new ListAdapter()));
        ht1.clear();

        l.add("A");

        assertFalse("Il removeall dice di aver rimosso elementi da una hashtable vuota", ks1.removeAll(l));

        ht1.put("A", 1);

        assertTrue("Removeall di una lista sembra non apportare modifiche alla hashtable", ks1.removeAll(l));
        assertTrue("La hashtable non è vuota dopo il remove", ht1.isEmpty());
    }

    @Test
    public void retainAllTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.KeySet ks1 = new MapAdapter().new KeySet(ht1);
        assertThrows("Il keyset non lancia NullPointerException alla richiesta di rimuovere una collection null", NullPointerException.class, () ->{ks1.retainAll(null);});

        ListAdapter l = new ListAdapter();
        
        ht1.put("A", 1);
        assertTrue("il removeall dice di aver rimosso elementi passando una collection vuota", ks1.retainAll(new ListAdapter()));
        assertTrue("La hashtable non è vuota dopo il remove", ht1.isEmpty());

        l.add("A");

        assertFalse("Il removeall dice di aver rimosso elementi da una hashtable vuota", ks1.retainAll(l));

        ht1.put("A", 1);
        ht1.put("B", 2);

        assertTrue("retainAll di una lista sembra non apportare modifiche alla hashtable", ks1.retainAll(l));
        assertTrue("La hashtable non contiene gli elementi corretti dopo retainall", ht1.containsKey("A") && ht1.size() == 1);
    }

    @Test
    public void sizeTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.KeySet ks1 = new MapAdapter().new KeySet(ht1);
        assertEquals("La dimensione di una keyset vuoto non è 0", 0, ks1.size());
        
        ht1.put("A", 1);
        assertEquals("La dimensione di una keyset con un elemento non è 1", 1, ks1.size());

    }

    @Test
    public void toArrayTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.KeySet ks1 = new MapAdapter().new KeySet(ht1);

        assertArrayEquals("Il keyset vuoto non torna un array vuoto ", new Object[0], ks1.toArray());
        
        ht1.put("A", 1);
        ht1.put("B", 1);

        assertArrayEquals("Il keyset [A,B] non torna un array con i corretti elementi ", new Object[] {"A","B"}, ks1.toArray());
    }

    @Test
    public void toArrayObjectTest(){
        Hashtable ht1 =  new Hashtable();
        MapAdapter.KeySet ks1 = new MapAdapter().new KeySet(ht1);

        assertArrayEquals("Il keyset vuoto non torna un array vuoto dato un array vuoto", new Object[0], ks1.toArray(new Object[0]));
        
        ht1.put("A", 1);
        ht1.put("B", 1);

        assertArrayEquals("Il keyset [A,B] non torna un array con i corretti elementi nell'array passato ", new Object[] {"A","B",null,null}, ks1.toArray(new Object[4]));

        assertArrayEquals("Il keyset [A,B] non torna un array con i corretti elementi dato un array di dimensione corretta", new Object[] {"A","B"}, ks1.toArray(new Object[2]));
        
        assertArrayEquals("Il keyset [A,B] non torna un array con i corretti elementi dato un array di dimensione inferiore", new Object[] {"A","B"}, ks1.toArray(new Object[0]));
    }

}
