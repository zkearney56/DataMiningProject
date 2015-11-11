package main.node;

import java.util.ArrayList;

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
	public String acceptData(Object dataPoint) {
		if(testData(dataPoint)){
			if(right.acceptData(dataPoint).equals((String)((ArrayList) dataPoint).get(1))){
				return right.acceptData(dataPoint);
			}
			else{
				if(right instanceof Leaf){
					Node temp = right;
					right = new OrdinalDecisionNode((float) ((ArrayList) dataPoint).get(0));
					right.setLeft(temp);
					right.setRight(new Leaf((String)((ArrayList) dataPoint).get(1)));
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
				left = new OrdinalDecisionNode((float)((ArrayList) dataPoint).get(0));
				left.setRight(new Leaf((String)((ArrayList) dataPoint).get(1)));
				left.setLeft(temp);
			}
			return left.acceptData(dataPoint);
		}
	}
	@Override
	public boolean testData(Object dataPoint) {
		return (float)((ArrayList) dataPoint).get(0) >= breakValue;
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
