//Adapter
import myAdapter.ListAdapter;
//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.runner.*;
import org.junit.Test;


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
    }
    
    @Test
    public void addIndexTester(){
        ListAdapter l1 = new ListAdapter();
        
        assertThrows("La lista non lancia indexOutOfBound quando viene inserito un elemento fuori posizione massima", IndexOutOfBoundsException.class, () -> {l1.add(1,"A");});
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
}