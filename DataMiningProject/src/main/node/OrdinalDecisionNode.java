package main.node;

import java.util.ArrayList;

import main.DataPoint;

public class OrdinalDecisionNode implements Node {
	Node left;
	Node right;
	
	float breakValue;
	
	public OrdinalDecisionNode(float breakValue){
		this.breakValue = breakValue;
		left = new Leaf("NULLVALUE");
		right = new Leaf("NULLVALUE");
	}
	
	//THIS IS BROKEN
	@Override
	public String acceptData(DataPoint dataPoint) {
		if(testData(dataPoint)){
			if(right.acceptData(dataPoint).equals((String)dataPoint.getClassification())){
				return right.acceptData(dataPoint);
			}
			else{
				if(right instanceof Leaf){
					Node temp = right;
					right = new OrdinalDecisionNode((float)dataPoint.getDataVal(0));
					right.setLeft(temp);
					right.setRight(new Leaf((String) dataPoint.getClassification()));
					return right.acceptData(dataPoint);
				}
				else{
					return right.acceptData(dataPoint);
				}
			}
		}
		else{
			if(left instanceof Leaf){
				Node temp = left;
				left = new OrdinalDecisionNode((float)dataPoint.getDataVal(0));
				left.setRight(new Leaf((String)dataPoint.getClassification()));
				left.setLeft(temp);
			}
			return left.acceptData(dataPoint);
		}
	}
	@Override
	public boolean testData(DataPoint dataPoint) {
		return (float)dataPoint.getDataVal(0) >= breakValue;
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
