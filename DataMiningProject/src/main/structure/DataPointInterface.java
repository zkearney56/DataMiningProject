package main.structure;

/**
 * Interface for a DataPoint.
 * 
 * @author Zachary Kearney
 * 
 * Last Edited: 12/1/2015
 *
 */
public interface DataPointInterface {

	/**
	 * Returns the classification of the datapoint.
	 * 
	 * @return returns the classification of the DataPoint.
	 */

	String getClassification();

	/**
	 * Returns the type of the DataPoint. Types can either be numerical or
	 * categorical.
	 * 
	 * @param column
	 *            - The column to access.
	 * @return returns the type of the Column as either "Numeric" or
	 *         "Categorical"
	 */

	String getType(int column);

	/**
	 * Returns all data in the row as an array list.
	 * 
	 * @return returns all data in the row as an ArrayList.
	 */

	DMArrayList<Object> getData();

	/**
	 * Removes a specified column from the row.
	 * 
	 * @param column
	 *            - The column to remove.
	 */

	void removeData(int column);

	/**
	 * Returns the value from a specified column.
	 * 
	 * @param column
	 *            - The column to access.
	 * @return returns the data from the column.
	 */

	Object getDataVal(int column);

	/**
	 * Sets the classification of the DataPoint and removes the column.
	 * 
	 * @param column
	 *            - the column to set as the main class.
	 */

	void setClass(int column);

	/**
	 * Used to create csv files from datapoints.
	 */

	@Override
	String toString();

}