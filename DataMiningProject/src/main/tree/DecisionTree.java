package main.tree;

import java.util.*;

import main.algorithm.Algorithm;
import main.node.*;
import main.structure.DataPoint;

public class DecisionTree {
	
	static Node rootNode;
	Algorithm algorithm;
	public DecisionTree(Algorithm a){
		algorithm = a;
		rootNode = algorithm.getBestNode();
	}
	
	public void trainTree(){
		//Set up the root node to decide based from the first input
		while(algorithm.hasData()){
			DataPoint current = algorithm.getNextDataPoint();
			rootNode.acceptData(current);
		}
		inOrderCheck(rootNode);
		if(algorithm.hasData()){
			trainTree();
		}
	}
	public void inOrderCheck(Node n){
		if(n.getLeft() instanceof Leaf){
			if(!((Leaf) n.getLeft()).isLeaf()){
				n.setLeft(((Leaf) n.getLeft()).makeNewDecisionNode());
				algorithm.resetDataList();
			}
		}
		else{
			inOrderCheck(n.getLeft());
		}
		if(n.getRight() instanceof Leaf){
			if(!((Leaf) n.getRight()).isLeaf()){
				n.setRight(((Leaf) n.getRight()).makeNewDecisionNode());
				algorithm.resetDataList();
			}
		}
		else{
			inOrderCheck(n.getRight());
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


