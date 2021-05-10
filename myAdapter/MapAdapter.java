//package myAdapter;

import java.util.Enumeration;
import java.util.Hashtable;

public class MapAdapter implements HMap {
    
    //Variables
    private Hashtable ht;


    //Entry class
    public class EntryAdapter implements HEntry{

        private Object key;
        
        private Object value;


        private EntryAdapter(Object key, Object value){
            this.key = key;
            this.value = value;
        }

        public boolean equals(Object o){
            
            if(o instanceof HEntry){
                return (this.getKey() == null ? ((HEntry)o).getKey() == null : this.getKey().equals(((HEntry)o).getKey())) && (this.getValue() == null ? ((HEntry)o).getValue() == null : this.getValue().equals(((HEntry)o).getValue())) ;
            }
            return false;
        }

        public Object getKey(){
            return key;
        }

        public Object getValue(){
            return value;
        }

        public int hashCode(){
            return (this.getKey()==null ? 0 : this.getKey().hashCode()) ^ (this.getValue()==null ? 0 : this.getValue().hashCode());
        }

        public Object setValue(Object value) throws NullPointerException{
            
            ht.remove(this.key);
            ht.put(this.key, value);
            Object aux = this.value;

            this.value = value;

            return aux;

        }


    } 

    //Constructors

    public MapAdapter(){
        ht = new Hashtable();
    }

    public MapAdapter(int initialCapacity) throws IllegalArgumentException{
        ht = new Hashtable(initialCapacity);
    }

    public MapAdapter(HMap map) throws NullPointerException{
        if(map == null) throw new NullPointerException();
        ht = new Hashtable(map.size());
        this.putAll(map);
    }

    //Methods
    
    public void clear(){
        ht.clear();
    }

    public boolean containsKey(Object key){
        return ht.containsKey(key);
    }

    public boolean containsValue(Object value) throws NullPointerException{
        return ht.contains(value);
    }

    public HSet entrySet(){

        return new EntrySet(ht);
    }

    public boolean equals(Object o){
        if(!(o instanceof HMap)) return false;
        return this.entrySet().equals(((HMap)o).entrySet());
    }

    public Object get(Object key) throws NullPointerException{
        return ht.get(key);
    }

    public int hashCode(){
        int sum = 0;
        HIterator elem = this.entrySet().iterator();

        while(elem.hasNext()){
            sum += elem.next().hashCode();
        }
        return sum;
    }

    public boolean isEmpty(){
        return ht.isEmpty();
    }

    public HSet keySet(){

        return new KeySet(ht);
    }

    public Object put(Object key, Object value) throws NullPointerException{
        
        Object aux = ht.remove(key);
        ht.put(key, value);
        return aux;
    }

    public void putAll(HMap t) throws NullPointerException{
        
        if(t == null) throw new NullPointerException("La mappa passata è null, impossibile aggiungere elementi");

        HIterator elems = t.entrySet().iterator();

        while(elems.hasNext()){
            HEntry e = (HEntry)elems.next();
            this.ht.put(e.getKey(), e.getValue());
        }
    }    

    public Object remove(Object key){
        return ht.remove(key);
    }

    public int size(){
        return ht.size();
    }


    public HCollection values(){
        return new ValueCollection(ht);
    }

    public String toString(){
        return ht.toString();
    }


    
    public class KeySet implements HSet{
        
        private Hashtable ht;

        public KeySet(Hashtable ht){
            this.ht = ht;
        }

        public boolean add(Object o) throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }

