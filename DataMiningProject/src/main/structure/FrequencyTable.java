package main.structure;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class is used by the Entropy and Gini algorithm in order to help process the
 * data in a dataList. It is meant to be made from one column of a
 * dataList where the attributes are the possible values of that column, and the
 * types are the possible classification types. The data can be accessed by
 * either using two integers like a 2d array, or by using the attribute and type
 * names like a hashtable.
 * 
 * @author Dan Martin
 *
 */
public class FrequencyTable {
	Hashtable frequency;
	Vector types;
	Vector attributes;

	/**
	 * Constructs a frequency table
	 */
	public FrequencyTable() {
		frequency = new Hashtable<Object, Hashtable>();
		types = new Vector<String>();
		attributes = new Vector<Object>();
	}

	/**
	 * Sets the possible type values.
	 * 
	 * @param t
	 *            a vector of Strings, one for each type possible in the
	 *            dataList.
	 */
	public void setTypes(Vector<String> t) {
		types = t;
	}

	/**
	 * Sets the possible Attributes. This can be string data like
	 * "red","green","blue", or numbers for the possible bins for numeric data.
	 * 
	 * @param a
	 *            a vector of the possible attributes, one for each attribute in
	 *            a single column of the dataList.
	 */
	public void setAttributes(Vector<Object> a) {
		attributes = a;
		for (int i = 0; i < attributes.size(); i++) {
			Hashtable temp = new Hashtable<String, Integer>();
			for (int j = 0; j < types.size(); j++) {
				temp.put(types.get(j), 0);
			}
			frequency.put(attributes.get(i), temp);
		}
	}

	/**
	 * Increments the value at the index specified by using some attribute and
	 * type name.
	 * 
	 * @param a
	 *            the attribute that needs to be incremented
	 * @param t
	 *            the type value that corresponds to the attribute to be
	 *            incremented.
	 */
	public void increment(Object a, String t) {
		((Hashtable) frequency.get(a)).put(t, ((Integer) ((Hashtable) frequency.get(a)).get(t)) + 1);
	}

	/**
	 * Gets the value of the index specified by the attribute and type (like a
	 * hashtable)
	 * 
	 * @param a
	 *            the value used to access the attribute part of the table.
	 * @param t
	 *            the value used to access the type part of the table.
	 * @return the value at the index specified by the attribute and type.
	 */
	public int getValue(Object a, String t) {
		return ((int) ((Hashtable) frequency.get(a)).get(t));
	}

	/**
	 * Treats the frequencyTable as a 2d array with the Attributes as the rows
	 * and types as columns.
	 * 
	 * @param a
	 *            the index of the attribute to access.
	 * @param t
	 *            the index of the type to access.
	 * @return the value at the index specified by the attribute and type.
	 */
	public Integer getValue(int a, int t) {

		return getValue(getAttribute(a), getType(t));
	}

	/**
	 * Gets the number of types in the table.
	 * 
	 * @return the number of types in the table.
	 */
	public int getTypesSize() {
		return types.size();
	}

	/**
	 * Gets the number of attributes in the table.
	 * 
	 * @return the number of attributes in the table.
	 */
	public int getAttributesSize() {
		return attributes.size();
	}

	/**
	 * Gets the type needed to access a specific index of the table.
	 * 
	 * @param i
	 *            the index to look.
	 * @return returns a String that corresponds to the type used to access that
	 *         index of the table.
	 */
	public String getType(int i) {
		return (String) types.get(i);
	}

	/**
	 * Gets the attribute needed to access a specific index of the table.
	 * 
	 * @param i
	 *            the index to look.
	 * @return returns a Object that corresponds to the attribute used to access
	 *         that index of the table.
	 */
	public Object getAttribute(int i) {
		return attributes.get(i);
	}

	/**
	 * Gets the sum of each frequency table entry for some attribute. Basically
	 * just returns the sum of the row accessed by the attribute a.
	 * 
	 * @param a
	 *            the attribute that corresponds to the row to sum up.
	 * @return the sum of the row.
	 */
	public int getTotal(Object a) {
		int sum = 0;
		Enumeration e = ((Hashtable) frequency.get(a)).keys();
		while (e.hasMoreElements()) {
			sum += ((Integer) ((Hashtable) frequency.get(a)).get(e.nextElement()));
		}
		return sum;
	}

	/**
	 * completely resets the frequency table.
	 */
	public void clear() {
		frequency.clear();
		types.clear();
		attributes.clear();
	}
}
