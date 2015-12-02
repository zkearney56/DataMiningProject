package main.structure;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.opencsv.CSVReader;

/**

 * Object container for all data, contains three array lists containing the headers, data rows, and column attributes.
 * After initializing, use the readFile method to fill with data.
 * To clone, create a new DataList using the DataList(DataList list) constructor.
 * 
 * @author Zachary Kearney
 *
 * Last Edited: 11/30/2015
 */
public class DataList implements Cloneable {

	private DMArrayList<DataPoint> dataPoints;
	private DMArrayList<Object> dataHeaders;
	private DMArrayList<Attribute> dataAttributes;
	private String classification = "";

	/**
	 * Constructor for an empty datalist.
	 */

	public DataList() {
		dataPoints = new DMArrayList<DataPoint>();
		dataHeaders = new DMArrayList<Object>();
		dataAttributes = new DMArrayList<Attribute>();
	}

	/**
	 * Constructor to clone an existing datalist.
	 * 
	 * @param list
	 *            The DataList to clone.
	 */

	public DataList(DataList list) {
		dataPoints = new DMArrayList<DataPoint>();
		dataHeaders = new DMArrayList<Object>();
		dataAttributes = new DMArrayList<Attribute>();
		for (int i = 0; i < list.getPoints().size(); i++) {
			dataPoints.add(new DataPoint(list.getRow(i)));
		}
		for (int i = 0; i < list.getHeaders().size(); i++) {
			dataHeaders.add(list.getHead(i));
		}
		for (int i = 0; i < list.getAttributes().size(); i++) {
			dataAttributes.add(new Attribute(list.getAttribute(i)));
		}
	}

	/**
	 * Returns an ArrayList of the column headers.
	 * 
	 * @return returns a DMArrayList containing the headers.
	 */

	public DMArrayList<Object> getHeaders() {
		return dataHeaders;
	}

	/**
	 * Returns an ArrayList of the attributes.
	 * 
	 * @return returns a DMArrayList containing the attributes.
	 */
	public DMArrayList<Attribute> getAttributes() {
		return dataAttributes;
	}

	/**
	 * Returns an ArrayList of the data points.
	 * 
	 * @return returns a DMArrayList containing the dataPoints.
	 */

	public DMArrayList<DataPoint> getPoints() {
		return dataPoints;
	}

	/**
	 * Returns the main classification.
	 * 
	 * @return returns the main classification.
	 */

	public String getClassification() {
		return classification;
	}

	/**
	 * Sets the data attributes.
	 * 
	 * @param dataAttributes
	 *            The DataAttributes DMArrayList to clone.
	 */

	public void setAttributes(DMArrayList<Attribute> dataAttributes) {
		this.dataAttributes = dataAttributes;
	}

	/**
	 * Sets the column headers.
	 * 
	 * @param dataHeaders
	 *            the DataHeaders DMArrayList to clone.
	 */

	public void setHeaders(DMArrayList<Object> dataHeaders) {
		this.dataHeaders = dataHeaders;
	}

	/**
	 * Sets the data points.
	 * 
	 * @param dataPoints
	 *            the DataPoints DMArrayList to clone.
	 */

	public void setData(DMArrayList<DataPoint> dataPoints) {
		this.dataPoints = dataPoints;
	}

	/**
	 * Resets all attributes.
	 */

	private void resetAttributes() {
		dataAttributes.clear();
		for (int i = 0; i < dataHeaders.size(); i++) {
			dataAttributes.add(createAttribute(i));
		}
	}

	/**
	 * Returns the Attribute for the specified column.
	 * 
	 * @param column
	 *            The column of the Attribute.
	 * @return returns the specified Attribute.
	 */

	public Attribute getAttribute(int column) {
		return dataAttributes.get(column);
	}

	/**
	 * Adds a datapoint to the list.
	 * 
	 * @param point
	 *            The DataPoint to add.
	 */

	public void addDataPoint(DataPoint point) {
		dataPoints.add(point);
	}

	/**
	 * Returns the number of rows.
	 * 
	 * @return returns the number of rows.
	 */

