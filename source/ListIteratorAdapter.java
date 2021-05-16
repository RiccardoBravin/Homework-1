package myAdapter;
import java.util.NoSuchElementException;

public class ListIteratorAdapter implements HListIterator {

    public ListIteratorAdapter(HList _list){     //forse protected
        list = _list;
        fromIndex = 0;
        toIndex = list.size();
        cursor = 0;
        indexOfLastObjectReturned = -1;
        removeUse = false;
        addUse = false;
        lastCallIsNext = false;
    }

    public ListIteratorAdapter(HList _list, int fromIndex) throws IndexOutOfBoundsException {  //forse protected
        list = _list;
        if(fromIndex > list.size() || fromIndex < 0) throw new IndexOutOfBoundsException();
        this.fromIndex = fromIndex;
        toIndex = list.size();
        cursor = fromIndex;
        indexOfLastObjectReturned = -1;
        removeUse = false;
        addUse = false;
        lastCallIsNext = false;
    }

    public  ListIteratorAdapter(HList _list, int fromIndex, int toIndex) {     //forse protected
        list = _list;
        if(fromIndex < 0 || toIndex > list.size() || fromIndex > toIndex) throw new IndexOutOfBoundsException();
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        toIndex = list.size();
        cursor = fromIndex;
        indexOfLastObjectReturned = -1;
        removeUse = false;
        addUse = false;
        lastCallIsNext = false;
    }

    public ListIteratorAdapter(HList _list, int fromIndex, int toIndex, int index) {
        list = _list;
        if(fromIndex < 0 || toIndex > list.size() || fromIndex > toIndex  || index < fromIndex || index > toIndex) throw new IndexOutOfBoundsException();
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        toIndex = list.size();
        cursor = index;
        indexOfLastObjectReturned = -1;
        removeUse = false;
        addUse = false;
        lastCallIsNext = false;
    }

    public void add(Object o) {
        list.add(cursor, o);
        toIndex++;
        cursor++;
        addUse = true;
        
    }

    public boolean hasNext(){
        return (cursor < toIndex);
    }

    public boolean hasPrevious(){
        return (cursor > fromIndex);
    }

    public Object next() throws NoSuchElementException {
        if(!hasNext()) throw new NoSuchElementException();
        indexOfLastObjectReturned = cursor;
        removeUse = false;
        addUse = false;
        lastCallIsNext = true;
        return list.get(cursor++);
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
        return list.get(cursor);
    }

    public int previousIndex(){
        return cursor - 1;
    }

    public void remove() throws  IllegalStateException {                                                //ci sono modifiche da fare per la gestione di fromIndex e toIndex??
        if(removeUse || addUse || indexOfLastObjectReturned==-1) throw new IllegalStateException();
        list.remove(indexOfLastObjectReturned);
        if(lastCallIsNext) cursor--;
        //se arrivo da next il cursore fa --, se arrivo da previous il cursore sta fermo
        removeUse = true;
        
        toIndex--;
    }

    public void set(Object o) throws IllegalStateException {
        if(removeUse || addUse || indexOfLastObjectReturned==-1) throw new IllegalStateException();
        list.set(indexOfLastObjectReturned, o);
    }

    private int cursor;
    private int indexOfLastObjectReturned;
    private boolean removeUse;
    private boolean addUse;
    private boolean lastCallIsNext;
    private int fromIndex, toIndex;
    private HList list;

}
