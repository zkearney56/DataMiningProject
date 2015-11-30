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
	 * Accepts an arraylist containing all data in a row
	 * @param data
	 */
	public DataPoint(DMArrayList <Object> data){
		
		classification = null;
		this.data = data;
		
	}
	
	public String getClassification(){
		
	return classification;
	
	}
	
	public String getType(int column){
		try  
		  {  
		    double d = Double.parseDouble((String) data.get(column));  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return "Categorial";  
		  }  
		  return "Numeric";  
	}
	
	public DMArrayList<Object> getData(){
		return data;
	}
	
	public void removeData(int column){
		data.remove(column);
	}
	
	public Object getDataVal(int column){
		
		return data.get(column);
		
	}
	
	public void setClass(int column){
		
		classification = (String) data.get(column);
		data.remove(column);
		//data.add(0, classification);
		
	}
}