	public int getNumRows() {
		return dataPoints.size();
	}

	/**
	 * Returns the specified row as a DataPoint.
	 * 
	 * @param row
	 *            The row to return.
	 * @return returns the specified row as a DataPoint.
	 */

	public DataPoint getRow(int row) {

		return dataPoints.get(row);

	}

	/**
	 * Returns the specified data from (row,column).
	 * 
	 * @param row
	 *            The specified row.
	 * @param column
	 *            The specified column.
	 * @return returns the value as an Object.
	 */

	public Object getRowColVal(int row, int column) {

		return dataPoints.get(row).getDataVal(column);

	}

	/**
	 * Returns the type of the specified column.
	 * 
	 * @param column
	 *            the specified column.
	 * @return returns the type of the specified column.
	 */

	public String getType(int column) {
		return dataPoints.get(0).getType(column);
	}

	/**
	 * Removes a row from the data.
	 * 
	 * @param row
	 *            the row to remove
	 */

	public void removeRow(int row) {

		dataPoints.remove(row);

	}

	/**
	 * Reads a csv file and fills the arraylists with data from the file.
	 * 
	 * @param file
	 *            The csv file to read.
	 */

	public void readFile(File file) {
		dataPoints.clear();
		dataHeaders.clear();
		dataAttributes.clear();
		System.out.println(file.getAbsolutePath());
		try {
			CSVReader csvReader = new CSVReader(new FileReader(file));
			String[] row;
			boolean header = false;
			while ((row = csvReader.readNext()) != null) {
				if (!header) {
					dataHeaders = new DMArrayList<Object>(row);
					header = true;
				} else {
					dataPoints.add(new DataPoint(new DMArrayList<Object>(row)));
				}
			}

			csvReader.close();
			for (int i = 0; i < dataHeaders.size(); i++) {
				dataAttributes.add(createAttribute(i));
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Trims the dataPoints list by a set number.
	 * 
	 * @param num
	 *            the number of DataPoints to trim.
	 */

	public void trimList(int num) {
		dataPoints.trim(num);
	}

	/**
	 * Removes the column from the data.
	 * 
	 * @param column
	 *            The column to remove from data.
	 */

	public void removeColumn(int column) {

		dataHeaders.remove(column);
		for (int i = 0; i < dataPoints.size(); i++) {
			dataPoints.get(i).removeData(column);
		}
	}

	/**
	 * Sets the main class of the data to the specified column and removes that
	 * column.
	 * 
	 * @param column
	 *            The column to be set to main classification.
	 */

	public void setClass(int column) {

		classification = (String) dataHeaders.get(column);
		dataHeaders.remove(column);
		// dataTypes.add(0, classification);
		for (int i = 0; i < dataPoints.size(); i++) {
			dataPoints.get(i).setClass(column);
		}
	}

	/**
	 * Sets the classification of the data list but doesnt remove from
	 * ArrayLists
	 * 
	 * @param classification
	 *            String containing classification.
	 */

	public void setClass(String classification) {
		this.classification = classification;
	}

	/**
	 * Returns the header of the specified column.
	 * 
	 * @param column
	 *            the specified column.
	 * @return returns the header of the specified column.
	 */

	public Object getHead(int column) {
		return dataHeaders.get(column);
	}

	/**
	 * Returns the number of columns.
	 * 
	 * @return returns the number of columns.
	 */

	public int getLength() {
		return dataHeaders.size();
	}

	/**
	 * Creates an attribute from the specified column.
	 * 
	 * @param column
	 *            the specified column.
	 * @return returns a new Attribute created from column data.
	 */

	private Attribute createAttribute(int column) {

		if (column > dataHeaders.size())
			throw new ArrayIndexOutOfBoundsException();
		String name = (String) dataHeaders.get(column);
		if (getType(column).equals("Categorial")) {
			DMArrayList<String> colVals = new DMArrayList<String>();
			for (int i = 0; i < dataPoints.size(); i++) {
				colVals.add(((String) dataPoints.get(i).getDataVal(column)));
			}
			return new Attribute(name, "Categorial", colVals);
		}

		else if (getType(column).equals("Numeric")) {
			DMArrayList<Double> colVals = new DMArrayList<Double>();
			for (int i = 0; i < dataPoints.size(); i++) {
				colVals.add(Double.parseDouble((String) dataPoints.get(i).getDataVal(column)));
			}
			return new Attribute(name, "Numeric", colVals);
		} else
			return null;
	}

	/**
	 * Returns an iterator for the data types.
	 * 
	 * @return returns dataHeaders iterator.
	 */

	public Iterator<Object> dataHeadersIterator() {
		return dataHeaders.iterator();
	}

	/**
	 * Returns an iterator for the data points.
	 * 
	 * @return returns dataPoints iterator
	 */

	public Iterator<DataPoint> dataPointsIterator() {
		return dataPoints.iterator();
	}

	/**
	 * Returns an iterator for the data attributes.
	 * 
	 * @return returns dataAttributes iterator
	 */

	public Iterator<Attribute> dataAttributesIterator() {
		return dataAttributes.iterator();
	}

	/**
	 * Writes the current list to a csv file with headers followed by data
	 * 
	 * @param filename
	 *            CSV filepath
	 */

	public void writeToCSV(String filename) {
		String NEW_LINE = "\n";
		try {
			FileWriter fileWriter = new FileWriter(filename);
			fileWriter.append(dataHeaders.toString());
			fileWriter.append(NEW_LINE);
			fileWriter.append(dataPoints.toString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Writes the current attributes to a csv file. Headers contain attribute
	 * information.
	 * 
	 * @param filename
	 *            - CSV FilePath
	 */

	public void attributeToCsv(String filename) {
		String NEW_LINE = "\n";
		try {
			FileWriter fileWriter = new FileWriter(filename);
			fileWriter.append("Name,Type,Unique Values,Minimum,Maximum,Mean,StdDev");
			fileWriter.append(NEW_LINE);
			fileWriter.append(dataAttributes.toString());
			fileWriter.flush();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns an object array containing two new dataLists. The first datalist
	 * is the training set. The second datalist is the test set. The sets are
	 * shuffled to select every other value.
	 * 
	 * @return returns Object array containing dataLists.
	 */

	public Object[] everyOther() {
		DataList training = new DataList();
		DataList test = new DataList();
		for (int i = 0; i < dataPoints.size(); i++) {
			if ((i & 1) == 0) {
				training.addDataPoint(dataPoints.get(i));
			} else {
				test.addDataPoint(dataPoints.get(i));
			}
		}
		training.setHeaders(this.getHeaders());
		test.setHeaders(this.getHeaders());
		training.setClass(this.getClassification());
		test.setClass(this.getClassification());
		return (new Object[] { training, test });
	}

	/**
	 * Returns an object array containing two new dataLists. The first dataList
	 * is the training set. The second dataList is the test set. The sets are
	 * shuffled randomly, then split by the specified percent.
	 * 
	 * @param percent
	 *            the percent you wish to be used for training.
	 * @return new Object array containing the dataLists.
	 */

	public Object[] percentSplit(float percent) {
		DataList training = new DataList();
		DataList test = new DataList();
		DMArrayList<DataPoint> grabBag = dataPoints;
		grabBag.shuffle();
		int size = grabBag.size();
		int trainCount = (int) percent * size;
		trainCount = trainCount / 100;
		int testCount = grabBag.size() - trainCount;
		for (int i = 0; i < trainCount; i++) {
			training.addDataPoint(grabBag.get(0));
			grabBag.remove(0);
		}
		for (int i = 0; i < testCount; i++) {
			test.addDataPoint(grabBag.get(0));
			grabBag.remove(0);
		}
		training.setHeaders(this.getHeaders());
		test.setHeaders(this.getHeaders());
		training.resetAttributes();
		test.resetAttributes();
		training.setClass(this.getClassification());
		test.setClass(this.getClassification());
		return (new Object[] { training, test });
	}
}
