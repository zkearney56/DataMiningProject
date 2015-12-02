package main.algorithm;

import java.util.Vector;

import main.node.Node;
import main.structure.DataList;
import main.structure.DataPoint;
import main.structure.FrequencyTable;

public abstract class Algorithm {
	
	protected DataList dataList;
	protected int currentDataPoint;
	protected int numBins;
	
	Algorithm(DataList dataList){
		this.dataList = dataList;
		currentDataPoint = 0;
		numBins = 10;
	}
	Algorithm(){
		currentDataPoint = 0;
		numBins = 10;
	}
	
	public void setDataList(DataList dataList){
		this.dataList = dataList;
		currentDataPoint = 0;
	}
	public abstract Node getBestNode();
	
	public DataPoint getNextDataPoint() {
		currentDataPoint++;
		if(dataList.getNumRows() >= currentDataPoint - 1){
			return dataList.getRow(currentDataPoint - 1);
		}
		else{
			return dataList.getRow(dataList.getLength() - 1);
		}
	}
	public DataPoint removeDataPoint() {
		DataPoint temp = dataList.getRow(currentDataPoint);
		dataList.removeRow(currentDataPoint);
		return temp;
	}
	public void resetDataList() {
		currentDataPoint = 0;
	}
	public boolean hasData(){
		return dataList.getNumRows() > currentDataPoint;
	}
	public void setNumBins(int b){
		if(b > 0){
			numBins = b;
		}
		else{
			System.out.println("Num bins too low. Setting num bins to 10");
			numBins = 10;
		}
	}
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
			//This might mess stuff up because float
			/*float sum = 0;
			for(int i = 0; i < dataList.getNumRows(); i++){
				sum = sum + (float)dataList.getRow(i).getDataVal(col);
			}
			sum = sum/numBins;*/
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
