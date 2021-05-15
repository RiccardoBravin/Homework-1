package myTest;
//Internal
import myAdapter.ListAdapter;
import myAdapter.ListAdapter.LimitedListAdapter;
//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;
import org.junit.runner.*;
import org.junit.Test;

public class LimitedListTester {
    
    @Test
    public void constructorTester(){
        
        assertThrows("Sublist non lancia eccezioni se passate liste vuote", NullPointerException.class, () -> {new ListAdapter().new LimitedListAdapter(null,0,0);});
        
        ListAdapter l1 = new ListAdapter();
        assertThrows("Sublist non lancia eccezioni se passati indici non validi per la lista vuota (0,0)", IndexOutOfBoundsException.class, () -> {l1.new LimitedListAdapter(l1,0,0);});
        assertThrows("Sublist non lancia eccezioni se passati indici non validi per la lista vuota (0,1)", IndexOutOfBoundsException.class, () -> {l1.new LimitedListAdapter(l1,0,1);});
        assertThrows("Sublist non lancia eccezioni se passati indici non validi per la lista vuota (-1,0)", IndexOutOfBoundsException.class, () -> {l1.new LimitedListAdapter(l1,-1,0);});

        l1.add(null);
        assertThrows("Sublist non lancia eccezioni se passati indici non validi per la lista usata (0,1)", IndexOutOfBoundsException.class, () -> {l1.new LimitedListAdapter(l1,-1,0);});

        l1.add("A");
        l1.add("A");
        l1.add("A");
        assertThrows("Sublist non lancia eccezioni se passati indici non validi per la lista usata (3,1)", IndexOutOfBoundsException.class, () -> {l1.new LimitedListAdapter(l1,3,1);});

        assertNotNull("Sublist è null se richiamata correttamente", l1.new LimitedListAdapter(l1,0,1));   
    }

