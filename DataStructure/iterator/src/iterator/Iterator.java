/*
 * This is used to show how to design a generic iterator in Java.
 * To see more about the idea, including why iterator is important etc. 
 * check this post "Design Problem - Iterator"
 *
 * Author: Pufan Jiang
 * Date: 2016/03/04
 */

package iterator;

import java.util.NoSuchElementException;

public interface Iterator<T> {
    /*
     * Get current value pointed by iterator
     */
    public T getCurrent() throws NoSuchElementException;

    /*
     * Get next value of iterator, move iterator to next position at the
     * same time. If already at end of data structure, throw exception.
     */
    public T next() throws NoSuchElementException;

    /*
     * Check if we hit end of data structure.
     */
    public boolean hasNext();
}
