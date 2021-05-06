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


        EntryAdapter(Object key, Object value){
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

        Enumeration k = ht.keys();
        Enumeration e = ht.elements();

        HSet ret = new SetAdapter();
                
        while(k.hasMoreElements()){
            ret.add(new EntryAdapter(k.nextElement(), e.nextElement()));
        }

        return ret;
    }

    public boolean equals(Object o){
        return this.entrySet().equals(((HMap)o).entrySet());
    }

    public Object get(Object key){
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

        Enumeration key = ht.keys();
        
        HSet set = new SetAdapter();

        while(key.hasMoreElements()){
            set.add(key.nextElement());
        }

        return set;
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
        
        HCollection coll = new CollectionAdapter(); //CREDO VADA CONTROLLATO SE POSSO RITORNARE SEMPLICEMENTE UNA LISTA DATO CHE IMPLEMENTA COLLECTION
        Enumeration aux = ht.elements();
        while(aux.hasMoreElements()){
            coll.add(aux.nextElement());
        }
        
        return coll;
    }


    //Metodi da eliminare terminati i test
    public Hashtable getHashtable(){
        return ht;
    }

}
