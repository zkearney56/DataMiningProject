package main.structure;
/**
 * Author: Zachary Kearney
 * Last Edited: 11/30/2015
 * Custom ArrayList class to hold all data from a file.
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

public class DMArrayList<E> implements DMList<E>, Cloneable, Iterable<E>{

	private Object elementData[]={};
	private static final int INITIAL_CAPACITY = 10;
	private int size = 0;
	
	public DMArrayList(){
		elementData = new Object[INITIAL_CAPACITY];
		size = 0;
	}

	public DMArrayList(E[] row){
		elementData = row;
		size = row.length;
	}
	
	private DMArrayList(E[] row, int size) {
		elementData = row;
		this.size = size;;
	}
	
	public DMArrayList clone(){
		Object[] newData = elementData.clone();
		return new DMArrayList(newData, size);
	}

	public void add(E e) {
		if(size == elementData.length){
			increaseCapacity();
		}
		elementData[size++] = e;
	}
	
	public void add(int index, E e){
		if(size == elementData.length){
			increaseCapacity();
		}
		for(int i = size; i > index; i--){
			elementData[i] = elementData[i-1];
		}
		elementData[index] = e;
		size++;
	}
	
	public void clear() {
		
		elementData = new Object[INITIAL_CAPACITY];
		size = 0;
		
	}
	
	@SuppressWarnings("unchecked")
	public E get(int index) {
		if(indexRange(index)) throw new ArrayIndexOutOfBoundsException();	
		return (E) elementData[index];
	}

	public void remove(int index) {
		if(indexRange(index)) throw new ArrayIndexOutOfBoundsException();
		for(int i = index; i < size-1; i++){
		elementData[i] = elementData[i+1];
		}
		elementData[size-1] = null;
		size--;
	}

	public int size(){
		return size;
	}
	
	public void set(int index, E e) {
		if(indexRange(index))throw new ArrayIndexOutOfBoundsException();
		elementData[index] = e;
	}
	
	private void increaseCapacity(){
		int newCapacity = elementData.length*2;
		elementData = Arrays.copyOf(elementData, newCapacity);
	}
	
	private boolean indexRange(int index){
		if(index >= size || index < 0){
			return true;
		}
		else{
			return false;
		}
	}
	public void swap(int index1, int index2){
		if(indexRange(index1)||indexRange(index2)) throw new ArrayIndexOutOfBoundsException();
		Object e1 = elementData[index1];
		elementData[index1] = elementData[index2];
		elementData[index2] = e1;
	}
	
	private int randNum(){
		int rand = ThreadLocalRandom.current().nextInt(0,size-1);
		return rand;
	}
	
	public void shuffle(){
		for(int i = 1; i < size*5; i++){
			swap(i%size, randNum());
			
		}
	}
	
	public void trim(int num){
		if(num > size) throw new ArrayIndexOutOfBoundsException();
		for(int i = 0, x = size-1; i < num; i ++, x--){
			this.remove(x);
		}
	}
	
	public Iterator<E> iterator() {
		return new ArrayListIterator();
	}
	
	private class ArrayListIterator implements Iterator<E>{
		private int index = 0;	
		public boolean hasNext() {
			return index < size;
		}
		public E next() {
			if(!hasNext()) throw new NoSuchElementException();
			@SuppressWarnings("unchecked")
			E e = (E) elementData[index];
			index++;
			return e;
		}
		public void remove() {
		//NoRemoveFunction
		}
	}
}
