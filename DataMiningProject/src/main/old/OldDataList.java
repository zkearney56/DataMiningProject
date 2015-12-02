package main.old;
/**
 * Author: Zachary Kearney
 * Last Edited: 11/30/2015
 */
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

import com.opencsv.CSVReader;

import main.structure.Attribute;
import main.structure.DMArrayList;
import main.structure.DataPoint;

@Deprecated
public class OldDataList {
	
	private DMArrayList<DataPoint> dataPoints;
	private DMArrayList<Object> dataTypes;
	private DMArrayList<Attribute> dataAttributes;
	private String classification = "";
	
	public OldDataList(){
		dataPoints = new DMArrayList<DataPoint>();
		dataTypes = new DMArrayList<Object>();
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
	
	public void addDataPoint(DataPoint point){
		dataPoints.add(point);
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
	
	public void trimList(int num){
		dataPoints.trim(num);
	}
	
	public int getDataSize()
	{
	return dataPoints.size();
	}
	
	public void declareClass(){
		
	}
	
	public DataPoint getRow(int column){
		
		return dataPoints.get(column);
		
	}
	
	public Object getRowColVal(int row, int column){
		
		return dataPoints.get(row).getDataVal(column);
		
	}
	
	public String getType(int column){
		return dataPoints.get(0).getType(column);
	}
	
	public void removeRow(int row){
		
		dataPoints.remove(row);
		
	}
	
	/**public Attribute getAttribute(int column){
		
		if(column > dataTypes.size()) throw new ArrayIndexOutOfBoundsException();
		String name = (String)dataTypes.get(column);
		if(getType(column).equals("Categorial")){
			//return new Attribute(name, "Categorial");
		}
		
		else if(getType(column).equals("Numeric")){
			DMArrayList<Double> colVals = new DMArrayList<Double>();
			for(int i = 0; i < dataPoints.size(); i++){
				colVals.add(Double.parseDouble((String)dataPoints.get(i).getDataVal(column)));
			}
			double min = MathFunctions.min(colVals);
			double max = MathFunctions.max(colVals);
			double mean = MathFunctions.mean(colVals);
			double stdDev = MathFunctions.stdDev(mean, colVals);
			return new Attribute(name, "Numeric", min, max, mean, stdDev);
		}
		else return null;
		
	}
*/
	public void removeColumn(int column){
		
		dataTypes.remove(column);
		for(int i = 0; i < dataPoints.size(); i++){
			dataPoints.get(i).removeData(column);
		}
	}
	
	public void setClass(int column){
	
		classification = (String) dataTypes.get(column);
		dataTypes.remove(column);
		//dataTypes.add(0, classification);
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
	
	/**public void clearTestSets(){
		training.clear();
		test.clear();
	}
	*/
	
	public Object[] everyOther(){
		OldDataList training = new OldDataList();
		OldDataList test = new OldDataList();
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
	
	public Object[] randomShuffle(float percent){
		OldDataList training = new OldDataList();
		OldDataList test = new OldDataList();
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
		return (new Object[] {training,test});
	}
	
	/**public void randomShuffle(float percent){
		clearTestSets();
		DMArrayList<DataPoint> grabBag = dataPoints;
		grabBag.shuffle(100);
		int size = grabBag.size();
		percent = percent/100;
		int trainCount = (int)(percent*size);
		int testCount = grabBag.size() - trainCount;
		for(int i = 0; i < trainCount; i++){
			training.add(grabBag.get(0));
			grabBag.remove(0);
		}
		for(int i = 0; i< testCount; i++){
			test.add(grabBag.get(0));
			grabBag.remove(0);
		}
	}
	*/
}
