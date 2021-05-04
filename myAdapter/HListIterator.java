package myAdapter;

import java.util.NoSuchElementException;

public interface HListIterator extends HIterator {
    
    void add(Object o) throws UnsupportedOperationException, ClassCastException, IllegalArgumentException;

    boolean hasNext();

    boolean hasPrevious();

    Object next() throws NoSuchElementException;

    int nextIndex();

    Object previous() throws NoSuchElementException;

    int previousIndex();

    void remove() throws UnsupportedOperationException, IllegalStateException;

    void set(Object o) throws UnsupportedOperationException, ClassCastException, IllegalArgumentException, IllegalStateException;
}
