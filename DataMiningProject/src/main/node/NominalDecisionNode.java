package main.node;

import java.util.ArrayList;

import main.structure.DataList;
import main.structure.DataPoint;

public class NominalDecisionNode implements Node {
	Node left;
	Node right;
	
	String breakString;
	int dataIndex;
	public NominalDecisionNode(String breakValue, int dataIndex){
		this.breakString = breakValue;
		this.dataIndex = dataIndex;
		left = new Leaf("NULLVALUE");
		right = new Leaf("NULLVALUE");
	}
	
	public Object getSplitValue(){
		return breakString;
	}
	public int getDataIndex(){
		return dataIndex;
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
		return dataPoint.getDataVal(dataIndex).equals(breakString);
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
		return "(x = " + breakString + ")"; 
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
	
	public void setBreakString(String newValue){
		breakString = newValue;
	}
}
