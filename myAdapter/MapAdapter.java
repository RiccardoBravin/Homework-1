package myAdapter;

import java.util.Hashtable;

public class MapAdapter {
    
    private Hashtable ht;

    public MapAdapter(){
        ht = new Hashtable();
    }

    public MapAdapter(int initialCapacity){
        ht = new Hashtable(initialCapacity);
    }

    public boolean isEmpty(){
        return ht.isEmpty();
    }


}
