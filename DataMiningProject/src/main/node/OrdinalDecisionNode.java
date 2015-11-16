package main.node;

import java.util.ArrayList;

import main.DataPoint;

public class OrdinalDecisionNode implements Node {
	Node left;
	Node right;
	
	float breakValue;
	int dataIndex;
	public OrdinalDecisionNode(float breakValue, int dataIndex){
		this.breakValue = breakValue;
		this.dataIndex = dataIndex;
		left = new Leaf("NULLVALUE");
		right = new Leaf("NULLVALUE");
	}
	
	@Override
	public String acceptData(DataPoint dataPoint) {
		if(testData(dataPoint)){
			return right.acceptData(dataPoint);
		}
		else{
			return left.acceptData(dataPoint);
		}
	}
	@Override
	public boolean testData(DataPoint dataPoint) {
		return (float)dataPoint.getDataVal(dataIndex) >= breakValue;
	}
	
	
	public Node getResultNode(DataPoint dataPoint) {
		if(testData(dataPoint)){
			return right;
		}
		else{
			return left;
		}
	}
	
	
	public String toString(){
		return "(x >= " + Float.toString(breakValue) + ")"; 
	}
	@Override
	public Node getLeft() {
		return left;
	}
	@Override
	public Node getRight() {
		return right;
	}
	
	public void setLeft(Node left){
		this.left = left;
	}
	
	public void setRight(Node right){
		this.right = right;
	}
	
	public void setBreakValue(int newValue){
		breakValue = newValue;
	}
}
