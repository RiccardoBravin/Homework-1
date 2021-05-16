//import myAdapter.*;
import org.hamcrest.*;
import org.hamcrest.core.IsEqual;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import org.junit.runner.*;
import org.w3c.dom.ranges.Range;

import org.junit.Test;



public class ListTester {
    @Test
    public void constructorTest(){
        ListAdapter list = new ListAdapter();
        assertNotNull("Il costruttore crea un oggetto null", list);
        assertTrue("Il costruttore non crea una lista vuota", list.isEmpty());
    }

    
    @Test
    public void addTest() {
        ListAdapter list1 = new ListAdapter();
        ListAdapter list2 = new ListAdapter();

        Object obj1 = new Object();
        Object obj2 = new Object();
        Object objNull = null;
        
        list1.add(obj1);
        assertEquals("add(Object) non aggiunge un elemento non null se la lista è vuota (obj1)", 1, list1.size());
        assertTrue("La lista non contiene l'elemento inserito (obj1)", list1.contains(obj1));
        
        list2.add(objNull);
        assertEquals("add(Object) non aggiunge un elemento null se la lista è vuota (objNull)", 1, list2.size());
        assertTrue("La lista non contiene l'elemento inserito (ObjNull)", list2.contains(objNull));

        
        list1.add(obj2);
        assertEquals("add(Object) non aggiunge un elemento non null se la lista non è vuota (obj2)", 2, list1.size());
        assertTrue("La lista non contiene l'elemento inserito (obj2)", list1.contains(obj2));  
        
        list1.add(objNull);
        assertEquals("add(Object) non aggiunge un elemento null se la lista non è vuota (objNull)", 3, list1.size());
        assertTrue("La lista non contiene l'elemento inserito (objNull)", list1.contains(objNull)); 
    }

    @Test
    public void addIndexTest() {
        ListAdapter list1 = new ListAdapter();
        ListAdapter list2 = new ListAdapter();

        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;
        Object objNull3 = null;

        assertThrows("add(int, Object) aggiunge un elemento  in posizione -1/0  (obj1)", IndexOutOfBoundsException.class, ()->{list1.add(-1, obj1);});
        assertTrue("add(int, Object) modifica la lista dopo aver lanciato un'eccezione (-1)", list1.isEmpty());
        
        assertThrows("add(int, Object) aggiunge un elemento in posizione 1/0  (obj1)", IndexOutOfBoundsException.class, ()->{list1.add(1, obj1);});
        assertTrue("add(int, Object) modifica la lista dopo aver lanciato un'eccezione (1)", list1.isEmpty());
        
        list1.add(0, obj1);
        assertEquals("add(int, Object) non aggiunge un elemento != null in posizione 0/0 (obj1)", 1, list1.size());
        assertEquals("add(int, Object) non contiene l'elemento inserito in posizione 0/0 (obj1)", obj1, list1.get(0));

        list1.add(1, obj2);
        assertEquals("add(int, Object) non aggiunge un elemento != null in posizione 1/1 (obj2)", 2, list1.size());
        assertEquals("add(int, Object) non contiene l'elemento inserito in posizione 1/1 (obj2)", obj2, list1.get(1));

        list2.add(0, objNull1);
        assertEquals("add(int, Object) non aggiunge un elemento null in posizione 0/2 (objNull1)", 1, list2.size());
        assertEquals("add(int, Object) non contiene l'elemento inserito in posizione 0/0 (objNull1)", objNull1, list2.get(0));

        list2.add(1, objNull2);
        assertEquals("add(int, Object) non aggiunge un elemento null in posizione 1/3  (objNull2)", 2, list2.size());
        assertEquals("add(int, Object) non contiene l'elemento inserito in posizione 1/1 (objNull2)", objNull2, list2.get(1));

        list1.add(1, obj3);
        assertEquals("add(int, Object) non aggiunge un elemento != null in posizione 1/4 (obj3)", 3, list1.size());
        assertEquals("add(int, Object) non contiene l'elemento inserito in posizione 1/2 (obj3)", obj3, list1.get(1));

        list2.add(1, objNull3);
        assertEquals("add(int, Object) non aggiunge un elemento null in posizione 1/5  (objNull3)", 3, list2.size());
        assertEquals("add(int, Object) non contiene l'elemento inserito in posizione 1/3 (objNull3)", objNull3, list2.get(1));
    }

