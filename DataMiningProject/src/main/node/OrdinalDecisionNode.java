package main.node;

import main.structure.DataPoint;

public class OrdinalDecisionNode implements Node {
	Node left;
	Node right;
	
	float breakValue;
	int dataIndex;
	String attributeName;
	public OrdinalDecisionNode(float breakValue, int dataIndex, String attribute){
		this.breakValue = breakValue;
		this.dataIndex = dataIndex;
		attributeName = attribute;
	}
	
	public Object getSplitValue(){
		return breakValue;
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
		return Float.parseFloat((String) dataPoint.getDataVal(dataIndex)) <= breakValue;
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
		return "(" + attributeName + " >= " + Float.toString(breakValue) + ")"; 
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
	
	public void setBreakValue(float newValue){
		breakValue = newValue;
	}
}
