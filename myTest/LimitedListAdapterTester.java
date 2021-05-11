import myAdapter.ListAdapter;
import myAdapter.ListAdapter.LimitedListAdapter;

//Hamcrest
import org.hamcrest.*;
//Junit
import static org.junit.Assert.*;
import org.junit.runner.*;
import org.junit.Test;

public class LimitedListAdapterTester {
    
    @Test
    public void constructorTester(){
        
        assertThrows("Sublist non lancia eccezioni se passate liste vuote", NullPointerException.class, () -> {new ListAdapter().new LimitedListAdapter(null,0,0);});
        
        ListAdapter l1 = new ListAdapter();
        assertThrows("Sublist non lancia eccezioni se passati indici non validi per la lista vuota (0,0)", IndexOutOfBoundsException.class, () -> {l1.new LimitedListAdapter(l1,0,0);});
        assertThrows("Sublist non lancia eccezioni se passati indici non validi per la lista vuota (0,1)", IndexOutOfBoundsException.class, () -> {l1.new LimitedListAdapter(l1,0,1);});
        assertThrows("Sublist non lancia eccezioni se passati indici non validi per la lista vuota (-1,0)", IndexOutOfBoundsException.class, () -> {l1.new LimitedListAdapter(l1,-1,0);});

        l1.add(null);
        assertThrows("Sublist non lancia eccezioni se passati indici non validi per la lista usata (0,1)", IndexOutOfBoundsException.class, () -> {l1.new LimitedListAdapter(l1,-1,0);});
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
}
