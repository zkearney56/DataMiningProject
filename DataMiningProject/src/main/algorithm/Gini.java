package main.algorithm;

import main.algorithm.*;
import main.node.Node;
import main.structure.DataList;

public class Gini extends Algorithm{

	
	Gini(DataList dataList){
		super(dataList);
	}
	Gini(){
		super();
	}
	@Override
	public Node getBestNode() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private int getBestColumn(){
		float GiniValue[] = new float[dataList.getLength()];
		for(int i = 0; i < GiniValue.length; i++){
			
		}
		return currentDataPoint;
	}

}
