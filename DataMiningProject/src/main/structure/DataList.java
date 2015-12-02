package main.structure;

/**
 * Author: Zachary Kearney
 * Last Edited: 11/30/2015
 * Object container for all data, contains three array lists containing the headers, data rows, and column attributes.
 */

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.opencsv.CSVReader;

public class DataList implements Cloneable {
	
	private DMArrayList<DataPoint> dataPoints;
	private DMArrayList<Object> dataHeaders;
	private DMArrayList<Attribute> dataAttributes;
	private String classification = "";

	/**
	 * Constructor for an empty datalist.
	 */
	
	public DataList(){
		dataPoints = new DMArrayList<DataPoint>();
		dataHeaders = new DMArrayList<Object>();
		dataAttributes = new DMArrayList<Attribute>();
	}
	
	/**
	 * Constructor to clone an existing datalist.
	 * @param list
	 */
	
	public DataList(DataList list){
		dataPoints = new DMArrayList<DataPoint>();
		dataHeaders = new DMArrayList<Object>();
		dataAttributes = new DMArrayList<Attribute>();
		for(int i = 0; i < list.getPoints().size(); i++){
			dataPoints.add(new DataPoint(list.getRow(i)));
		}
		for(int i = 0; i < list.getHeaders().size(); i++){
			dataHeaders.add(list.getHead(i));
		}
		for(int i = 0; i < list.getAttributes().size(); i++){
			dataAttributes.add(new Attribute(list.getAttribute(i)));
		}
	}
	
	/**
	 * Returns an ArrayList of the column headers.
	 * @return
	 */
	
	public DMArrayList<Object> getHeaders(){
		return dataHeaders;
	}
	
	/**
	 * Returns an ArrayList of the attributes.
	 * @return
	 */
	public DMArrayList<Attribute> getAttributes(){
		return dataAttributes;
	}
	
	/**
	 * Returns an ArrayList of the data points.
	 * @return
	 */
	
	public DMArrayList<DataPoint> getPoints(){
		return dataPoints;
	}
	
	/**
	 * Returns the main classification.
	 * @return
	 */
	
	public String getClassification(){
		return classification;
	}
	
	/**
	 * Sets the data attributes.
	 * @param dataAttributes
	 */
	
	public void setAttributes(DMArrayList<Attribute> dataAttributes){
		this.dataAttributes = dataAttributes;
	}
	
	/**
	 * Sets the column headers.
	 * @param dataTypes
	 */
	
	public void setHeaders(DMArrayList<Object> dataTypes){
		this.dataHeaders = dataTypes;	
	}
	
	/**
	 * Sets the data points.
	 * @param dataPoints
	 */
	
	public void setData(DMArrayList<DataPoint> dataPoints){
		this.dataPoints = dataPoints;
	}
	
	/**
	 * Resets all attributes.
	 */
	
	private void resetAttributes(){
		dataAttributes.clear();
		for(int i = 0; i < dataHeaders.size(); i++){
			dataAttributes.add(createAttribute(i));
		}
	}
	
	/**
	 * Returns the Attribute for the specified column.
	 * @param column
	 * @return
	 */
	
	public Attribute getAttribute(int column){
		return dataAttributes.get(column);
	}
	/**
	 * Adds a datapoint to the list.
	 * @param point
	 */
	
	public void addDataPoint(DataPoint point){
		dataPoints.add(point);
	}
	
	/**
	 * Returns the number of rows.
	 * @return
	 */
	
	public int getNumRows()
	{
	return dataPoints.size();
	}
	
	/**
	 * Returns the specified row as a DataPoint.
	 * @param column
	 * @return
	 */
	
	public DataPoint getRow(int column){
		
		return dataPoints.get(column);
		
	}
	
	/**
	 * Returns the specified data from (row,column).
	 * @param row
	 * @param column
	 * @return
	 */
	
	public Object getRowColVal(int row, int column){
		
		return dataPoints.get(row).getDataVal(column);
		
	}
	
	/**
	 * Returns the type of the specified column.
	 * @param column
	 * @return
	 */
	
	public String getType(int column){
		return dataPoints.get(0).getType(column);
	}
	
	/**
	 * Removes a row from the data.
	 * @param row
	 */
	
	public void removeRow(int row){
		
		dataPoints.remove(row);
		
	}
	
	/**
	 * Reads a csv file and fills the arraylists with data from the file.
	 * @param file
	 */
	
	public void readFile(File file){
		dataPoints.clear();
		dataHeaders.clear();
		dataAttributes.clear();
			System.out.println(file.getAbsolutePath());
		try{
			CSVReader csvReader = new CSVReader(new FileReader(file));
			String[] row;
			boolean header = false;
			while((row = csvReader.readNext()) != null) {
				if(!header){
					dataHeaders = new DMArrayList<Object>(row);
					header = true;
				}				
					else{				
				dataPoints.add(new DataPoint(new DMArrayList<Object>(row)));
			}
				}
			
			csvReader.close();
			for(int i = 0; i < dataHeaders.size(); i++){
				dataAttributes.add(createAttribute(i));
			}
		}

		catch(Exception e){
			e.printStackTrace();}
							
	}
	
	/**
	 * Trims the dataPoints list by a set number.
	 * @param num
	 */
	
	public void trimList(int num){
		dataPoints.trim(num);
	}
	
	/**
	 * Removes the column from the data.
	 * @param column
	 */
	
