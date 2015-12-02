package main.structure;

/**
 * Interface for an Attribute
 * 
 * @author Zachary Kearney
 * 
 * Last Edited: 12/1/2015
 *
 */
public interface AttributeInterface {

	/**
	 * Returns the minimum value
	 * 
	 * @return the min of the Attribute.
	 */

	double getMin();

	/**
	 * Returns the maximum value
	 * 
	 * @return the max of the Attribute.
	 */

	double getMax();

	/**
	 * Returns the mean of the values
	 * 
	 * @return the mean of the Attribute.
	 */

	double getMean();

	/**
	 * Returns the standard deviation of the values
	 * 
	 * @return the standard deviation of the Attribute.
	 */

	double getStdDev();

	/**
	 * Returns the number of unique values
	 * 
	 * @return the number of unique values of the Attribute.
	 */

	int getNumUniqueVals();

	/**
	 * Returns the type of the Attribute, can be "Numeric" or "Categorical"
	 * 
	 * @return the type of the Attribute
	 */

	String getType();

	/**
	 * Returns the name of the values
	 * 
	 * @return the name of the Attribute
	 */

	String getName();

	/**
	 * Used to signify is attribute is to be ignored
	 * 
	 * @param t
	 *            boolean
	 */

	void ignore(boolean t);

	/**
	 * Returns ignore value
	 * 
	 * @return boolean signifying if Attribute is ignored.
	 */

	boolean getIgnore();

	/**
	 * Outputs the Attribute to a csv readable format string.
	 */

	@Override
	String toString();

}