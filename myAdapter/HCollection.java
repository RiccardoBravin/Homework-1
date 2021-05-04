package myAdapter;
public interface HCollection {

    boolean	add(Object o) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException;
   
    boolean	addAll(HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException;
  
    void clear() throws UnsupportedOperationException;
    
    boolean	contains(Object o) throws ClassCastException, NullPointerException;
    
    boolean	containsAll(HCollection c) throws ClassCastException, NullPointerException;

    boolean	equals(Object o);

    int	hashCode();

    boolean	isEmpty();

    HIterator iterator();
    
    boolean	remove(Object o) throws ClassCastException, NullPointerException, UnsupportedOperationException;

    boolean	removeAll(HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException;

    boolean	retainAll(HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException;

    int	size();

    Object[] toArray();

    Object[] toArray(Object[] a) throws ArrayStoreException, NullPointerException;
}
