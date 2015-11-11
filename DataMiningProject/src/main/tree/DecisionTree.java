package main.tree;

import java.util.*;

import main.node.*;

public class DecisionTree {
	
	static Node rootNode;
	
	public DecisionTree(){
		//TODO
	}
	
	public void trainTree(ArrayList<ArrayList> data){
		//Set up the root node to decide based from the first input
		rootNode = new OrdinalDecisionNode((float) data.get(0).get(0));
		rootNode.setRight(new Leaf((String)data.get(0).get(1)));
		
		for(int i = 0; i < data.size(); i++){
			System.out.println(rootNode.acceptData(data.get(i)));
		}
	}
	
	public void classify(Object data){
		
	}
	
	public static void main(String args[]){
		
		DecisionTree myTree = new DecisionTree();
		ArrayList<ArrayList> myList = new ArrayList<>();
		int testSize = 12;
		
		for(int i = 1; i < testSize + 1; i++){
			ArrayList<Object> list = new ArrayList<Object>();
			
			list.add((float)i);
			myList.add(list);
			
			if( i < testSize/2){
				myList.get(i - 1).add("below " + Float.toString(testSize/2));
			}
			if(i == 25){
				myList.get(i - 1).add("equal " + Float.toString(testSize/2));
			}
			else{
				myList.get(i - 1).add("above " + Float.toString(testSize/2));
			}
		}
		Random myRand = new Random();
		for(int j = 0; j < testSize; j++){
			float tempF;
			String tempS;
			int rand = myRand.nextInt(testSize);
			
			tempF = (float) myList.get(rand).get(0);
			tempS = (String) myList.get(rand).get(1);
			
			myList.get(rand).set(0, (float) myList.get(j).get(0));
			myList.get(rand).set(1, (String) myList.get(j).get(1));
			
			myList.get(j).set(0, tempF);
			myList.get(j).set(1, tempS);
		}
	//	for(int i = 0; i < 10; i++){
			
		//	System.out.println(myList.get(i).get(0));
	//	}
		myTree.trainTree(myList);
		myTree.inOrderPrint();
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


