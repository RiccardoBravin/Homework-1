//package myAdapter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

public class HashTableKeyIterator implements HIterator{

    public HashTableKeyIterator(Hashtable hashT) {
        this.hashT = hashT;
        keys = hashT.keys();
    }


    public boolean hasNext() {
        return keys.hasMoreElements();
    }


    public Object next() throws NoSuchElementException {
        lastReturned = keys.nextElement();
        return lastReturned;
    }


    public void remove() {
        hashT.remove(lastReturned);
        
    }

    Hashtable hashT;
    Enumeration keys;
    Object lastReturned;

}
