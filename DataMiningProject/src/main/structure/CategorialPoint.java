package main.structure;

/**
 * @author Zachary Kearney Last Edited: 11/30/2015 Container for a
 *         CategorialPoint in an array list.
 */

public class CategorialPoint implements CategorialPointInterface {

	private String name;
	private int count;

	/**
	 * Initial constructor for the point, count starts at 1
	 * 
	 * @param name
	 *            - The name of the CategorialPoint.
	 */

	public CategorialPoint(String name) {
		this.name = name;
		count = 1;
	}

	/**
	 * Constructor to clone an existing point
	 * 
	 * @param point
	 *            - The CategorialPoint to clone.
	 */

	public CategorialPoint(CategorialPoint point) {
		this.name = point.name;
		this.count = point.count;
	}

	/* (non-Javadoc)
	 * @see main.structure.CategorialPointInterface#increment()
	 */

	@Override
	public void increment() {
		count++;
	}

	/* (non-Javadoc)
	 * @see main.structure.CategorialPointInterface#getCount()
	 */

	@Override
	public int getCount() {
		return count;
	}

	/* (non-Javadoc)
	 * @see main.structure.CategorialPointInterface#getName()
	 */

	@Override
	public String getName() {
		return name;
	}
}