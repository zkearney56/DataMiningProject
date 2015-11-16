package main.tree;

import java.util.*;

import main.algorithm.Algorithm;
import main.node.*;
import main.DataPoint;
public class DecisionTree {
	
	static Node rootNode;
	Algorithm algorithm;
	public DecisionTree(Algorithm a){
		algorithm = a;
	}
	
	public void trainTree(){
		//Set up the root node to decide based from the first input
		rootNode = algorithm.getBestNode();
		while(algorithm.hasData()){
			DataPoint current = algorithm.getNextDataPoint();
			//if the data is correctly classified, remove the data from the list
			if(rootNode.acceptData(current).equals(current.getClassification())){
				algorithm.removeDataPoint();
			}
			else{
				insertEndNode(current, algorithm.getBestNode());
				algorithm.resetDataList();
			}
		}
	}
	
	public String classify(DataPoint data){
		return rootNode.acceptData(data);
	}
	
	public void insertEndNode(DataPoint dataPoint, Node node){
		Node n = rootNode.getResultNode(dataPoint);
		while(!(n.getResultNode(dataPoint) instanceof Leaf)){
			n = n.getResultNode(dataPoint);
		}
		if(n.testData(dataPoint)){
			n.setRight(node);
		}
		else{
			n.setLeft(node);
		}
	}
	
	public void inOrderPrint(){
		printInOrder(rootNode);
		System.out.println("");
	}
	private void printInOrder(Node n){
		if(n instanceof Leaf){
			System.out.println(n);
			return;
		}
		printInOrder(n.getLeft());
		System.out.println(n);
		printInOrder(n.getRight());
	}
	
	public static void main(String args[]){
		
	}
}


