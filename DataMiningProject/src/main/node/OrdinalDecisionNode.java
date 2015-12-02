package main.node;

import main.structure.DataPoint;

/**
 * This class implements the Node interface and is made to deal with any Numeric data.
 * Both types of decision nodes are intended to accept some data and make a decision that
 * will help classify the data.
 * 
 * @author Dan Martin
 *
 */
public class OrdinalDecisionNode implements Node {
	Node left;
	Node right;
	
	float breakValue;
	int dataIndex;
	String attributeName;
	
	/**
	 * This constructs an OrdinalDecisionNode that will make a decision using the dataIndex and breakValue.
	 * @param breakValue if a dataPoint has this value at the correct index then it will route the data right.
	 * @param dataIndex this is the index that the node will look to compare the breakValue to.
	 * @param attribute this is the name of the attribute that the node will be making decisions with.
	 */
	public OrdinalDecisionNode(float breakValue, int dataIndex, String attribute){
		this.breakValue = breakValue;
		this.dataIndex = dataIndex;
		attributeName = attribute;
	}
	
	/**
	 * @return the value at which the node will split the data.
	 */
	public Object getSplitValue(){
		return breakValue;
	}
	/**
	 * @return the index of a dataPoint that it will look at to make comparisons.
	 */
	public int getDataIndex(){
		return dataIndex;
	}
	/**
	 * Accepts a single dataPonint and routes it left or right for classification.
	 * @return the final classification of the dataPoint.
	 */
	public String acceptData(DataPoint dataPoint) {
		if(testData(dataPoint)){
			return right.acceptData(dataPoint);
		}
		else{
			return left.acceptData(dataPoint);
		}
	}
	/**
	 * Accepts a single dataPonint and routes it left or right for classification.
	 * @return the final classification of the dataPoint.
	 */
	public String trainData(DataPoint dataPoint) {
		if(testData(dataPoint)){
			return right.trainData(dataPoint);
		}
		else{
			return left.trainData(dataPoint);
		}
	}
	
	/**
	 * Performs the test using the breakValue to check if data should be routed left or right.
	 * @return t/f if the data matches the breakValue.
	 */
	public boolean testData(DataPoint dataPoint) {
		return Float.parseFloat((String) dataPoint.getDataVal(dataIndex)) <= breakValue;
	}
	
	/**
	 * @return the nodes attribute and breakValue in parentheses.
	 */
	public String toString(){
		return "(" + attributeName + " >= " + Float.toString(breakValue) + ")"; 
	}
	/**
	 * @return the left child node.
	 */
	public Node getLeft() {
		return left;
	}
	/**
	 * @return the right child node.
	 */
	public Node getRight() {
		return right;
	}
	/**
	 * Sets the left child node to a new node.
	 * @param the node that this nodes left child will be set to.
	 */
	public void setLeft(Node left){
		this.left = left;
	}
	/**
	 * Sets the right child node to a new node.
	 * @param the node that this nodes right child will be set to.
	 */
	public void setRight(Node right){
		this.right = right;
	}
	/**
	 * Sets the breakValue to a new breakValue.
	 * @param newValue the value of the new breakValue.
	 */
	public void setBreakValue(float newValue){
		breakValue = newValue;
	}
}
