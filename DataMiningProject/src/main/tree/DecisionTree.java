package main.tree;

import main.algorithm.Algorithm;
import main.node.Leaf;
import main.node.Node;
import main.structure.DataPoint;

/**
 * This class makes a decision tree that will use whatever algorithm is given to it to classify data.
 * @author Dan Martin
 *
 */
public class DecisionTree {
	
	static Node rootNode;
	Algorithm algorithm;
	
	/**
	 * Constructs a DecisionTree and prepares it to be trained.
	 * @param a the algorithm that will be used to build the tree.
	 */
	public DecisionTree(Algorithm a){
		algorithm = a;
		//Create the root node using the algorithm.
		rootNode = algorithm.getBestNode();
	}
	/**
	 * Uses the algorithm to create the entire decision tree.
	 */
	public void trainTree(){
		while(algorithm.hasData()){
			DataPoint current = algorithm.getNextDataPoint();
			rootNode.trainData(current);
		}
		inOrderCheck(rootNode);
		if(algorithm.hasData()){
			trainTree();
		}
	}
	/**
	 * Does an in order traversal of the tree to check if any of the leaf nodes need to split again.
	 * If they do a new node is constructed and inserted.
	 * The algorithms dataList is then reset so that all of the data can be put into the tree again.
	 * @param n the node which to start the traversal.
	 */
	public void inOrderCheck(Node n){
		if(n.getLeft() instanceof Leaf){
			if(!((Leaf) n.getLeft()).isLeaf()){
				Node temp = ((Leaf) n.getLeft()).makeNewDecisionNode();
				if(n.getRight() instanceof Leaf){
					if(((Leaf)n.getRight()).getListSize() > 0){
						n.setLeft(temp);
						algorithm.resetDataList();
					}
				}
				else{
					n.setLeft(temp);
					algorithm.resetDataList();
				}
			}
		}
		else{
			inOrderCheck(n.getLeft());
		}
		if(n.getRight() instanceof Leaf){
			if(!((Leaf) n.getRight()).isLeaf()){
				Node temp = ((Leaf) n.getRight()).makeNewDecisionNode();
				if(n.getLeft() instanceof Leaf){
					if(((Leaf)n.getLeft()).getListSize() > 0){
						n.setRight(temp);
						algorithm.resetDataList();
					}
				}
				else{
					n.setRight(temp);
					algorithm.resetDataList();
				}
			}
		}
		else{
			inOrderCheck(n.getRight());
		}
	}
	/**
	 * Classifies a single dataPoint once the tree has been trained.
	 * @param data the dataPoint to be classified.
	 * @return the classification that the tree gave the dataPoint.
	 */
	public String classify(DataPoint data){
		return rootNode.acceptData(data);
	}
	
	/**
	 * Does an inOrder traversal of the tree and prints out each node.
	 */
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


