package main.structure;
/**
 * Author: Zachary Kearney
 * Last Edited: 11/30/2015
 * Interface for a custom list class.
 */

public interface DMList<E> {
	
	/**
	 * Adds the element to the list.
	 * @param e
	 */
	
	public void add(E e);
	
	/**
	 * Adds the element to the list at the specified index.
	 * @param index
	 * @param e
	 */
	
	public void add(int index, E e);
	
	/**
	 * Clears all data from the list.
	 */
	
	public void clear();
	
	/**
	 * Compares two objects.
	 * @param o
	 * @return
	 */
	
	public boolean equals(Object o);
	
	/**
	 * Returns element at specified index.
	 * @param index
	 * @return
	 */
	
	public E get(int index);
	
	/**
	 * Removes element at specified index.
	 * @param index
	 */
	
	public void remove(int index);
	
	/**
	 * Sets element at specified index.
	 * @param index
	 * @param Element
	 */
	
	public void set(int index, E Element);
	
	/**
	 * Randomly shuffles list.
	 */
	
	public void shuffle();
	
	/**
	 * Returns the number of elements in the list.
	 * @return
	 */
	
	public int size();
	
	/**
	 * Swaps the elements at the specified indices.
	 * @param index1
	 * @param index2
	 */
	
	public void swap(int index1, int index2);
	
	/**
	 * Trims the list by the specified number.
	 * @param num
	 */
	
	public void trim(int num);

}