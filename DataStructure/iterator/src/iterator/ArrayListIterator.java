/*
 * This is used to show how to design a generic iterator in Java.
 * To see more about the idea, including why iterator is important etc. 
 * check this post "Design Problem - Iterator"
 *
 * Author: Pufan Jiang
 * Date: 2016/03/04
 */

package iterator;

import iterator.Iterator;
import java.util.NoSuchElementException;

public class ArrayListIterator<T> implements Iterator<T> {
    //Size of underlying data structure
    private int size;
    //Used to iterate through data structure, it always points to next element
    //to be iterated
    private int cursor;
    //Point to current value, initially -1
    private int current;
    //Used to save underlying data structure
    private Object[] elementData;

    /*
     * We use list to represent inner data structure of an ArrayList.
     */
    public ArrayListIterator(Object[] array) {
        elementData = new Object[array.length];
        System.arraycopy(array, 0, elementData, 0, array.length);
        size = elementData.length;
        cursor = 0;
        current = -1;
    }

    /*
     * Get current value pointed by iterator
     */
    @SuppressWarnings("unchecked")
    public T getCurrent() throws NoSuchElementException {
        if(current < 0) {
            throw new NoSuchElementException();
        }
        return (T)elementData[current];
    }

    /*
     * Get next value of iterator, move iterator to next position at the
     * same time. If already at end of data structure, throw exception.
     */
    @SuppressWarnings("unchecked")
    public T next() throws NoSuchElementException {
        if(!hasNext()) {
            throw new NoSuchElementException();
        }
        current = cursor++;
        return (T)elementData[current];
    }

    /*
     * Check if we hit end of data structure.
     */
    public boolean hasNext() {
        return cursor != size;
    }

    public static void main(String[] argv) {
        //Test1, generate ArrayListIterator
        ArrayListIterator<Integer> iterator = new ArrayListIterator<Integer>(new Integer[]{1,2});
        //Test 2, check hasNext
        assert(iterator.hasNext());
        //Test 3, get next value
        assert(iterator.next() == 1);
        //Test 4, get current value
        assert(iterator.getCurrent() == 1);
        //Test 5, check hasNext
        assert(iterator.hasNext());
        //Test 6, get next value
        assert(iterator.next() == 2);
        //Test 7, get current value
        assert(iterator.getCurrent() == 2);
        //Test 5, check hasNext
        assert(!iterator.hasNext());
        System.out.println("test passed");
    }
}
