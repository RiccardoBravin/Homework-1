//Adapter
import myAdapter.ListAdapter;

//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;
import org.junit.runner.*;
import org.junit.Test;


import java.util.Random;




public class ListTester{

    @Test
    public void constructorTester(){
        ListAdapter l1 = new ListAdapter();
        assertNotNull("Il costruttore di default di list 1 ritorna un oggetto null", l1);
        
        ListAdapter l2 = new ListAdapter(5);
        assertNotNull("Il costruttore di default di list 2 ritorna un oggetto null", l2);

        assertTrue("La lista 1 non è vuota", l1.isEmpty());
        assertTrue("La lista 2 non è vuota", l2.isEmpty());
        assertEquals("Le due liste vuote create non hanno la stessa dimensione", l1.size(), l2.size());
        assertEquals("Due liste appena create sono diverse", l1, l2);
    }    

    @Test
    public void addTester(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");
        assertFalse("La lista è vuota dopo un inserimento", l1.isEmpty());
        assertEquals("La lista non contiene solo un elemento", 1, l1.size());
        assertTrue("La lista non contiene l'oggetto appena inserito", l1.contains("A"));
        assertFalse("La lista contiene elementi non inseriti (1)", l1.contains(1));
        assertFalse("La lista contiene elementi non inseriti ('Z')", l1.contains("Z"));
        
        l1.add(1);
        assertFalse("La lista è vuota dopo un secondo inserimento", l1.isEmpty());
        assertEquals("La lista non contiene due elementi", 2, l1.size());
        assertTrue("La lista non contiene l'oggetto appena inserito", l1.contains(1));
        assertTrue("La lista non contiene più l'oggetto inserito in precedenza", l1.contains("A"));

        l1.add(null);
        assertFalse("La lista è vuota dopo un terzo inserimento", l1.isEmpty());
        assertEquals("La lista non contiene tre elementi", 3, l1.size());
        assertTrue("La lista non contiene l'oggetto appena inserito", l1.contains(null));


        assertFalse("La lista contiene elementi non inseriti ('Z')", l1.contains("Z"));

        assertEquals("Il primo elemento inserito non si trova correttamente all'inizio della lista", 0, l1.indexOf("A"));
        assertEquals("Il secondo elemento inserito non si trova correttamente nella seconda posizione della lista", 1, l1.indexOf(1));
    }
    
    @Test
    public void addIndexTester(){
        ListAdapter l1 = new ListAdapter();
        
        assertThrows("La lista non lancia indexOutOfBound quando viene inserito un elemento fuori posizione massima", IndexOutOfBoundsException.class, () -> {l1.add(1,"A");});
        assertThrows("La lista non lancia indexOutOfBound quando viene inserito un elemento fuori posizione massima", IndexOutOfBoundsException.class, () -> {l1.add(-1,"A");});
        assertTrue("La stringa contiene qualcosa pur avendo lanciato un eccezzione all'inserimento", l1.isEmpty());
        
        l1.add(0,"A");
        assertTrue("La lista non contiene l'elemento inserito", l1.contains("A"));
        
        l1.add(0,"B");
        assertEquals("L'elemento inserito B non è nella posizione corretta", "B", l1.get(0));
        
        l1.add("C");
        assertEquals("L'elemento C non è inserito nella posizione corretta", 2, l1.indexOf("C"));
        assertEquals("L'elemento B non è inserito nella posizione corretta", 0, l1.indexOf("B"));
        
        l1.add(1,100);
        assertEquals("L'elemento inserito 100 non è nella posizione corretta", 100, l1.get(1));
        assertEquals("L'elemento 100 non è inserito nella posizione corretta", 1, l1.indexOf(100));
        
        int x =  new Random().nextInt(10);
        for(int i = 0; i < x + 10 ; i++) l1.add(i,i);
        
        for(int i = 0; i < x + 10 ; i++) assertEquals("l'elemento in posizione " + i + "non è corretto", i, l1.get(i));
        
        //System.out.println(l1.toString());
    }

