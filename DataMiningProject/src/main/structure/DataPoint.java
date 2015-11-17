package main.structure;
import java.util.ArrayList;


public class DataPoint {
	
	private ArrayList<Object> data;
	private String classification;

	public DataPoint(ArrayList <Object> data){
		
		classification = null;
		this.data = data;
		
	}
	
	public String getClassification(){
		
	return classification;
	
	}
	
	public ArrayList<Object> getData(){
		return data;
	}
	
	public void removeData(int column){
		data.remove(column);
	}
	
	public Object getDataVal(int val){
		
		return data.get(val);
		
	}
	
	public void setClass(int column){
		
		classification = (String) data.get(column);
		data.remove(column);
		data.add(0, classification);
		
	}
}
