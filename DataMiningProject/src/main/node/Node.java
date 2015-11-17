package main.node;

import main.structure.DataList;
import main.structure.DataPoint;

public interface Node {
	String acceptData(DataPoint dataPoint);
	boolean testData(DataPoint dataPoint);
	Node getLeft();
	Node getRight();
	void setRight(Node right);
	void setLeft(Node left);
	Node getResultNode(DataPoint dataPoint);
}
