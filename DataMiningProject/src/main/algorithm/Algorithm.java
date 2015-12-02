package main.algorithm;

import java.util.Vector;

import main.node.Node;
import main.structure.DataList;
import main.structure.DataPoint;
import main.structure.FrequencyTable;

/**
 * This class is the base class for each decision tree algorithm to extend from.
 * This will manage the DataList and can create empty frequency tables from one attribute.
 * @author Dan Martin
 */

public abstract class Algorithm {
	protected DataList dataList;
	protected int currentDataPoint;
	protected int numBins;
	
	/**
	 * Constructs an Algorithm object, sets the default number of bins for numerical data to 10.
	 * @param dataList This is the dataList object that the Algorithm will use and manage.
	 */
	Algorithm(DataList dataList){
		this.dataList = dataList;
		currentDataPoint = 0;
		numBins = 10;
	}
	/**
	 * Constructs an Algorithm object, sets the default number of bins for numerical data to 10.
	 */
	Algorithm(){
		currentDataPoint = 0;
		numBins = 10;
	}
	/**
	 * Sets the Algorithms dataList.
	 * @param dataList DataList containing all data.
	 */
	public void setDataList(DataList dataList){
		this.dataList = dataList;
		currentDataPoint = 0;
	}
	/**
	 * This method must be implemented in a child class.
	 * @return Returns a Nominal, or Ordinal decision node that is created by a child algorithm class.
	 */
	public abstract Node getBestNode();
	
	/**
	 * @return the next DataPoint in the dataList.
	 */
	public DataPoint getNextDataPoint() {
		currentDataPoint++;
		if(dataList.getNumRows() >= currentDataPoint - 1){
			return dataList.getRow(currentDataPoint - 1);
		}
		else{
			return dataList.getRow(dataList.getLength() - 1);
		}
	}
	/**
	 * Removes the current DataPoint from the DataList.
	 * @return the removed DataPoint.
	 */
	public DataPoint removeDataPoint() {
		DataPoint temp = dataList.getRow(currentDataPoint);
		dataList.removeRow(currentDataPoint);
		return temp;
	}
	/**
	 * Resets the DataList so that it will start returning DataPoints from the start again.
	 */
	public void resetDataList() {
		currentDataPoint = 0;
	}
	/**
	 * Checks if the currentDataPoint is a valid value in the list.
	 * If this returns false, use resetDataList() to reset the currentDataPoint to 0.
	 * @return true/false if the currentDataPoint is a valid value in the DataList
	 */
	public boolean hasData(){
		return dataList.getNumRows() > currentDataPoint;
	}
	/**
	 * Sets the number of bins that numerical data will be split into for classification.
	 * @param b must be greater than 0.
	 */
	public void setNumBins(int b){
		if(b > 0){
			numBins = b;
		}
		else{
			System.out.println("Num bins too low. Setting num bins to 10");
			numBins = 10;
		}
	}
	/**
	 * Creates an empty FrequencyTable from one column in the DataList.
	 * @param col the column number in the DataList that will be used to generate the table.
	 * @return the FrequencyTable generated from the column.
	 */
	protected FrequencyTable getFrequencyTable(int col){
		FrequencyTable table = new FrequencyTable();
		Vector<Object> a = new Vector<Object>();
		Vector<String> t = new Vector<String>();
		
		boolean isString = false;
		try{
			Float.parseFloat((String) dataList.getRow(0).getDataVal(col));
		}
		catch(NumberFormatException e)
		{
		  isString = true;
		}
		if(isString){
			for(int j = 0; j < dataList.getNumRows(); j++){
				if(!t.contains(((DataPoint)dataList.getRow(j)).getClassification())){
					t.add(((DataPoint)dataList.getRow(j)).getClassification());
				}
				if(!a.contains(dataList.getRow(j).getDataVal(col))){
					a.add(dataList.getRow(j).getDataVal(col));
				}
			}
			table.setTypes(t);
			table.setAttributes(a);
		}
		else{
			for(int j = 0; j < dataList.getNumRows(); j++){
				try{
					if(!t.contains(((DataPoint)dataList.getRow(j)).getClassification())){
						t.add(((DataPoint)dataList.getRow(j)).getClassification());
					}
				}
				catch(Exception e){
					
				}
			}
			for(int i = 0; i < numBins; i++){
				a.add(i);
			}
			table.setTypes(t);
			table.setAttributes(a);
		}
		return table;
	}
}
