import myAdapter.*;
import org.hamcrest.*;
import org.hamcrest.core.IsEqual;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.plaf.BorderUIResource.EmptyBorderUIResource;

import org.junit.runner.*;
import org.junit.Test;

public class ListIteratorTester {
    @Test
    public void constructor1Test() {
        HList list = new ListAdapter();

        assertNotNull("ListIteratorAdapter(HList) crea un oggetto null", new ListIteratorAdapter(list));
    }

    @Test
    public void constructor2Test () {
        HList list = new ListAdapter();

        assertThrows("ListIteratorAdapter(HList, int) non lancia IndexOutOfBoundException con indice negativo", IndexOutOfBoundsException.class, ()->{new ListIteratorAdapter(list, -1);});
        
        assertThrows("ListIteratorAdapter(HList, int) non lancia IndexOutOfBoundException con indice maggiorei di size()", IndexOutOfBoundsException.class, ()->{new ListIteratorAdapter(list, 1);});

        assertNotNull("ListIteratorAdapter(HList, int) crea un oggetto null", new ListIteratorAdapter(list, 0));
    }

    @Test
    public void constructor3Test () {
        HList list = new ListAdapter();

        assertThrows("ListIteratorAdapter(HList, int, int) non lancia IndexOutOfBoundException (-1, 0)", IndexOutOfBoundsException.class, ()->{new ListIteratorAdapter(list, -1, 0);});
        
        assertThrows("ListIteratorAdapter(HList, int, int) non lancia IndexOutOfBoundException (0, 1)", IndexOutOfBoundsException.class, ()->{new ListIteratorAdapter(list, 0, 1);});

        list.add(new Object());
        assertThrows("ListIteratorAdapter(HList, int, int) non lancia IndexOutOfBoundException (1, 0)", IndexOutOfBoundsException.class, ()->{new ListIteratorAdapter(list, 1, 0);});

        assertNotNull("ListIteratorAdapter(HList, int, int) crea un oggetto null", new ListIteratorAdapter(list, 0, 0));
    }

    @Test
    public void constructor4Test () {
        HList list = new ListAdapter();
        list.add(new Object());
        list.add(new Object());

        assertThrows("ListIteratorAdapter(HList, int, int) non lancia IndexOutOfBoundException (-1, 0, 0)", IndexOutOfBoundsException.class, ()->{new ListIteratorAdapter(list, -1, 0, 0);});
        
        assertThrows("ListIteratorAdapter(HList, int, int) non lancia IndexOutOfBoundException (0, 3, 1)", IndexOutOfBoundsException.class, ()->{new ListIteratorAdapter(list, 0, 3, 1);});

        assertThrows("ListIteratorAdapter(HList, int, int) non lancia IndexOutOfBoundException (1, 0, 1)", IndexOutOfBoundsException.class, ()->{new ListIteratorAdapter(list, 1, 0, 1);});

        assertThrows("ListIteratorAdapter(HList, int, int) non lancia IndexOutOfBoundException (1, 2, 0)", IndexOutOfBoundsException.class, ()->{new ListIteratorAdapter(list, 1, 2, 0);});

        assertThrows("ListIteratorAdapter(HList, int, int) non lancia IndexOutOfBoundException (0, 2, 3)", IndexOutOfBoundsException.class, ()->{new ListIteratorAdapter(list, 0, 2, 3);});

        assertNotNull("ListIteratorAdapter(HList, int, int) crea un oggetto null", new ListIteratorAdapter(list, 0, 2, 1));
    }

    @Test
    public void addTest() {
        HList emptyList = new ListAdapter();
        Object obj = new Object();

        emptyList.add(obj);
        assertTrue("add() non funziona su una lista vuota", emptyList.get(0).equals(obj));

        emptyList.add(null);
        assertTrue("add() non funziona su una lista non vuota", emptyList.get(1) == null);
    }

    @Test
    public void hasNextTest() {
        HList list = new ListAdapter();
        ListIteratorAdapter iter1 = new ListIteratorAdapter(list);

        assertFalse("hasNext() ritorna true su una lista vuota", iter1.hasNext());

        list.add(new Object());
        ListIteratorAdapter iter2 = new ListIteratorAdapter(list);
        assertTrue("hasNext() ritorna false su una lista non vuota senza aver chiamato next()", iter2.hasNext());

        ListIteratorAdapter iter3 = new ListIteratorAdapter(list, 1);
        assertFalse("hasNext() ritorna true su una lista non vuota con cursore alla fine", iter3.hasNext());
    }

