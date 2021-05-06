import java.util.NoSuchElementException;
import java.util.Vector;

public class SetAdapter implements HSet{
    
    Vector v;

    public SetAdapter(){
        v = new Vector();
    }

    public boolean add(Object o){
        
        if(v.contains(o)){
            return false;
        }
        v.addElement(o);
        return true;
    }

    public boolean addAll(HCollection c){
        HIterator iter = c.iterator();
        boolean changed = false;

        while(iter.hasNext()){
            if(this.add(iter.next())){
                changed = true;
            } 
        }

        return changed;
    }

    public void clear(){
        v.removeAllElements();
    }

    public boolean contains(Object o){
        return v.contains(o);
    }
    
    public boolean containsAll(HCollection c){
        HIterator iter =c.iterator();
        
        while(iter.hasNext()){
            if(!this.contains(iter.next()))
                return false;
        }
        return true;
    }

    public boolean equals(Object o){
        
        return (o instanceof HSet) && (((HSet)o).containsAll(this)) && (this.containsAll((HSet)o));

    }

    public int hashCode(){
        int sum = 0;
        HIterator iter = this.iterator();
        while(iter.hasNext()){
            sum += iter.next().hashCode();
        }
        return sum;
    }

    public boolean isEmpty(){
        return v.isEmpty();
    }

    public HIterator iterator(){
        return new IteratorAdapter();
    }

    //potrebbe essere necessario farlo diventare public per testare
    protected class IteratorAdapter implements HIterator{
        int index;
        boolean nexted = false;

        public IteratorAdapter(){
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


    public boolean remove(Object o){
        return v.removeElement(o);
    }

    public boolean removeAll(HCollection c){  //BISOGNA CONTROLLARE CHE MI PASSI SEMPRE UNA HCOLLECTION E CHE JAVA NON PERMETTA DI PASSARE ALTRO ALTRIMENTI BISOGNA FARE UN THROW

        HIterator iter = c.iterator();
        boolean changed = false;
        while(iter.hasNext()){
            if(v.removeElement(iter.next())){
                changed = true;
            }
        }

        return changed;

    }

    public boolean retainAll(HCollection c) throws UnsupportedOperationException, ClassCastException, NullPointerException{ //STESSA ROBA DI SOPRA

        HIterator iter = this.iterator();
        boolean changed = false;
        
        while(iter.hasNext()){
            if(!c.contains(iter.next())){
                iter.remove();
                changed = true;
            }
        }

        return changed;
    }

    public int size(){
        return v.size();
    }


}
