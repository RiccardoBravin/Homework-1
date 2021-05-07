import java.util.NoSuchElementException;
import java.util.Vector;

public class ListIteratorAdapter implements HListIterator{
    private int index;
    private Vector v;
    private int lastIndex = -1;

    public ListIteratorAdapter(Vector v){
        this.v = v;
        this.index = 0;
    }
    public ListIteratorAdapter(Vector v, int index) throws IllegalArgumentException{
        if(index > v.size()) throw new IllegalArgumentException();
        this.v = v;
        this.index = index;
    }

    public void add(Object o){
        v.insertElementAt(o, index);
        index++;
    }

    public boolean hasNext(){
        return (index < v.size()); 
    }

    public boolean hasPrevious(){
        return (index > 0);
    }

    public Object next() throws NoSuchElementException{
        if(!this.hasNext()) throw new NoSuchElementException();
        nexted = true;
        lastIndex = index;
        return v.elementAt(index++);
    }

    public int nextIndex(){
        return index; //the index of the element that would be returned by a subsequent call to next, or list size if list iterator is at end of list.
    }

    public Object previous() throws NoSuchElementException{
        if(!this.hasPrevious()) throw new NoSuchElementException();
        nexted = false;
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
        lastIndex = -1;
        index--;
    }

    public void set(Object o) throws IllegalStateException{
        if(lastIndex == -1) throw new IllegalStateException();
        v.setElementAt(o, lastIndex);
        lastIndex = -1;
    }
}