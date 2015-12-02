package main.node;

import main.algorithm.Algorithm;
import main.structure.DMArrayList;
import main.structure.DataList;
import main.structure.DataPoint;

public class Leaf implements Node {

	String classification;
	
	Node left;
	Node right;
	DataList list;
	Algorithm algorithm;
	
	public Leaf(String classification, Algorithm a){
		this.classification = classification;
		list = new DataList();
		algorithm = a;
	}
	@Override
	public String acceptData(DataPoint dataPoint){
		list.addDataPoint(dataPoint);
		return classification;
	}

	@Override
	public boolean testData(DataPoint dataPoint) {
		return false;
	}

	public boolean isLeaf(){
		for(int i = 0; i < list.getNumRows(); i++){
			if(!list.getRow(i).getClassification().equals(classification)){
				return false;
			}
		}
		return true;
	}
	
	public void setHeaders(DMArrayList<Object> dataTypes){
		list.setHeaders(dataTypes);	
	}
	
	public Node makeNewDecisionNode(){
		String temp = "";
		temp = list.getRow(0).getClassification();
		for(int i = 1; i < list.getNumRows(); i++){
			if(!(list.getRow(i).getClassification().equals(temp))){
				algorithm.setDataList(list);
				return algorithm.getBestNode();
			}
		}
		Leaf l = new Leaf(list.getRow(0).getClassification(), algorithm);
		return l;
	}
	public int getListSize(){
		return list.getNumRows();
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

	public Object getSplitValue(){
		return false;
	}
	public int getDataIndex(){
		return 0;
	}
	@Override
	public Node getResultNode(DataPoint dataPoint) {
		return this;
	}
}
