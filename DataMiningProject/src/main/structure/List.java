package main.structure;

public interface List<E> {
	
	public void add(E e);
	
	public void add(int index, E e);
	
	public void clear();
	
	public boolean equals(Object o);
	
	public E get(int index);
	
	public void remove(int index);
	
	public void set(int index, E Element);

}
