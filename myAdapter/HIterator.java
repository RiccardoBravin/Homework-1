package myAdapter;

import java.util.NoSuchElementException;

public interface HIterator {
    
    boolean hasNext();

    Object next() throws NoSuchElementException;

    void remove() throws UnsupportedOperationException, IllegalStateException;
}
