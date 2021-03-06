package main.structure;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Custom ArrayList class to hold all data from a file.
 * 
 * @author Zachary Kearney
 * 
 * Last Edited: 11/30/2015
 */

public class DMArrayList<E> implements DMList<E>, Cloneable, Iterable<E> {

	private Object elementData[] = {};
	private static final int INITIAL_CAPACITY = 10;
	private int size = 0;

	/**
	 * Initial Constructor for an array list.
	 */

	public DMArrayList() {
		elementData = new Object[INITIAL_CAPACITY];
		size = 0;
	}

	/**
	 * Constructor for creating an ArrayList from an array of elements.
	 * 
	 * @param row
	 *            - ObjectArray containing data to fill the array.
	 */

	public DMArrayList(E[] row) {
		elementData = row;
		size = row.length;
	}

	/**
	 * Constructor for creating an ArrayList from an array of elements of a set
	 * size.
	 * 
	 * @param row
	 *            - ObjectArray containing data to fill the array.
	 * @param size
	 *            - A set size for the ArrayList.
	 */

	@SuppressWarnings("unused")
	private DMArrayList(E[] row, int size) {
		elementData = row;
		this.size = size;
		;
	}

	/**
	 * Constructor used to clone an existing ArrayList.
	 * 
	 * @param list
	 *            - The DMArrayList to clone.
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DMArrayList(DMArrayList list) {
		elementData = new Object[INITIAL_CAPACITY];
		size = 0;
		for (int i = 0; i < list.size(); i++) {
			add((E) list.get(i));
		}
	}

	@Override
	public void add(E e) {
		if (size == elementData.length) {
			increaseCapacity();
		}
		elementData[size++] = e;
	}

	@Override
	public void add(int index, E e) {
		if (size == elementData.length) {
			increaseCapacity();
		}
		for (int i = size; i > index; i--) {
			elementData[i] = elementData[i - 1];
		}
		elementData[index] = e;
		size++;
	}

	@Override
	public void clear() {

		elementData = new Object[INITIAL_CAPACITY];
		size = 0;

	}

	@Override
	@SuppressWarnings("unchecked")
	public E get(int index) {
		if (indexRange(index))
			throw new ArrayIndexOutOfBoundsException();
		return (E) elementData[index];
	}

	@Override
	public void remove(int index) {
		if (indexRange(index))
			throw new ArrayIndexOutOfBoundsException();
		for (int i = index; i < size - 1; i++) {
			elementData[i] = elementData[i + 1];
		}
		elementData[size - 1] = null;
		size--;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void set(int index, E e) {
		if (indexRange(index))
			throw new ArrayIndexOutOfBoundsException();
		elementData[index] = e;
	}

	private void increaseCapacity() {
		int newCapacity = elementData.length * 2;
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	private boolean indexRange(int index) {
		if (index >= size || index < 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void swap(int index1, int index2) {
		if (indexRange(index1) || indexRange(index2))
			throw new ArrayIndexOutOfBoundsException();
		Object e1 = elementData[index1];
		elementData[index1] = elementData[index2];
		elementData[index2] = e1;
	}

	private int randNum() {
		int rand = ThreadLocalRandom.current().nextInt(0, size - 1);
		return rand;
	}

	@Override
	public void shuffle() {
		for (int i = 1; i < size * 5; i++) {
			swap(i % size, randNum());

		}
	}

	@Override
	public void trim(int num) {
		if (num > size)
			throw new ArrayIndexOutOfBoundsException();
		for (int i = 0, x = size - 1; i < num; i++, x--) {
			this.remove(x);
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new ArrayListIterator();
	}

	/**
	 * Prints the ArrayList to the SystemOutputStream.
	 */
	@SuppressWarnings("rawtypes")
	public void printArray() {
		if (DMArrayList.this.size == 0) {
			System.out.println("None");
		} else {
			Iterator itr = DMArrayList.this.iterator();
			while (itr.hasNext()) {
				System.out.println((String) itr.next());
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		@SuppressWarnings("rawtypes")
		Iterator itr = DMArrayList.this.iterator();
		if (DMArrayList.this.get(0) instanceof Attribute || DMArrayList.this.get(0) instanceof DataPoint) {
			while (itr.hasNext()) {
				stringBuilder.append(itr.next().toString());
			}
			return stringBuilder.toString();
		} else {
			while (itr.hasNext()) {
				stringBuilder.append(itr.next().toString());
				if (itr.hasNext()) {
					stringBuilder.append(",");
				}
			}
			return stringBuilder.toString();
		}
	}

	/**
	 * Iterator for the arraylist.
	 * 
	 * @author zrk1002
	 *
	 */

	private class ArrayListIterator implements Iterator<E> {
		private int index = 0;

		@Override
		public boolean hasNext() {
			return index < size;
		}

		@Override
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			@SuppressWarnings("unchecked")
			E e = (E) elementData[index];
			index++;
			return e;
		}

		@Override
		public void remove() {
			// NoRemoveFunction
		}
	}
}
