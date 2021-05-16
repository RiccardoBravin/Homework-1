package myAdapter;

import java.util.Enumeration;
import java.util.Hashtable;

public class MapAdapter implements HMap {

    public class EntryAdapter implements HMap.HEntry {

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

        public boolean equals(Object obj){
            if(!(obj instanceof HMap.HEntry)) return false;
            HEntry HE = (HMap.HEntry)obj;
            if(!this.getKey().equals(HE.getKey())) return false;
            if(!this.getValue().equals(HE.getValue())) return false;
            return true;
        } 

        public String toString() {
            return key.toString() + value.toString();
        }
        private Object key;
        private Object value;
    }

    
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

    
    public boolean containsKey(Object key) throws NullPointerException {
        return hashT.containsKey(key);
    }

    
    public boolean containsValue(Object value) throws NullPointerException {
        return hashT.contains(value);
    }

    
    public HSet entrySet() {                                    //backing
        Enumeration keys = hashT.keys();
        Enumeration values = hashT.elements();

        HSet toReturn = new EntrySetAdapter(this);

        return toReturn;
    }

    
    public Object get(Object key) throws NullPointerException {
        return hashT.get(key);
    }

    
    public boolean isEmpty() {
        return hashT.isEmpty();
    }

    
    public HSet keySet() {                                          //backing
        return new KeySetAdapter(this);
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

    
    public Object remove(Object key) throws NullPointerException {
        return hashT.remove(key);
    }

    
    public int size() {
        return hashT.size();
    }

    
    public HCollection values() {                             //backing  
        return new ValueCollection(hashT);
    }

    public String toString() {
        return hashT.toString();
    }
    
    private Hashtable hashT;

    public class EntrySetAdapter implements HSet {

        public EntrySetAdapter(MapAdapter map) throws NullPointerException {
            this.map = map;
            this.hashT = map.hashT;
        }
        
        
        public boolean add(Object o) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    
        
        public boolean addAll(HCollection c) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    
        
        public void clear()  {
            map.clear();
            
        }
    
        
        public boolean contains(Object o) throws NullPointerException {            
            if(o == null) throw new NullPointerException();
            if(!(o instanceof HMap.HEntry)) return false;
            HMap.HEntry HEAux = (HMap.HEntry)o;
            Enumeration keys = map.hashT.keys();
            Enumeration values = map.hashT.elements();
    
            while(keys.hasMoreElements()){
                HMap.HEntry temp = new MapAdapter().new EntryAdapter(keys.nextElement(), values.nextElement());
                if(HEAux.equals(temp))
                    return true;
            }
            return false;
        }
    
        
        public boolean containsAll(HCollection c) throws  NullPointerException {
            if(c == null) throw new NullPointerException();
            HIterator iter = c.iterator();
            
            while(iter.hasNext()) {
                if(!contains(iter.next()))
                    return false;
            }
            return true;
        }

        
        public boolean isEmpty() {
            return map.hashT.isEmpty();
        }
    
        
        public HIterator iterator() {
            return new HashtableIterator(map.hashT);
        }
    
        
        public boolean remove(Object o) throws ClassCastException, NullPointerException {
            if(o == null) throw new NullPointerException();
            if(!(o instanceof HMap.HEntry)) throw new ClassCastException();
            HMap.HEntry temp = (HMap.HEntry)o;
            if(map.remove(temp.getKey()) == null)
                return false;
            return true;
        }
    
        
        public boolean removeAll(HCollection c) throws NullPointerException {
            if(c == null) throw new NullPointerException();
            HIterator iter = c.iterator();
            boolean removed = false;
    
            while(iter.hasNext()){
                Object temp = iter.next();
                if(temp instanceof HMap.HEntry) {
                    HMap.HEntry HETemp = (HMap.HEntry)temp;
                    if(map.remove(HETemp.getKey()) != null)
                        removed = true;
                }
            }
            return removed;
        }
    
        
        public boolean retainAll(HCollection c) throws ClassCastException, NullPointerException {
            if(c == null) throw new NullPointerException();
    
            Enumeration keys = map.hashT.keys();
            Enumeration values = map.hashT.elements();
            boolean changed = false;
    
            while(keys.hasMoreElements()) {
                HMap.HEntry temp = new MapAdapter().new EntryAdapter(keys.nextElement(), values.nextElement());
                HIterator CIter = c.iterator();
                boolean found = false;
                if(!c.contains(temp)) {
                    map.remove(temp.getKey());
                    changed = true;
                }
            }
            return changed;
        }
    
        
        public int size() {
            return map.size();
        }
    
        
        public Object[] toArray() {
            Enumeration keys = map.hashT.keys();
            Enumeration values = map.hashT.elements();
            Object[] arr = new Object[map.hashT.size()];
    
            for(int i=0; keys.hasMoreElements(); i++){
                arr[i] = new MapAdapter().new EntryAdapter(keys.nextElement(), values.nextElement());
            }
            return arr;
        }
    
        
        public Object[] toArray(Object[] a) throws ArrayStoreException, NullPointerException {
            if(a == null) throw new NullPointerException();
            if(a.length >= map.hashT.size()){
                Enumeration keys = map.hashT.keys();
                Enumeration values = map.hashT.elements();
                for(int i=0; keys.hasMoreElements(); i++){
                    a[i] = new MapAdapter().new EntryAdapter(keys.nextElement(), values.nextElement());
                }
                return a;
            } else return this.toArray();
        }

    
        private MapAdapter map;
        private Hashtable hashT;
    }

    
    //______________________________________________________________________________________________________
    
    public class KeySetAdapter implements HSet{

        public KeySetAdapter(MapAdapter map) {
            this.map = map;
            this.hashT = map.hashT; 
        }
    
    
        public boolean add(Object o) throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }
    
        
        public boolean addAll(HCollection c) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    
        
        public void clear() {
            map.clear();
        }
    
        
        public boolean contains(Object o) throws  NullPointerException {
            if(o == null) throw new NullPointerException();
            return map.containsKey(o);
        }
    
        
        public boolean containsAll(HCollection c) throws NullPointerException {
            if(c == null) throw new NullPointerException();
            HIterator iter = c.iterator();
    
            while(iter.hasNext()){
                if(!map.containsKey(iter.next()))
                    return false;
            }
    
            return true;
        }
    
        
        public boolean isEmpty() {
            return map.isEmpty();
        }
    
        
        public HIterator iterator() {
            return new HashtableIterator(hashT);
        }

        public HIterator valueIterator() {
            return new HashtableValueIterator(hashT);
        }
    
        
        public boolean remove(Object o) throws NullPointerException {
            if(o == null) throw new NullPointerException();
            if(map.remove(o) == null)
                return false;
            return true;
        }
    
        
        public boolean removeAll(HCollection c) throws NullPointerException {
            if(c == null) throw new NullPointerException();
            HIterator iter = c.iterator();
            boolean somethingRemoved = false;
    
            while(iter.hasNext()) {
                if(remove(iter.next()))
                    somethingRemoved = true;
            }
            return somethingRemoved;
        }
    
        
        public boolean retainAll(HCollection c) throws NullPointerException {
            if(c == null) throw new NullPointerException();
            HIterator iter = this.iterator();
            boolean somethingRemoved = false;
    
            /*if(!iter.hasNext()) {
                int size = map.size();
                map.clear();
                if(size==0) return false;
                else return true;
            }*/ 
            while(iter.hasNext()){
                Object next = iter.next();
                if(next instanceof HMap.HEntry) {
                    Object nextKey = ((HMap.HEntry)next).getKey();
                    if(!c.contains(nextKey)){
                        map.remove(nextKey);
                        somethingRemoved = true;
                    }
                }
            }
    
            return somethingRemoved;
        }
    
        
        public int size() {
            return map.size();
        }
    
        
        public Object[] toArray() {
            Object[] arr = new Object[hashT.size()];
            Enumeration keys = hashT.keys();
            for(int i=0; keys.hasMoreElements(); i++) {
                arr[i] = keys.nextElement();
            }
            return arr;
        }
    
        
        public Object[] toArray(Object[] a) throws NullPointerException, ClassCastException {
            if(a == null) throw new NullPointerException();
            if(a.length >= hashT.size()) {
                Enumeration keys = hashT.keys();
                for(int i=0; keys.hasMoreElements(); i++) {
                    a[i] = keys.nextElement();
                }
                return a;
            } else return toArray();
        }
    
        private MapAdapter map;
        private Hashtable hashT;
        
    }

