package main.algorithm;

import main.structure.DataList;
import main.structure.DataPoint;
import main.node.Node;

public interface Algorithm {
	Node getBestNode();
	DataPoint getNextDataPoint();
	DataPoint removeDataPoint();
	void resetDataList();
	boolean hasData();
}
