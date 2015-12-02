package main.node;

import main.algorithm.Algorithm;
import main.structure.DMArrayList;
import main.structure.DataList;
import main.structure.DataPoint;

/**
 * This class implements the Node interface, and is used as the leaf nodes for the tree.
 * When data is classified in the tree it will always end up going to a leaf. If the tree
 * is being trained then the leaf will keep a record of what data it has received, and will
 * create a new decision node from the data if some item has been incorrectly classified.
 * 
 * @author Dan Martin
 */
public class Leaf implements Node {

	String classification;
	
	Node left;
	Node right;
	DataList list;
	Algorithm algorithm;
	
	/**
	 * Creates a new leaf with some classification string, and gives it an algorithm to use
	 * when making new nodes.
	 * @param classification the classification of the data that makes it to the leaf.
	 * @param a the algorithm that will be used to construct a new node.
	 */
	public Leaf(String classification, Algorithm a){
		this.classification = classification;
		list = new DataList();
		algorithm = a;
	}

	/**
	 * Accepts a dataPoint.
	 * @param a dataPoint that is being classified.
	 * @return the classification that it thinks that dataPoint is.
	 */
	public String acceptData(DataPoint dataPoint){
		return classification;
	}

	/**
	 * Accepts a dataPoint, and adds it to its dataList.
	 * @param a dataPoint that is being classified.
	 * @return the classification that it thinks that dataPoint is.
	 */
	public String trainData(DataPoint dataPoint){
		list.addDataPoint(dataPoint);
		return classification;
	}
	

	/**
	 * A leaf Node should not need to do any tests on the data, but it is required from the partent Node class.
	 * @return false.
	 */
	public boolean testData(DataPoint dataPoint) {
		return false;
	}

	/**
	 * Checks if all of the dataPoints it has collected match its own classification.
	 * If they don't then the node should not be a leaf and needs to create a new decision node.
	 * @return t/f if the node should be a leaf.
	 */
	public boolean isLeaf(){
		for(int i = 0; i < list.getNumRows(); i++){
			if(!list.getRow(i).getClassification().equals(classification)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Sets the headers of the contained dataList.
	 * @param dataTypes a DMArrayList of the header values.
	 */
	public void setHeaders(DMArrayList<Object> dataTypes){
		list.setHeaders(dataTypes);	
	}
	
	/**
	 * Creates a new Decision Node using the data it has collected, and the algorithm it was constructed with.
	 * It will also check if all of the data it has collected is of the same type. If it is then it will return
	 * a new leaf with that classification to take its place instead of a decision node.
	 * @return a new Decision Node made from the leafs dataList. 
	 */
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
	/**
	 * @return the size of the dataList.
	 */
	public int getListSize(){
		return list.getNumRows();
	}
	/**
	 * The method used to print this node.
	 * @return the leafs classification in parentheses.
	 */
	public String toString(){
		return "(" + classification + ")"; 
	}
	

	/**
	 * This method is not needed for a leaf.
	 * @return null.
	 */
	public Node getLeft() {
		return null;
	}
	/**
	 * This method is not needed for a leaf.
	 * @return null.
	 */
	public Node getRight() {
		return null;
	}	
	/**
	 * This method is not needed for a leaf and does nothing.
	 */
	public void setLeft(Node left){}
	/**
	 * This method is not needed for a leaf and does nothing.
	 */
	public void setRight(Node right){}
	
	/**
	 * Sets the classification of the node to whatever string is passed to it.
	 * @param s the new classification of the leaf.
	 */
	public void setClass(String s){
		classification = s;
	}
	/**
	 * This is not needed for a leaf and does nothing.
	 * @return false
	 */
	public Object getSplitValue(){
		return false;
	}
	/**
	 * This is not needed for a leaf and does nothing.
	 * @return 0
	 */
	public int getDataIndex(){
		return 0;
	}
}
