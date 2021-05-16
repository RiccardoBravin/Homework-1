package myAdapter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

import myAdapter.HMap.HEntry;

public class HashtableValueIterator implements HIterator {

    public HashtableValueIterator(Hashtable hashT) {
        this.hashT = hashT;
        keys = hashT.keys();
        values = hashT.elements();
    }

    
    public boolean hasNext() {
        return keys.hasMoreElements();
        
    }

    
    public Object next() throws NoSuchElementException {
        if(!hasNext()) throw new NoSuchElementException();
        lastReturned =  keys.nextElement();
        return values.nextElement();
    }

    
    public void remove() {
        hashT.remove(lastReturned);
    }

    private Hashtable hashT;
    private Enumeration keys;
    private Enumeration values;
    private Object lastReturned;
    
}