    @Test
    public void addAllTester(){
        ListAdapter l1 = new ListAdapter();
        
        assertThrows("La lista non lancia NullPointerException quando viene passata un null", NullPointerException.class, () -> {l1.addAll(null);});
        assertThrows("La lista non lancia IndexOutOfBoundsException quando viene passato un indice invalido (100)", IndexOutOfBoundsException.class, () -> {l1.addAll(100,l1);});
        assertThrows("La lista non lancia IndexOutOfBoundsException quando viene passato un indice invalido (-1)", IndexOutOfBoundsException.class, () -> {l1.addAll(-1,l1);});
        assertTrue("La stringa non è rimasta vuota dopo aver lanciato errori", l1.isEmpty());

        l1.addAll(l1);
        assertTrue("La stringa non è rimasta vuota dopo aver aggiunto una collection vuota", l1.isEmpty());

        l1.add("A");
        l1.addAll(l1);
        assertEquals("La stringa non ha correttamente aggiunto la collection", l1.get(0), l1.get(1));

        ListAdapter l2 = new ListAdapter();
        l2.addAll(l1);
        assertEquals("Le liste l1 e l2 non sono identiche dopo un addall in lista vuota", l2, l1);
        
        l2 = new ListAdapter();
        assertFalse("L'addall di una collection vuota a l1 dice che l1 è stata modificata", l1.addAll(l2));
        
        l2.add(1);
        assertTrue("L'addall di una collection non vuota a l1 dice che l1 non è stata modificata", l1.addAll(1,l2));
        assertEquals("L'elemento 1 non è stato inserito nella posizione corretta da addall", 1, l1.get(1));

        //System.out.println(l1);
    }

    @Test
    public void clearTester(){
        ListAdapter l1 = new ListAdapter();
        assertTrue("La lista appena creata non è vuota", l1.isEmpty());
        
        l1.clear();
        assertTrue("La lista dopo clear non è più vuota", l1.isEmpty());

        listFiller(l1, 10);
        assertEquals("La dimensione della lista non è corretta dopo l'inserimento", 10, l1.size());

        l1.clear();
        assertEquals("La dimensione della lista non è corretta dopo clear", 0, l1.size());
        assertFalse("La lista contiene elementi quando dovrebbe essere vuota", l1.contains("Test"));

    } 

    @Test
    public void containsTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add(1);
        l1.add("A");
        assertTrue("La lista non contiene A", l1.contains("A"));
        assertTrue("La lista non contiene 1", l1.contains(1));
        assertFalse("La lista contiene '1' quando non è stato inserito", l1.contains("1"));
        assertFalse("La lista contiene null quando non è stato inserito", l1.contains(null));

        //altri test da aggiungere? 

