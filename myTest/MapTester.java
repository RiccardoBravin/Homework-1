//package myTest;

//Adapter
import myAdapter.MapAdapter;
//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;
import org.junit.runner.*;
import org.junit.Test;



public class MapTester {
    
    @Test
    public void constructorTester(){
        MapAdapter ht1 = new MapAdapter();
        assertNotNull("The Map default constructor returns a null object", ht1);
        
        MapAdapter ht2 =  new MapAdapter(5);
        assertNotNull("The Map default constructor returns a null object", ht2);

        assertEquals(ht1.getHashtable(), ht2.getHashtable());
    }    

    @Test
    public void copyConstructorTest(){
        assertTrue(true);
    }

    @Test
    public void putTest(){
        
        MapAdapter ht1 = new MapAdapter();
        ht1.put("A", 1);
        
        assertTrue("La mappa in realtà sembra non contenere effettivamente 1", ht1.getHashtable().contains(1));
        assertFalse("La mappa in realtà sembra contenere A quando era stata inserita come chiave", ht1.getHashtable().contains("A"));

        assertTrue("La mappa in realtà sembra non contenere effettivamente 1", ht1.getHashtable().containsKey("A"));
        assertFalse("La mappa in realtà sembra contenere A quando era stata inserita come chiave", ht1.getHashtable().containsKey(1));


    }

    
}
