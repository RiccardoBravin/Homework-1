import org.hamcrest.*;
import org.hamcrest.core.IsEqual;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.plaf.BorderUIResource.EmptyBorderUIResource;

import org.junit.runner.*;


import org.junit.Test;

public class RangedListTester {
    
    @Test
    public void constructorTest() {
        ListAdapter list = new ListAdapter();

        Object obj1 = new Object();
        Object obj2 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;

        list.add(obj1);
        list.add(objNull1);
        list.add(obj2);
        list.add(objNull2);

        RangedListAdapter rlist = new RangedListAdapter(list, 2, 4);

        assertNotNull("Il costruttore crea un oggetto null", rlist);
        assertEquals("Il costruttore non espone un oggetto della giusta lunghezza", 2, rlist.size());
    }

    @Test
    public void addTest() {
        ListAdapter list = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        Object obj5 = new Object();
        Object obj6 = new Object();
        
        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);

        RangedListAdapter rlist = new RangedListAdapter(list, 0, 4);
        rlist.add(obj4);
        assertEquals("add() non agisce correttamente", obj4, list.get(4));

        RangedListAdapter rlist1 = new RangedListAdapter(list, 0, 0);
        rlist1.add(obj5);
        assertEquals("add() non agisce correttamente", obj5, list.get(0));

        RangedListAdapter rlist3 = new RangedListAdapter(list, 1, 3);
        rlist3.add(obj6);
        assertEquals("add() non agisce correttamente", obj6, list.get(3));

