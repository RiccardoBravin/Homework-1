package myTest;
//Adapter
import myAdapter.ListAdapter;
import myAdapter.ListAdapter.ListIteratorAdapter;
//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;
import org.junit.runner.*;
import org.junit.Test;
//Extra
import java.util.NoSuchElementException;


public class ListIteratorTester {

    @Test 
    public void constructorTest(){
        ListAdapter l1 = new ListAdapter();
        
        assertThrows("Il costruttore non lancia IllegalArgumentException se viene passata una lista nulla", IllegalArgumentException.class, () -> {l1.new ListIteratorAdapter(null);});
        assertThrows("Il costruttore non lancia IllegalArgumentException se viene passata una lista nulla e indice corretto", IllegalArgumentException.class, () -> {l1.new ListIteratorAdapter(null,0);});
        assertThrows("Il costruttore non lancia IllegalArgumentException se viene passata una lista nulla e indici corretti", IllegalArgumentException.class, () -> {l1.new ListIteratorAdapter(null,0,1,0);});

        assertThrows("Il costruttore non lancia IndexOutOfBoundsException se viene passata una lista corretta e indice non valido (-1)", IndexOutOfBoundsException.class, () -> {l1.new ListIteratorAdapter(l1,-1);});
        assertThrows("Il costruttore non lancia IndexOutOfBoundsException se viene passata una lista corretta e indice non valido (1)", IndexOutOfBoundsException.class, () -> {l1.new ListIteratorAdapter(l1,1);});

        assertThrows("Il costruttore non lancia IndexOutOfBoundsException se viene passata una lista corretta e indici non validi (-1,0,0)", IndexOutOfBoundsException.class, () -> {l1.new ListIteratorAdapter(l1,-1,0,0);});
        assertThrows("Il costruttore non lancia IndexOutOfBoundsException se viene passata una lista corretta e indici non validi (0,0,0)", IndexOutOfBoundsException.class, () -> {l1.new ListIteratorAdapter(l1,0,0,0);});
        assertThrows("Il costruttore non lancia IndexOutOfBoundsException se viene passata una lista corretta e indici non validi (0,1,-1)", IndexOutOfBoundsException.class, () -> {l1.new ListIteratorAdapter(l1,0,1,-1);});
        assertThrows("Il costruttore non lancia IndexOutOfBoundsException se viene passata una lista corretta e indici non validi (0,1,1)", IndexOutOfBoundsException.class, () -> {l1.new ListIteratorAdapter(l1,0,1,1);});
        assertThrows("Il costruttore non lancia IndexOutOfBoundsException se viene passata una lista corretta e indici non validi (1,0,0)", IndexOutOfBoundsException.class, () -> {l1.new ListIteratorAdapter(l1,1,0,0);});
        assertThrows("Il costruttore non lancia IndexOutOfBoundsException se viene passata una lista corretta e indici non validi (0,-1,0)", IndexOutOfBoundsException.class, () -> {l1.new ListIteratorAdapter(l1,0,-1,0);});

        assertNotNull("Il costruttore dell'iteratore ritrna valore nullo (1)", l1.new ListIteratorAdapter(l1));
        assertNotNull("Il costruttore dell'iteratore ritrna valore nullo (2)", l1.new ListIteratorAdapter(l1,0));
        
        l1.add("A");
        assertNotNull("Il costruttore dell'iteratore ritrna valore nullo (3)", l1.new ListIteratorAdapter(l1,0,1,0));
        
        l1.add("A");
        l1.add("A");
        l1.add("A");
        l1.add("A");
        l1.add("A");
        
        assertNotNull("Il costruttore dell'iteratore ritrna valore nullo (4)", l1.new ListIteratorAdapter(l1,2));
        assertNotNull("Il costruttore dell'iteratore ritrna valore nullo (3)", l1.new ListIteratorAdapter(l1,1,3,2));

    }

    @Test
    public void addTest(){
        ListAdapter l1 = new ListAdapter();
        ListAdapter.ListIteratorAdapter li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1);
        li1.add("A");
        assertEquals("L'inserimento di A tramite iteratore non è andato a buon fine", "A", l1.get(0));

        li1.add(5);
        assertEquals("L'inserimento di 5 tramite iteratore non è andato a buon fine", 5, l1.get(1));

