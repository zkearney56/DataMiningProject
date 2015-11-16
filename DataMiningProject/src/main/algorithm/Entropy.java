package main.algorithm;

import main.DataList;
import main.DataPoint;
import main.node.Node;

public class Entropy implements Algorithm{

	DataList dataList;
	int currentDataPoint;
	Entropy(DataList dataList){
		this.dataList = dataList;
		currentDataPoint = 0;
	}
	@Override
	public Node getBestNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataPoint getNextDataPoint() {
		currentDataPoint++;
		if(dataList.getLength() >= currentDataPoint){
			return dataList.getRow(currentDataPoint - 1);
		}
		else{
			return dataList.getRow(dataList.getLength() - 1);
		}
	}

	@Override
	public DataPoint removeDataPoint() {
		DataPoint temp = dataList.getRow(currentDataPoint);
		dataList.removeRow(currentDataPoint);
		return temp;
	}

	@Override
	public void resetDataList() {
		currentDataPoint = 0;
	}
	
	public boolean hasData(){
		return !(dataList.getLength() == 0);
	}
}
