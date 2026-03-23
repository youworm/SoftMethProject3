package util;

import java.util.Iterator;

/**
 * Generic class list
 *
 * @author youwen
 * @param <E> Element, can be anything
 */
public class List<E> implements Iterable<E> {
    private static final int CAPACITY = 4;

    private E[] objects; //E is the name for the generic type
    private int size;

    /**
     * Constructs a list with capacity of 4
     */
    public List() {
        objects = (E[]) new Object[CAPACITY];
        size = 0;
    } //new an array type-casted to E with a capacity of 4.

    /**
     * Finds E in list
     *
     * @param e the element we are finding
     * @return the index of the element if found, -1 if not found
     */
    private int find(E e) {
        for  (int i = 0; i < size; i++) {
            if(objects[i].equals(e)) {
                return i;
            }
        }
        return -1;
    } //return -1 if not found

    /**
     * Grows list by capacity size 4
     */
    private void grow() {
        E[] newArray = (E[]) new Object[size + CAPACITY];
        for(int i = 0; i < size; i++) {
            newArray[i] = objects[i];
        }
        objects = newArray;
    } //grow the size of the array by 4

    /**
     * Checks if e is inside the list
     * @param e the element we are checking
     * @return true if contains e
     */
    public boolean contains(E e) {
        return find(e) != -1;
    }

    /**
     * Adds element to the list
     * @param e Element to be added
     */
    public void add(E e) {
        if(size == objects.length) {
            grow();
        }
        objects[size] = e;
        size++;
    }

    /**
     * Removes element from the list
     *
     * @param e element to be removed
     */
    public void remove(E e) {
        int index = find(e);
        if(index != -1) {
            objects[index] = objects[size - 1];
            objects[size - 1] = null;
            size--;
        }
    }

    /**
     * Checks if list is empty
     *
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks size of list
     * @return size of list
     */
    public int size() {
        return size;
    }

    /**
     * Returns an iterator over the elements in this list
     *
     * @return an iterator that traverses the list using for each
     */
    public Iterator<E> iterator() {
        return new ListIterator();
    } //traversing the list using for each

    /**
     * Finds the object at the index
     *
     * @param index index of object in list
     * @return object at the given index
     */
    public E get(int index) {
        if(index < 0 || index >= size) {
            return null;
        }
        return objects[index];
    } //return the object at the index

    /**
     * Sets the element at the given index
     *
     * @param index index of object
     * @param e object to be put in index
     */
    public void set(int index, E e) {
        if(index >= 0 && index < size) {
            objects[index] = e;
        }
    } //put object e at the index

    /**
     * find the index of given element
     *
     * @param e element we want to find the index of
     * @return the index of the element
     */
    public int indexOf(E e) {
        return find(e);
    } //return index of object e, or return -1
    //private inner class for the iterator to work properly

    /**
     * An iterator implementation for traversing elements stored in the list
     */
    private class ListIterator implements Iterator<E> {
        int current = 0; //current index when traversing the list (array)

        /**
         * Checks if there is an element after current one
         *
         * @return true if there are more elements
         */
        @Override
        public boolean hasNext(){
            return current < size;
        } //if it’s empty or at the end of the array

        /**
         * Returns the next element in iteration
         *
         * @return the next object in the iteration
         */
        @Override
        public E next(){
            return objects[current++];
        } //return the next object in the list
    }
}
