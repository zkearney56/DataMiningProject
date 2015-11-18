package main.structure;

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
	}

	public DMArrayList(E[] row) {
		elementData = row;
		size = row.length;
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
		if(index > size || index < 0){
			return true;
		}
		else{
			return false;
		}
	}
	public void swap(int index1, int index2){
		if(indexRange(index1)||indexRange(index2)) throw new ArrayIndexOutOfBoundsException();
		Object e1 = elementData[index1];
		Object e2 = elementData[index2];
		elementData[index1] = e2;
		elementData[index2] = e1;
	}
	
	private int randNum(){
		int rand = ThreadLocalRandom.current().nextInt(0,size);
		return rand;
	}
	
	public void randShufflePercent(int percent){
		float count = (percent/100) * size;
		for(int i = 0; i < size; i ++){
			
		}
	}
	
	public void shuffle(int iterations){
		int x = 0;
		for(int i = 1; i < size*iterations*100; i++){
			swap(x, randNum());
			if(x<size){
				x++;
			}
			else{
				x=0;
			}
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
			E e = (E) elementData[index];
			index++;
			return e;
		}
		public void remove() {
		//NoRemoveFunction
		}
	}
}