        li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,1);
        li1.add(null);
        assertEquals("L'inserimento di null tramite iteratore non è andato a buon fine", null, l1.get(1));

        li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,0,1,0);
        li1.add(1.1);
        assertEquals("L'inserimento di 1.1 tramite iteratore non è andato a buon fine", 1.1, l1.get(0));
    }

    @Test
    public void hasNextTest(){
        ListAdapter l1 = new ListAdapter();
        ListAdapter.ListIteratorAdapter li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1);
        assertFalse("L'iteratore crede di avere un elemento successivo su lista vuota", li1.hasNext());
        li1.add("A");
        assertFalse("L'iteratore crede di avere un elemento successivo quando in ultima posizione", li1.hasNext());
        
        li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,0);
        assertTrue("L'iteratore crede di non avere successivi mentre è in posizione 0 con lista non vuota", li1.hasNext());
        li1.add("B");

        li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,0,1,1);
        assertFalse("L'iteratore crede di avere un elemento successivo quando la lista ha successivi ma questo è limitato", li1.hasNext());

    }
    
    @Test
    public void hasPrevTest(){
        ListAdapter l1 = new ListAdapter();
        ListAdapter.ListIteratorAdapter li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1);
        assertFalse("L'iteratore crede di avere un elemento successivo su lista vuota", li1.hasPrevious());
        li1.add("A");
        assertTrue("L'iteratore crede di non avere un elemento che lo precede in ultima posizione su lista non vuota", li1.hasPrevious());
        
        li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,0);
        assertFalse("L'iteratore crede di avere precendenti mentre è in posizione 0 con lista non vuota", li1.hasPrevious());
        li1.add("B");

        li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,1,2,0);
        assertFalse("L'iteratore crede di avere un elemento precedente quando la lista ha precendenti ma questo è limitato", li1.hasPrevious());
    }

    @Test
    public void nextTest(){
        ListAdapter l1 = new ListAdapter();
        ListAdapter.ListIteratorAdapter li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1);
        
        assertThrows("Quando la lista non ha elementi successivi e viene chiamato next non lancia NoSuchElementException", NoSuchElementException.class, () -> {li1.next();});
        l1.add("A");
        l1.add("B");
        l1.add("C");

        ListAdapter.ListIteratorAdapter li2 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1);

        assertEquals("Next non ritorna l'elemento successivo corretto A", "A", li2.next());
        li2.previous();
        assertEquals("Next non ritorna l'elemento successivo corretto A", "A", li2.next());
        assertEquals("Next non ritorna l'elemento successivo corretto B", "B", li2.next());
        assertEquals("Next non ritorna l'elemento successivo corretto C", "C", li2.next());

        li2 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,2);
        assertEquals("Next non ritorna l'elemento successivo corretto C", "C", li2.next());

        ListAdapter.ListIteratorAdapter li3 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,1,2,0);
        assertEquals("Next non ritorna l'elemento successivo corretto B", "B", li3.next());
        assertThrows("Quando la lista non ha elementi successivi e viene chiamato next non lancia NoSuchElementException", NoSuchElementException.class, () -> {li3.next();});
    }


    @Test
    public void nextIndexTest(){
        ListAdapter l1 = new ListAdapter();
        ListAdapter.ListIteratorAdapter li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1);
        
        assertEquals("Se la lista è vuota non viene ritonato il valore corretto da nextIndex", 0, li1.nextIndex());

        l1.add("A");
        l1.add("B");
        l1.add("C");

        li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1);

        assertEquals("NextIndex non ritorna l'indice successivo corretto 0", 0, li1.nextIndex());
        li1.next();
        assertEquals("NextIndex non ritorna l'indice successivo corretto 1", 1, li1.nextIndex());
        li1.previous();
        assertEquals("NextIndex non ritorna l'indice successivo corretto 2", 0, li1.nextIndex());

        li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,2);
        assertEquals("NextIndex non ritorna l'indice successivo corretto 2", 2, li1.nextIndex());

        ListAdapter.ListIteratorAdapter li3 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,1,2,0);
        assertEquals("NextIndex non ritorna l'indice successivo corretto 1", 0, li3.nextIndex());
        li3.next();
        assertEquals("NextIndex non ritorna la dimensione della lista dopo aver tornato l'ultimo elemento", 1, li3.nextIndex());
    }

    @Test
    public void prevTest(){
        ListAdapter l1 = new ListAdapter();
        ListAdapter.ListIteratorAdapter li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1);
        
        assertThrows("Quando la lista non ha elementi precedenti e viene chiamato previous non lancia NoSuchElementException", NoSuchElementException.class, () -> {li1.previous();});
        l1.add("A");
        l1.add("B");
        l1.add("C");

        ListAdapter.ListIteratorAdapter li2 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,3);

        assertEquals("Previous non ritorna l'elemento precedente corretto C", "C", li2.previous());
        li2.next();
        assertEquals("Previous non ritorna l'elemento precedente corretto C", "C", li2.previous());
        assertEquals("Previous non ritorna l'elemento precedente corretto B", "B", li2.previous());
        assertEquals("Previous non ritorna l'elemento precedente corretto A", "A", li2.previous());

        li2 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,1);
        assertEquals("Previous non ritorna l'elemento precedente corretto A", "A", li2.previous());

        ListAdapter.ListIteratorAdapter li3 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,1,2,1);
        assertEquals("Previous non ritorna l'elemento precedente corretto B", "B", li3.previous());
        assertThrows("Quando la lista non ha elementi precedenti e viene chiamato previous non lancia NoSuchElementException", NoSuchElementException.class, () -> {li3.previous();});
    }


    @Test
    public void prevIndexTest(){
        ListAdapter l1 = new ListAdapter();
        ListAdapter.ListIteratorAdapter li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1);
        
        assertEquals("Se la lista è vuota non viene ritonato il valore corretto da nextIndex", -1, li1.previousIndex());

        l1.add("A");
        l1.add("B");
        l1.add("C");

        li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1);

        assertEquals("PreviousIndex non ritorna l'indice precedente corretto 0", -1, li1.previousIndex());
        li1.next();
        assertEquals("PreviousIndex non ritorna l'indice precedente corretto 1", 0, li1.previousIndex());
        li1.previous();
        assertEquals("PreviousIndex non ritorna l'indice precedente corretto 2", -1, li1.previousIndex());

        li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,2);
        assertEquals("PreviousIndex non ritorna l'indice precedente corretto 2", 1, li1.previousIndex());

        ListAdapter.ListIteratorAdapter li3 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,1,2,0);
        assertEquals("PreviousIndex non ritorna l'indice precedente corretto 1", -1, li3.previousIndex());
        li3.next();
    }

    @Test
    public void removeTest(){
        ListAdapter l1 = new ListAdapter();
        ListAdapter.ListIteratorAdapter li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1);
        li1.add("X");
        assertThrows("remove non lancia IllegalStateException quando viene fatto prima di next o previous", IllegalStateException.class, () -> {li1.remove();});
        assertEquals("previous non ritorna l'lemento corretto","X",li1.previous());
        li1.remove();
        assertTrue("remove non rimuove correttamente dalla lista", l1.isEmpty());

        li1.add("A");
        li1.add("B");
        li1.add("C");
        li1.add("D");

        ListAdapter.ListIteratorAdapter li2 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,2);
        assertThrows("remove non lancia IllegalStateException quando viene fatto prima di next o previous quando è in mezzo alla lista", IllegalStateException.class, () -> {li2.remove();});
        li2.next();
        li2.remove();

        assertArrayEquals("Il remove non ha rimosso correttamente l'ultimo elemento chiamato dalla lista", new Object[] {"A", "B", "D"}, l1.toArray());

        l1.add(2, "C");

        ListAdapter.ListIteratorAdapter li3 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,1,3,0);
        li3.next();
        
        li3.remove();
        assertEquals("PreviousIndex non ritorna Il valore corretto dopo il remove","C",li3.previous());

    }



    @Test
    public void setTest(){
        ListAdapter l1 = new ListAdapter();
        ListAdapter.ListIteratorAdapter li1 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1);
        assertThrows("set non lancia IllegalStateException quando viene fatto prima di next o previous", IllegalStateException.class, () -> {li1.set(1);});
        li1.add("X");
        li1.previous();
        li1.set("A");
        assertEquals("Set non ha correttamente cambiato il valore nella lista", "A", li1.next());

        l1.add("B");
        l1.add("C");
        l1.add("D");

        ListAdapter.ListIteratorAdapter li2 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,1);
        assertThrows("set non lancia IllegalStateException quando viene fatto prima di next o previous quando è in mezzo alla lista", IllegalStateException.class, () -> {li2.set(1);});
        li2.next();
        li2.set("X");
        assertArrayEquals("Il set non ha correttamente sostituito l'ultimo elemento chiamato dalla lista", new Object[] {"A", "X", "C", "D"}, l1.toArray());

        ListAdapter.ListIteratorAdapter li3 = (ListAdapter.ListIteratorAdapter) l1.new ListIteratorAdapter(l1,1,3,0);
        li3.next();
        li3.set("XX");
        assertEquals("PreviousIndex non non ritorna Il valore corretto dopo il set XX","XX",li3.previous());

    }

}
