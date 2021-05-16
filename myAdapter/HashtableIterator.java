//package myAdapter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

import myAdapter.HMap.HEntry;

public class HashtableIterator implements HIterator {

    public HashtableIterator(Hashtable hashT) {
        this.hashT = hashT;
        keys = hashT.keys();
        values = hashT.elements();
    }

    
    public boolean hasNext() {
        return keys.hasMoreElements();
        
    }

    
    public Object next() throws NoSuchElementException {
        if(!hasNext()) throw new NoSuchElementException();
        lastReturned = new MapAdapter().new EntryAdapter(keys.nextElement(), values.nextElement());
        return lastReturned;
    }

    
    public void remove() {
        hashT.remove(lastReturned.getKey());
    }

    private Hashtable hashT;
    private Enumeration keys;
    private Enumeration values;
    private HMap.HEntry lastReturned;
    
}
