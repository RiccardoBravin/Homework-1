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
            modCount++;
        }catch (ArrayIndexOutOfBoundsException aioobe){
            System.out.println(aioobe.getMessage());
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean add(Object obj){
        v.addElement(obj);
        modCount++;
        return true;
    }

    public boolean addAll(HCollection c) throws NullPointerException{
        return addAll(v.size(), c);
    }

    public boolean addAll(int index, HCollection c) throws NullPointerException, IndexOutOfBoundsException{
        if(c == null) throw new NullPointerException("Collection null");
        if(index < 0 || index > size()) throw new IndexOutOfBoundsException("Indice negativo");
        
        HIterator elems = c.iterator();
        
        
        while(elems.hasNext()){
            add(index++, elems.next());
        }

        return !c.isEmpty();
    }

    public void clear(){
        if(!isEmpty()){
            modCount++;
            this.v.clear();
        }
        
    }

    public boolean contains(Object o){
        return this.v.contains(o);
    }

    public boolean containsAll(HCollection c) throws NullPointerException{
        if(c == null) throw new NullPointerException("Collection null");

        HIterator elems = c.iterator();

        while(elems.hasNext()){
            if(!this.contains(elems.next())){
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
        return new ListIteratorAdapter(); 
    }

    public int lastIndexOf(Object o){
        return v.lastIndexOf(o);
    }

    public HListIterator listIterator(){
        return new ListIteratorAdapter();
    }

    public HListIterator listIterator(int index) throws IndexOutOfBoundsException{
        if((index < 0 || index > v.size())) throw new IndexOutOfBoundsException();
        return new ListIteratorAdapter(index);
    }

    public Object remove(int index) throws IndexOutOfBoundsException{
        try{
            Object aux = v.elementAt(index);
            v.removeElementAt(index);
            modCount++;
            return aux;
        }catch(ArrayIndexOutOfBoundsException aioobe){
            System.out.println(aioobe.getMessage());
            throw new IndexOutOfBoundsException(aioobe.getMessage());
        }
    }

    public boolean remove(Object o){
        boolean aux = v.removeElement(o);
        if(aux){
            modCount++;
        }
        return aux;
    }

    public boolean removeAll(HCollection c) throws NullPointerException{
        if(c == null) throw new NullPointerException("Collection null");

        HIterator elems = c.iterator();
        boolean changed = false;
        
        while(elems.hasNext()){
            if(this.remove(elems.next())){
                changed = true;
                modCount++;
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
        if(fromIndex < toIndex || fromIndex < 0 || toIndex > v.size()) throw new IndexOutOfBoundsException();
        
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
            if(index < 0 || index > size()) throw new IndexOutOfBoundsException();
            super.add(index + start, Element);
        }

        public boolean add(Object obj) throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            return super.add(obj);
        }

        public boolean addAll(HCollection c) throws NullPointerException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            return super.addAll(end, c);
        }

        public boolean addAll(int index, HCollection c) throws NullPointerException, IndexOutOfBoundsException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if(index < 0 || index > size()) throw new IndexOutOfBoundsException();
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
            if(index < 0 || index > size()) throw new IndexOutOfBoundsException();
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

        public boolean isEmpty() throws IllegalStateException{
                if(modCheck != modCount) throw new IllegalStateException("State modified");
                return start == end;
        }

        public HIterator iterator() throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            return new ListIteratorAdapter(start, end, 0); 
        }

        public int lastIndexOf(Object o) throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            int ind = v.lastIndexOf(o, end);
            if(ind > start){
                return ind;
            }

            return -1;
        }

        public HListIterator listIterator() throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            return new ListIteratorAdapter(start, end, 0); 
        }
        
        public HListIterator listIterator(int index) throws IndexOutOfBoundsException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if((index < 0 || index > size())) throw new IndexOutOfBoundsException();
            return new ListIteratorAdapter(start, end, index);
        }
        

        public Object remove(int index) throws IndexOutOfBoundsException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if((index < 0 || index > size())) throw new IndexOutOfBoundsException();
            return super.remove(index + start);

        }

        public boolean remove(Object o) throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");

            for(int i = start; i < end; i++){
                if(v.elementAt(i).equals(o)){
                    super.remove(i);
                    return true;
                }    
            }
            return false;
        }
        
        public boolean removeAll(HCollection c) throws NullPointerException, IllegalStateException{ //da verificare
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            return super.removeAll(c); 
        }

        public boolean retainAll(HCollection c) throws NullPointerException, IllegalStateException{ //da verificare
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            return super.retainAll(c);
        }


        public Object set(int index, Object element){
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if((index < 0 || index > size())) throw new IndexOutOfBoundsException();
            return super.set(index, element);
        }

        public int size() throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            return end-start;
        }

        public HList subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if((fromIndex < 0 || toIndex > size() || fromIndex > toIndex)) throw new IndexOutOfBoundsException();
            return new LimitedListAdapter(fromIndex + start, toIndex + start);

        }

        public Object[] toArray() throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            Object[] arr = new Object[size()];
            for(int i = start; i < end; i++){
                arr[i - start] = v.get(i);
            }
            return arr;
        }

        public Object[] toArray(Object[] a) throws NullPointerException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if(a == null) throw new NullPointerException("Array null");
    
            if(a.length >= size()){
                for(int i = start; i < end; i++){
                    a[i - start] = v.elementAt(i);
                }
                return a;
            }
            return toArray();
        }

    }
    
    
    public class ListIteratorAdapter implements HListIterator{
        private int index;
        private int start;
        private int end;
        private int lastIndex = -1;
    
        public ListIteratorAdapter(){
            start = 0;
            end = v.size();
            this.index = 0;
        }
        public ListIteratorAdapter(int index) throws IndexOutOfBoundsException{
            if(index > v.size()) throw new IndexOutOfBoundsException();
            start = 0;
            end = v.size();
            this.index = index;
        }

        public ListIteratorAdapter(int fromIndex, int toIndex, int index) throws IndexOutOfBoundsException{
            if(index > toIndex - fromIndex) throw new IndexOutOfBoundsException();
            start = fromIndex;
            end = toIndex;
            this.index = index + fromIndex;
        }
    
        public void add(Object o){
            v.insertElementAt(o, index);
            index++;
            end++;
            modCount++;
        }
    
        public boolean hasNext(){
            return (index < end); 
        }
    
        public boolean hasPrevious(){
            return (index > start);
        }
    
        public Object next() throws NoSuchElementException{
            if(!this.hasNext()) throw new NoSuchElementException();
            lastIndex = index;
            return v.elementAt(index++);
        }
    
        public int nextIndex(){
            return index; //the index of the element that would be returned by a subsequent call to next, or list size if list iterator is at end of list.
        }
    
        public Object previous() throws NoSuchElementException{
            if(!this.hasPrevious()) throw new NoSuchElementException();
            index--;
            lastIndex = index;
            return v.elementAt(index);
        }
    
        public int previousIndex(){
            return index - 1;  //the index of the element that would be returned by a subsequent call to previous, or -1 if list iterator is at beginning of list.
        }
    
        public void remove() throws IllegalStateException{
            if(lastIndex == -1) throw new IllegalStateException();
            v.removeElementAt(lastIndex);
            modCount++;
            end--;
            lastIndex = -1;
            index--;
        }
    
        public void set(Object o) throws IllegalStateException{
            if(lastIndex == -1) throw new IllegalStateException();
            v.setElementAt(o, lastIndex);
            lastIndex = -1;
        }
    }

    public String toString(){
        String s = "[";
        if(!isEmpty()){
            s += v.elementAt(0);
            for(int i = 1; i < v.size(); i++){
                s += ", " + v.elementAt(i).toString();
            }
        }
        return s + "]";
    }
}

