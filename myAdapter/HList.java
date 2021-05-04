package myAdapter;

public interface HList extends HCollection {

    /**
     * sos
     * 
     * @param index
     * @param Element
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @throws IndexOutOfBoundsException
     */
    void add(int index, Object Element) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException, IndexOutOfBoundsException;

    
    boolean add(Object obj) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException;

    boolean addAll(HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException;

    boolean addAll(int index, HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException, IndexOutOfBoundsException;

    void clear();

    
    boolean contains(Object o) throws ClassCastException, NullPointerException;

    boolean containsAll(HCollection c) throws ClassCastException, NullPointerException;

    boolean equals(Object o);

    Object get(int index);

    int hasCode();

    int indexOf(Object o) throws ClassCastException, NullPointerException;

    /**
     * Returns true if this list contains no elements.
     * 
     * @return true if this list contains no elements.

     */
    boolean isEmpty();

    HIterator iterator();

    int lastIndexOf(Object o) throws ClassCastException, NullPointerException;

    HListIterator listIterator();

    HListIterator listIterator(int index) throws IndexOutOfBoundsException;

    Object remove(int index);

    boolean remove(Object o) throws ClassCastException, NullPointerException, UnsupportedOperationException;

    boolean removeAll(HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException;

    boolean retainAll(HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException;

    Object set(int index, Object element) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException, IndexOutOfBoundsException;
    
    /**
     * Returns the number of elements in this list. If this list contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     * 
     * @return the number of elements in this list.
     */
    int size();

    HList subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException;

    Object[] toArray();

    Object[] toArray(Object[] a) throws ArrayStoreException, NullPointerException;
}