        public boolean addAll(HCollection o) throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }

        public void clear(){
            ht.clear();
        }

        public boolean contains(Object o){
            return ht.containsKey(o);
        }

        public boolean containsAll(HCollection c) throws NullPointerException{
            if(c == null) throw new NullPointerException("Collection null");
            HIterator iter = c.iterator();
            while(iter.hasNext()){
                if(!contains(iter.next())){
                    return false;
                }
            }
            return true;
        }

        public boolean equals(Object o){
            return (o instanceof HSet) && (((HSet)o).containsAll(this)) && (this.containsAll((HSet)o));
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
            return ht.isEmpty();
        }

        public HIterator iterator(){
            return new IteratorAdapter(this);
        }

        public boolean remove(Object o){
            if(ht.remove(o) == null)  return false;
            return true;
        }

        public boolean removeAll(HCollection c) throws NullPointerException{  
            if(c == null) throw new NullPointerException("Collection null");

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

            if(c == null) throw new NullPointerException("Collection null");
    
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
            return ht.size();
        }

        public Object[] toArray(){
            Object[] arr = new Object[this.size()];
            Enumeration keys = ht.keys();

            for(int i = 0; i < ht.size(); i++){
                arr[i] = keys.nextElement();
            }
            return arr;
        }

        public Object[] toArray(Object[] a) throws NullPointerException{
        
            if(a == null) throw new NullPointerException("Array null");
    
            if(a.length >= this.size()){
                Enumeration keys = ht.keys();
                for(int i = 0; i < ht.size(); i++){
                    a[i] = keys.nextElement();
                }
                return a;
            }
            return this.toArray();
        }

        

    }

    public class EntrySet implements HSet{
        private Hashtable ht;

        public EntrySet(Hashtable ht){
            this.ht = ht;
        }

        public boolean add(Object o) throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }

        public boolean addAll(HCollection o) throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }

        public void clear(){
            ht.clear();
        }

        public boolean contains(Object o){
            if(! (o instanceof EntryAdapter)) return false;
            EntryAdapter obj = (EntryAdapter)o;
            return ht.containsKey(obj.getKey()) && ht.get(obj.getKey()).equals(obj.getValue());
        }

        public boolean containsAll(HCollection c) throws NullPointerException{
            if(c == null) throw new NullPointerException("Collection null");
            HIterator iter = c.iterator();
            while(iter.hasNext()){
                if(!contains(iter.next())){
                    return false;
                }
            }
            return true;
        }

        public boolean equals(Object o){
            return (o instanceof HSet) && (((HSet)o).containsAll(this)) && (this.containsAll((HSet)o));
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
            return ht.isEmpty();
        }

        public HIterator iterator(){
            return new IteratorAdapter(this);
        }

        public boolean remove(Object o){
            if(! (o instanceof EntryAdapter)) return false;
            if(ht.remove(((EntryAdapter)o).getKey()) == null)  return false;
            return true;
        }

        public boolean removeAll(HCollection c) throws NullPointerException{  
            if(c == null) throw new NullPointerException("Collection null");

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

            if(c == null) throw new NullPointerException("Collection null");
    
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
            return ht.size();
        }

        public Object[] toArray(){
            Object[] arr = new Object[this.size()];
            Enumeration keys = ht.keys();

            for(int i = 0; i < ht.size(); i++){
                Object key = keys.nextElement();
                arr[i] = new EntryAdapter(key, ht.get(key));
            }
            return arr;
        }

        public Object[] toArray(Object[] a) throws NullPointerException{
        
            if(a == null) throw new NullPointerException("Array null");
    
            if(a.length >= this.size()){
                Enumeration keys = ht.keys();
                for(int i = 0; i < ht.size(); i++){
                    Object key = keys.nextElement();
                    a[i] = new EntryAdapter(key, ht.get(key));
                }
                return a;
            }
            return this.toArray();
        }

        
    }

    public class ValueCollection implements HCollection{
        private Hashtable ht;

        public ValueCollection(Hashtable ht){
            this.ht = ht;
        }

        public boolean add(Object o) throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }

        public boolean addAll(HCollection o) throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }

        public void clear(){
            ht.clear();
        }

        public boolean contains(Object o){
            return ht.contains(o);
        }

        public boolean containsAll(HCollection c) throws NullPointerException{
            if(c == null) throw new NullPointerException("Collection null");
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
            return ht.isEmpty();
        }

        public HIterator iterator(){
            return new IteratorAdapter(this);
        }

        public boolean remove(Object o){ //chiedere se va bene che rimuova un elemento qualsiasi
            Enumeration keys = ht.keys();
            while(keys.hasMoreElements()){
                Object k = keys.nextElement();
                if(ht.get(k) == o){
                    ht.remove(k);
                    return true;
                } 
            }
            return false;
        }

        public boolean removeAll(HCollection c) throws NullPointerException{  
            if(c == null) throw new NullPointerException("Collection null");

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

            if(c == null) throw new NullPointerException("Collection null");
    
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
            return ht.size();
        }

        public Object[] toArray(){
            Object[] arr = new Object[this.size()];
            Enumeration elems = ht.elements();

            for(int i = 0; i < ht.size(); i++){
                arr[i] = elems.nextElement();
            }
            return arr;
        }

        public Object[] toArray(Object[] a) throws NullPointerException{
        
            if(a == null) throw new NullPointerException("Array null");
    
            if(a.length >= this.size()){
                Enumeration elems = ht.elements();
                for(int i = 0; i < ht.size(); i++){
                    a[i] = elems.nextElement();
                }
                return a;
            }
            return this.toArray();
        }
    }
}