    @Test
    public void addTester(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");
        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        sl1.add("B");
        
        assertArrayEquals("Add di limitedList non inserisce correttamente l'elemento in list", new Object[] {"A","B"}, l1.toArray());
        assertThrows("Add sulla stessa lista può venir utilizzato più volte senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.add("C");});
        assertArrayEquals("Add di limitedList inserisce elementi in list dopo un throw", new Object[] {"A","B"}, l1.toArray());
        
        ListAdapter sl2 = (ListAdapter)l1.subList(0,1);
        sl2.add("C");
        assertArrayEquals("Add di limitedList non inserisce correttamente l'elemento in list", new Object[] {"A","C","B"}, l1.toArray());
        
        ListAdapter sl3 = (ListAdapter)l1.subList(1,3);
        assertArrayEquals("La sottolista ritornata non è uguale a quella attesa", new Object[] {"C","B"}, sl3.toArray());

        sl2 = (ListAdapter)l1.subList(1,3);
        assertTrue("add ritorna vero anche se la lista non è stata modificata", sl2.add("D"));
        assertArrayEquals("Add di limitedList non inserisce correttamente l'elemento in list", new Object[] {"A","C","B","D"}, l1.toArray());
    }

    @Test
    public void addIndexTester(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");
        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        
        assertThrows("La sottolista non lancia IndexOutOfBoundsException quando viene passato un indice errato (-1)", IndexOutOfBoundsException.class, () -> {sl1.add(-1, "x");});
        assertThrows("La sottolista non lancia IndexOutOfBoundsException quando viene passato un indice errato (1)", IndexOutOfBoundsException.class, () -> {sl1.add(2, "x");});

        sl1.add(0, "B");

        
        assertArrayEquals("Add di limitedList non inserisce correttamente l'elemento in list", new Object[] {"B","A"}, l1.toArray());
        assertThrows("Add sulla stessa lista può venir utilizzato più volte senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.add(1,"C");});
        assertArrayEquals("Add di limitedList inserisce elementi in list dopo un throw", new Object[] {"B","A"}, l1.toArray());
    
        ListAdapter sl2 = (ListAdapter)l1.subList(0,1);
        sl2.add(1,"C");
        assertArrayEquals("Add di limitedList non inserisce correttamente l'elemento in list", new Object[] {"B","C","A"}, l1.toArray());
        
        ListAdapter sl3 = (ListAdapter)l1.subList(1,3);
        assertArrayEquals("La sottolista ritornata non è uguale a quella attesa", new Object[] {"C","A"}, sl3.toArray());
        
        
        //System.out.println(sl3);
    }

    @Test
    public void addAllTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");
        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);

        assertThrows("La sottolistalista non lancia NullPointerException se viene passata una collection null",NullPointerException.class, () -> {sl1.addAll(0,null);});

        assertThrows("La sottolistalista non lancia IndexOutOfBoundsException se viene passato un indice non valido (-1)",IndexOutOfBoundsException.class, () -> {sl1.addAll(-1,l1);});
        assertThrows("La sottolistalista non lancia IndexOutOfBoundsException se viene passato un indice non valido (5)",IndexOutOfBoundsException.class, () -> {sl1.addAll(5,l1);});

        sl1.addAll(l1);

        assertArrayEquals("Addall di limitedList non inserisce correttamente la collection in list", new Object[] {"A","A"}, l1.toArray());
        assertThrows("Add sulla stessa lista può venir utilizzato più volte senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.addAll(1,l1);});
        assertArrayEquals("Add di limitedList inserisce elementi in list dopo un throw", new Object[] {"A","A"}, l1.toArray());

        l1.remove("A");
        l1.add("B");
        l1.add("C");
        ListAdapter l2 = new ListAdapter();
        l2.add(1);
        l2.add(2);
        ListAdapter sl2 = (ListAdapter)l1.subList(0,2);
        
        assertTrue("addall pur aggiungendo non ritorna correttamente il fatto di aver apportato modifiche", sl2.addAll(sl2.size(), l2));

        assertArrayEquals("Addall di limitedList non inserisce correttamente la collection in list", new Object[] {"A", "B", 1, 2, "C"}, l1.toArray());
        assertThrows("Addall sulla stessa lista può venir utilizzato più volte senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.addAll(l2);});

        ListAdapter l3 = new ListAdapter();
        ListAdapter sl3 = (ListAdapter)l1.subList(0,1);

        assertFalse("addall dice di aver effettuato modifiche pur avendo passato una lista vuota", sl3.addAll(0, l3));

    }

    @Test
    public void clearTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");
        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);

        sl1.clear();
        assertTrue("La lista non è stata correttamente svuotata", l1.isEmpty());
        assertThrows("clear sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.clear();});

        l1.add("A");
        l1.add("B");
        l1.add("C");
        l1.add("D");
        ListAdapter sl2 = (ListAdapter)l1.subList(1,3);

        sl2.clear();
        assertArrayEquals("clear non rimuove gli oggetti nelle posizioni corrette",new Object[] {"A", "D"} , l1.toArray());

    }

    @Test
    public void containsTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");
        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);

        assertTrue("La sottolista (0,1) non contiene il valore cercato A", sl1.contains("A"));
        l1.add("B");
        l1.add("C");
        l1.add("D");
        assertThrows("contains sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.contains("A");});
        
        ListAdapter sl2 = (ListAdapter)l1.subList(1,3);
        assertFalse("La sottolista contiene il valore cercato D", sl2.contains("D"));
        assertTrue("La sottolista non contiene il valore cercato B", sl2.contains("B"));
    }