	public void removeColumn(int column){
		
		dataHeaders.remove(column);
		for(int i = 0; i < dataPoints.size(); i++){
			dataPoints.get(i).removeData(column);
		}
	}
	
	/**
	 * Sets the main class of the data.
	 * @param column
	 */
	
	public void setClass(int column){
	
		classification = (String) dataHeaders.get(column);
		dataHeaders.remove(column);
		//dataTypes.add(0, classification);
		for(int i = 0; i < dataPoints.size(); i++){
			dataPoints.get(i).setClass(column);
		}
	}
	
	/**
	 * Sets the classification of the data list.
	 * @param classification
	 */
	
	public void setClass(String classification){
		this.classification = classification;
	}
	
	/**
	 * Returns the header of the specified column.
	 * @param column
	 * @return
	 */
	
	public Object getHead(int column){
		return dataHeaders.get(column);
	}
	
	/**
	 * Returns the number of columns.
	 * @return
	 */
	
	public int getLength(){
		return dataHeaders.size();
	}
	
	/**
	 * Creates an attribute from the specified column.
	 * @param column
	 * @return
	 */

	private Attribute createAttribute(int column){
		
		if(column > dataHeaders.size()) throw new ArrayIndexOutOfBoundsException();
		String name = (String)dataHeaders.get(column);
		if(getType(column).equals("Categorial")){
			DMArrayList<String> colVals = new DMArrayList<String>();
			for(int i = 0; i < dataPoints.size(); i++){
				colVals.add(((String)dataPoints.get(i).getDataVal(column)));
			}
			return new Attribute(name, "Categorial", colVals);
		}
		
		else if(getType(column).equals("Numeric")){
			DMArrayList<Double> colVals = new DMArrayList<Double>();
			for(int i = 0; i < dataPoints.size(); i++){
				colVals.add(Double.parseDouble((String)dataPoints.get(i).getDataVal(column)));
			}
			return new Attribute(name, "Numeric", colVals);
		}
		else return null;	
	}
	
	/**
	 * Returns an iterator for the data types.
	 * @return
	 */
	
	public Iterator<Object> dataTypeIterator(){
		return dataHeaders.iterator();
	}
	
	/**
	 * Returns an iterator for the data points.
	 * @return
	 */
	
	public Iterator<DataPoint> dataPointsIterator(){
		return dataPoints.iterator();
	}
	
	/**
	 * Returns an iterator for the data attributes.
	 * @return
	 */
	
	public Iterator<Attribute> dataAttributesIterator(){
		return dataAttributes.iterator();
	}
	
	/**
	 * Writes the current list to a csv file with headers followed by data
	 * @param filename
	 */
	
	public void writeToCSV(String filename){
		String NEW_LINE = "\n";
		try {
			FileWriter fileWriter = new FileWriter(filename);
			fileWriter.append(dataHeaders.toString());
			fileWriter.append(NEW_LINE);
			fileWriter.append(dataPoints.toStringObj());
			fileWriter.flush();
            fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			        		
	}
	
	/**
	 * Writes the current attributes to a csv file.
	 * Headers contain attribute information.
	 * @param filename
	 */
	
	public void attributeToCsv(String filename){
		String NEW_LINE = "\n";
		try{
			FileWriter fileWriter = new FileWriter(filename);
			fileWriter.append("Name,Type,Unique Values,Minimum,Maximum,Mean,StdDev");
			fileWriter.append(NEW_LINE);
			fileWriter.append(dataAttributes.toStringObj());
			fileWriter.flush();
			fileWriter.close();
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns an object array containing two new dataLists.
	 * The first datalist is the training set.
	 * The second datalist is the test set.
	 * The sets are shuffled to select every other value.
	 * @return
	 */
	
	public Object[] everyOther(){
		DataList training = new DataList();
		DataList test = new DataList();
		for(int i = 0; i < dataPoints.size(); i++){
			if((i & 1 ) == 0){
				training.addDataPoint(dataPoints.get(i));
			}
			else{
				test.addDataPoint(dataPoints.get(i));
			}
		}
		training.setHeaders(this.getHeaders());
		test.setHeaders(this.getHeaders());
		training.setClass(this.getClassification());
		test.setClass(this.getClassification());
		return (new Object[] {training,test});
	}
	
	/**
	 * Returns an object array containing two new dataLists.
	 * The first dataList is the training set.
	 * The second dataList is the test set.
	 * The sets are shuffled randomly, then split by the specified percent.
	 * @param percent
	 * @return
	 */
	
	public Object[] randomShuffle(float percent){
		DataList training = new DataList();
		DataList test = new DataList();
		DMArrayList<DataPoint> grabBag = dataPoints;
		grabBag.shuffle();
		int size = grabBag.size();
		int trainCount = (int)percent*size;
		trainCount = trainCount/100;
		int testCount = grabBag.size() - trainCount;
		for(int i = 0; i < trainCount; i++){
			training.addDataPoint(grabBag.get(0));
			grabBag.remove(0);
		}
		for(int i = 0; i< testCount; i++){
			test.addDataPoint(grabBag.get(0));
			grabBag.remove(0);
		}
		training.setHeaders(this.getHeaders());
		test.setHeaders(this.getHeaders());
		training.resetAttributes();
		test.resetAttributes();
		training.setClass(this.getClassification());
		test.setClass(this.getClassification());
		return (new Object[] {training,test});
	}
}
