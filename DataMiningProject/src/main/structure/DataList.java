package main.structure;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.opencsv.CSVReader;


public class DataList {
	
	private DMArrayList<DataPoint> dataPoints;
	private DMArrayList<Object> dataTypes;
	private DMArrayList<DataPoint> training;
	private DMArrayList<DataPoint> test;
	private String classification = "";
	
	public DataList(){
		dataPoints = new DMArrayList<DataPoint>();
		dataTypes = new DMArrayList<Object>();
		training = new DMArrayList<DataPoint>();
		test = new DMArrayList<DataPoint>();
	}
	
	public DMArrayList<Object> getHeaders(){
		return dataTypes;
	}
	
	public DMArrayList<DataPoint> getPoints(){
		return dataPoints;
	}
	
	public void setHeaders(DMArrayList<Object> dataTypes){
		this.dataTypes = dataTypes;	
	}
	
	public void setData(DMArrayList<DataPoint> dataPoints){
		this.dataPoints = dataPoints;
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
				dataPoints.add(new DataPoint(new DMArrayList<Object>(row)));
			}
				}	
			csvReader.close();
		}
		catch(Exception e){
			e.printStackTrace();}
							
	}
	
	public void declareClass(){
		
	}
	
	public DataPoint getRow(int column){
		
		return dataPoints.get(column);
		
	}
	
	public Object getRowColVal(int row, int column){
		
		return dataPoints.get(row).getDataVal(column);
		
	}
	
	public void removeRow(int row){
		
		dataPoints.remove(row);
		
	}
	
	public void removeColumn(int column){
		
		dataTypes.remove(column);
		for(int i = 0; i < dataPoints.size(); i++){
			dataPoints.get(i).removeData(column);
		}
	}
	
	public void setClass(int column){
	
		classification = (String) dataTypes.get(column);
		dataTypes.remove(column);
		dataTypes.add(0, classification);
		for(int i = 0; i < dataPoints.size(); i++){
			dataPoints.get(i).setClass(column);
		}
	}
	
	public Object getHead(int column){
		return dataTypes.get(column);
	}
	
	public int getLength(){
		return dataTypes.size();
	}
	
	public int getNumRows(){
		return dataPoints.size();
	}
	
	public Iterator<Object> dataTypeIterator(){
		return dataTypes.iterator();
	}
	
	public Iterator<DataPoint> dataPointsIterator(){
		return dataPoints.iterator();
	}
	
	/**
	 * Returns training set at [0] and test set at [1];
	 * @param folds
	 * @return
	 */
	public Object[] nFoldCrossValid(int folds){
		return null;
	}	
	
	public void clearTestSets(){
		training.clear();
		test.clear();
	}
	
	public void everyOther(){
		clearTestSets();
		for(int i = 0; i < dataPoints.size(); i++){
			if((i & 1 ) == 0){
				training.add(dataPoints.get(i));
			}
			else{
				test.add(dataPoints.get(i));
			}
		}
	}
	
	public Object[] randomShuffle(int percent){
		return null;
	}
}
