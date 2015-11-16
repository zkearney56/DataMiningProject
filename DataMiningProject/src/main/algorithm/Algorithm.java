package main.algorithm;

import main.DataList;
import main.DataPoint;
import main.node.Node;

public interface Algorithm {
	Node getBestNode();
	DataPoint getNextDataPoint();
	DataPoint removeDataPoint();
	void resetDataList();
	boolean hasData();
}