    @Test
    public void addAllTest() {
        ListAdapter collectionMix = new ListAdapter();
        ListAdapter NullCollection = null;

        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj4 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;

        collectionMix.add(obj1);
        collectionMix.add(objNull1);
        collectionMix.add(obj2);
        collectionMix.add(objNull2);

        ListAdapter emptyList = new ListAdapter();
        ListAdapter notEmptyList = new ListAdapter();
        notEmptyList.add(obj4);

        assertThrows("addAll(HCollection) non lancia un eccezione se il parametro è null", NullPointerException.class, ()->{emptyList.addAll(NullCollection);});

        emptyList.addAll(collectionMix);
        assertEquals("addAll(HCollection) non inserisce una HCollection in una lista vouta", collectionMix, emptyList);
        
        notEmptyList.addAll(collectionMix);
        collectionMix.add(0, obj4);
        assertEquals("addAll(HCollection) non inserisce una HCollection in una lista non vuota", collectionMix, notEmptyList);
    }

    @Test
    public void addAllIndexTest() {
        ListAdapter collectionMix = new ListAdapter();
        ListAdapter NullCollection = null;
        ListAdapter emptyCollection = new ListAdapter();

        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj4 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;

        collectionMix.add(obj1);
        collectionMix.add(objNull1);
        collectionMix.add(obj2);
        collectionMix.add(objNull2);

        ListAdapter emptyList = new ListAdapter();
        ListAdapter notEmptyList = new ListAdapter();
        notEmptyList.add(obj4);

        assertThrows("addAll(int, HCollection) non lancia un eccezione se il parametro è null con indice 0/0", NullPointerException.class, ()->{emptyList.addAll(0, NullCollection);});

        assertThrows("addAll(int, HCollection) non lancia un eccezione con indice -1/0", IndexOutOfBoundsException.class, ()->{emptyList.addAll(-1, emptyCollection);});

        assertThrows("addAll(int, HCollection) non lancia un eccezione con indice 1/0", IndexOutOfBoundsException.class, ()->{emptyList.addAll(1, emptyCollection);});

        assertThrows("addAll(int, HCollection) non lancia un eccezione con indice 2/1", IndexOutOfBoundsException.class, ()->{notEmptyList.addAll(-1, emptyCollection);});

        emptyList.addAll(0, collectionMix);
        assertEquals("addAll(int, HCollection) non inserisce una HCollection in una lista con indice 0/0", collectionMix, emptyList);
        
        notEmptyList.addAll(0, collectionMix);
        collectionMix.add(obj4);
        assertEquals("addAll(int, HCollection) non inserisce una HCollection con indice 0/1", collectionMix, notEmptyList);

        notEmptyList.addAll(1, collectionMix);
        collectionMix.add(1, obj4);
        collectionMix.add(1, objNull2);
        collectionMix.add(1,obj2);
        collectionMix.add(1, objNull1);
        collectionMix.add(1, obj1);
        assertEquals("addAll(int, HCollection) non inserisce una HCollection con indice 1/4", collectionMix, notEmptyList);

        
        notEmptyList.addAll(notEmptyList.size(), collectionMix);
        collectionMix.add(10, obj4);
        collectionMix.add(10, objNull2);
        collectionMix.add(10, obj2);
        collectionMix.add(10, objNull1);
        collectionMix.add(10, obj4);
        collectionMix.add(10, objNull2);
        collectionMix.add(10, obj2);
        collectionMix.add(10, objNull1);
        collectionMix.add(10, obj1);
        collectionMix.add(10, obj1);
        assertEquals("addAll(int, HCollection) non inserisce una HCollection con indice size()", collectionMix, notEmptyList);
    }

    @Test
    public void clearTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;

        ListAdapter emptyList = new ListAdapter();
        ListAdapter notEmptyList = new ListAdapter();
        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(objNull2);

        emptyList.clear();
        assertEquals("clear() fa cose strane su una lista vuota", 0, emptyList.size());

