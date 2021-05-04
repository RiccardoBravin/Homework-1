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

    /**
     * 
     * @param obj
     * @return
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    boolean add(Object obj) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException;

    /**
     * 
     * @param c
     * @return
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    boolean addAll(HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException;

    /**
     * 
     * @param index
     * @param c
     * @return
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @throws IndexOutOfBoundsException
     */
    boolean addAll(int index, HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException, IndexOutOfBoundsException;

    /**
     * 
     */
    void clear();

    /**
     * 
     * @param o
     * @return
     * @throws ClassCastException
     * @throws NullPointerException
     */
    boolean contains(Object o) throws ClassCastException, NullPointerException;

    /**
     * 
     * @param c
     * @return
     * @throws ClassCastException
     * @throws NullPointerException
     */
    boolean containsAll(HCollection c) throws ClassCastException, NullPointerException;

    /**
     * 
     * @param o
     * @return
     */
    boolean equals(Object o);
    
    /**
     * 
     * @param index
     * @return
     */
    Object get(int index);

    /**
     * 
     * @return
     */
    int hasCode();

    /**
     * 
     * @param o
     * @return
     * @throws ClassCastException
     * @throws NullPointerException
     */
    int indexOf(Object o) throws ClassCastException, NullPointerException;

    /**
     * Returns true if this list contains no elements.
     * 
     * @return true if this list contains no elements.
     */
    boolean isEmpty();

    /**
     * 
     * @return
     */
    HIterator iterator();

    /**
     * 
     * @param o
     * @return
     * @throws ClassCastException
     * @throws NullPointerException
     */
    int lastIndexOf(Object o) throws ClassCastException, NullPointerException;

    /**
     * 
     * @return
     */
    HListIterator listIterator();

    /**
     * 
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    HListIterator listIterator(int index) throws IndexOutOfBoundsException;

    /**
     * 
     * @param index
     * @return
     */
    Object remove(int index);

    /**
     * 
     * @param o
     * @return
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws UnsupportedOperationException
     */
    boolean remove(Object o) throws ClassCastException, NullPointerException, UnsupportedOperationException;

    /**
     * 
     * @param c
     * @return
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     */
    boolean removeAll(HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException;

    /**
     * 
     * @param c
     * @return
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     */
    boolean retainAll(HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException;

    /**
     * 
     * @param index
     * @param element
     * @return
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @throws IndexOutOfBoundsException
     */
    Object set(int index, Object element) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException, IndexOutOfBoundsException;
    
    /**
     * Returns the number of elements in this list. If this list contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     * 
     * @return the number of elements in this list.
     */
    int size();

    /**
     * 
     * @param fromIndex
     * @param toIndex
     * @return
     * @throws IndexOutOfBoundsException
     */
    HList subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException;

    /**
     * 
     * @return
     */
    Object[] toArray();

    /**
     * 
     * @param a
     * @return
     * @throws ArrayStoreException
     * @throws NullPointerException
     */
    Object[] toArray(Object[] a) throws ArrayStoreException, NullPointerException;
}
