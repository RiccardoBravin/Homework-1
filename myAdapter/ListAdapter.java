//package myAdapter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;


public class ListAdapter implements HList {

    public ListAdapter(){
        vec = new Vector();
        modCount = 0;
    }

   
    public void add(int index, Object Element) throws IndexOutOfBoundsException {
        try {
            vec.insertElementAt(Element, index);
            modCount++;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

   
    public boolean add(Object obj) {
        vec.addElement(obj);
        modCount++;
        return true;
    }

   
    public boolean addAll(HCollection c) throws NullPointerException {
        if(c == null) throw new NullPointerException();

        int oldSize = size();
        HIterator iter = c.iterator(); 

        while(iter.hasNext()){
            add(iter.next());
        }

        if(oldSize == vec.size()) {
            return false; 
        } else {
            modCount++;
            return true;
        }
    }

   
    public boolean addAll(int index, HCollection c) throws IndexOutOfBoundsException, NullPointerException {
        if(c == null) throw new NullPointerException();
        if(index < 0 || index > vec.size()) throw new IndexOutOfBoundsException();

        int oldSize = size();
        HIterator iter = c.iterator();
        int i = 0;

        while(iter.hasNext()){
            add(index + i, iter.next());
            i++;
        }

        if(oldSize == vec.size()) {
            return false; 
        } else {
            return true;
        }
    }

   
    public void clear() {
        vec.removeAllElements();
        modCount++;
    }

   
    public boolean contains(Object o) {
        return vec.contains(o);
    }

   
    public boolean containsAll(HCollection c) throws NullPointerException {
        if(c == null) throw new  NullPointerException();

        HIterator iter = c.iterator();

        while(iter.hasNext()){
            if(!this.contains(iter.next())) {
                return false;
            }
        }

        return true;
    }


    public boolean equals(Object o) {
        if(o == null) return false;
        if(!(o instanceof HList)) return false;
        HList casted = (HList)o;
        if(casted.size() != vec.size()) return false;

        HIterator OIter = casted.iterator();
        HIterator iter = this.iterator();
        while(iter.hasNext()){
            Object iterElement = iter.next();
            Object OiterElement = OIter.next();
            if(iterElement != null && OIter != null){
                if(!iterElement.equals(OiterElement)) 
                    return false;
            } else if( (iterElement == null && OiterElement != null) || (iterElement != null && OiterElement == null)) {
                return false;
            }
        }

        return true;
    }

   
    public Object get(int index) throws IndexOutOfBoundsException {
        try{
            return vec.elementAt(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

   
    public int hashCode() {
        int hashCode = 1;
        HIterator iter = this.iterator();

        while(iter.hasNext()){
            Object obj = iter.next();
            int increment = 0;
            if(obj != null)
                increment = obj.hashCode();
            else increment = 0;
            hashCode = 31*hashCode + increment;
        }

        return hashCode;
    }

   
    public int indexOf(Object o) {
        return vec.indexOf(o);
    }

   
    public boolean isEmpty() {
        return vec.isEmpty();
    }

   
    public HIterator iterator() {
        return (HIterator)listIterator();
    }

   
    public int lastIndexOf(Object o) {
        return vec.lastIndexOf(o);
    }

   
    public HListIterator listIterator() {
        return new ListIteratorAdapter(this);
    }

   
    public HListIterator listIterator(int index) throws IndexOutOfBoundsException {
        if(index >= vec.size() || index < 0) throw new IndexOutOfBoundsException();
        return new ListIteratorAdapter(this, index);
    }

   
    public Object remove(int index) throws IndexOutOfBoundsException {
            try{
                Object removed = vec.elementAt(index);
                vec.removeElementAt(index);
                modCount++;
                return removed;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException();
            }
    }

   
    public boolean remove(Object o) {
        if(vec.remove(o)){
            modCount++;
            return true;
        }
        return false;
    }

   
    public boolean removeAll(HCollection c) throws NullPointerException {
        if(c == null) throw new NullPointerException();
        
        int oldSize = vec.size();
        for(int i=0; i<vec.size(); i++){
            if(c.contains(vec.elementAt(i))){
                vec.removeElementAt(i);
                i--;
            }
        }

        if(oldSize == vec.size()) {  
            return false;
        } else {
            modCount++;
            return true;
        }
    }

   
    public boolean retainAll(HCollection c) throws NullPointerException {
        if(c == null) throw new NullPointerException();
        
        int oldSize = vec.size();
        for(int i=0; i<vec.size(); i++){
            if(!c.contains(vec.elementAt(i))){
                vec.removeElementAt(i);
                i--;
            }
        }

        if(oldSize == vec.size()) {  
            return false;
        } else {
            modCount++;
            return true;
        }
    }

   
    public Object set(int index, Object element) throws IndexOutOfBoundsException {
        try{
            Object removed = vec.elementAt(index);
            vec.setElementAt(element, index);
            modCount++;
            return removed;
        } catch (ArrayIndexOutOfBoundsException e ) {
            throw new IndexOutOfBoundsException();
        }
        
    }

   
    public int size() {
        return vec.size();
    }

   
    public HList subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
        if(fromIndex < 0 || toIndex > vec.size() || fromIndex > toIndex) throw new IndexOutOfBoundsException();
        return new RangedListAdapter(this, fromIndex, toIndex);        
    }

   
    public Object[] toArray() {
        Object[] array = new Object[vec.size()];
        for(int i=0; i<vec.size(); i++){
            array[i] = vec.elementAt(i);
        }
        return array;
    }

   
    public Object[] toArray(Object[] a) throws ArrayStoreException, NullPointerException {
        if(a == null) throw new NullPointerException();
        if(a.length >= vec.size()){
            for(int i = 0; i<vec.size(); i++) {
                a[i] = vec.elementAt(i);
            }
            return a;
        } else {
            return toArray();
        }
    }

    public String toString(){
        return vec.toString();
    }


    
    private Vector vec;
    protected int  modCount;

}
