package myAdapter;

import java.util.Vector;

public class ListAdapter implements HList {

    public ListAdapter(){
        vec = new Vector();
    }
    
   
    public void add(int index, Object Element) throws IndexOutOfBoundsException {
        try {
            vec.insertElementAt(Element, index);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

   
    public boolean add(Object obj) {
        vec.addElement(obj);
        return true;
    }

   
    public boolean addAll(HCollection c) throws NullPointerException {
        if(c == null) throw new NullPointerException();

        int oldSize = size();
        Hiterator iter = c.iterator(); 

        while(iter.hasNext()){
            add(iter.next());
        }

        if(oldSize == vec.size()) return false; else return true;
    }

   
    public boolean addAll(int index, HCollection c) throws IndexOutOfBoundsException, NullPointerException {
        if(c == null) throw new NullPointerException();

        int oldSize = size();
        HIterator iter = c.iterator();
        int i = 0;

        while(iter.hasNext){
            add(index + i, c.next());
            i++;
        }

        if(oldSize == vec.size()) return false; else return true;
    }

   
    public void clear() {
        vec.removeAllElements();
    }

   
    public boolean contains(Object o) {
        return vec.contains(o);
    }

   
    public boolean containsAll(HCollection c) throws NullPointerException {
        if(c == null) throw new  NullPointerException();

        HIterator iter = c.iterator();

        while(iter.hasNext()){
            if(!contains(iter.next())) 
                return false;
        }

        return true;
    }


    public boolean equals(Object o) {
        if(o == null) return false;
        if(!o.instanceOf(HList)) return false;
        if(o.size() != size()) return false;

        HIterator OIter = o.iterator();
        HIterator iter = iterator();
        while(iter.hasNext()){
            if(!iter.next.equals(OIter.next())) return false;
        }

        return true;
    }

   
    public Object get(int index) {
        try{
            return vec.elementAt(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

   
    public int hasCode() {
        int hasCode = 1;
        HIterator iter = iterator();

        while(iter.hasNext()){
            Object obj = iter.next();
            int increment = 0;
            if(obj != null)
                increment = obj.hashCode();
            hasCode = 31*hasCode + increment;
        }

        return hasCode;
    }

   
    public int indexOf(Object o) {
        return vec.indexOf(o);
    }

   
    public boolean isEmpty() {
        return vec.isEmpty();
    }

   
    public HIterator iterator() {
        // TODO Auto-generated method stub
        return null;
    }

   
    public int lastIndexOf(Object o) {
        return vec.lastIndexOf(o);
    }

   
    public HListIterator listIterator() {
        // TODO Auto-generated method stub
        return null;
    }

   
    public HListIterator listIterator(int index) throws IndexOutOfBoundsException {
        // TODO Auto-generated method stub
        return null;
    }

   
    public Object remove(int index) {
            try{
                Object removed = vec.elementAt(index);
                vec.removeElementAt(index);
                return removed;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException();
            }
    }

   
    public boolean remove(Object o) {
        return vec.removeElement(o);
    }

   
    public boolean removeAll(HCollection c) throws NullPointerException {
        if(c==null) throw new NullPointerException();

        int oldSize = size();
        HIterator iter = c.iterator();
        while(iter.hasNext()){
            vec.remove(iter.next());
        }

        if(oldSize == vec.size()) return false; else return true;
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
        if(oldSize == vec.size()) return false; else return true;
    }

   
    public Object set(int index, Object element) throws IndexOutOfBoundsException {
        try{
            Object removed = vec.elementAt(index);
            vec.setElementAt(element, index);
            return removed;
        } catch (ArrayIndexOutOfBoundsException e ) {
            throw new IndexOutOfBoundsException();
        }
        
    }

   
    public int size() {
        return vec.size();
    }

   
    public HList subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
        // TODO Auto-generated method stub
        return null;
    }

   
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

   
    public Object[] toArray(Object[] a) throws ArrayStoreException, NullPointerException {
        // TODO Auto-generated method stub
        return null;
    }

    private Vector vec;
}
