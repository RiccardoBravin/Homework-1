

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
                return (this.getKey()==null ? ((HEntry)o).getKey()==null : this.getKey().equals(((HEntry)o).getKey()))  && (this.getValue()==null ? ((HEntry)o).getValue()==null : this.getValue().equals(((HEntry)o).getValue())) ;
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
        return null;
    }

    public boolean equals(Object o){
        if(o instanceof HMap)
            return this.entrySet().equals(((HMap)o).entrySet());
        return false;
    }

    public Object get(Object key) throws ClassCastException, NullPointerException{
        //da vedere
        return null;
    }

    public int hashCode(){
        //fa fatto l'hashcode di ogni entry della mappa
        return 0;
    }

    public boolean isEmpty(){
        return ht.isEmpty();
    }

    public HSet keySet(){
        // ... to do ...
        return null;
    }

    public Object put(Object key, Object value) throws UnsupportedOperationException, ClassCastException, IllegalArgumentException, NullPointerException{
        // ... to do ...
        return null;
    }

    public void putAll(HMap t) throws UnsupportedOperationException, ClassCastException, IllegalArgumentException, NullPointerException{

    }

    public Object remove(Object key) throws ClassCastException, NullPointerException, UnsupportedOperationException{
        return null;
    }

    public int size(){
        return 0;
    }

    public HCollection values(){
        return null;
    }


}
