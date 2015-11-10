package main;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import com.opencsv.CSVReader;


public class DataList {
	
	ArrayList<DataPoint> dataList;
	ArrayList<Object> dataTypes;
	
	public void readFile(){	
			
		try{
			String csvFile = "blank.csv";
			CSVReader csvReader = new CSVReader(new FileReader(csvFile));
			Object[] row = csvReader.readNext();
			for(int i =0; i <= row.length; i++){
				dataTypes.add(row[i]);
			}
			while((row = csvReader.readNext()) != null) {
				dataList.add(new DataPoint(new ArrayList<Object>(Arrays.asList(row))));
			}
			csvReader.close();
		}
		catch(Exception e){
			e.printStackTrace();}
							
	}
	
	public DataPoint getVal(int column){
		
		return dataList.get(column);
		
	}
	
	public void setClass(int column){
	
		dataTypes.remove(column);
		dataTypes.add(0, "Classification");
		for(int i = 0; i <= dataList.size(); i++){
			dataList.get(i).setClass(column);
		}
		
	}
}