    //____________________________________________________________________________________________
    public class ValueCollection implements HCollection{
        private Hashtable hashT;
    
        public ValueCollection(Hashtable hashT){
            if(hashT == null) throw new NullPointerException();
            this.hashT = hashT;
        }
    
        public boolean add(Object o) throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }
    
        public boolean addAll(HCollection o) throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }
    
        public void clear(){
            hashT.clear();
        }
    
        public boolean contains(Object o){
            return hashT.contains(o);
        }
    
        public boolean containsAll(HCollection c) throws NullPointerException{
            if(c == null) throw new NullPointerException();
            HIterator iter = c.iterator();
            while(iter.hasNext()){
                if(!contains(iter.next())){
                    return false;
                }
            }
            return true;
        }
    
        public boolean equals(Object o){
            return (o instanceof HCollection) && (((HCollection)o).containsAll(this)) && (this.containsAll((HCollection)o));
        }
    
        public int hashCode(){
            int sum = 0;
            HIterator iter = this.iterator();
            while(iter.hasNext()){
                sum += iter.next().hashCode();
            }
            return sum;
        }        
    
        public boolean isEmpty(){
            return hashT.isEmpty();
        }
    
        public HIterator iterator(){
            return new HashtableValueIterator(hashT);
        }
    
        public boolean remove(Object o){
            Enumeration keys = hashT.keys();
            while(keys.hasMoreElements()){
                Object k = keys.nextElement();
                if(hashT.get(k) == o){
                    hashT.remove(k);
                    return true;
                } 
            }
            return false;
        }
    
        public boolean removeAll(HCollection c) throws NullPointerException{  
            if(c == null) throw new NullPointerException();
    
            HIterator iter = c.iterator();
            boolean changed = false;
    
            while(iter.hasNext()){
                if(remove(iter.next())){
                    changed = true;
                }
            }
    
            return changed;
        }
    
        public boolean retainAll(HCollection c) throws NullPointerException{
    
            if(c == null) throw new NullPointerException();
    
            HIterator iter = this.iterator();
            boolean changed = false;
            
            while(iter.hasNext()){
                if(!c.contains(iter.next())){
                    iter.remove();
                    changed = true;
                }
            }
    
            return changed;
        }
    
        public int size(){
            return hashT.size();
        }
    
        public Object[] toArray(){
            Object[] arr = new Object[this.size()];
            Enumeration elems = hashT.elements();
    
            for(int i = 0; i < hashT.size(); i++){
                arr[i] = elems.nextElement();
            }
            return arr;
        }
    
        public Object[] toArray(Object[] a) throws NullPointerException{
        
            if(a == null) throw new NullPointerException("Array null");
    
            if(a.length >= this.size()){
                Enumeration elems = hashT.elements();
                for(int i = 0; i < hashT.size(); i++){
                    a[i] = elems.nextElement();
                }
                return a;
            }
            return this.toArray();
        }
    }
}
