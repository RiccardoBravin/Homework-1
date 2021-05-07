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

    public boolean addAll(HCollection c) throws NullPointerException{

        if(c == null) throw new NullPointerException("Collection null");

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
    
    public boolean containsAll(HCollection c) throws NullPointerException{
        if(c == null) throw new NullPointerException("Collection null");

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
        return new IteratorAdapter(v);
    }



    public boolean remove(Object o){
        return v.removeElement(o);
    }

    public boolean removeAll(HCollection c) throws NullPointerException{  //BISOGNA CONTROLLARE CHE MI PASSI SEMPRE UNA HCOLLECTION E CHE JAVA NON PERMETTA DI PASSARE ALTRO ALTRIMENTI BISOGNA FARE UN THROW
        if(c == null) throw new NullPointerException("Collection null");
        HIterator iter = c.iterator();
        boolean changed = false;
        while(iter.hasNext()){
            if(v.removeElement(iter.next())){
                changed = true;
            }
        }

        return changed;

    }

    public boolean retainAll(HCollection c) throws NullPointerException{ //STESSA ROBA DI SOPRA

        if(c == null) throw new NullPointerException("Collection null");

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

    public Object[] toArray(){
        Object[] arr = new Object[this.size()];
        for(int i = 0; i < v.size(); i++){
            arr[i] = v.elementAt(i);
        }
        return arr;
    }

    public Object[] toArray(Object[] a) throws NullPointerException{
        
        if(a == null) throw new NullPointerException("Array null");

        if(a.length >= this.size()){
            for(int i = 0; i < v.size(); i++){
                a[i] = v.elementAt(i);
            }
            return a;
        }
        return this.toArray();
    }


}
