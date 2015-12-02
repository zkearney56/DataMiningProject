package main.structure;

/**
 * @author Zachary Kearney Last Edited: 11/30/2015 Interface for a custom list
 *         class.
 */

public interface DMList<E> {

	/**
	 * Adds the element to the list.
	 * 
	 * @param e
	 *            - An element
	 */

	public void add(E e);

	/**
	 * Adds the element to the list at the specified index.
	 * 
	 * @param index
	 *            - The index to add
	 * @param e
	 *            - Element to add
	 */

	public void add(int index, E e);

	/**
	 * Clears all data from the list.
	 */

	public void clear();

	/**
	 * Compares two objects.
	 * 
	 * @param o
	 *            - Object to compare.
	 * @return true or false
	 */

	@Override
	public boolean equals(Object o);

	/**
	 * Returns element at specified index.
	 * 
	 * @param index
	 *            - The index to access
	 * @return returns Object located at specified index
	 */

	public E get(int index);

	/**
	 * Removes element at specified index.
	 * 
	 * @param index
	 *            - index to remove
	 */

	public void remove(int index);

	/**
	 * Sets element at specified index.
	 * 
	 * @param index
	 *            - index to add
	 * @param Element
	 *            - Element to add
	 */

	public void set(int index, E Element);

	/**
	 * Randomly shuffles list.
	 */

	public void shuffle();

	/**
	 * Returns the number of elements in the list.
	 * 
	 * @return returns the size
	 */

	public int size();

	/**
	 * Swaps the elements at the specified indices.
	 * 
	 * @param index1
	 *            - first element position
	 * @param index2
	 *            - second element position
	 */

	public void swap(int index1, int index2);

	/**
	 * Trims the list by the specified number.
	 * 
	 * @param num
	 *            - number of elements to trim
	 */

	public void trim(int num);

	/**
	 * Used to output to a csv style file for arraylists not containing objects
	 * containing a custom toString method.
	 */

	@Override
	public String toString();
}