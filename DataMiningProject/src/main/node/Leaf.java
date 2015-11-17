package main.node;

import main.structure.DataList;
import main.structure.DataPoint;

public class Leaf implements Node {

	String classification;
	
	Node left;
	Node right;
	
	public Leaf(String classification){
		this.classification = classification;
	}
	
	
	@Override
	public String acceptData(DataPoint dataPoint){
		return classification;
	}

	@Override
	public boolean testData(DataPoint dataPoint) {
		return false;
	}

	@Override
	public Node getLeft() {
		return null;
	}

	@Override
	public Node getRight() {
		return null;
	}
	
	public void setLeft(Node left){
		this.left = left;
	}
	
	public void setRight(Node right){
		this.right = right;
	}
	
	public void setClass(String s){
		classification = s;
	}
	public String toString(){
		return "(" + classification + ")"; 
	}


	@Override
	public Node getResultNode(DataPoint dataPoint) {
		return this;
	}
}
