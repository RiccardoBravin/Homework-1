import java.util.NoSuchElementException;
import java.util.Vector;


public class ListAdapter implements HList {
    private Vector v;

    private int modCount = 0; 

    public ListAdapter(){
        v = new Vector();
    }

    public ListAdapter(int initialCapacity){
        v  = new Vector(initialCapacity);
    }

    public void add(int index, Object Element) throws IndexOutOfBoundsException{
        try{
            v.insertElementAt(Element, index);
        }catch (ArrayIndexOutOfBoundsException aioobe){
            System.out.println(aioobe.getMessage());
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean add(Object obj){
        v.addElement(obj);
        return true;
    }

    public boolean addAll(HCollection c) throws NullPointerException{
        if(c == null) throw new NullPointerException("Collection null");
        
        HIterator elems = c.iterator();
        
        while(elems.hasNext()){
            this.v.addElement(elems.next());
        }

        return !c.isEmpty();
    }

    public boolean addAll(int index, HCollection c) throws NullPointerException, IndexOutOfBoundsException{
        if(c == null) throw new NullPointerException("Collection null");
        if(index < 0) throw new IndexOutOfBoundsException("Indice negativo");
        
        HIterator elems = c.iterator();
        

        while(elems.hasNext()){
            this.v.insertElementAt(elems.next(), index++);
        }

        return !c.isEmpty();
    }

    public void clear(){
        this.v.clear();
    }

    public boolean contains(Object o){
        return this.v.contains(o);
    }

    public boolean containsAll(HCollection c) throws NullPointerException{
        if(c == null) throw new NullPointerException("Collection null");

        HIterator elems = c.iterator();

        while(elems.hasNext()){
            if(!this.v.contains(elems.next())){
                return false;
            }
        }

        return false;

    }

    public boolean equals(Object o){
        if(!(o instanceof HList)) return false;
        if(this.size() !=  ((HList)o).size()) return false;

        HIterator itero = ((HList)o).iterator();
        HIterator itert = this.iterator();

        while(itero.hasNext()){
            if(!itero.next().equals(itert.next())) return false;
        }

        return true;
    }

    public  Object get(int index) throws IndexOutOfBoundsException{
        try{
            return v.elementAt(index);
        }catch (ArrayIndexOutOfBoundsException aioobe){
            System.out.println(aioobe.getMessage());
            throw new IndexOutOfBoundsException(aioobe.getMessage());
        }

    }

    public int hashCode(){
        int hashCode = 1;
        HIterator i = this.iterator();
        while (i.hasNext()) {

            Object obj = i.next();
            hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
        }

        return hashCode;
        
    }

    public int indexOf(Object o){
        return v.indexOf(o);
    }

    public boolean isEmpty(){
        return v.isEmpty();
    }

    public HIterator iterator(){
        return new IteratorAdapter(v); 
    }

    public int lastIndexOf(Object o){
        return v.lastIndexOf(o);
    }

    public HListIterator listIterator(){
        return new ListIteratorAdapter(v);
    }

    public HListIterator listIterator(int index) throws IndexOutOfBoundsException{
        if((index < 0 || index > v.size())) throw new IndexOutOfBoundsException();
        return new ListIteratorAdapter(v, index);
    }

    public Object remove(int index){
        try{
            Object aux = v.elementAt(index);
            v.removeElementAt(index);
            return aux;
        }catch(ArrayIndexOutOfBoundsException aioobe){
            System.out.println(aioobe.getMessage());
            throw new IndexOutOfBoundsException(aioobe.getMessage());
        }
    }

    public boolean remove(Object o){
        return v.removeElement(o);
    }

    public boolean removeAll(HCollection c) throws NullPointerException{
        if(c == null) throw new NullPointerException("Collection null");

        HIterator elems = c.iterator();
        boolean changed = false;
        
        while(elems.hasNext()){
            if(this.v.removeElement(elems.next())){
                changed = true;
            }
        }

        return changed;
    }

    public boolean retainAll(HCollection c) throws NullPointerException{
        if(c == null) throw new NullPointerException("Collection null");
        HIterator elems = this.iterator();
        boolean changed = false;
        
        while(elems.hasNext()){
            if(!c.contains(elems.next())){
                elems.remove();
                changed = true;
            }
        }

        return changed;
    }

    public Object set(int index, Object element){
        try{
            Object aux = v.elementAt(index);
            v.setElementAt(element, index);
            return aux;
        }catch(ArrayIndexOutOfBoundsException aioobe){
            System.out.println(aioobe.getMessage());
            throw new IndexOutOfBoundsException(aioobe.getMessage());
        }
    }

    public int size(){
        return v.size();
    }

    public HList subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException{
        if(fromIndex > toIndex || fromIndex < 0 || toIndex > v.size()) throw new IndexOutOfBoundsException();
        
        return new LimitedListAdapter(fromIndex, toIndex);
    }

    public Object[] toArray(){
        Object[] arr = new Object[this.size()];
        for(int i = 0; i < v.size(); i++){
            arr[i] = v.get(i);
        }
        return arr;
    }

    public Object[] toArray(Object[] a) throws NullPointerException{
        if(a == null) throw new NullPointerException("Array null");

        if(a.length >= this.size()){
            for(int i = 0; i < v.size(); i++){
                a[i] = v.elementAt(i);
            }
            return a;
        }
        return this.toArray();
    }

    public class LimitedListAdapter extends ListAdapter{
        int start;
        int end;
        int modCheck;

        public LimitedListAdapter(int fromIndex, int toIndex){

            modCheck = modCount;
            start = fromIndex;
            end = toIndex;
        }

        public void add(int index, Object Element) throws IndexOutOfBoundsException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if(index < 0 || index > this.size()) throw new IndexOutOfBoundsException();
            super.add(index + start, Element);
        }

        public boolean add(Object obj) throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            return super.add(obj);
        }

        public boolean addAll(HCollection c) throws NullPointerException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            return super.addAll(c);
        }

        public boolean addAll(int index, HCollection c) throws NullPointerException, IndexOutOfBoundsException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if(index < 0 || index > this.size()) throw new IndexOutOfBoundsException();
            return super.addAll(index, c);
        }

        public void clear() throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            for(int i = start; i < end; i++){
                v.removeElementAt(start);
            }
            modCount++;

        }

        public boolean contains(Object o) throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            for(int i = start; i < end; i++){
                if(v.elementAt(i).equals(o)){
                    return true;
                }
            }
            return false;
        }

        public boolean containsAll(HCollection c) throws NullPointerException, IllegalStateException{
            if(c == null) throw new NullPointerException("Collection null");
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            
            HIterator elems = c.iterator();

            while(elems.hasNext()){
                if(!this.contains(elems.next())){
                    return false;
                }
            }

            return false;
        }

        public boolean equals(Object o) throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if(!(o instanceof HList)) return false;
            if(this.size() !=  ((HList)o).size()) return false;
    
            HIterator itero = ((HList)o).iterator();
            
            int i = start;
            while(itero.hasNext()){
                if(!itero.next().equals(v.elementAt(i))) return false;
                i++;
            }
    
            return true;
        }

        public  Object get(int index) throws IndexOutOfBoundsException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if(index < 0 || index > this.size()) throw new IndexOutOfBoundsException();
            return super.get(index);
        }

        public int hashCode() throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            int hashCode = 1;
            for(int i = start; i < end; i++){

                Object obj = v.elementAt(i);
                hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
            }

            return hashCode;
        }

        public int indexOf(Object o) throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            int ind = v.indexOf(o, start);
            if(ind < end){
                return ind;
            }

            return -1;
        }
        
    }
    


}
