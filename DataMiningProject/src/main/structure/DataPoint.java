package main.structure;

import java.util.Iterator;

/**
 *  Container for a row of data within an ArrayList
 *  
 * @author Zachary Kearney
 * 
 *  Last Edited: 11/30/2015
 */

public class DataPoint implements DataPointInterface {

	private DMArrayList<Object> data;
	private String classification;

	/**
	 * Constructor which accepts an arraylist containing all data in a row
	 * 
	 * @param data
	 *            - DMArrayList which contains row data.
	 */
	public DataPoint(DMArrayList<Object> data) {

		classification = null;
		this.data = data;

	}

	/**
	 * Constructor to clone an existing DataPoint.
	 * 
	 * @param point
	 *            - DataPoint to clone.
	 */

	public DataPoint(DataPoint point) {
		this.classification = point.classification;
		this.data = new DMArrayList<Object>(point.data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.structure.DataPointInterface#getClassification()
	 */

	@Override
	public String getClassification() {

		return classification;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.structure.DataPointInterface#getType(int)
	 */

	@Override
	public String getType(int column) {
		try {
			@SuppressWarnings("unused")
			double d = Double.parseDouble((String) data.get(column));
		} catch (NumberFormatException nfe) {
			return "Categorial";
		}
		return "Numeric";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.structure.DataPointInterface#getData()
	 */

	@Override
	public DMArrayList<Object> getData() {
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.structure.DataPointInterface#removeData(int)
	 */

	@Override
	public void removeData(int column) {
		data.remove(column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.structure.DataPointInterface#getDataVal(int)
	 */

	@Override
	public Object getDataVal(int column) {

		return data.get(column);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.structure.DataPointInterface#setClass(int)
	 */

	@Override
	public void setClass(int column) {

		classification = (String) data.get(column);
		data.remove(column);
		// data.add(0, classification);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.structure.DataPointInterface#toString()
	 */

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		@SuppressWarnings("rawtypes")
		Iterator itr = data.iterator();
		while (itr.hasNext()) {
			stringBuilder.append(itr.next().toString());
			if (itr.hasNext()) {
				stringBuilder.append(",");
			} else {
				stringBuilder.append("\n");
			}
		}
		return stringBuilder.toString();
	}
}
