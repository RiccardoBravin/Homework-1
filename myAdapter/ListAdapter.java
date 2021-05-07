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
            modCount++;
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
            if(!this.contains(iter.next())) 
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
        HIterator iter = this.iterator();
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

   
    public int hashCode() {
        int hashCode = 1;
        HIterator iter = this.iterator();

        while(iter.hasNext()){
            Object obj = iter.next();
            int increment = 0;
            if(obj != null)
                increment = obj.hashCode();
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
        return new ListIteratorAdapter();
    }

   
    public HListIterator listIterator(int index) throws IndexOutOfBoundsException {
        if(index > vec.size()) throw new IndexOutOfBoundsException();
        return new ListIteratorAdapter(index);
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
        if(c==null) throw new NullPointerException();

        int oldSize = size();
        HIterator iter = c.iterator();
        while(iter.hasNext()){
            vec.remove(iter.next());
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
        return new RangedListAdapter(fromIndex, toIndex);        
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
            Object[] toReturn = new Object[vec.size()];
            for(int i = 0; i<vec.size(); i++) {
                a[i] = vec.elementAt(i);
            }
            return toReturn;
        }
    }


    private class ListIteratorAdapter implements HListIterator {

        private ListIteratorAdapter(){     //forse protected
            fromIndex = 0;
            toIndex = vec.size();
            cursor = 0;
            indexOfLastObjectReturned = -1;
            removeUse = false;
            addUse = false;
            lastCallIsNext = false;
        }

        private ListIteratorAdapter(int fromIndex) {     //forse protected
            this.fromIndex = fromIndex;
            toIndex = vec.size();
            cursor = fromIndex;
            indexOfLastObjectReturned = -1;
            removeUse = false;
            addUse = false;
            lastCallIsNext = false;
        }

        private ListIteratorAdapter(int fromIndex, int toIndex) {     //forse protected
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            toIndex = vec.size();
            cursor = fromIndex;
            indexOfLastObjectReturned = -1;
            removeUse = false;
            addUse = false;
            lastCallIsNext = false;
        }

        public void add(Object o) {
            vec.insertElementAt(o, cursor);
            toIndex++;
            addUse = true;
            modCount++;
        }

        public boolean hasNext(){
            return (cursor <= toIndex);
        }

        public boolean hasPrevious(){
            return (cursor > fromIndex);
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

        public void remove() throws  IllegalStateException {                                                //ci sono modifiche da fare per la gestione di fromIndex e toIndex??
            if(removeUse || addUse || indexOfLastObjectReturned==-1) throw new IllegalStateException();
            vec.remove(indexOfLastObjectReturned);
            if(lastCallIsNext) cursor--;
            //se arrivo da next il cursore fa --, se arrivo da previous il cursore sta fermo
            removeUse = true;
            modCount++;
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
        private int fromIndex, toIndex;

    }

    
    private Vector vec;
    private int  modCount;

    public class RangedListAdapter extends ListAdapter {
        
        private int fromIndex, toIndex, modCount, size;

        public RangedListAdapter(int fromIndex, int toIndex){
            modCount = super.modCount;
            size = toIndex - fromIndex;
        }

        public void add(int index, Object Element) throws IndexOutOfBoundsException {
            if(modCount != super.modCount) throw new IllegalStateException();
            if(index < 0 || index >= size ) throw new IndexOutOfBoundsException();
            super.add(index + fromIndex, Element);
        }
    
       
        public boolean add(Object obj) {
            if(modCount != super.modCount) throw new IllegalStateException();
            super.modCount++;
            return super.add(obj);
        }
    
       
        public boolean addAll(HCollection c) throws NullPointerException {                  //ho modificato modCount giusto? 
            if(modCount != super.modCount) throw new IllegalStateException();
            return super.addAll(c);
        }
    
       
        public boolean addAll(int index, HCollection c) throws IndexOutOfBoundsException, NullPointerException {
            if(modCount != super.modCount) throw new IllegalStateException();
            return super.addAll(index + fromIndex, c)
        }
    
       
        public void clear() {
            if(modCount != super.modCount) throw new IllegalStateException();
            for(int i=0; i<size; i++){
                vec.removeElementAt(fromIndex);
            }
            super.modCount++;
        }
    
       
        public boolean contains(Object o) {
            if(modCount != super.modCount) throw new IllegalStateException();
            for(int i=fromIndex; i<toIndex; i++){
                if(vec.elementAt(i).equals(o))
                    return true;
            }
            return false;
        }
    
       
        public boolean containsAll(HCollection c) throws NullPointerException {
            if(modCount != super.modCount) throw new IllegalStateException();
            return super.containsAll(c);
        }
    
    
        public boolean equals(Object o) {
            if(modCount != super.modCount) throw new IllegalStateException();
            return super.equals(o);
        }
    
       
        public Object get(int index) {
            if(modCount != super.modCount) throw new IllegalStateException();
            return super.get(index + fromIndex);
        }
    
       
        public int hashCode() {
            if(modCount != super.modCount) throw new IllegalStateException();
            return super.hashCode();
        }
    
       
        public int indexOf(Object o) {
            for(int i=fromIndex; i<toIndex; i++){
                if(vec.elementAt(i).equals(0))
                    return i;
            }
            return -1;
        }
    
       
        public boolean isEmpty() {              //SI????
            return size == 0;
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
            if(index > vec.size()) throw new IndexOutOfBoundsException();
            return new ListIteratorAdapter(index);
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
            if(c==null) throw new NullPointerException();
    
            int oldSize = size();
            HIterator iter = c.iterator();
            while(iter.hasNext()){
                vec.remove(iter.next());
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
                return removed;
            } catch (ArrayIndexOutOfBoundsException e ) {
                throw new IndexOutOfBoundsException();
            }
            
        }
    
       
        public int size() {
            return vec.size();
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
                Object[] toReturn = new Object[vec.size()];
                for(int i = 0; i<vec.size(); i++) {
                    a[i] = vec.elementAt(i);
                }
                return toReturn;
            }
        }
    }
}
