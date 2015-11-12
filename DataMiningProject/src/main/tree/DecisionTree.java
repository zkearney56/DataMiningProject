package main.tree;

import java.util.*;

import main.node.*;
import main.DataList;
public class DecisionTree {
	
	static Node rootNode;
	
	public DecisionTree(){
		//TODO
	}
	
	public void trainTree(DataList data){
		//Set up the root node to decide based from the first input
		rootNode = new OrdinalDecisionNode((float) data.getRow(0).getDataVal(0));
		rootNode.setRight(new Leaf((String)data.getRow(0).getClassification()));
		
		for(int i = 0; i < data.getLength(); i++){
			System.out.println(rootNode.acceptData(data.getRow(i).getDataVal(0)));
		}
	}
	
	public void classify(Object data){
		
	}
	
	public static void main(String args[]){
		
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
}


