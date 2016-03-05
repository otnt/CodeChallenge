# Design Problem - Iterator

## TL;DR

This article talks about how to design a generic iterator that supports ```next()```, ```hasNext()``` and ```getCurrent()``` methods.

## Why use iterator?

To traverse a data structure, one could either use index to go through it, or could use an iterator. An iterator basically has several advantages over index traverse:

1. Most important, at least to me, is that given a streaming data, which means we don't know size of input, then we could not use index to go through since we don't know when to stop.
2. Even we could know the size beforehand, it could be difficult/expensive to calculate this. For example, calculation of size of a LinkedList could be O(N), through not the case in Java.
2. There are quite a few important data structures that doesn't support random access, including Set, LinkedList, Tree etc.
3. Iterator provides a high level concept of traversing, using the general function ```hasNext()```, ```next()``` etc. so that we could iterate despite underlying data structure. See more about [generic programming](https://en.wikipedia.org/wiki/Generic_programming).
4.  Iterator is fail-fast (at least in Java), which means it could help to prevent some concurrent modification conflict. On the other side, index traverse doesn't provide such protection.

## What is Iterator

The core idea of iterator is that it knows

1. the current position in a data structure
2. how to go to the next position
3. if it is the end of data structure

By abstracting these three most important functions, an iterator has to implement three corresponding methods:

1. ```getCurrent()``` return current value pointed by iterator
2. ```next()``` go to next position. If such position doesn't exist, throw an exception.
3. ```hasNext()``` check if it is the end, this function should be called each time before we call ```next()```

Given these information, we have our iterator interface:

```java
public interface Iterator<T> {
	public T getCurrent() throws NoSuchElementException;
	public T next() throws NoSuchElementException;
	public boolean hasNext();
}
```

Fairly easy, right? Indeed it is! Now let's see how to design a concrete iterator.

## Iterator of ArrayList

In Java, get size of ArrayList is constant time, so we could use size as boundary to check if we hit end of list.

First, we need some private field to help manage iteration.

```java
//Size of underlying data structure
private int size;
//Used to iterate through data structure, it always points to next element
//to be iterated
private int cursor;
//Point to current value, initially -1
private int current;
//Used to save underlying data structure
private Object[] elementData;
```

Then, check if we hit end of list is simple, just check if cursor is at end of list.

```java
/*
 * Check if we hit end of data structure.
 */
public boolean hasNext() {
    return cursor != size;
}
```

In order to get next value, we first check ```hasNext()```, if true then we go on return the value pointed by cursor, otherwise we throw exception.
``` java
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
```

Finally, to get current value is very straightforward.

```java
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
```

You could get full source code at [here](https://github.com/otnt/CodeChallenge/tree/master/DataStructure/iterator/src/iterator).

## Java Open-JDK Implementation

To learn more about iterator in Java, I highly suggest you read some Java source code. They are extremely easy to follow.

1. [Iterator interface](http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b27/java/util/Iterator.java)
2. [ArrayList Iterator](http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/8u40-b25/java/util/ArrayList.java#ArrayList.Itr)
3. [ListIterator interface](http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/util/ListIterator.java#ListIterator) (An iterator supports both moving forward and backward)
4. [ArrayList ListIterator](http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/8u40-b25/java/util/ArrayList.java#ArrayList.ListItr)
5. [Hash Iterator](http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/8u40-b25/java/util/HashMap.java#HashMap.HashIterator) (Iterator implementation in HashMap is a little bit different since the underlying data structure is different. But the general idea is the same)

