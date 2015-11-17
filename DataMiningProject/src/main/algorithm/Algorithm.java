package main.algorithm;

import main.structure.DataList;
import main.structure.DataPoint;
import main.node.Node;

public abstract class Algorithm {
	protected DataList dataList;
	protected int currentDataPoint;
	protected int numBins;
	void Entropy(DataList dataList){
		this.dataList = dataList;
		currentDataPoint = 0;
		numBins = 10;
	}
	
	public abstract Node getBestNode();
	
	public DataPoint getNextDataPoint() {
		currentDataPoint++;
		if(dataList.getLength() >= currentDataPoint){
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
		return !(dataList.getLength() == 0);
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
}