    @Test
    public void containsAllTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);

        assertThrows("containsall non lancia NullPointerException se viene passata una lista nulla", NullPointerException.class, () -> {sl1.containsAll(null);});

        assertTrue("La lista non contiene se stessa", sl1.containsAll(sl1));
        l1.add("B");
        l1.add("C");
        l1.add("D");
        assertThrows("containsall sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.containsAll(l1);});

        ListAdapter sl2 = (ListAdapter)l1.subList(1,3);
        ListAdapter l2 = new ListAdapter();
        l2.add("B");
        l2.add("C");
        assertTrue("La lista non contiene gli elementi corretti B,C", sl2.containsAll(l2));

        l2.add("D");
        assertFalse("La lista non contiene un elemento non presente D", sl2.containsAll(l2));
    }

    @Test
    public void equalsTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        assertFalse("La sottolista piena non è diversa da una lista nulla", sl1.equals(null));
        assertTrue("La sottolista non è uguale a se stessa", sl1.equals(sl1));

        l1.add("B");
        l1.add("C");
        l1.add("D");
        assertThrows("equals sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.equals(l1);});
        
        ListAdapter sl2 = (ListAdapter)l1.subList(0,2);
        assertFalse("La lista risulta uguale ad una lista diversa", sl2.equals(l1));

        sl2 = (ListAdapter)l1.subList(0,4);
        assertTrue("La sottolista non è uguale a una lista con gli stessi elementi", sl2.equals(l1));
        
    }

    @Test
    public void getTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        assertThrows("La lista non lancia IndexOutOfBoundException se viene passato un indice invalido (-1)", IndexOutOfBoundsException.class, () -> {sl1.get(-1);});
        assertThrows("La lista non lancia IndexOutOfBoundException se viene passato un indice invalido (1)", IndexOutOfBoundsException.class, () -> {sl1.get(1);});
        assertEquals("La lista non sembra contenere A all'indice corretto", "A", sl1.get(0));

        l1.add("B");
        l1.add("C");
        l1.add("D");
        assertThrows("get sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.get(0);});

        ListAdapter sl2 = (ListAdapter)l1.subList(1,4);
        assertEquals("La sottolista non sembra contenere il corretto elemento rispetto alla lista originaria", l1.get(1), sl2.get(0));
        assertEquals("La sottolista non sembra contenere il corretto elemento rispetto alla lista originaria", l1.get(3), sl2.get(2));
    }

    @Test
    public void hashTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add(null);

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        assertEquals("Hash code della sottolista diverso da quello atteso (31)", 31, sl1.hashCode());

        l1.add("B");
        l1.add("C");
        l1.add("D");
        assertThrows("get sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.hashCode();});

        ListAdapter sl2 = (ListAdapter)l1.subList(1,4);
        assertEquals("Hash code della sottolista diverso da quello atteso", 95362, sl2.hashCode());
    }

    @Test
    public void indexOfTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        assertEquals("L'indice dell'oggetto cercato è diverso da quello atteso(-1)", -1, sl1.indexOf(null));
        assertEquals("L'indice dell'oggetto cercato è diverso da quello atteso(0)", 0, sl1.indexOf("A"));

        l1.add("B");
        l1.add("C");
        l1.add("D");
        assertThrows("get sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.indexOf("B");});

        ListAdapter sl2 = (ListAdapter)l1.subList(1,4);
        assertEquals("L'indice dell'oggetto cercato è diverso da quello atteso(0)", 0, sl2.indexOf("B"));
        assertEquals("L'indice dell'oggetto cercato è diverso da quello atteso(0)", 2, sl2.indexOf("D"));
    }


    @Test
    public void isEmptyTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        assertFalse("La lista è vuota pur non essendo possibile creare liste vuote", sl1.isEmpty());
        sl1.clear();
        assertThrows("clear sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.isEmpty();});
    }

    //Iterator non viene testato perchè richiama listIterator

    @Test
    public void lastIndexTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);

        assertEquals("La sottolista non ritorna correttamente l'ultimo indice dell'elemento A", 0, sl1.lastIndexOf("A"));
        assertEquals("La sottolista non ritorna -1 se l'oggetto non è presente", -1, sl1.lastIndexOf(null));
        
        l1.add("B");
        l1.add("C");
        l1.add("D");
        assertThrows("lastIndexOf sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.lastIndexOf("B");});
        
        ListAdapter sl2 = (ListAdapter)l1.subList(1,3);
        assertEquals("La sottolista non ritorna correttamente l'ultimo indice dell'elemento B", 1, sl2.lastIndexOf("C"));
        assertEquals("La sottolista non ritorna -1 se l'oggetto è presente al di fuori di essa D", -1, sl2.lastIndexOf("D"));
    }

    @Test
    public void listIteratorTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);

        assertThrows("La sottolista non lancia IndexOutOfBoundsException se viene passato un indice non accettabile (2)", IndexOutOfBoundsException.class, () -> {sl1.listIterator(2);});
        assertThrows("La sottolista non lancia IndexOutOfBoundsException se viene passato un indice non accettabile (-1)", IndexOutOfBoundsException.class, () -> {sl1.listIterator(-1);});
        assertNotNull("La sottolista ritorna un valore nullo quando viene chiamato iterator", sl1.iterator());
        l1.add("B");
        l1.add("C");
        l1.add("D");

        assertThrows("lastIndexOf() sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.listIterator();});
        assertThrows("lastIndexOf(1) sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.listIterator(1);});
        
        ListAdapter sl2 = (ListAdapter)l1.subList(1,3);
        
        assertThrows("La sottolista non lancia IndexOutOfBoundsException se viene passato un indice non accettabile (3)", IndexOutOfBoundsException.class, () -> {sl2.listIterator(3);});
        assertNotNull("La sottolista ritorna un valore nullo quando viene chiamato iterator", sl2.iterator());

    }

    @Test
    public void removeIndexTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        assertThrows("remove non lancia IndexOutOfBoundsException se viene passato un indice non accettabile (1)", IndexOutOfBoundsException.class, () -> {sl1.remove(1);});
        assertThrows("remove non lancia IndexOutOfBoundsException se viene passato un indice non accettabile (-1)", IndexOutOfBoundsException.class, () -> {sl1.remove(-1);});
        assertEquals("Remove non ritorna l'oggetto corretto dalla rimozione di A", "A", sl1.remove(0));
        assertTrue("La rimozione dalla sottolista non ha avuto effetto sulla lista madre", l1.isEmpty());
        assertThrows("remove sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.remove(1);});

        l1.add("A");
        l1.add("B");
        l1.add("C");
        l1.add("D");

        ListAdapter sl2 = (ListAdapter)l1.subList(1,3);
        assertEquals("Remove non ritorna l'oggetto corretto dalla rimozione di C", "C", sl2.remove(1));
        assertArrayEquals("La sottolista non elimina i dati nella posizione corretta", new String[] {"A", "B", "D"}, l1.toArray());
    }

    @Test
    public void removeObjTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        assertFalse("Remove dice di aver rimosso un elemento non presente nella sottolista", sl1.remove(null));
        assertTrue("Remove dice di non aver rimosso elementi pur avendo richiesto la rimozione di A presente nella sottolista", sl1.remove("A"));
        assertTrue("La rimozione dalla sottolista non ha avuto effetto sulla lista madre", l1.isEmpty());
        assertThrows("remove sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.remove("A");});

        l1.add("A");
        l1.add("B");
        l1.add("C");
        l1.add("D");

        ListAdapter sl2 = (ListAdapter)l1.subList(1,3);
        assertTrue("Remove non ritorna l'oggetto corretto dalla rimozione di C", sl2.remove("C"));
        assertArrayEquals("La sottolista non elimina i dati nella posizione corretta", new String[] {"A", "B", "D"}, l1.toArray());
    }

    @Test
    public void removeAllTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        assertThrows("removeAll non lancia NullPointerException se viene passata una collection nulla",NullPointerException.class, () -> {sl1.removeAll(null);});
        assertTrue("Removeall dice di non aver rimosso elementi pur avendo modificato la sottolista", sl1.removeAll(sl1));
        assertTrue("La rimozione dalla sottolista non ha avuto effetto sulla lista madre", l1.isEmpty());

        l1.add("A");
        l1.add("B");
        l1.add("B");
        l1.add("C");
        l1.add("D");
        assertThrows("removeALl sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.removeAll(l1);});


        ListAdapter l2 = new ListAdapter();
        l2.add("A");
        l2.add("B");
        l2.add("C");
        l2.add("D");

        ListAdapter sl2 = (ListAdapter)l1.subList(1,4);
        assertTrue("Removeall dice di non aver rimosso elementi pur dovendo modificare la sottolista", sl2.removeAll(l2));
        assertArrayEquals("La sottolista non elimina i dati nella posizione corretta", new String[] {"A", "D"}, l1.toArray());
        
        ListAdapter sl3 = (ListAdapter)l2.subList(1,3);
        assertFalse("Removeall dice di aver rimosso elementi pur non contenendo elementi da eliminare", sl3.removeAll(l1));
        assertArrayEquals("La sottolista non elimina i dati nella posizione corretta", new String[] {"A", "B", "C", "D"}, l2.toArray());
    }

    @Test
    public void retainAllTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        assertThrows("retainAll non lancia NullPointerException se viene passata una collection nulla",NullPointerException.class, () -> {sl1.retainAll(null);});
        assertFalse("retainAll dice di aver rimosso elementi pur non dovendo modificare la sottolista", sl1.retainAll(l1));
        assertFalse("retainAll sulla sottolista non ha avuto effetto desiderato sulla lista madre", l1.isEmpty());
        
        l1.add("A");
        l1.add("B");
        l1.add("C");
        l1.add("D");

        assertThrows("retainAll sulla stessa lista può venir utilizzato dopo modifiche senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.retainAll(l1);});


        ListAdapter l2 = new ListAdapter();
        l2.add("B");
        l2.add("D");

        ListAdapter sl2 = (ListAdapter)l1.subList(0,4);
        assertTrue("RetainAll dice di non aver rimosso elementi pur dovendo modificare la sottolista", sl2.retainAll(l2));
        assertArrayEquals("La sottolista non elimina i dati correttamente nella posizione corretta", new String[] {"B", "D"}, l1.toArray());
        
        l2.add("B");
        l2.add("D");

        l1.clear();
        ListAdapter sl3 = (ListAdapter)l2.subList(0,3);
        assertTrue("Removeall dice di aver rimosso elementi pur non contenendo elementi da eliminare", sl3.retainAll(l1));
        assertArrayEquals("La sottolista non elimina i dati nella posizione corretta", new String[] {"D"}, l2.toArray());
        
    }

    @Test
    public void setTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        assertThrows("set non lancia IndexOutOfBoundsException se viene passato un puntatore non valido (-1)", IndexOutOfBoundsException.class, () -> {sl1.set(-1,null);});
        assertThrows("set non lancia IndexOutOfBoundsException se viene passato un puntatore non valido (1)", IndexOutOfBoundsException.class, () -> {sl1.set(1,null);});

        assertEquals("set non ritorna l'elemento della sottolista sostituito corretto A", "A" ,sl1.set(0,"B"));
        assertEquals("set sulla sottolista non ha modificato correttamente la lista madre", "B", l1.get(0));

        assertEquals("set permette di modificare più volte la sottolista perchè non è una modifica strutturale", "B", sl1.set(0,"B"));

        l1.add("E");
        l1.add("C");
        l1.add("X");
        assertThrows("set può venir utilizzato dopo modifiche alla lista madre senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.set(0,"A");});

        ListAdapter sl2 = (ListAdapter)l1.subList(1,4);
        assertEquals("set non ritorna l'elemento della sottolista sostituito corretto E", "E" ,sl2.set(0,"B"));
        assertEquals("set non ritorna l'elemento della sottolista sostituito corretto X", "X" ,sl2.set(2,"D"));
        assertArrayEquals("La sottolista non elimina i dati nella posizione corretta", new String[] {"B", "B", "C", "D"}, l1.toArray());
    }


    @Test
    public void sizeTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        assertEquals("La sottolista è della dimensione corretta 1", 1, sl1.size());

        l1.add("A");
        l1.add("A");
        l1.add("A");
        l1.add("A");
        l1.add("A");
        assertThrows("size può venir utilizzato dopo modifiche alla lista madre senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.size();});

        ListAdapter sl2 = (ListAdapter)l1.subList(1,3);
        assertEquals("La sottolista è della dimensione corretta 2", 2, sl2.size());
        sl2 = (ListAdapter)l1.subList(1,6);
        assertEquals("La sottolista è della dimensione corretta 3", 5, sl2.size());
    }

    @Test
    public void subListTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        
        assertThrows("sublist non lancia IndexOutOfBoundsException se vengono passati indici non validi (0,0)" ,IndexOutOfBoundsException.class, () -> {sl1.subList(0,0);});
        assertThrows("sublist non lancia IndexOutOfBoundsException se vengono passati indici non validi (-1,1)" ,IndexOutOfBoundsException.class, () -> {sl1.subList(-1,1);});
        assertThrows("sublist non lancia IndexOutOfBoundsException se vengono passati indici non validi (2,1)" ,IndexOutOfBoundsException.class, () -> {sl1.subList(1,0);});
        
        assertNotNull("Sublist della sottolista non ritorna un oggetto nullo", sl1.subList(0, 1));
        assertEquals("La sottolista e la sublist chiamata non sono uguali", sl1, sl1.subList(0,1));
        l1.add("B");
        l1.add("C");
        l1.add("D");
        assertThrows("sublist può venir utilizzato dopo modifiche alla lista madre senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.subList(0,3);});
        
        ListAdapter sl2 = (ListAdapter)l1.subList(1,3);
        assertNotNull("Sublist della sottolista non ritorna un oggetto nullo", sl2.subList(0, 1));
        assertArrayEquals("La sottolista ottenuta con sublist non è quella attesa", new String[] {"B"}, sl2.subList(0, 1).toArray());
    }

    @Test
    public void toArrayTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        assertArrayEquals("Toarray della sottolista non corrisponde all'array atteso A", new Object[] {"A"}, sl1.toArray());
        
        l1.add("B");
        l1.add("C");
        l1.add(2.1);
        l1.add("D");
        assertThrows("toArray può venir utilizzato dopo modifiche alla lista madre senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.toArray();});

        ListAdapter sl2 = (ListAdapter)l1.subList(2,4);
        assertArrayEquals("Toarray della sottolista non corrisponde all'array atteso", new Object[] {"C", 2.1}, sl2.toArray());
    }

    @Test
    public void toArrayObjectTest(){
        ListAdapter l1 = new ListAdapter();
        l1.add("A");

        ListAdapter sl1 = (ListAdapter)l1.subList(0,1);
        assertArrayEquals("Toarray della sottolista non corrisponde all'array atteso A", new Object[] {"A"}, sl1.toArray(null));

        Object[] a1 = new Object[] {"C", "D"};
        assertArrayEquals("Toarray della sottolista non corrisponde all'array atteso {A,D}", new Object[] {"A","D"}, sl1.toArray(a1));
        assertArrayEquals("Toarray della sottolista non corrisponde all'array atteso {A}", new Object[] {"A"}, sl1.toArray(new Object[1]));
        assertArrayEquals("Toarray della sottolista non corrisponde all'array atteso {A,null}", new Object[] {"A", null}, sl1.toArray(new Object[2]));

        l1.add("B");
        l1.add("C");
        l1.add(2.1);
        l1.add("D");
        assertThrows("toArray può venir utilizzato dopo modifiche alla lista madre senza lanciare IllegalStateException",IllegalStateException.class, () -> {sl1.toArray(a1);});
        
        Object[] a2 = new Object[] {"C", "D"};
        ListAdapter sl2 = (ListAdapter)l1.subList(2,4);
        assertArrayEquals("Toarray della sottolista non corrisponde all'array atteso {C, 2.1, finito}", new Object[] {"C", 2.1, "finito"}, sl2.toArray(new Object[] {"Finalmente", "ho", "finito"}));
    }


}
