//package myAdapter;

import java.util.Enumeration;
import java.util.Hashtable;

public class MapAdapter implements HMap {

    public MapAdapter(){
        hashT = new Hashtable();
    }

    public MapAdapter(int initialCapacity){
        hashT = new Hashtable(initialCapacity);
    }

    public MapAdapter(HMap map) throws NullPointerException{
        hashT = new Hashtable(map.size());
        this.putAll(map);
    }

    public void clear() {
        hashT.clear();
    }

    
    public boolean containsKey(Object key)  {
        return hashT.containsKey(key);
    }

    
    public boolean containsValue(Object value) throws NullPointerException {
        return hashT.contains(value);
    }

    
    public HSet entrySet() {
        Enumeration keys = hashT.keys();
        Enumeration values = hashT.elements();

        HSet toReturn = new SetAdapter();

        while(keys.hasMoreElements()){
            toReturn.add(new EntryAdapter(keys.nextElement(), values.nextElement()));
        }
        return toReturn;
    }

    
    public Object get(Object key) {
        return hashT.get(key);
    }

    
    public boolean isEmpty() {
        return hashT.isEmpty();
    }

    
    public HSet keySet() {
        Enumeration keys = hashT.keys();

        HSet toReturn = new SetAdapter();

        while(keys.hasMoreElements()) {
            toReturn.add(keys.nextElement());
        }

        return toReturn;
    }

    
    public Object put(Object key, Object value) throws NullPointerException {
        Object toReturn = hashT.remove(key);
        hashT.put(key, value);
        return toReturn;
    }

    
    public void putAll(HMap t) throws NullPointerException {
        HIterator iter = t.entrySet().iterator();

        while(iter.hasNext()) {
            HEntry entry = (HEntry)iter.next();
            this.hashT.put(entry.getKey(), entry.getValue());
        }
    }

    
    public Object remove(Object key) throws ClassCastException, NullPointerException, UnsupportedOperationException {
        return hashT.remove(key);
    }

    
    public int size() {
        return hashT.size();
    }

    
    public HCollection values() {
        HCollection collection = new ListAdapter();
        Enumeration values = hashT.elements();
        
        while(values.hasMoreElements()) {
            collection.add(values.nextElement());
        }
        return collection;
    }

    public class EntryAdapter implements HEntry {

        public EntryAdapter(Object k, Object v){
            key = k;
            value = v;
        }

        public Object getKey() {
            return key;
        }

        
        public Object getValue() {
            return value;
        }

        
        public int hasCode() {
            return (getKey()==null   ? 0 : getKey().hashCode()) ^ (getValue()==null ? 0 : getValue().hashCode());
        }

        
        public Object setValue(Object value) {
            Object old = this.value;
            hashT.remove(key);
            hashT.put(key, value);
            this.value = value;
            return old;
        }

        private Object key;
        private Object value;

    }
    
    private Hashtable hashT;

}
