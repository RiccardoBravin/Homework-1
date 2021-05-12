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
        this.v.addElement(obj);
        this.modCount++;
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
        return new ListIteratorAdapter(this); 
    }

    public int lastIndexOf(Object o){
        return v.lastIndexOf(o);
    }

    public HListIterator listIterator() throws IndexOutOfBoundsException{
        return new ListIteratorAdapter(this);
    }

    public HListIterator listIterator(int index) throws IndexOutOfBoundsException{
        if((index < 0 || index >= v.size())) throw new IndexOutOfBoundsException();
        return new ListIteratorAdapter(this, index);
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
        if(v.removeElement(o)){
            modCount++;
            return true;
        }
        return false;
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
        if(fromIndex > toIndex || fromIndex < 0 || toIndex > v.size() || fromIndex-toIndex == 0) throw new IndexOutOfBoundsException();
        
        return new LimitedListAdapter(this, fromIndex, toIndex);
    }

    public Object[] toArray(){
        Object[] arr = new Object[this.size()];
        for(int i = 0; i < v.size(); i++){
            arr[i] = v.get(i);
        }
        return arr;
    }

    public Object[] toArray(Object[] a){
        if(a == null) return this.toArray();;

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
        ListAdapter l;

        public LimitedListAdapter(ListAdapter l, int fromIndex, int toIndex) throws IndexOutOfBoundsException, NullPointerException{
            if(l == null) throw new NullPointerException();
            if(fromIndex >= toIndex || fromIndex < 0 || toIndex > l.size()) throw new IndexOutOfBoundsException();
            this.l = l;
            modCheck = modCount;
            start = fromIndex;
            end = toIndex;
        }

        public void add(int index, Object Element) throws IndexOutOfBoundsException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if(index < 0 || index > size()) throw new IndexOutOfBoundsException();
            l.add(index + start, Element);
        }

        public boolean add(Object obj) throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            l.add(end, obj);
            return true;
        }

        public boolean addAll(HCollection c) throws NullPointerException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            return l.addAll(end, c);
        }

        public boolean addAll(int index, HCollection c) throws NullPointerException, IndexOutOfBoundsException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if(index < 0 || index > size()) throw new IndexOutOfBoundsException();
            return l.addAll(start + index, c);
        }

        public void clear() throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            for(int i = start; i < end; i++){
                l.remove(start);
            }
        }

        public boolean contains(Object o) throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            for(int i = 0; i < size(); i++){
                if(get(i).equals(o)){
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

            return true;
        }

        public boolean equals(Object o) throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if(!(o instanceof HList)) return false;
            if(this.size() !=  ((HList)o).size()) return false;
    
            HIterator itero = ((HList)o).iterator();
            
            int i = 0;
            while(itero.hasNext()){
                if(!itero.next().equals(l.get(i))) return false;
                i++;
            }
    
            return true;
        }

        public  Object get(int index) throws IndexOutOfBoundsException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if(index < 0 || index >= this.size()) throw new IndexOutOfBoundsException();
            return l.get(index+start);
        }

        public int hashCode() throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            int hashCode = 1;
            for(int i = 0; i < size(); i++){

                Object obj = get(i);
                hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
            }

            return hashCode;
        }

        public int indexOf(Object o) throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            for(int i = 0; i < size(); i++){
                if(get(i).equals(o)) return i;
            }

            return -1;
        }

        public boolean isEmpty() throws IllegalStateException{
                if(modCheck != modCount) throw new IllegalStateException("State modified");
                return start == end;
        }

        public HIterator iterator() throws IllegalStateException{
            return listIterator();
        }

        public int lastIndexOf(Object o) throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            for(int i = size()-1; i >= 0; i--){
                if(get(i).equals(o)) return i;
            }

            return -1;
        }

        public HListIterator listIterator() throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            return new ListIteratorAdapter(l, start, end, 0); 
        }
        
        public HListIterator listIterator(int index) throws IndexOutOfBoundsException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if((index < 0 || index > size())) throw new IndexOutOfBoundsException();
            return new ListIteratorAdapter(l, start, end, index);
        }
        

        public Object remove(int index) throws IndexOutOfBoundsException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if((index < 0 || index >= size())) throw new IndexOutOfBoundsException();
            return l.remove(index + start);

        }

        public boolean remove(Object o) throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");

            for(int i = 0; i < this.size(); i++){
                if(get(i).equals(o)){
                    remove(i);
                    return true;
                }    
            }
            return false;
        }
        
        public boolean removeAll(HCollection c) throws NullPointerException, IllegalStateException{ //da verificare
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if(c == null) throw new NullPointerException("Collection null");

            HIterator elems = c.iterator();
            boolean changed = false;
            
            while(elems.hasNext()){

                Object aux = elems.next();
                for(int i = start; i < end; i++){
                    if(l.get(i).equals(aux)){
                        l.remove(i);
                        changed = true;
                        i--;
                        end--;
                    }    
                }
            }

            return changed; 
        }

        public boolean retainAll(HCollection c) throws NullPointerException, IllegalStateException{ //da verificare
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if(c == null) throw new NullPointerException("Collection null");
            
            boolean changed = false;
            
            for(int i = start; i < end; i++){
                if(!c.contains(l.get(i))){
                    l.remove(i);
                    changed = true;
                    i--;
                    end--;
                }    
            }

            return changed;
        }


        public Object set(int index, Object element){
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if((index < 0 || index >= size())) throw new IndexOutOfBoundsException();
            return l.set(index + start, element);
        }

        public int size() throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            return end-start;
        }

        public HList subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if((fromIndex < 0 || toIndex > size() || fromIndex >= toIndex   )) throw new IndexOutOfBoundsException();
            return new LimitedListAdapter(l, fromIndex + start, toIndex + start);

        }

        public Object[] toArray() throws IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            Object[] arr = new Object[size()];
            for(int i = 0; i < size(); i++){
                arr[i] = this.get(i);
            }
            return arr;
        }

        public Object[] toArray(Object[] a) throws NullPointerException, IllegalStateException{
            if(modCheck != modCount) throw new IllegalStateException("State modified");
            if(a == null) return this.toArray();
    
            if(a.length >= size()){
                for(int i = 0; i < size(); i++){
                    a[i] = this.get(i);
                }
                return a;
            }
            return toArray();
        }

        public String toString(){
            String s = "[";
            if(!isEmpty()){
                s += get(0);
                for(int i = 1; i < size(); i++){
                    s += ", " + get(i).toString();
                }
            }
            return s + "]";
        }

    }
    
    
    public class ListIteratorAdapter implements HListIterator{
        private int index;
        private int start;
        private int end;
        private int lastIndex = -1;
        private ListAdapter l;
    
        public ListIteratorAdapter(ListAdapter l) throws IllegalArgumentException{
            if(l == null) throw new IllegalArgumentException();
            this.l = l;
            start = 0;
            end = v.size();
            this.index = 0;
        }
        public ListIteratorAdapter(ListAdapter l, int index) throws IndexOutOfBoundsException{
            if(l == null) throw new IllegalArgumentException();
            this.l = l;
            if(index < 0 || index > l.size()) throw new IndexOutOfBoundsException();
            start = 0;
            end = l.size();
            this.index = index;
        }

        public ListIteratorAdapter(ListAdapter l, int fromIndex, int toIndex, int index) throws IndexOutOfBoundsException{
            if(l == null) throw new IllegalArgumentException();
            this.l = l;
            if(toIndex <= fromIndex || fromIndex < 0 || toIndex > l.size() || index > toIndex - fromIndex || index < 0 ) throw new IndexOutOfBoundsException();
            start = fromIndex;
            end = toIndex;
            this.index = index + fromIndex;
        }
    
        public void add(Object o){
            l.add(index, o);
            lastIndex = -1;
            index++;
            end++;
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
            return l.get(index++);
        }
    
        public int nextIndex(){
            return index - start; //the index of the element that would be returned by a subsequent call to next, or list size if list iterator is at end of list.
        }
    
        public Object previous() throws NoSuchElementException{
            if(!this.hasPrevious()) throw new NoSuchElementException();
            index--;
            lastIndex = index;
            return l.get(index);
        }
    
        public int previousIndex(){
            return index - 1 - start;  //the index of the element that would be returned by a subsequent call to previous, or -1 if list iterator is at beginning of list.
        }
    
        public void remove() throws IllegalStateException{
            if(lastIndex == -1) throw new IllegalStateException();
            l.remove(lastIndex);
            lastIndex = -1;
            end--;
        }
    
        public void set(Object o) throws IllegalStateException{
            if(lastIndex == -1) throw new IllegalStateException();
            l.set(lastIndex, o);
        }
    }

    public String toString(){
        return v.toString();
    }

}

