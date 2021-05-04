package myAdapter;
public interface HMap {
    
    /**
     * Removes all mappings from this map (optional operation).
     * 
     * @throws UnsupportedOperationException if clear is not supported by the map
     */
    void clear() throws UnsupportedOperationException;

    /**
     * Returns true if this map contains a mapping for the specified key. More formally, returns true if and only if this map contains at a mapping for a key k such that (key==null ? k==null : key.equals(k)). (There can be at most one such mapping.)
     * 
     * @param key - key whose presence in this map is to be tested.
     * @return true if this map contains a mapping for the specified key, false otherwise.
     * @throws ClassCastException if the key is of an inappropriate type for this map (optional).
     * @throws NullPointerException if the key is null and this map does not not permit null keys (optional).
     */
    boolean containsKey(Object key) throws ClassCastException, NullPointerException;

    /**
     * Returns true if this map maps one or more keys to the specified value. More formally, returns true if and only if this map contains at least one mapping to a value v such that (value==null ? v==null : value.equals(v)). This operation will probably require time linear in the map size for most implementations of the Map interface.
     * 
     * @param value - value whose presence in this map is to be tested.
     * @return true if this map maps one or more keys to the specified value, false otherwise.
     * @throws ClassCastException if the value is of an inappropriate type for this map (optional).
     * @throws NullPointerException if the value is null and this map does not not permit null values (optional).
     */
    boolean containsValue(Object value) throws ClassCastException, NullPointerException;

    /**
     * Returns a set view of the mappings contained in this map. Each element in the returned set is a Map.Entry. The set is backed by the map, so changes to the map are reflected in the set, and vice-versa. If the map is modified while an iteration over the set is in progress, the results of the iteration are undefined. The set supports element removal, which removes the corresponding mapping from the map, via the Iterator.remove, Set.remove, removeAll, retainAll and clear operations. It does not support the add or addAll operations.
     * 
     * @return a set view of the mappings contained in this map.
     */
    Set entrySet();

    /**
     * Compares the specified object with this map for equality. Returns true if the given object is also a map and the two Maps represent the same mappings. More formally, two maps t1 and t2 represent the same mappings if t1.entrySet().equals(t2.entrySet()). This ensures that the equals method works properly across different implementations of the Map interface.
     * 
     * @param o - object to be compared for equality with this map.
     * @return true if the specified object is equal to this map.
     */
    boolean equals(Object o);

    /**
     * Returns the value to which this map maps the specified key. Returns null if the map contains no mapping for this key. A return value of null does not necessarily indicate that the map contains no mapping for the key; it's also possible that the map explicitly maps the key to null. The containsKey operation may be used to distinguish these two cases.
     * More formally, if this map contains a mapping from a key k to a value v such that (key==null ? k==null : key.equals(k)), then this method returns v; otherwise it returns null. (There can be at most one such mapping.)
     * 
     * @param key - key whose associated value is to be returned.
     * @return the value to which this map maps the specified key, or null if the map contains no mapping for this key.
     * @throws ClassCastException the value to which this map maps the specified key, or null if the map contains no mapping for this key.
     * @throws NullPointerException key is null and this map does not not permit null keys (optional).
     */
    Object get(Object key) throws ClassCastException, NullPointerException;

    
    int hashCode();

    boolean isEmpty();

    Set keySet();

    Object put(Object key, Object value) throws UnsupportedOperationException, ClassCastException, IllegalArgumentException, NullPointerException;

    void putAll(Map t) throws UnsupportedOperationException, ClassCastException, IllegalArgumentException, NullPointerException;

    Object remove(Object key) throws ClassCastException, NullPointerException, UnsupportedOperationException;

    int size();

    Collection values();
}
