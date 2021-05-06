import java.util.NoSuchElementException;
import java.util.Vector;

public class IteratorAdapter implements HIterator{
    private int index;
    private boolean nexted = false;
    private Vector v;

    public IteratorAdapter(Vector v){
        index = 0;
    }
    public IteratorAdapter(int i) throws IllegalArgumentException{
        if(i < v.size())
            throw new IllegalArgumentException();
        i = index;
    }

    public boolean hasNext(){
        return index < v.size();
    }

    public Object next() throws NoSuchElementException{
        if(!this.hasNext()){
            throw new NoSuchElementException();
        }
        
        nexted = true;
        index++;
        return v.get(index - 1);
            
    }

    public void remove() throws IllegalStateException{
        if(!nexted){
            throw new IllegalStateException();
        }
        nexted = false;
        index--;
        v.removeElementAt(index);
        
    }
}