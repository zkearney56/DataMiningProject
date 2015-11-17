package main.structure;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import com.opencsv.CSVReader;


public class DataList {
	
	ArrayList<DataPoint> dataList;
	ArrayList<Object> dataTypes;
	
	public DataList(){
		dataList = new ArrayList<DataPoint>();
		dataTypes = new ArrayList<Object>();
	}
	
	public void readFile(File file){	
			System.out.println(file.getAbsolutePath());
		try{
			CSVReader csvReader = new CSVReader(new FileReader(file));
			String[] row;
			boolean header = false;
			while((row = csvReader.readNext()) != null) {
				if(!header){
					for(int i = 0; i < row.length; i++){					
					System.out.println(row[i]);
					dataTypes.add(row[i]);					
					}
					header = true;
				}				
					else{				
				dataList.add(new DataPoint(new ArrayList<Object>(Arrays.asList(row))));
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