        assertThrows("la sottolista non viene invalidata dopo un add()", IllegalStateException.class, ()->{rlist3.add(obj6);});
    }

    public void addIndex() {
        ListAdapter list = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        Object obj5 = new Object();
        Object obj6 = new Object();
        
        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);

        RangedListAdapter rlist = new RangedListAdapter(list, 1, 3);
        rlist.add(0, obj4);
        assertEquals("add(int) non agisce correttamente", obj4, list.get(1));
        
        RangedListAdapter rlist1 = new RangedListAdapter(list, 1, 3);
        rlist1.add(1, obj5);
        assertEquals("add() non agisce correttamente", obj5, list.get(2));

        RangedListAdapter rlist2 = new RangedListAdapter(list, 1, 3);
        rlist2.add(2, obj6);
        assertEquals("add() non agisce correttamente", obj6, list.get(3));

        assertThrows("la sottolista non viene invalidata dopo un add(int)", IllegalStateException.class, ()->{rlist2.add(2, obj6);});

        RangedListAdapter rlist3 = new RangedListAdapter(list, 1, 3);
        assertThrows("sublist() non lancia IndexOutOfBoundException con indice negativo", IndexOutOfBoundsException.class, ()->{rlist3.add(-1, obj6);});
        assertThrows("sublist() non lancia IndexOutOfBoundException con indice maggiore della dimensione", IndexOutOfBoundsException.class, ()->{rlist3.add(10, obj6);});
    }

    @Test 
    public void addAllTest() {
        ListAdapter list = new ListAdapter();
        HCollection collection = new ListAdapter();
        ListAdapter ref = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        Object obj5 = new Object();
        Object obj6 = new Object();
        
        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);

        collection.add(obj5);
        collection.add(obj6);

        ref.add(obj1);
        ref.add(obj2);
        ref.add(obj3);
        ref.add(obj5);
        ref.add(obj6);
        ref.add(obj4);

        RangedListAdapter rlist = new RangedListAdapter(list, 1, 3);
        assertTrue("addAll(int, HCollection) non torna true", rlist.addAll(collection));
        assertEquals("addAll(HCollection) non inserisce correttamente", ref, list);

        assertThrows("la sottolista non viene invalidata dopo un addAll(HCollection))", IllegalStateException.class, ()->{rlist.addAll(collection);});
    }

    @Test
    public void addAllIndexTest() {
        ListAdapter list = new ListAdapter();
        HCollection collection = new ListAdapter();
        ListAdapter ref = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        Object obj5 = new Object();
        Object obj6 = new Object();
        
        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);

        collection.add(obj5);
        collection.add(obj6);

        ref.add(obj1);
        ref.add(obj2);
        ref.add(obj5);
        ref.add(obj6);
        ref.add(obj3);
        ref.add(obj4);

        RangedListAdapter rlist = new RangedListAdapter(list, 1, 3);
        assertTrue("addAll(int, HCollection) non torna true", rlist.addAll(1, collection));
        assertEquals("addAll(int, HCollection) non inserisce correttamente", ref, list);

        assertThrows("la sottolista non viene invalidata dopo un addAll(int, HCollection))", IllegalStateException.class, ()->{rlist.addAll(1, collection);});
        
        RangedListAdapter rlist1 = new RangedListAdapter(list, 1, 3);
        assertThrows("addAll(int, HCollection) non lancia IndexOutOfBoundException con indice negativo", IndexOutOfBoundsException.class, ()->{rlist1.addAll(-1, collection);});
        assertThrows("addAll(int, HCollection) non lancia IndexOutOfBoundException con indice maggiore della sua dimensione", IndexOutOfBoundsException.class, ()->{rlist1.addAll(5, collection);});

        //non testo NullPointerException perchè vengono lanciate dai metodi di ListAdapter testati separatamente.
    }

    @Test
    public void clearTest(){
        ListAdapter list = new ListAdapter();
        ListAdapter ref = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);

        ref.add(obj1);
        ref.add(obj4);

        RangedListAdapter rlist = new RangedListAdapter(list, 1, 3);
        rlist.clear();

        assertEquals("clear() non agisce correttamente", ref, list);

        assertThrows("la sottolista non viene invalidata dopo un clear())", IllegalStateException.class, ()->{rlist.clear();});
    }

    @Test
    public void containsTest() {
        ListAdapter list = new ListAdapter();
        
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object objNull = null;
        
        list.add(obj1);
        list.add(obj2);
        list.add(objNull);

        RangedListAdapter rlist = new RangedListAdapter(list, 0, 0);
        assertFalse("contains() trova qualcosa in una sublist vuota", rlist.contains(obj1));

        RangedListAdapter rlist1 = new RangedListAdapter(list, 1, 3);
        assertFalse("contains() trova qualcosa che non è presente nemmeno nell'intera lista", rlist1.contains(obj3));
        assertFalse("contains() trova qualcosa che non è presente nella sublist ma nella lista", rlist1.contains(obj1));

        assertTrue("contains() non trova un elemento !=null che è presente nella sublist", rlist1.contains(obj2));
        assertTrue("contains() non trova un elemento null che è presente nella sublist", rlist1.contains(objNull));

        list.add(new Object());
        assertThrows("contains() non viene invalidato da una chiamata ad add()", IllegalStateException.class, ()->{rlist.contains(null);});
    }

    @Test
    public void containsAllTest() {
        ListAdapter list = new ListAdapter();
        HCollection collection = new ListAdapter();
        HCollection collection1 = new ListAdapter();
        HCollection emptyCollection = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        Object obj5 = new Object();
        Object obj6 = new Object();
        
        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);

        collection.add(obj5);
        collection.add(obj6);

        collection1.add(obj2);
        collection1.add(obj3);

        RangedListAdapter rlist = new RangedListAdapter(list, 0, 0);
        assertFalse("containsAll(HCollection) restituisce true su una sublist vuota", rlist.containsAll(collection));
        assertFalse("containsAll(HCollection) parrebbe che l'insieme vuoto sia contenuto nell'insieme vuoto...", rlist.containsAll(emptyCollection));

        RangedListAdapter rlist1 = new RangedListAdapter(list, 1, 3);
        assertTrue("containsAll() restituisce false pur essendo presenti gli elementi nella sottolista", rlist1.containsAll(collection1));

        rlist.add(new Object());
        assertThrows("containsAll() non viene invalidato da una chiamata ad add()", IllegalStateException.class, ()->{rlist.containsAll(null);});
    }

    @Test
    public void equalsTest() {
        ListAdapter list1 = new ListAdapter();
        ListAdapter list2 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list1.add(obj1);
        list1.add(obj2);
        list1.add(obj3);
        list1.add(obj4);

        list2.add(obj1);
        list2.add(obj2);
        list2.add(obj3);
        list2.add(obj4);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 0, 0);
        RangedListAdapter rlist2 = new RangedListAdapter(list1, 1, 3);
        RangedListAdapter rlist3 = new RangedListAdapter(list2, 0, 3);

        assertTrue("equals() restituisce false fra lo stesso oggetto vuoto", rlist1.equals(rlist1));
        assertTrue("equals() restituisce false fra lo stesso oggetto non vuoto", rlist2.equals(rlist2));
        assertTrue("equals() restituisce false fra lo stesso oggetto creato da due liste diverse", rlist2.equals(rlist2));
        assertFalse("equals() restituisce true fra lo un oggetto vuoto e uno no", rlist1.equals(rlist2));
        assertFalse("equals() restituisce true fra due oggetti non vuoti diversi", rlist2.equals(rlist3));
        assertFalse("equals() restituisce true fra un oggetto e un null", rlist2.equals(null));

        rlist1.add(new Object());
        assertThrows("equals() non viene invalidato da una chiamata ad add()", IllegalStateException.class, ()->{rlist1.equals(null);});
    }

    @Test
    public void getTest() {
        ListAdapter list1 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list1.add(obj1);
        list1.add(obj2);
        list1.add(null);
        list1.add(obj3);
        list1.add(obj4);

        RangedListAdapter rlist = new RangedListAdapter(list1, 1, 4);

        assertThrows("get(int) non lancia IndexOutOfBoundException con indice negativo", IndexOutOfBoundsException.class, ()->{rlist.get(-1);});
        assertThrows("get(int) non lancia IndexOutOfBoundException con indice maggiore della dimensione", IndexOutOfBoundsException.class, ()->{rlist.get(3);});

        assertEquals("get(int) non restituisce il valore all'inizio della sublist", obj2, rlist.get(0));
        assertEquals("get(int) non restituisce il valore in mezzo alla lista", null, rlist.get(1));
        assertEquals("get(int) non restituisce il valore alla fine della sublist", obj3, rlist.get(2));

        rlist.add(new Object());
        assertThrows("get(int) non viene invalidato da una chiamata ad add()", IllegalStateException.class, ()->{rlist.get(0);});
    }

    @Test 
    public void hashCodeTest() {
        ListAdapter list1 = new ListAdapter();
        Object obj1 = 1;
        Object obj2 = 2;
        Object obj3 = 'x';
        Object obj4 = "ciao";
        
        list1.add(obj1);
        list1.add(obj2);
        list1.add(null);
        list1.add(obj3);
        list1.add(obj4);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 0, 0);
        RangedListAdapter rlist2 = new RangedListAdapter(list1, 1, 4);
        
        assertEquals("hashCode() non restituisce il risultato corretto (1)", 1, rlist1.hashCode());
        assertEquals("hashCode() non restituisce il risultato corretto (1)", 31833, rlist2.hashCode());

        rlist1.add(new Object());
        assertThrows("hashCode(int) non viene invalidato da una chiamata ad add()", IllegalStateException.class, ()->{rlist1.hashCode();});
    }

    @Test 
    public void indexOfTest() {
        ListAdapter list1 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list1.add(obj1);
        list1.add(obj2);
        list1.add(null);
        list1.add(obj3);
        list1.add(obj4);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 0, 0);
        RangedListAdapter rlist2 = new RangedListAdapter(list1, 1, 4);

        assertEquals("indexOf(Object) trova quacosa in una sublist vuota", -1, rlist1.indexOf(obj1));
        assertEquals("indexOf(Object) non trova un elemento non null", 0, rlist2.indexOf(obj2));
        assertEquals("indexOf(Object) non trova un null", 1, rlist2.indexOf(null));

        rlist1.add(new Object());
        assertThrows("IndexOf(Object) non viene invalidato da una chiamata ad add()", IllegalStateException.class, ()->{rlist1.indexOf(obj1);});
    }

    @Test
    public void isEmptyTest() {
        ListAdapter list1 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list1.add(obj1);
        list1.add(obj2);
        list1.add(null);
        list1.add(obj3);
        list1.add(obj4);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 0, 0);
        RangedListAdapter rlist2 = new RangedListAdapter(list1, 1, 4);

        assertTrue("isEmpty() sbaglia su una sublist vuota", rlist1.isEmpty());
        assertFalse("isEmpty() sbaglia su una sublist non vuota", rlist2.isEmpty());

        rlist1.add(new Object());
        assertThrows("isEmpty() non viene invalidato da una chiamata ad add()", IllegalStateException.class, ()->{rlist1.isEmpty();});
    }

    @Test
    public void iteratorTest() {
        ListAdapter list1 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list1.add(obj1);
        list1.add(obj2);
        list1.add(null);
        list1.add(obj3);
        list1.add(obj4);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 0, 0);
        RangedListAdapter rlist2 = new RangedListAdapter(list1, 1, 4);

        assertNotNull("iterator() non costruisce da una sublist vuota",rlist1.iterator());
        assertNotNull("iterator() non costruisce da una sublist non vuota",rlist2.iterator());

        rlist1.add(new Object());
        assertThrows("iterator() non viene invalidato da una chiamata ad add()", IllegalStateException.class, ()->{rlist1.iterator();});
    }

    @Test
    public void lastIndexOfTest() {
        ListAdapter list1 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        
        list1.add(obj1);
        list1.add(obj2);
        list1.add(null);
        list1.add(obj1);
        list1.add(obj2);
        list1.add(obj2);
        list1.add(obj2);
        list1.add(null);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 0, 0);
        RangedListAdapter rlist2 = new RangedListAdapter(list1, 1, 6);

        assertEquals("lastIndexOf(Object) non funziona su una sublist vuota con un oggetto non null", -1, rlist1.lastIndexOf(obj2));
        assertEquals("lastIndexOf(Object) non funziona su una sublist vuota con un oggetto null", -1, rlist1.lastIndexOf(null));

        assertEquals("lastIndexOf(Object) non funziona su una sublist non vuota con un oggetto non null", 4, rlist2.lastIndexOf(obj2));
        assertEquals("lastIndexOf(Object) non funziona su una sublist non vuota con un oggett null", 1, rlist2.lastIndexOf(null));

        rlist1.add(new Object());
        assertThrows("lastIndexOf(Object) non viene invalidato da una chiamata ad add()", IllegalStateException.class, ()->{rlist1.lastIndexOf(obj1);});
    }

    @Test
    public void listIteratorTest() {
        ListAdapter list1 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        
        list1.add(obj1);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(obj2);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 0, 0);
        RangedListAdapter rlist2 = new RangedListAdapter(list1, 1, 3);

        assertNotNull("listIterator() non costruisce partendo da una sublist vuota", rlist1.listIterator());
        assertNotNull("listIterator() non costruisce partendo da una sublist non vuota", rlist2.listIterator());

        rlist1.add(new Object());
        assertThrows("listIterator() non viene invalidato da una chiamata ad add()", IllegalStateException.class, ()->{rlist1.listIterator();});
    }

    @Test
    public void ListiIteratorIndexTest() {
        ListAdapter list1 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        
        list1.add(obj1);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(obj2);

        RangedListAdapter rlist2 = new RangedListAdapter(list1, 1, 3);

        assertThrows("listIterator(int) non lancia IndexOutOfBoundException con indice negativo", IndexOutOfBoundsException.class, ()->{rlist2.listIterator(-1);});
        assertThrows("listIterator(int) non lancia IndexOutOfBoundException con indice maggiore della dimensione", IndexOutOfBoundsException.class, ()->{rlist2.listIterator(3);});

        assertNotNull("listIterator(int) non costruisce partendo da una sublist non vuota", rlist2.listIterator(0));

        rlist2.add(new Object());
        assertThrows("listIterator(int) non viene invalidato da una chiamata ad add()", IllegalStateException.class, ()->{rlist2.listIterator(1);});
    }

    @Test
    public void removeIndexTest() {
        ListAdapter list1 = new ListAdapter();
        ListAdapter list2 = new ListAdapter();
        ListAdapter list3 = new ListAdapter();
        ListAdapter list4 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        
        list1.add(obj1);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(null);
        list1.add(obj1);

        list2.add(obj1);
        list2.add(obj2);
        list2.add(obj1);
        list2.add(null);
        list2.add(obj1);

        list3.add(obj1);
        list3.add(obj2);
        list3.add(obj1);
        list3.add(null);
        list3.add(obj1);

        list4.add(obj1);
        list4.add(obj2);
        list4.add(obj1);
        list4.add(null);
        list4.add(obj1);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 1, 4);
        assertEquals("remove(int) non rimuove l'elemento all'inizio", obj2, rlist1.remove(0));          //QUESTA E' MAGIA PURA
        
        RangedListAdapter rlist2 = new RangedListAdapter(list2, 1, 4);
        assertEquals("remove(int) non rimuove l'elemento in mezzo", obj1, rlist2.remove(1));
        
        RangedListAdapter rlist3 = new RangedListAdapter(list3, 1, 4);
        assertEquals("remove(int) non rimuove l'elemento alla fine", null, rlist3.remove(2));
    
        assertThrows("remove(int) non viene invalidato alla seconda chiamata", IllegalStateException.class, ()->{rlist2.remove(1);});

        RangedListAdapter rlist4 = new RangedListAdapter(list4, 1, 4);
        assertThrows("remove(int) non lancia IndexOutOfBoundException con indice negativo", IndexOutOfBoundsException.class, ()->{rlist4.remove(-1);});
        assertThrows("remove(int) non lancia IndexOutOfBoundException con indice maggiore delle dimensione", IndexOutOfBoundsException.class, ()->{rlist4.remove(3);});
    }

    @Test
    public void removeObject() {
        ListAdapter list1 = new ListAdapter();
        ListAdapter list2 = new ListAdapter();
        ListAdapter list3 = new ListAdapter();
        ListAdapter list4 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list1.add(obj3);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(null);
        list1.add(obj4);

        list2.add(obj3);
        list2.add(obj2);
        list2.add(obj1);
        list2.add(null);
        list2.add(obj4);

        list3.add(obj3);
        list3.add(obj2);
        list3.add(obj1);
        list3.add(null);
        list3.add(obj4);

        list4.add(obj3);
        list4.add(obj2);
        list4.add(obj1);
        list4.add(null);
        list4.add(obj4);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 1, 4);
        assertTrue("remove(Object) non rimuove l'elemento all'inizio(1)", rlist1.remove(obj1) && !list1.contains(obj1));
          
        
        RangedListAdapter rlist2 = new RangedListAdapter(list2, 1, 4);
        assertTrue("remove(Object) non rimuove l'elemento in mezzo(1)", rlist2.remove(obj2) && !list2.contains(obj2));
       
        
        RangedListAdapter rlist3 = new RangedListAdapter(list3, 1, 4);
        assertTrue("remove(Object) non rimuove l'elemento alla fine(2)", rlist3.remove(null) && !list3.contains(null));

        RangedListAdapter rlist4 = new RangedListAdapter(list4, 1, 4);
        assertFalse("remove(Object) rimuove un elemento che non c'è", rlist4.remove(new Object()));
    
        assertThrows("remove(Object) non viene invalidato alla seconda chiamata", IllegalStateException.class, ()->{rlist2.remove(1);});
    }

    @Test
    public void removeAllTest() {
        ListAdapter list1 = new ListAdapter();
        ListAdapter list2 = new ListAdapter();
        ListAdapter list3 = new ListAdapter();
        ListAdapter list4 = new ListAdapter();
        ListAdapter list5 = new ListAdapter();
        HCollection emptyCollection = new ListAdapter();
        HCollection collection = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list1.add(obj3);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(null);
        list1.add(obj4);

        list2.add(obj3);
        list2.add(obj2);
        list2.add(obj1);
        list2.add(null);
        list2.add(obj4);

        list3.add(obj3);
        list3.add(obj2);
        list3.add(obj1);
        list3.add(null);
        list3.add(obj4);

        list4.add(obj3);
        list4.add(obj2);
        list4.add(obj1);
        list4.add(null);
        list4.add(obj4);

        list5.add(obj3);
        list5.add(obj2);
        list5.add(obj1);
        list5.add(null);
        list5.add(obj4);

        collection.add(obj1);
        collection.add(null);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 0, 0);
        assertFalse("removeAll(HCollection) rimuove una HCollection vuota da una sublist vuota", rlist1.removeAll(emptyCollection) && list1.size()!=5);

        RangedListAdapter rlist2 = new RangedListAdapter(list2, 1, 4);
        assertFalse("removeAll(HCollection) rimuove una HCollection vuota da una sublistnon vuota", rlist2.removeAll(emptyCollection) && list2.size()!=5 );

        RangedListAdapter rlist3 = new RangedListAdapter(list3, 0, 0);
        assertFalse("removeAll(HCollection) rimuove una HCollection  non vuota da una sublist vuota", rlist3.removeAll(collection) && list3.size()!=5);

        RangedListAdapter rlist4 = new RangedListAdapter(list4, 0, 2);
        assertFalse("removeAll(HCollection) rimuove una HCollection non vuota ma non presente da una sublist non vuota", rlist4.removeAll(collection) && list4.size()!=5);

        RangedListAdapter rlist5 = new RangedListAdapter(list5, 1, 4);
        assertTrue("removeAll(HCollection) non rimuove una HCollection non vuota presente da una sublist non vuota", rlist5.removeAll(collection) && list5.size()==3);
    
        assertThrows("removeAll(HCollection) non viene invalidato alla seconda chiamata", IllegalStateException.class, ()->{rlist5.removeAll(collection);});
    }

    @Test
    public void retainAllTest() {
        ListAdapter list1 = new ListAdapter();
        ListAdapter list2 = new ListAdapter();
        ListAdapter list3 = new ListAdapter();
        ListAdapter list4 = new ListAdapter();
        ListAdapter list5 = new ListAdapter();
        HCollection emptyCollection = new ListAdapter();
        HCollection collection = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list1.add(obj3);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(null);
        list1.add(obj4);

        list2.add(obj3);
        list2.add(obj2);
        list2.add(obj1);
        list2.add(null);
        list2.add(obj4);

        list3.add(obj3);
        list3.add(obj2);
        list3.add(obj1);
        list3.add(null);
        list3.add(obj4);

        list4.add(obj3);
        list4.add(obj2);
        list4.add(obj1);
        list4.add(null);
        list4.add(obj4);

        list5.add(obj3);
        list5.add(obj2);
        list5.add(obj1);
        list5.add(null);
        list5.add(obj4);

        collection.add(obj1);
        collection.add(null);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 0, 0);
        assertFalse("retainAll(HCollection) rimuove qualcosa vuota da una sublist vuota con HCollection vuota", rlist1.retainAll(emptyCollection) && list1.size()!=5);

        RangedListAdapter rlist2 = new RangedListAdapter(list2, 1, 4);
        assertTrue("retainAll(HCollection) non rimuove tutta la sublist non vuota con una HCollection vuota", rlist2.retainAll(emptyCollection) && list2.size()==2);

        RangedListAdapter rlist3 = new RangedListAdapter(list3, 0, 0);
        assertFalse("retainAll(HCollection) rimuove qualcosa da una sublist vuota con una HCollection non vuota", rlist3.retainAll(collection) && list3.size()!=5);

        RangedListAdapter rlist4 = new RangedListAdapter(list4, 0, 2);
        assertTrue ("retainAll(HCollection) non rimuove tutta la sublist non vuota che non contiene tutta la HCollection non vuota", rlist4.retainAll(collection) && list4.size()==3);

        RangedListAdapter rlist5 = new RangedListAdapter(list5, 1, 4);
        assertTrue("retainAll(HCollection) non rimuove parte della sublist non vuota contenuta nella HCollection non vuota", rlist5.retainAll(collection) && list5.size()==4);
    
        assertThrows("retainAll(HCollection) non viene invalidato alla seconda chiamata", IllegalStateException.class, ()->{rlist5.removeAll(collection);});
    }

    @Test
    public void setTest() {
        ListAdapter list1 = new ListAdapter();
        ListAdapter list2 = new ListAdapter();
        ListAdapter list3 = new ListAdapter();
        ListAdapter list4 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list1.add(obj3);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(null);
        list1.add(obj4);

        list2.add(obj3);
        list2.add(obj2);
        list2.add(obj1);
        list2.add(null);
        list2.add(obj4);

        list3.add(obj3);
        list3.add(obj2);
        list3.add(obj1);
        list3.add(null);
        list3.add(obj4);

        list4.add(obj3);
        list4.add(obj2);
        list4.add(obj1);
        list4.add(null);
        list4.add(obj4);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 1, 4);
        assertEquals("set(int, Object) non setta correttamente l'oggetto presente all'inizio della sublist non vuota (1)", obj2, rlist1.set(0, obj4));
        assertEquals("set(int, Object) non setta correttamente l'oggetto presente all'inizio della sublist non vuota (2)", obj4, list1.get(1));

        RangedListAdapter rlist2 = new RangedListAdapter(list2, 1, 4);
        assertEquals("set(int, Object) non setta correttamente l'oggetto presente in mezzo alla sublist non vuota (1)", obj1, rlist2.set(1, obj4));
        assertEquals("set(int, Object) non setta correttamente l'oggetto presente in mezzo alla sublist non vuota (2)", obj4, list2.get(2));

        RangedListAdapter rlist3 = new RangedListAdapter(list3, 1, 4);
        assertEquals("set(int, Object) non setta correttamente l'oggetto presente alla fine della sublist non vuota (1)", null, rlist3.set(2, obj4));
        assertEquals("set(int, Object) non setta correttamente l'oggetto presente alla fine della sublist non vuota (2)", obj4, list3.get(3));

        RangedListAdapter rlist4 = new RangedListAdapter(list4, 1, 4);
        assertThrows("set(int, Object) non lancia IndexOutOfBoundException con indice negativo", IndexOutOfBoundsException.class, ()->{rlist4.set(-1, obj4);});
        assertThrows("set(int, Object) non lancia IndexOutOfBoundException con indice maggiore della dimensione", IndexOutOfBoundsException.class, ()->{rlist4.set(4, obj4);});

        assertThrows("set(int, Object) non viene invalidato alla seconda chiamata", IllegalStateException.class, ()->{rlist3.set(0, null);});
    }

    @Test
    public void sizeTest() {
        ListAdapter list1 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list1.add(obj3);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(null);
        list1.add(obj4);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 0, 0);
        assertEquals("size() non restituisce 0 su una sublist vuota", 0, rlist1.size());

        RangedListAdapter rlist2 = new RangedListAdapter(list1, 1, 3);
        assertEquals("size() non restituisce la coretta dimensione", 2, rlist2.size());
    }

    @Test
    public void subListTest(){
        ListAdapter list1 = new ListAdapter();
        ListAdapter ref = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list1.add(obj3);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(null);
        list1.add(obj4);
        list1.add(obj3);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(null);
        list1.add(obj4);

        ref.add(null);
        ref.add(obj4);
        ref.add(obj3);
        ref.add(obj2);
        ref.add(obj1);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 2, 9);

        HList rrlist = rlist1.subList(1, 6);
        assertNotNull("sublist(int, int) non costruisce", rrlist);

        assertEquals("sublist(int, int) non costruisce correttamente una sub-sublist non vuota", ref, rrlist);

        assertEquals("sublist(int, int) non costruisce correttamente una sub-sublist vuota", new ListAdapter(), rlist1.subList(0,0));

        assertThrows("sublist(int, int) non lancia IndexOutOfBoundException con fromIndex negativo", IndexOutOfBoundsException.class, ()->{rlist1.subList(-1, 2);});
    
        assertThrows("sublist(int, int) non lancia IndexOutOfBoundException con toIndex > della dimensione", IndexOutOfBoundsException.class, ()->{rlist1.subList(0, 8);});

        assertThrows("sublist(int, int) non lancia IndexOutOfBoundException con fromIndex > toIndex", IndexOutOfBoundsException.class, ()->{rlist1.subList(5, 3);});

        rlist1.add(null);
        assertThrows("una sublist di sublist non viene invalidata da un add sulla sublist di origine", IllegalStateException.class, ()->{rrlist.size();});
    }

    @Test
    public void toArrayTest() {
        ListAdapter list1 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list1.add(obj3);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(null);
        list1.add(obj4);
        list1.add(obj3);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(null);
        list1.add(obj4);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 3, 8);
        RangedListAdapter rlist2 = new RangedListAdapter(list1, 5, 5);

        Object[] refArr = new Object[] {null, obj4, obj3, obj2, obj1};
        Object[] emptyArr = new Object[0];

        assertArrayEquals("toArray() non ritorna il giusto array da una sub", refArr, rlist1.toArray());

        assertArrayEquals("toArray() non ritorna il giusto array vuoto", emptyArr, rlist2.toArray());

        rlist2.add(new Object());
        assertThrows("toArray() non viene invalidato da una chiamata ad add()", IllegalStateException.class, ()->{rlist2.toArray();});
    }

    @Test
    public void toArrayWhitArrayTest() {
        ListAdapter list1 = new ListAdapter();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();
        
        list1.add(obj3);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(null);
        list1.add(obj4);
        list1.add(obj3);
        list1.add(obj2);
        list1.add(obj1);
        list1.add(null);
        list1.add(obj4);

        RangedListAdapter rlist1 = new RangedListAdapter(list1, 3, 8);

        Object[] refArr1 = new Object[] {null, obj4, obj3, obj2, obj1};
        Object[] refArr2 = new Object[] {null, obj4, obj3, obj2, obj1, null};
        Object[] emptyArr = new Object[0];

        Object[] smallArr = new Object[4];
        Object[] largeArr = new Object[6];
        Object[] correctArr = new Object[5];

        assertArrayEquals("toArray(Object[]) non funziona correttamente con un array vuoto", refArr1, rlist1.toArray(emptyArr));
        
        assertArrayEquals("toArray(Object[]) non funziona correttamente con un array di dimensione minore", refArr1, rlist1.toArray(smallArr));

        assertTrue("toArray(Object[]) non funziona correttamente con un array di dimensione corretta", rlist1.toArray(correctArr)==correctArr);

        assertArrayEquals("toArray(Object[]) non funziona correttamente con un array di dimensione maggiori", refArr2, rlist1.toArray(largeArr));

        rlist1.add(new Object());
        assertThrows("toArray(Object[]) non viene invalidato da una chiamata ad add()", IllegalStateException.class, ()->{rlist1.toArray(emptyArr);});
    }
}
