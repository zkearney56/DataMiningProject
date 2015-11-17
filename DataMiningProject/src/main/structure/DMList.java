package main.structure;

public interface DMList<E> {
	
	public void add(E e);
	
	public void clear();
	
	public boolean equals(Object o);
	
	public E get(int index);
	
	public void remove(int index);
	
	public void set(int index, E Element);

}