        //System.out.println(l1);
    }

    @Test
    public void equalsTest(){
        ListAdapter l1 = new ListAdapter();
        listFiller(l1, 2);
        l1.add(1);
        l1.add(2);
        l1.add(3);

        ListAdapter l2 = new ListAdapter();
        listFiller(l2, 2);
        assertNotEquals("Le due mappe risultano uguali pur non essendolo", l1, l2);
        
        l2.add(1);
        l2.add(2);
        l2.add(3);
        assertEquals("Le due mappe risultano diverse pur contenendo gli stessi elementi", l1, l2);
        
        assertEquals("Le mappe sono ritenute uguali pur avendo hashcode diversi", l1.hashCode(), l2.hashCode());
        
        //System.out.println(l1);
    }

    @Test
    public void getTest(){
        ListAdapter l1 = new ListAdapter();
        assertThrows("La lista vuota non lancia IndexOutOfBoundsException se viene richesto un valore ad indici non esistenti (-1)", IndexOutOfBoundsException.class, () -> {l1.get(-1);});
        assertThrows("La lista vuota non lancia IndexOutOfBoundsException se viene richesto un valore ad indici non esistenti (0)", IndexOutOfBoundsException.class, () -> {l1.get(0);});
        assertThrows("La lista vuota non lancia IndexOutOfBoundsException se viene richesto un valore ad indici non esistenti (1)", IndexOutOfBoundsException.class, () -> {l1.get(1);});
        
        l1.add("x");
        assertThrows("La lista vuota lancia IndexOutOfBoundsException se viene richesto un valore ad indici non esistenti (-1)", IndexOutOfBoundsException.class, () -> {l1.get(-1);});
        assertThrows("La lista vuota lancia IndexOutOfBoundsException se viene richesto un valore ad indici non esistenti (1)", IndexOutOfBoundsException.class, () -> {l1.get(1);});
        assertEquals("La lista sembra non contenere x al posto corretto", "x", l1.get(0));

        listFiller(l1, 10);
        l1.add(4, "xx");
        assertEquals("La lista sembra non contenere xx al posto corretto", "xx", l1.get(4));
        
        //System.out.println(l1);
    }

    @Test
    public void hashTest(){
        ListAdapter l1 = new ListAdapter();
        assertEquals("L'hash code della lista vuota non è 1 come atteso", 1, l1.hashCode());
        
        l1.add(null);
        assertEquals("L'hash code della lista contenente null non è 31 come atteso", 31, l1.hashCode());

        l1.add(10);
        assertEquals("L'hash code della lista [null, 10] non è 971 come atteso", 971, l1.hashCode());

        l1.add("A");
        assertEquals("L'hash code della lista [null, 10, 'A'] non è 971 come atteso", 30166, l1.hashCode());
    }

    @Test
    public void indexOfTest(){
        ListAdapter l1 = new ListAdapter();
        assertEquals("indexOf su lista vuota non da sempre -1 (null)", -1, l1.indexOf(null));
        assertEquals("indexOf su lista vuota non da sempre -1 (0)", -1, l1.indexOf(0));
        assertEquals("indexOf su lista vuota non da sempre -1 ('')", -1, l1.indexOf(""));

        l1.add(null);
        assertEquals("indexOf su lista [null] non trova null", 0, l1.indexOf(null));
        assertEquals("indexOf su lista [null] trova elementi non presenti (0)", -1, l1.indexOf(0));
        
        l1.clear();
        listFiller(l1, 5);
        l1.add(0, "x");
        l1.add(2, "x");
        l1.add(5, "x");
        assertEquals("indexOf non ritorna la prima occorrenza dell'elemento cercato (x)", 0, l1.indexOf("x"));
        l1.remove(0);
        assertEquals("indexOf non ritorna la prima occorrenza dell'elemento cercato dopo una rimozione (x)", 1, l1.indexOf("x"));
        //System.out.println(l1);
    }

    @Test
    public void isEmptyTest(){
        ListAdapter l1 = new ListAdapter();
        assertTrue("La lista appena inizializzata non è vuota", l1.isEmpty());

        l1.add(null);
        assertFalse("La lista contenente elementi dice di essere vuota", l1.isEmpty());

        l1.clear();
        assertTrue("La lista pulita contiene degli elementi", l1.isEmpty());
        //System.out.println(l1);
    }

    @Test
    public void iteratorTest(){
        ListAdapter l1 = new ListAdapter();
        HIterator i =  l1.iterator();
        assertNotNull("L'oggetto ritornato dall'iteratore è nullo", i);
        //Altri test?
    }

    @Test
    public void lastIndexOfTest(){
        ListAdapter l1 = new ListAdapter();
        assertEquals("indexOf su lista vuota non da sempre -1 (null)", -1, l1.lastIndexOf(null));
        assertEquals("indexOf su lista vuota non da sempre -1 (0)", -1, l1.lastIndexOf(0));
        assertEquals("indexOf su lista vuota non da sempre -1 ('')", -1, l1.lastIndexOf(""));

        l1.add(null);
        assertEquals("indexOf su lista [null] non trova null", 0, l1.lastIndexOf(null));
        assertEquals("indexOf su lista [null] trova elementi non presenti (0)", -1, l1.lastIndexOf(0));
        
        l1.clear();
        listFiller(l1, 5);
        l1.add(0, "x");
        l1.add(2, "x");
        l1.add(5, "x");
        assertEquals("indexOf non ritorna la prima occorrenza dell'elemento cercato (x)", 5, l1.lastIndexOf("x"));
        l1.remove(5);
        l1.remove(2);
        assertEquals("indexOf non ritorna la prima occorrenza dell'elemento cercato dopo una rimozione (x)", 0, l1.lastIndexOf("x"));
        //System.out.println(l1);
    }

    @Test
    public void listIteratorTest(){
        ListAdapter l1 = new ListAdapter();
        HIterator i = null;
        try{
            i = l1.listIterator();
            assertNotNull("L'inizializzazione dell'iteratore su una lista vuota non lancia IndexOutOfBoundsException", i);
        }catch(IndexOutOfBoundsException ioobe){
            assertNull("L'iteratore è inizializzato pur avendo lanciato un'eccezione", i);
        }
        
        
        assertThrows("L'inizializzazione dell'iteratore in una posizione non disponibile (-1) non lancia IndexOutOfBoundsException", IndexOutOfBoundsException.class, () -> {l1.listIterator(-1);});
        assertThrows("L'inizializzazione dell'iteratore in una posizione non disponibile (0) non lancia IndexOutOfBoundsException", IndexOutOfBoundsException.class, () -> {l1.listIterator(0);});
        assertThrows("L'inizializzazione dell'iteratore in una posizione non disponibile (1) non lancia IndexOutOfBoundsException", IndexOutOfBoundsException.class, () -> {l1.listIterator(1);});
        l1.add(null);
        assertThrows("L'inizializzazione dell'iteratore in una posizione non disponibile (-1) non lancia IndexOutOfBoundsException", IndexOutOfBoundsException.class, () -> {l1.listIterator(-1);});
        assertNotNull("L'inizializzazione dell'iteratore in posizione 0 ritorna null", l1.listIterator(0));
        assertThrows("L'inizializzazione dell'iteratore in una posizione non disponibile (1) non lancia IndexOutOfBoundsException", IndexOutOfBoundsException.class, () -> {l1.listIterator(1);});
        
        //Altri test?
    }









    private void listFiller(HCollection l, int number){
        for(int i = 0; i < number; i++)
            l.add("Test");
    }
}