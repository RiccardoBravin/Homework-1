package myAdapter;

import java.util.Hashtable;

public class MapAdapter implements HMap{
    
    private Hashtable map;

    public MapAdapter(){
        map = new Hashtable();
    }

    MapAdapter(int initialCapacity){
        map = new Hashtable(initialCapacity);
    }

    






}
