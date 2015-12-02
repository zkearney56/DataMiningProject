package main.structure;
/**
 * Author: Zachary Kearney
 * Last Edited: 11/30/2015
 * Container for a row of data within an ArrayList
 */
public class DataPoint {
	
	private DMArrayList<Object> data;
	private String classification;

	/**
	 * Constructor which accepts an arraylist containing all data in a row
	 * @param data
	 */
	public DataPoint(DMArrayList <Object> data){
		
		classification = null;
		this.data = data;
		
	}
	
	/**
	 * Constructor to clone an existing DataPoint.
	 * @param point
	 */
	
	public DataPoint(DataPoint point){
		this.classification = point.classification;
		this.data = new DMArrayList<Object>(point.data);
	}
	
	/**
	 * Returns the classification of the datapoint.
	 * @return
	 */
	
	public String getClassification(){
		
	return classification;
	
	}
	
	/**
	 * Returns the type of the DataPoint.
	 * Types can either be numerical or categorical.
	 * @param column
	 * @return
	 */
	
	public String getType(int column){
		try  
		  {  
		    @SuppressWarnings("unused")
			double d = Double.parseDouble((String) data.get(column));  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return "Categorial";  
		  }  
		  return "Numeric";  
	}
	
	/**
	 * Returns all data in the row as an array list.
	 * @return
	 */
	
	public DMArrayList<Object> getData(){
		return data;
	}
	
	/**
	 * Removes a specified column from the row.
	 * @param column
	 */
	
	public void removeData(int column){
		data.remove(column);
	}
	
	/**
	 * Returns the value from a specified column.
	 * @param column
	 * @return
	 */
	
	public Object getDataVal(int column){
		
		return data.get(column);
		
	}
	
	/**
	 * Sets the classification of the DataPoint and removes the column.
	 * @param column
	 */
	
	public void setClass(int column){
		
		classification = (String) data.get(column);
		data.remove(column);
		//data.add(0, classification);
		
	}
}
