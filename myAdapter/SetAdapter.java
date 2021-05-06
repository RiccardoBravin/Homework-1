import java.util.NoSuchElementException;
import java.util.Vector;


public class SetAdapter implements HSet {

    public SetAdapter(){
        vec = new Vector();
    }
    
    public boolean add(Object o) {
        if(vec.contains(o)) return false;
        vec.addElement(o);
        return true;
    }

    
    public boolean addAll(HCollection c) throws NullPointerException {
        if(c == null) throw new NullPointerException();
        
        int oldSize = vec.size();
        HIterator iter = c.iterator();

        while(iter.hasNext()) {
            Object temp = iter.next();
            if(!vec.contains(temp))
                vec.addElement(temp);
        }

        return (oldSize != vec.size());
    }

    
    public void clear() {
        vec.removeAllElements();
    }

    
    public boolean contains(Object o) {
        return vec.contains(o);
    }

    
    public boolean containsAll(HCollection c) throws NullPointerException {
        if(c == null) throw new NullPointerException();

        HIterator iter = c.iterator();
        while(iter.hasNext()) {
            if(!vec.contains(iter.next()))
                return false;
        }

        return true;
    }

    
    public boolean isEmpty() {
        return vec.isEmpty();
    }

    
    public HIterator iterator() {
        return new IteratorAdapter();
    }

    
    public boolean remove(Object o) {
        int i = vec.indexOf(o);
        if(i > -1) {
            vec.removeElementAt(i);
            return true;
        }
        return false;
    }

    
    public boolean removeAll(HCollection c) throws NullPointerException {
        if(c == null) throw new NullPointerException();
        int oldSize = vec.size();
        for(int i=0; i<vec.size(); i++){
            if(c.contains(vec.elementAt(i)))
                vec.removeElementAt(i);
        }

        return (vec.size() == oldSize);
    }

    
    public boolean retainAll(HCollection c) throws NullPointerException {
        if(c == null) throw new NullPointerException();
        int oldSize = vec.size();
        for(int i=0; i<vec.size(); i++){
            if(!c.contains(vec.elementAt(i)))
                vec.removeElementAt(i);
        }

        return (vec.size() == oldSize);
    }

    
    public int size() {
        return vec.size();
    }

    
    public Object[] toArray() {
        Object[] arr = new Object[vec.size()];
        for(int i=0; i<vec.size(); i++) {
            arr[i] = vec.elementAt(i);
        }
        return arr;
    }

    
    public Object[] toArray(Object[] a) throws ArrayStoreException, NullPointerException {
        if(a == null) throw new NullPointerException();
        if(a.length < vec.size()){
            Object[] arr = new Object[vec.size()];
            for(int i=0; i<vec.size(); i++) {
                arr[i] = vec.elementAt(i);
            }
            return arr;
        } else {
            for(int i=0; i<vec.size(); i++) {
                a[i] = vec.elementAt(i);        //basta questo? in caso è questa istruzione a lanciare l'eccezione?                  
            }
            return a;   
        }
    }

    public class IteratorAdapter implements HIterator {

        public IteratorAdapter(){
            cursor = 0;
        }
        
        public boolean hasNext(){
            return (cursor <= vec.size());
        }

        public Object next() throws NoSuchElementException{
            if(!hasNext()) throw new NoSuchElementException();
            return vec.elementAt(cursor++);
        }

        public void remove() throws UnsupportedOperationException, IllegalStateException{
            vec.removeElementAt(--cursor);
        }

        private int cursor;
    }
    
    private Vector vec;
    
}
