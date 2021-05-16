import myAdapter.*;
import org.hamcrest.*;
import org.hamcrest.core.IsEqual;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import org.junit.runner.*;
import org.junit.Test;

public class EntryAdapterTester {
    @Test
    public void constructorTest() {
        assertThrows("EntryAdapter(null, !null) non lancia nullPointerException", NullPointerException.class, ()->{new MapAdapter().new EntryAdapter(null, new Object());});
        assertThrows("EntryAdapter(null, !null) non lancia nullPointerException", NullPointerException.class, ()->{new MapAdapter().new EntryAdapter(new Object(), null);});
        assertNotNull("Il costruttore restituisce null", new MapAdapter().new EntryAdapter(new Object(), new Object()));
    }

    @Test
    public void getKeyTest() {
        Object k = new Object();
        Object v = new Object();

        HMap.HEntry entry = new MapAdapter().new EntryAdapter(k, v);
        assertEquals("getKey() non restituisce la chiave", k, entry.getKey());
    }

    @Test
    public void getValueTest() {
        Object k = new Object();
        Object v = new Object();

        HMap.HEntry entry = new MapAdapter().new EntryAdapter(k, v);
        assertEquals("getValue() non restituisce il valore", v, entry.getValue());
    }

    @Test
    public void hashCodeTest() {
        Object k = 1;
        Object v = 2;

        HMap.HEntry entry = new MapAdapter().new EntryAdapter(k, v);
        assertEquals("hashCode() non funziona corretamente", 3, entry.hasCode());
    }

    @Test
    public void setValueTest() {
        Object k = new Object();
        Object v = new Object();
        Object v1 = new Object();

        HMap.HEntry entry = new MapAdapter().new EntryAdapter(k, v);
        assertTrue("setValue(Object) non funziona corretamente", v==entry.setValue(v1) && entry.getValue()==v1);
        assertThrows("setValue(null) non lancia NullPointerException", NullPointerException.class, ()->{entry.setValue(null);});
    }

    @Test
    public void equalsTest() {
        Object k = new Object();
        Object v = new Object();

        HMap.HEntry entry1 = new MapAdapter().new EntryAdapter(k, v);
        HMap.HEntry entry1b = new MapAdapter().new EntryAdapter(k, v);
        HMap.HEntry entry2 = new MapAdapter().new EntryAdapter(k, new Object());
        HMap.HEntry entry3 = new MapAdapter().new EntryAdapter(new Object(), v);
        HMap.HEntry entry4 = new MapAdapter().new EntryAdapter(new Object(), new Object());

        assertTrue("equals non funziona con una entry su se stessa", entry1.equals(entry1));
        assertTrue("equals non funziona con una entry su una identica", entry1.equals(entry1b));
        assertFalse("equals non funziona con una entry (k, v) su una entry (k, !v)", entry1.equals(entry2));
        assertFalse("equals non funziona con una entry (k, v) su una entry (!k, v)", entry1.equals(entry3));
        assertFalse("equals non funziona con una entry (k, v) su una entry (!k, !v)", entry1.equals(entry4));
    }
}