        notEmptyList.clear();
        assertEquals("clear() non funziona su una lista non vuota", 0, notEmptyList.size());
    }

    @Test
    public void containsTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;

        ListAdapter emptyList = new ListAdapter();
        ListAdapter notEmptyList = new ListAdapter();
        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(objNull2);
        

        assertFalse("contains(Object) trova un elemento null su una lista vuota", emptyList.contains(objNull1));
    
        assertFalse("contains(Object) trova un elemento !null su una lista vuota", emptyList.contains(obj1));

        assertFalse("contains(Object) trova un elemento non presente", notEmptyList.contains(obj3));

        assertTrue("contains(Object) non trova un elemento che è in posizione 0", notEmptyList.contains(obj1));
        
        assertTrue("contains(Object) non trova un elemento che è in mezzo alla lista", notEmptyList.contains(objNull1));

        assertTrue("contains(Object) non trova un elemento che è in posizione size()-1", notEmptyList.contains(objNull2));
    }

    @Test
    public void containsAllTest() {
        ListAdapter collectionMix = new ListAdapter();
        ListAdapter NullCollection = null;
        ListAdapter emptyCollection = new ListAdapter();

        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj4 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;

        collectionMix.add(obj1);
        collectionMix.add(objNull1);
        collectionMix.add(obj2);
        collectionMix.add(objNull2);

        ListAdapter emptyList = new ListAdapter();
        ListAdapter notEmptyList = new ListAdapter();
        notEmptyList.add(obj4);

        assertThrows("containsAll(HCollection) non lancia NullPointerException", NullPointerException.class, ()->{emptyList.containsAll(NullCollection);});

        assertFalse("containsAll(HCollection) trova qualcosa che non c'è", emptyList.containsAll(collectionMix));
        
        assertFalse("containsAll(HCollection) trova qualcosa che non c'è", notEmptyList.containsAll(collectionMix));

        notEmptyList.addAll(collectionMix);
        assertTrue("containsAll(HCollection) non trova qualcosa che c'è", notEmptyList.contains(obj1) && notEmptyList.contains(objNull1) && notEmptyList.contains(obj2) && notEmptyList.contains(objNull2) && notEmptyList.contains(obj4));

        assertTrue("containsAll(HCollection) non trova una HCollection vuota", notEmptyList.containsAll(emptyCollection));
    }

    @Test
    public void equalsTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object objNull1 = null;

        ListAdapter emptyList1 = new ListAdapter();
        ListAdapter emptyList2 = new ListAdapter();
        ListAdapter notEmptyList1 = new ListAdapter();
        ListAdapter notEmptyList2 = new ListAdapter();
        ListAdapter notEmptyList3 = new ListAdapter();

        notEmptyList1.add(obj1);
        notEmptyList2.add(obj1);

        notEmptyList3.add(obj1);
        notEmptyList3.add(obj2);
        notEmptyList3.add(objNull1);

        assertTrue("equals(Object) non restituisce true sulla medesima lista vouta", emptyList1.equals(emptyList1));
        assertTrue("equals(Object) non restituisce true sulla un'altra lista vouta", emptyList1.equals(emptyList2));

        assertTrue("equals(Object) non restituisce true sulla medesima lista non vouta", notEmptyList1.equals(notEmptyList1));
        assertTrue("equals(Object) non restituisce true sulla un'altra lista non vouta", notEmptyList1.equals(notEmptyList2));

        assertFalse("equals(Object) non restituisce false su una lista diversa da una lista non vouta", notEmptyList1.equals(notEmptyList3));
        assertFalse("equals(Object) non restituisce false su una lista diversa da una lista vuota", emptyList1.equals(notEmptyList3));
    }

    @Test
    public void getTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;

        ListAdapter emptyList = new ListAdapter();
        ListAdapter notEmptyList = new ListAdapter();

        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(objNull2);

        assertThrows("get(int) non lancia OutOfBoundException con indice negativo", IndexOutOfBoundsException.class, ()->{notEmptyList.get(-1);});
        assertThrows("get(int) non lancia OutOfBoundException con indice 0 su una lista vouta", IndexOutOfBoundsException.class, ()->{emptyList.get(0);});
        assertThrows("get(int) non lancia OutOfBoundException con indice > size()", IndexOutOfBoundsException.class, ()->{notEmptyList.get(6);});

        assertEquals("get(int) non restuisce l'elemento all'inizio", obj1, notEmptyList.get(0));
        assertEquals("get(int) non restuisce l'elemento in mezzo ", objNull1, notEmptyList.get(1));
        assertEquals("get(int) non restuisce l'elemento alla fine", objNull2, notEmptyList.get(3));

    }

    @Test
    public void hashCodeTest() {
        Object obj1 = '1';
        Object obj2 = "ciao";
        Object objNull1 = null;
        Object objNull2 = null;

        ListAdapter notEmptyList = new ListAdapter();
        ListAdapter emptyList = new ListAdapter();
    
        assertEquals("hashCode() non restituisce il valore aspettato su una lista vuota", 1, emptyList.hashCode());

        notEmptyList.add(obj1);
        assertEquals("hashCode() non restituisce il valore aspettato su una lista di dimensione 1", 80, notEmptyList.hashCode());

        notEmptyList.add(objNull1);
        assertEquals("hashCode() non restituisce il valore aspettato su una lista di dimensione 2", 2480, notEmptyList.hashCode());

        notEmptyList.add(obj2);
        assertEquals("hashCode() non restituisce il valore aspettato su una lista di dimensione 3", 3130212, notEmptyList.hashCode());

        notEmptyList.add(objNull2);
        assertEquals("hashCode() non restituisce il valore aspettato su una lista di dimensione 4", 97036572, notEmptyList.hashCode());
    }

    @Test
    public void indexOfTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;

        ListAdapter notEmptyList = new ListAdapter();
        ListAdapter emptyList = new ListAdapter();

        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(objNull2);
        notEmptyList.add(obj3);

        assertEquals("indexOf() non restituisce -1 su una lista vuota", -1, emptyList.indexOf(objNull1));

        assertEquals("indexOf() trova correttamente l'elemento all'inizio", 0, notEmptyList.indexOf(obj1));

        assertEquals("indexOf() trova correttamente l'elemento alla fine ", 4, notEmptyList.indexOf(obj3));

        assertEquals("indexOf() trova correttamente l'elemento a metà ", 1, notEmptyList.indexOf(null));
    }

    @Test
    public void isEmptyTest(){
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;

        ListAdapter notEmptyList = new ListAdapter();
        ListAdapter emptyList = new ListAdapter();

        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(objNull2);
        notEmptyList.add(obj3);

        assertTrue("isEmpty() risponde false su una lista vuota", emptyList.isEmpty());
        
        assertFalse("isEmpty() risponda true su una lista non vuota", notEmptyList.isEmpty());
    }

    @Test
    public void iteratorTest() {
        ListAdapter list = new ListAdapter();
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());

        assertNotNull("iterator() restituisce null", list.iterator());
    }

    @Test 
    public void lastIndexOfTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;

        ListAdapter notEmptyList = new ListAdapter();
        ListAdapter emptyList = new ListAdapter();

        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(objNull2);
        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(objNull2);
        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(objNull2);

        assertEquals("lastIndexOf(int) non restituisce -1 su una lista vuota", -1, emptyList.lastIndexOf(obj1));

        assertEquals("lastIndexOf(int) non restituisce -1 per un elemento non presente", -1, notEmptyList.lastIndexOf(obj3));

        assertEquals("lastIndexOf(int) l'indice corretto per un oggetto !=null presente più volte", 8, notEmptyList.lastIndexOf(obj1));

        assertEquals("lastIndexOf(int) l'indice corretto per un oggetto !=null presente più volte", 10, notEmptyList.lastIndexOf(obj2));

        assertEquals("lastIndexOf(int) l'indice corretto per un oggetto !=null presente più volte", 11, notEmptyList.lastIndexOf(null));
    }

    @Test
    public void listIteratorTest() {
        ListAdapter list = new ListAdapter();
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());

        assertNotNull("ListIterator() restituisce null", list.listIterator());
    }

    @Test
    public void listIteratorWithIndexTest() {
        ListAdapter list = new ListAdapter();
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());

        assertNotNull("ListIterator(int) restituisce null", list.listIterator(0));
        assertThrows("listIteartor(-1) non lancia IndexOutOfBoundsException", IndexOutOfBoundsException.class, ()->{list.listIterator(-1);});
        assertThrows("listIteartor(int) non lancia IndexOutOfBoundsException con indice >size()-1", IndexOutOfBoundsException.class, ()->{list.listIterator(4);});
    }

    @Test
    public void removeIndexTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;

        ListAdapter notEmptyList = new ListAdapter();

        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(objNull2);
        notEmptyList.add(obj2);

        assertThrows("remove(int) non lancia IndexOutOfBuondException con indice negativo", IndexOutOfBoundsException.class, ()->{notEmptyList.get(-1);});

        assertThrows("remove(int) non lancia IndexOutOfBuondException con indice maggiore di size()", IndexOutOfBoundsException.class, ()->{notEmptyList.get(4);});
        
        assertTrue("remove(int) non rimuove l'elemento corretto presente all'inizio", obj1.equals(notEmptyList.remove(0)) && notEmptyList.size()==3);

        assertTrue("remove(int) non restituisce l'elemento corretto presentea a metà", notEmptyList.remove(1)==null && notEmptyList.size()==2);

        assertTrue("remove(int) non rimuove l'elemento corretto presente alla fine", obj2.equals(notEmptyList.remove(1)) && notEmptyList.size()==1);

    }

    @Test
    public void removeTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;

        ListAdapter notEmptyList = new ListAdapter();
        ListAdapter emptyList = new ListAdapter();

        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(objNull2);

        assertFalse("remove(Object) non restituisce false per una lista vuota", emptyList.remove(objNull1));
        assertFalse("remove(Object) non restituisce false per un oggetto non presente", notEmptyList.remove(obj3));

        assertTrue("remove(Object) non restituisce true per un oggetto presente o non  elimina l'oggetto", notEmptyList.remove(obj1) && !notEmptyList.contains(obj1)); 
        assertTrue("remove(Object) non restituisce true per un null presente o non  elimina l'oggetto", notEmptyList.remove(null));       
    }

    @Test
    public void removeAllTest() {
        ListAdapter collectionMix = new ListAdapter();
        ListAdapter NullCollection = null;
        ListAdapter emptyCollection = new ListAdapter();

        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object objNull1 = null;
        Object objNull2 = null;

        collectionMix.add(obj1);
        collectionMix.add(objNull1);
        collectionMix.add(objNull2);

        ListAdapter notEmptyList = new ListAdapter();

        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(objNull2);
        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(objNull2);
        notEmptyList.add(obj3);

        assertThrows("removeAll(HCollection) non lancia NullPointerException", NullPointerException.class, ()->{notEmptyList.removeAll(NullCollection);});

        assertFalse("removeAll(HCollection) elimina qualcosa a partire da una HCollection vuota", notEmptyList.removeAll(emptyCollection));

        assertTrue("removeAll(HCollection) non elimina correttamente", notEmptyList.removeAll(collectionMix) && !notEmptyList.contains(obj1) && notEmptyList.contains(obj2) && !notEmptyList.contains(objNull1)&& !notEmptyList.contains(objNull2));
    }

    @Test
    public void retainAllTest() {
        ListAdapter collectionMix = new ListAdapter();
        ListAdapter nullCollection = null;
        ListAdapter emptyCollection = new ListAdapter();

        Object obj1 = new Object();
        Object obj2 = new Object();
        Object objNull1 = null;

        collectionMix.add(obj1);
        collectionMix.add(objNull1);

        ListAdapter notEmptyList = new ListAdapter();
        ListAdapter emptyList = new ListAdapter();

        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);


        assertThrows("retainAll(HCollection) non lancia NullPointerException", NullPointerException.class, ()->{emptyList.retainAll(nullCollection);});

        assertFalse("retainAll(HCollection) non restituisce falso da una HCollection vuota su una HList vuota", emptyList.retainAll(emptyCollection));

        assertTrue("retainAll(HCollection) non elimina tutta la lista passato come parametro una HCollection vuota", notEmptyList.retainAll(emptyCollection) && notEmptyList.size()==0);

        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        assertTrue("retainAll(HCollection) non rimuove correttamente", notEmptyList.retainAll(collectionMix) && notEmptyList.equals(collectionMix));
    }

    @Test
    public void setTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object objNull1 = null;


        ListAdapter notEmptyList = new ListAdapter();
        ListAdapter emptyList = new ListAdapter();

        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);


        assertThrows("set(int, Object) non lancia IndexOutOfBoundException con indice negativo", IndexOutOfBoundsException.class, ()-> {emptyList.set(-1, obj1);});

        assertThrows("set(int, Object) non lancia IndexOutOfBoundException con indice maggiore di size()", IndexOutOfBoundsException.class, ()-> {emptyList.set(1, obj1);});

        assertTrue("set(int, Object) non setta un oggetto all'inizio", notEmptyList.set(0, obj3).equals(obj1) && notEmptyList.contains(obj3));

        assertTrue("set(int, Object) non setta un oggetto al centro ", notEmptyList.set(1, obj1) == null && notEmptyList.contains(obj1));

        assertTrue("set(int, Object) non setta un oggetto alla fine", notEmptyList.set(2, objNull1).equals(obj2) && notEmptyList.contains(objNull1));
    }

    @Test 
    public void sizeTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object objNull1 = null;

        ListAdapter emptyList = new ListAdapter();

        assertEquals("size() non funziona su una lista vuota", 0, emptyList.size());

        emptyList.add(obj1);
        assertEquals("size() non funziona su una lista non vuota", 1, emptyList.size());

        emptyList.add(obj2);
        assertEquals("size() non funziona su una lista non vuota", 2, emptyList.size());

        emptyList.add(obj3);
        assertEquals("size() non funziona su una lista non vuota", 3, emptyList.size());

        emptyList.add(objNull1);
        assertEquals("size() non funziona su una lista non vuota", 4, emptyList.size());
    }

    @Test
    public void subListTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object objNull1 = null;

        ListAdapter notEmptyList = new ListAdapter();
        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(obj3);


        assertThrows("subList(int, int) non lancia IndexOutOfBoundException (-1, 0)", IndexOutOfBoundsException.class, ()->{notEmptyList.subList(-1, 0);});
        assertThrows("subList(int, int) non lancia IndexOutOfBoundException (0, 2)", IndexOutOfBoundsException.class, ()->{notEmptyList.subList(0, 6);});
        assertThrows("subList(int, int) non lancia IndexOutOfBoundException (1, 0)", IndexOutOfBoundsException.class, ()->{notEmptyList.subList(1, 0);});

    }

    @Test
    public void toArrayTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object objNull1 = null;


        ListAdapter notEmptyList = new ListAdapter();
        ListAdapter emptyList = new ListAdapter();

        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(obj3);

        assertArrayEquals("toArray() non funziona su una lista vuota", new Object[0], emptyList.toArray());

        assertArrayEquals("toArray() non funziona su una lista non vuota", new Object[] {obj1, objNull1, obj2, obj3}, notEmptyList.toArray());
    }

    @Test
    public void toArrayWhitArrayTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object objNull1 = null;


        ListAdapter notEmptyList = new ListAdapter();


        notEmptyList.add(obj1);
        notEmptyList.add(objNull1);
        notEmptyList.add(obj2);
        notEmptyList.add(obj3);

        Object[] nullVector = null;
        Object[] emptyVector = new Object[0];
        Object[] correctVector = new Object[4];
        Object[] largeVector = new Object[5];
        String[] stringVector = new String[10];
        Object[] refVector = new Object[] {obj1, objNull1, obj2, obj3};

        assertThrows("toArray(Object[]) non lancia NullPointerException", NullPointerException.class, ()->{notEmptyList.toArray(nullVector);});

        assertThrows("toArray(Object[]) non lancia ArrayStoreException", ArrayStoreException.class, ()->{notEmptyList.toArray(stringVector);});

        assertArrayEquals("toArray(Object[]) non funziona passando un vettore vuoto", refVector, notEmptyList.toArray(emptyVector));

        assertArrayEquals("toArray(Object[]) non funziona passando un vettore di dimensioni giuste", refVector, notEmptyList.toArray(correctVector));

        assertArrayEquals("toArray(Object[]) non funziona passando un vettore più grande", new Object[] {obj1, objNull1, obj2, obj3, null}, notEmptyList.toArray(largeVector));
    }
}


