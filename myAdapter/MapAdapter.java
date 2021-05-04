package myAdapter;

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
            
            return (o instanceof HEntry) && (this.getKey()==null ? o.getKey()==null : this.getKey().equals(o.getKey()))  && (this.getValue()==null ? o.getValue()==null : this.getValue().equals(o.getValue())) ;
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

        public Object setValue(Object value){
            
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

    public MapAdapter(int initialCapacity){
        ht = new Hashtable(initialCapacity);
    }

    public MapAdapter(MapAdapter map){
        //... to do ...
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
        //... to do ...
    }

    public boolean equals(Object o){
        return this.entrySet().equals(o.entrySet());
    }

    public int hashCode(){
        //fa fatto l'hashcode di ogni entry della mappa
    }

    public boolean isEmpty(){
        return ht.isEmpty();
    }

    public HSet keySet(){
        // ... to do ...
    }


}
