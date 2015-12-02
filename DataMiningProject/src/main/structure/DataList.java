package main.structure;

/**
 * Author: Zachary Kearney
 * Last Edited: 11/30/2015
 * Object container for all data, contains three array lists containing the headers, data rows, and column attributes.
 */

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

import com.opencsv.CSVReader;

public class DataList implements Cloneable {
	
	private DMArrayList<DataPoint> dataPoints;
	private DMArrayList<Object> dataTypes;
	private DMArrayList<Attribute> dataAttributes;
	private String classification = "";

	public DataList(){
		dataPoints = new DMArrayList<DataPoint>();
		dataTypes = new DMArrayList<Object>();
		dataAttributes = new DMArrayList<Attribute>();
	}
	
	public DataList(DMArrayList<DataPoint> dataPoints, DMArrayList<Object> dataTypes, DMArrayList<Attribute> dataAttributes){
		this.dataPoints = dataPoints;
		this.dataTypes = dataTypes;
		this.dataAttributes = dataAttributes;
	}
	
	public DataList(DataList list){
		dataPoints = new DMArrayList<DataPoint>();
		dataTypes = new DMArrayList<Object>();
		dataAttributes = new DMArrayList<Attribute>();
		for(int i = 0; i < list.getPoints().size(); i++){
			dataPoints.add(new DataPoint(list.getRow(i)));
		}
		for(int i = 0; i < list.getHeaders().size(); i++){
			dataTypes.add(list.getHead(i));
		}
		for(int i = 0; i < list.getAttributes().size(); i++){
			dataAttributes.add(new Attribute(list.getAttribute(i)));
		}
	}
	
	public DMArrayList<Object> getHeaders(){
		return dataTypes;
	}
	
	public DMArrayList<Attribute> getAttributes(){
		return dataAttributes;
	}
	
	public DMArrayList<DataPoint> getPoints(){
		return dataPoints;
	}
	
	public String getClassification(){
		return classification;
	}
	
	public void setAttributes(DMArrayList<Attribute> dataAttributes){
		this.dataAttributes = dataAttributes;
	}
	
	public void setHeaders(DMArrayList<Object> dataTypes){
		this.dataTypes = dataTypes;	
	}
	
	public void setData(DMArrayList<DataPoint> dataPoints){
		this.dataPoints = dataPoints;
	}
	
	public void resetAttributes(){
		dataAttributes.clear();
		for(int i = 0; i < dataTypes.size(); i++){
			dataAttributes.add(getAttribute(i));
		}
	}
	
	public void addDataPoint(DataPoint point){
		dataPoints.add(point);
	}
	
	/**
	 * Reads a csv file and fills the arraylists with data from the file.
	 * @param file
	 */
	public void readFile(File file){
		dataPoints.clear();
		dataTypes.clear();
		dataAttributes.clear();
			System.out.println(file.getAbsolutePath());
		try{
			CSVReader csvReader = new CSVReader(new FileReader(file));
			String[] row;
			boolean header = false;
			while((row = csvReader.readNext()) != null) {
				if(!header){
					dataTypes = new DMArrayList<Object>(row);
					header = true;
				}				
					else{				
				dataPoints.add(new DataPoint(new DMArrayList<Object>(row)));
			}
				}
			
			csvReader.close();
			for(int i = 0; i < dataTypes.size(); i++){
				dataAttributes.add(getAttribute(i));
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
	 * Returns the number of rows.
	 * @return
	 */
	public int getDataSize()
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
	 * Returns the attribute from the specified column.
	 * @param column
	 * @return
	 */
	public Attribute getAttribute(int column){
		
		if(column > dataTypes.size()) throw new ArrayIndexOutOfBoundsException();
		String name = (String)dataTypes.get(column);
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
	 * Removes the column from the data.
	 * @param column
	 */
	public void removeColumn(int column){
		
		dataTypes.remove(column);
		for(int i = 0; i < dataPoints.size(); i++){
			dataPoints.get(i).removeData(column);
		}
	}
	/**
	 * Sets the main class of the data.
	 * @param column
	 */
	public void setClass(int column){
	
		classification = (String) dataTypes.get(column);
		dataTypes.remove(column);
		//dataTypes.add(0, classification);
		for(int i = 0; i < dataPoints.size(); i++){
			dataPoints.get(i).setClass(column);
		}
	}
	
	/**
	 * Returns the header of the specified column.
	 * @param column
	 * @return
	 */
	public Object getHead(int column){
		return dataTypes.get(column);
	}
	
	/**
	 * Returns the number of columns.
	 * @return
	 */
	public int getLength(){
		return dataTypes.size();
	}
	
	/**
	 * Returns the number of rows.
	 * @return
	 */
	public int getNumRows(){
		return dataPoints.size();
	}
	
	public Iterator<Object> dataTypeIterator(){
		return dataTypes.iterator();
	}
	
	public Iterator<DataPoint> dataPointsIterator(){
		return dataPoints.iterator();
	}
	
	public Iterator<Attribute> dataAttributesIterator(){
		return dataAttributes.iterator();
	}
	
	
	/**
	 * Returns training set at [0] and test set at [1];
	 * @param folds
	 * @return
	 */
	public Object[] nFoldCrossValid(int folds){
		return null;
	}	
	
	/**public void clearTestSets(){
		training.clear();
		test.clear();
	}
	*/
	
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
		return (new Object[] {training,test});
	}
}