    @Test
    public void hasPreviousTest() {
        HList list = new ListAdapter();
        ListIteratorAdapter iter1 = new ListIteratorAdapter(list);

        assertFalse("hasPrevious() ritorna true su una lista vuota", iter1.hasPrevious());

        list.add(new Object());
        ListIteratorAdapter iter2 = new ListIteratorAdapter(list, 0, 1, 1);
        assertTrue("hasPrevious() ritorna false su una lista non vuota con cursore non all'inizio", iter2.hasPrevious());

        ListIteratorAdapter iter3 = new ListIteratorAdapter(list);
        assertFalse("hasPrevious() ritorna true su una lista non vuota con cursore all'inizio'", iter3.hasPrevious());
    }

    @Test
    public void nextTest() {
        HList list = new ListAdapter();
        ListIteratorAdapter iter1 = new ListIteratorAdapter(list);

        assertThrows("next() non lancia NoSuchElementException su una lista vuota", NoSuchElementException.class, ()->{iter1.next();});
    
        Object obj = new Object();
        list.add(obj);
        ListIteratorAdapter iter2 = new ListIteratorAdapter(list);
        assertEquals("next() non ritorna l'elemento corretto", obj, iter2.next());

        assertThrows("next() non lancia NoSuchElementException al termine di una lista", NoSuchElementException.class, ()->{iter2.next();});
    }

    @Test
    public void nextIndexTest() {
        HList list = new ListAdapter();
        ListIteratorAdapter iter1 = new ListIteratorAdapter(list);

        assertEquals("nextIndex non torna 0 su una lista vuota", 0, iter1.nextIndex());

        list.add(new Object());
        ListIteratorAdapter iter2 = new ListIteratorAdapter(list);
        assertEquals("nextIndex() non ritorna l'indice corretto (0)", 0, iter2.nextIndex());

        iter2.next();
        assertEquals("nextIndex() non ritorna l'indice corretto (1)", 1, iter2.nextIndex());
    }

    @Test
    public void previousTest() {
        HList list = new ListAdapter();
        ListIteratorAdapter iter1 = new ListIteratorAdapter(list);

        assertThrows("previous() non lancia NoSuchElementException su una lista vuota", NoSuchElementException.class, ()->{iter1.previous();});
    
        Object obj = new Object();
        list.add(obj);
        ListIteratorAdapter iter2 = new ListIteratorAdapter(list);
        iter2.next();
        assertEquals("previous() non ritorna l'elemento corretto", obj, iter2.previous());

        assertThrows("previous() non lancia NoSuchElementException all'inizio della lista", NoSuchElementException.class, ()->{iter2.previous();});
    }

    @Test
    public void previousIndexTest() {
        HList list = new ListAdapter();
        ListIteratorAdapter iter1 = new ListIteratorAdapter(list);

        assertEquals("previousIndex() non torna -1 su una lista vuota", -1, iter1.previousIndex());

        list.add(new Object());
        ListIteratorAdapter iter2 = new ListIteratorAdapter(list);
        iter2.next();
        assertEquals("nextIndex() non ritorna l'indice corretto (0)", 0, iter2.previousIndex());

        iter2.previous();
        assertEquals("nextIndex() non ritorna l'indice corretto (-1)", -1, iter2.previousIndex());
    }

    @Test
    public void removeTest() {
        HList list = new ListAdapter();
        ListIteratorAdapter iter1 = new ListIteratorAdapter(list);
        assertThrows("remove() non lancia IllegalStateExcpetion su una lista vuota", IllegalStateException.class, ()->{iter1.remove();});

        list.add(new Object());
        list.add(new Object());
        ListIteratorAdapter iter2 = new ListIteratorAdapter(list);

        iter2.next();
        iter2.remove();
        assertEquals("remove() non rimuove", 1, list.size());

        assertThrows("remove() non viene bloccato alla seconda chiamata", IllegalStateException.class, ()->{iter2.remove();});

        ListIteratorAdapter iter3 = new ListIteratorAdapter(list);
        iter3.add(new Object());
        assertThrows("remove() non viene bloccato dopo una chiamata ad add()", IllegalStateException.class, ()->{iter3.remove();});
    }

    @Test
    public void setTest() {
        HList list = new ListAdapter();
        ListIteratorAdapter iter1 = new ListIteratorAdapter(list);
        assertThrows("set(Object) non lancia IllegalStateExcpetion su una lista vuota", IllegalStateException.class, ()->{iter1.set(new Object());});

        list.add(new Object());
        list.add(new Object());
        ListIteratorAdapter iter2 = new ListIteratorAdapter(list);

        Object obj = new Object();
        iter2.next();
        iter2.set(obj);
        assertEquals("set(Object) non setta correttamente l'elemento", obj, list.get(0));

        iter2.remove();
        assertThrows("set(Object) non viene bloccato dopoi una chimata a remove()", IllegalStateException.class, ()->{iter2.set(new Object());});

        ListIteratorAdapter iter3 = new ListIteratorAdapter(list);
        iter3.add(new Object());
        assertThrows("set(Object) non viene bloccato dopo una chiamata ad add()", IllegalStateException.class, ()->{iter3.set(new Object());});
    }
}