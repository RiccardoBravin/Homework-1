import java.util.NoSuchElementException;
import java.util.Vector;

public class IteratorAdapter implements HIterator{
    private int index;
    private boolean nexted = false;
    private HCollection coll;

    public IteratorAdapter(HCollection c){
        this.coll = c;
        index = 0;
    }
    public IteratorAdapter(HCollection c, int i) throws IllegalArgumentException{
        if(i <= c.size() || i < 0)throw new IllegalArgumentException();
        this.coll = c;
        i = index;
    }

    public boolean hasNext(){
        return index < coll.size();
    }

    public Object next() throws NoSuchElementException{
        if(!this.hasNext()){
            throw new NoSuchElementException();
        }
        
        nexted = true;
        index++;
        return coll.toArray()[index - 1];
            
    }

    public void remove() throws IllegalStateException{
        if(!nexted){
            throw new IllegalStateException();
        }
        nexted = false;
        index--;
        coll.remove(coll.toArray()[index]);
        
    }
}