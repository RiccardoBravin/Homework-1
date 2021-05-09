//Adapter
import myAdapter.ListAdapter;

import org.graalvm.compiler.graph.NodeList.SubList;
//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.runner.*;

import ListAdapter.LimitedListAdapter;

import org.junit.Test;


public class ListTester{

    private boolean add;












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

        System.out.println(l1);
    }

    @Test
    public void equalsTest(){
        ListAdapter l1 = new ListAdapter();
        listFiller(l1, 2);
        l1.add(1);
        l1.add(2);
        l1.add(3);
        ListAdapter.LimitedListAdapter l2 = (ListAdapter.LimitedListAdapter)l1.subList(1, l1.size()-1);
        
        System.out.println(l1);
        System.out.println(l2.size());
        System.out.println(l2.get(1));
        System.out.println();

        System.out.println("MODCOUNT");
        System.out.println(l1.getModCount());
        System.out.println(l2.stateCheck());
        //l2.add(4);
        //l1.add(5);
        System.out.println(l1.getModCount());
        System.out.println(l2.stateCheck());

        //System.out.println(l2.size());
        System.out.println(l1);
        System.out.println(l2);
        
    }












    private void listFiller(HCollection l, int number){
        for(int i = 0; i < number; i++)
            l.add("Test");
    }
}