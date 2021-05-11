import java.util.NoSuchElementException;
import java.util.Vector;

public class IteratorAdapter implements HIterator{
    private int index;
    private boolean nexted = false;
    private HCollection coll;

    public IteratorAdapter(HCollection c) throws NullPointerException{
        if (c == null) throw new NullPointerException("La collection passata è null");
        //if(c.isEmpty()) throw new IndexOutOfBoundsException("La collection passata è vuota, impossibile iterare");
        this.coll = c;
        index = 0;
    }
    public IteratorAdapter(HCollection c, int i) throws NullPointerException, IndexOutOfBoundsException{
        if (c == null) throw new NullPointerException("La collection passata è null");
        if(i >= c.size() || i < 0) throw new IndexOutOfBoundsException("L'indice indicato non rientra nei margini della collection");
        this.coll = c;
        index = i;
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
            throw new IllegalStateException("Next non chiamato prima una chiamata a remove");
        }
        nexted = false;
        index--;
        coll.remove(coll.toArray()[index]);
        
    }
}