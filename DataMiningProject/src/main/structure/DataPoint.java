package main.structure;
import java.util.ArrayList;


public class DataPoint {
	
	private DMArrayList<Object> data;
	private String classification;

	public DataPoint(DMArrayList <Object> data){
		
		classification = null;
		this.data = data;
		
	}
	
	public String getClassification(){
		
	return classification;
	
	}
	
	public DMArrayList<Object> getData(){
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
