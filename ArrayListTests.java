import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

public class ArrayListTests {

    private ArrayListInterface<String> list;

    public static final int TIMEOUT = 200;
    private String[] that;
    @org.junit.Before
    public void setUp() {
        that = new String[ArrayListInterface.INITIAL_CAPACITY];
        list = new ArrayList<String>();
    }

    @org.junit.Test(timeout = TIMEOUT, expected =
            IndexOutOfBoundsException.class)
    public void falseAddAtIndex() {
        list.addAtIndex(Integer.MAX_VALUE, "a");
    }

    @org.junit.Test(timeout = TIMEOUT)
    public void addAtIndex() {
        list.addAtIndex(0, "omega");
        list.addAtIndex(0, "alpha");
        list.addAtIndex(2, "beta");
        list.addAtIndex(2, "delta");

        that[0] = "alpha";
        that[1] = "omega";
        that[2] = "delta";
        that[3] = "beta";
        assertArrayEquals(that, list.getBackingArray());
    }

    @org.junit.Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException
            .class)
    public void addAtIndexTooSmall() {
        list.addAtIndex(-40, "Whoa");
    }

    @org.junit.Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException
            .class)
    public void addAtIndexTooBig() {
        list.addAtIndex(40, "Whoa");
    }

    @org.junit.Test(timeout = TIMEOUT)
    public void addToFrontBig() {
        for (int i = 0; i < 60; i += 3) {
            list.addToFront("asdf");
            list.addToFront("bbbb");
            list.addToFront("gg");
        }

        that = new String[ArrayListInterface.INITIAL_CAPACITY * 8];
        for (int i = 0; i < 60; i += 3) {
            that[i] = "gg";
            that[i + 1] = "bbbb";
            that[i + 2] = "asdf";
        }
        assertArrayEquals(that, list.getBackingArray());
    }

    @org.junit.Test(timeout = TIMEOUT)
    public void addToBack() {
        list.addToBack("A");
        list.addToFront("B");
        list.addToBack("C");

        that[0] = "B";
        that[1] = "A";
        that[2] = "C";
        assertArrayEquals(that, list.getBackingArray());
    }

    @org.junit.Test(timeout = TIMEOUT, expected = IllegalArgumentException
            .class)
    public void falseAddToBack() {
        list.addToBack("A");
        list.addToFront("B");
        list.addToBack("C");
        list.addToBack(null);
    }

    @org.junit.Test(timeout = TIMEOUT)
    public void removeAtIndex() {
        list.addToFront("A");
        list.addToFront("B");
        list.addToFront("C");
        list.removeAtIndex(2);

        that[0] = "C";
        that[1] = "B";
        assertArrayEquals(that, list.getBackingArray());
    }

    @org.junit.Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException
            .class)
    public void falseRemoveAtIndexBig() {
        list.addToFront("A");
        list.addToFront("B");
        list.addToFront("C");
        list.removeAtIndex(15);
    }

    @org.junit.Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException
            .class)
    public void falseRemoveAtIndexSmall() {
        list.addToFront("A");
        list.addToFront("B");
        list.addToFront("C");
        list.removeAtIndex(-1);
    }

    @org.junit.Test(timeout = TIMEOUT)
    public void removeFromFrontFinalState() {
        for (char i = 'a'; i < 'p'; i++) {
            list.addToBack("" + i);
        }
        for (int i = 0; i < 10; i++) {
            list.removeFromFront();
        }
        that = new String[ArrayListInterface.INITIAL_CAPACITY * 2];
        for (char i = 'k'; i < 'p'; i++) {
            that[i - 'k'] = "" + i;
        }
        assertArrayEquals(that, list.getBackingArray());
    }

    @org.junit.Test(timeout = TIMEOUT)
    public void removeFromBackNull() {
        assertNull(list.removeFromBack());
    }

    @org.junit.Test(timeout = TIMEOUT)
    public void removeFromFrontNull() {
        list.addToBack("bop");
        list.removeFromFront();
        assertNull(list.removeFromFront());
    }

    @org.junit.Test(timeout = TIMEOUT)
    public void get() {
        list.addAtIndex(0, "Some");
        list.addAtIndex(1, "body");
        list.addAtIndex(2, "once");
        list.addAtIndex(3, "told");
        list.addAtIndex(4, "me");
        list.addAtIndex(5, "the");
        list.addAtIndex(6, "world");
        list.addAtIndex(7, "was");
        list.addAtIndex(8, "gonna");
        list.addAtIndex(9, "roll");
        list.addAtIndex(10, "me");
        list.addAtIndex(11, "I");
        list.addAtIndex(12, "ain't");
        list.addAtIndex(13, "the sharpest tool in the shed.");
        assertEquals("ain't", list.get(12));
        assertEquals("Some", list.get(0));
    }

    @org.junit.Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException
            .class)
    public void falseGetSmall() {
        list.addToFront("A");
        list.addToFront("B");
        list.addToFront("C");
        list.get(Integer.MIN_VALUE);
    }

    @org.junit.Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException
            .class)
    public void falseGetBig() {
        list.addToFront("A");
        list.addToFront("B");
        list.addToFront("C");
        list.get(Integer.MAX_VALUE);
    }


    @org.junit.Test(timeout = TIMEOUT)
    public void isEmpty() {
        assertTrue(list.isEmpty());
        list.addToBack("ooooo");
        assertFalse(list.isEmpty());
    }

    @org.junit.Test(timeout = TIMEOUT)
    public void size() {
        assertEquals(0, list.size());
        list.addToFront("a");
        list.addToBack("b");
        list.addAtIndex(2, "o");
        list.removeFromFront();
        assertEquals(2, list.size());
    }

    @org.junit.Test(timeout = TIMEOUT)
    public void clear() {
        list.addAtIndex(0, "Some");
        list.addAtIndex(1, "body");
        list.addAtIndex(2, "once");
        list.addAtIndex(3, "told");
        list.addAtIndex(4, "me");
        list.addAtIndex(5, "the");
        list.addAtIndex(6, "world");
        list.addAtIndex(7, "was");
        list.addAtIndex(8, "gonna");
        list.addAtIndex(9, "roll");
        list.addAtIndex(10, "me");
        list.addAtIndex(11, "I");
        list.addAtIndex(12, "ain't");
        list.addAtIndex(13, "the sharpest tool in the shed.");
        list.clear();
        assertArrayEquals(that, list.getBackingArray());
    }

    @org.junit.Test(timeout = TIMEOUT)
    public void getBackingArray() {
        assertArrayEquals(that, list.getBackingArray());
    }

    @org.junit.Test(timeout = TIMEOUT)
    public void getBackingArrayWithStuff() {
        that = new String[ArrayListInterface.INITIAL_CAPACITY * 2];
        for (int i = 0; i < 20; i++) {
            list.addToBack("" + i);
            that[i] = "" + i;
        }
        assertArrayEquals(that, list.getBackingArray());
    }
}