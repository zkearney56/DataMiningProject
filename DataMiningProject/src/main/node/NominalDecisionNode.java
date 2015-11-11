package main.node;

public class NominalDecisionNode implements Node {
	
	Node left;
	Node right;
	
	public NominalDecisionNode(){
		left = new Leaf("NULLVALUE");
		right = new Leaf("NULLVALUE");
	}
	@Override
	public String acceptData(Object dataPoint) {
		if(testData(dataPoint)){
			return right.acceptData(dataPoint);
		}
		else{
			return left.acceptData(dataPoint);
		}
	}
	@Override
	public boolean testData(Object dataPoint) {
		return false;
	}
	
	@Override
	public Node getLeft() {
		return left;
	}
	@Override
	public Node getRight() {
		return right;
	}
	
	public void setRight(Node right){
		this.right = right;
	}
	
	public void setLeft(Node left){
		this.left = left;
	}
}
