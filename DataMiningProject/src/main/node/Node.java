package main.node;

import main.structure.DataPoint;
/**
 * The base node interface that each node should inherit from.
 * @author Dan Martin
 *
 */
public interface Node {
	String acceptData(DataPoint dataPoint);
	String trainData(DataPoint dataPoint);
	boolean testData(DataPoint dataPoint);
	Node getLeft();
	Node getRight();
	void setRight(Node right);
	void setLeft(Node left);
	int getDataIndex();
	Object getSplitValue();
}
