package myAdapter;

public interface HList {

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

    boolean isEmpty();

    HIterator iterator();

    int lastIndexOf(Object o) throws ClassCastException, NullPointerException;

    HListIterator listIterator();

    HListIterator listIterator(int index) throws IndexOutOfBoundsException;

    Object remove(int index);

    boolean remove(Object o) throws ClassCastException, NullPointerException, UnsupportedOperationException;

    boolean removeAll(HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException;

    boolean retainAll(HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException;

    object set(int index, Object element) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException, IndexOutOfBoundsException;

    int size();

    HList subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException;

    object[] toArray();

    Object[] toArray(Object[] a) throws ArrayStoreException, NullPointerException;
}
