package myAdapter;

public class RangedListAdapter extends ListAdapter {
        
    private int fromIndex, toIndex, modCount, size;
    private ListAdapter list;

    public RangedListAdapter(ListAdapter LA, int fromIndex, int toIndex){

        list = LA;
        modCount = list.modCount;
        size = toIndex - fromIndex;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    public void add(int index, Object Element) throws IndexOutOfBoundsException, IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        if(index < 0 || index >= size ) throw new IndexOutOfBoundsException();
        list.add(index + fromIndex, Element);
    }

   
    public boolean add(Object obj) throws IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        list.add(toIndex, obj);
        return true; 
    }

   
    public boolean addAll(HCollection c) throws NullPointerException, IllegalStateException {                  //ho modificato modCount giusto? 
        if(modCount != list.modCount) throw new IllegalStateException();
        return list.addAll(toIndex, c);
    }

   
    public boolean addAll(int index, HCollection c) throws IndexOutOfBoundsException, NullPointerException, IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        if(index < 0 || index >= size ) throw new IndexOutOfBoundsException();
        return list.addAll(index + fromIndex, c);
    }

   
    public void clear() throws IllegalStateException{
        if(modCount != list.modCount) throw new IllegalStateException();
        for(int i=fromIndex; i<toIndex; i++){
            list.remove(fromIndex);
        }
    }

   
    public boolean contains(Object o) throws IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        for(int i=fromIndex; i<toIndex; i++){
            if(o==null && list.get(i)== null)
                return true;
            if(list.get(i)== null)
                return o == null;
            if(list.get(i).equals(o))
                return true;
        }
        return false;
    }

   
    public boolean containsAll(HCollection c) throws NullPointerException, IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        if(fromIndex == toIndex) return false;
        for(int i=fromIndex; i<toIndex; i++) {
            if(!c.contains(list.get(i)))
                return false;
        }
        return true;
    }


    public boolean equals(Object o) throws IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        if(o == null) return false;
        if(!(o instanceof HList)) return false;
        HList casted = (HList)o;
        if(casted.size() != size()) return false;

        HIterator OIter = casted.iterator();
        for(int i=fromIndex; i<toIndex; i++) {
            Object iterElement = list.get(i);
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

   
    public Object get(int index) throws IllegalStateException, IndexOutOfBoundsException {
        if(modCount != list.modCount) throw new IllegalStateException();
        if(index < 0 || index >= size ) throw new IndexOutOfBoundsException();
        return list.get(index + fromIndex);
    }

   
    public int hashCode() throws IllegalStateException{
        if(modCount != list.modCount) throw new IllegalStateException();
        ListAdapter temp = new ListAdapter();
        for(int i=fromIndex; i<toIndex; i++){
            temp.add(list.get(i));
        }
        return temp.hashCode();
    }

   
    public int indexOf(Object o) throws IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        for(int i=fromIndex; i<toIndex; i++){
            if(o == null && list.get(i)==null) return i - fromIndex;
            if(o!=null && list.get(i)!=null && list.get(i).equals(o))
                return i - fromIndex;
        }
        return -1;
    }

   
    public boolean isEmpty() throws IllegalStateException {              //SI????
        if(modCount != list.modCount) throw new IllegalStateException();
        return size == 0;
    }

   
    public HIterator iterator() throws IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        return (HIterator)listIterator();
    }

   
    public int lastIndexOf(Object o) throws IllegalStateException{
        if(modCount != list.modCount) throw new IllegalStateException();
        int toReturn = -1;
        for(int i=fromIndex; i<toIndex; i++) {
            if(o==null && list.get(i)==null) toReturn = i-fromIndex;
            if(o!=null && list.get(i)!=null && list.get(i).equals(o))
                toReturn =  i - fromIndex;
        }
        return toReturn;
    }

   
    public HListIterator listIterator() throws IllegalStateException{
        if(modCount != list.modCount) throw new IllegalStateException();
        return new ListIteratorAdapter(list, fromIndex, toIndex);
    }

   
    public HListIterator listIterator(int index) throws IndexOutOfBoundsException, IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        if(index < 0 || index >= size ) throw new IndexOutOfBoundsException();
        return new ListIteratorAdapter(list, fromIndex, toIndex, index+fromIndex);
    }

   
    public Object remove(int index) throws IndexOutOfBoundsException, IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        if(index < 0 || index >= size ) throw new IndexOutOfBoundsException();
            return list.remove(index+fromIndex);
    }

   
    public boolean remove(Object o)  throws IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        for(int i=fromIndex; i<toIndex; i++) {
            if((list.get(i)==null && o==null) || (list.get(i)!=null &&list.get(i).equals(o))) {
                list.remove(i);
                return true;
            }
        }
        return false;
    }

   
    public boolean removeAll(HCollection c) throws NullPointerException, IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        if(c==null) throw new NullPointerException();

        boolean changed = false;
        for(int i=fromIndex; i<toIndex; i++){
            if(c.contains(list.get(i))){
                list.remove(i);
                i--;
                toIndex--;
                changed = true;
            }
        }

        return changed;
        
    }

   
    public boolean retainAll(HCollection c) throws NullPointerException, IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        if(c == null) throw new NullPointerException();
        
        boolean changed = false;
        for(int i=fromIndex; i<toIndex; i++){
            if(!c.contains(list.get(i))){
                list.remove(i);
                i--;
                toIndex--;
                changed = true;
            }
        }
        
        return changed;
    }

   
    public Object set(int index, Object element) throws IndexOutOfBoundsException, IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        if(index < 0 || index >= size ) throw new IndexOutOfBoundsException();

        return list.set(index+fromIndex, element);
        
    }

   
    public int size() throws IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        return size;
    }

    public HList subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException, IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        if(fromIndex < 0 || toIndex > size || fromIndex > toIndex) throw new IndexOutOfBoundsException();
        return new RangedListAdapter(list, fromIndex + this.fromIndex, toIndex + this.fromIndex);        
    }
    
    public Object[] toArray() throws IllegalStateException {
        if(modCount != list.modCount) throw new IllegalStateException();
        Object[] array = new Object[size];
        for(int i=0; i<size; i++){
            array[i] = list.get(i+fromIndex);
        }
        return array;
    }

   
    public Object[] toArray(Object[] a) throws ArrayStoreException, NullPointerException, IllegalStateException {
        if(a == null) throw new NullPointerException();
        if(a.length >= size){
            for(int i = 0; i<size; i++) {
                a[i] = list.get(i+fromIndex);
            }
            return a;
        } else {
           return toArray();
        }
    }

    public String toString() {
        String toReturn = new String();

        for(int i=0; i<size; i++) {
            toReturn += list.get(i+fromIndex).toString();
            toReturn += ", ";
        }

        return toReturn;
    }


}