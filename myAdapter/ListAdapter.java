//package myAdapter;

import java.util.NoSuchElementException;
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
        HIterator iter = c.iterator(); 

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

        while(iter.hasNext()){
            add(index + i, iter.next());
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
        if(!(o instanceof HList)) return false;
        HList casted = (HList)o;
        if(casted.size() != vec.size()) return false;

        HIterator OIter = casted.iterator();
        HIterator iter = iterator();
        while(iter.hasNext()){
            if(!iter.next().equals(OIter.next())) return false;
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
        return (HIterator)listIterator();
    }

   
    public int lastIndexOf(Object o) {
        return vec.lastIndexOf(o);
    }

   
    public HListIterator listIterator() {
        return new ListIteratorAdapter();
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
        Object[] array = new Object[vec.size()];
        for(int i=0; i<vec.size(); i++){
            array[i] = vec.elementAt(i);
        }
        return array;
    }

   
    public Object[] toArray(Object[] a) throws ArrayStoreException, NullPointerException {
        // TODO Auto-generated method stub
        return null;
    }


    public class ListIteratorAdapter implements HListIterator {

        private ListIteratorAdapter(){     //forse protected
            cursor = 0;
            indexOfLastObjectReturned = -1;
            removeUse = false;
            addUse = false;
            lastCallIsNext = false;
        }

        public void add(Object o) {
            vec.add(cursor, o);
            addUse = true;
        }

        public boolean hasNext(){
            return (cursor <= vec.size());
        }

        public boolean hasPrevious(){
            return (cursor > 0);
        }

        public Object next() throws NoSuchElementException{
            if(!hasNext()) throw new NoSuchElementException();
            indexOfLastObjectReturned = cursor;
            removeUse = false;
            addUse = false;
            lastCallIsNext = true;
            return vec.elementAt(cursor++);
        }

        public int nextIndex(){
            return cursor;
        }

        public Object previous() throws NoSuchElementException{
            if(!hasPrevious()) throw new NoSuchElementException();
            indexOfLastObjectReturned = --cursor;
            removeUse = false;
            addUse = false;
            lastCallIsNext = false;
            return vec.elementAt(cursor);
        }

        public int previousIndex(){
            return cursor - 1;
        }

        public void remove() throws  IllegalStateException {
            if(removeUse || addUse || indexOfLastObjectReturned==-1) throw new IllegalStateException();
            vec.remove(indexOfLastObjectReturned);
            if(lastCallIsNext) cursor--;
            //se arrivo da next il cursore fa --, se arrivo da previous il cursore sta fermo
            removeUse = true;
            
        }

        public void set(Object o) throws IllegalStateException {
            if(removeUse || addUse) throw new IllegalStateException();
            vec.setElementAt(o, indexOfLastObjectReturned);
        }

        private int cursor;
        private int indexOfLastObjectReturned;
        private boolean removeUse;
        private boolean addUse;
        private boolean lastCallIsNext;

    }

    
    private Vector vec;
}
