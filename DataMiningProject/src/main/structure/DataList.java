package main.structure;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import com.opencsv.CSVReader;


public class DataList {
	
	DMArrayList<DataPoint> dataList;
	DMArrayList<Object> dataTypes;
	
	public DataList(){
		dataList = new DMArrayList<DataPoint>();
		dataTypes = new DMArrayList<Object>();
	}
	
	public void readFile(File file){	
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
				dataList.add(new DataPoint(new DMArrayList<Object>(row)));
			}
				}	
			csvReader.close();
		}
		catch(Exception e){
			e.printStackTrace();}
							
	}
	
	public DataPoint getRow(int column){
		
		return dataList.get(column);
		
	}
	
	public Object getRowColVal(int row, int column){
		
		return dataList.get(row).getDataVal(column);
		
	}
	
	public void removeRow(int row){
		
		dataList.remove(row);
		
	}
	
	public void removeColumn(int column){
		
		dataTypes.remove(column);
		for(int i = 0; i <= dataList.size(); i++){
			dataList.get(i).removeData(column);
		}
	}
	
	public void setClass(int column){
	
		dataTypes.remove(column);
		dataTypes.add(0, "Classification");
		for(int i = 0; i <= dataList.size(); i++){
			dataList.get(i).setClass(column);
		}
		
	}
	
	public Object getHead(int column){
		return dataTypes.get(column);
	}
	
	public int getLength(){
		return dataTypes.size();
	}
	
	public int getNumRows(){
		return dataList.size();
	}
}
