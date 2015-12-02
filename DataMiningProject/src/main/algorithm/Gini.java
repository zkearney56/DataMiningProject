package main.algorithm;

import main.node.Leaf;
import main.node.Node;
import main.node.NominalDecisionNode;
import main.node.OrdinalDecisionNode;
import main.structure.DataList;
import main.structure.FrequencyTable;

public class Gini extends Algorithm{

	private FrequencyTable frequencyTable;
	
	public Gini(DataList dataList){
		super(dataList);
	}
	public Gini(){
		super();
	}
	@Override
	public Node getBestNode() {
		int column = findBestColumn();
		int maxIndex = 0;
		float min = 0;
		float max = 0;
		float sum = 0;
		Node n;
		int splitPoint = 0;
		String sSplitPoint = "";
		boolean isString = false;
		try{
			Float.parseFloat((String) dataList.getRow(0).getDataVal(column));
		}
		catch(NumberFormatException e)
		{
		  isString = true;
		}
		if(!isString){
			min = Float.parseFloat((String) dataList.getRow(0).getDataVal(column));
			max = Float.parseFloat((String) dataList.getRow(0).getDataVal(column));
			for(int k = 1; k < dataList.getNumRows(); k++){
				float s = Float.parseFloat((String) dataList.getRow(k).getDataVal(column));
				if(s < min){
					min = s;
				}
				if(s > max){
					max = s;
				}
			}
			float diff = max-min;
			float binNum = diff/(numBins-1);
			splitPoint = findBestSplitBin(column, min, binNum);
			n = new OrdinalDecisionNode(min + splitPoint*binNum, column, (String)dataList.getHead(column));
		}
		else{
			sSplitPoint = findBestSplitBin(column);
			n = new NominalDecisionNode(sSplitPoint, column, (String)dataList.getHead(column));
		}	
		
		int attributes[] = new int[frequencyTable.getTypesSize()];
		for(int i = 0; i < frequencyTable.getTypesSize(); i++){
			for(int j = 0; j < frequencyTable.getAttributesSize(); j++){
				attributes[i] = attributes[i] + frequencyTable.getValue(j, i);
			}
		}
		int first = attributes[0];
		int firstIndex = 0;
		int second = 0;
		int secondIndex = 0;
		for(int i = 1; i < attributes.length; i++){
			if(attributes[i] > first){
				first = attributes[i];
				firstIndex = i;
			}
		}
		//This is to pick a starting value for second
		secondIndex = (firstIndex + 1) % attributes.length;
		second = attributes[secondIndex];
		for(int i = 0; i < attributes.length; i++){
			if(attributes[i] > second && i != firstIndex){
				second = attributes[i];
				secondIndex = i;
			}
		}
		
		n.setLeft(new Leaf(frequencyTable.getType(secondIndex), new Entropy()));
		n.setRight(new Leaf(frequencyTable.getType(firstIndex), new Entropy()));
		((Leaf)n.getLeft()).setHeaders(dataList.getHeaders());
		((Leaf)n.getRight()).setHeaders(dataList.getHeaders());
		return n;
	}
	
	private String findBestSplitBin(int column) {
		frequencyTable = getFrequencyTable(column);
		for(int j = 0; j < dataList.getNumRows(); j++){			
			frequencyTable.increment(dataList.getRow(j).getDataVal(column), dataList.getRow(j).getClassification());
		}
		float giniValues[] = new float[frequencyTable.getAttributesSize()];
		for(int i = 0; i < giniValues.length; i++){
			giniValues[i] = 1;
		}
		for(int i = 0; i < giniValues.length; i++){
			int total = frequencyTable.getTotal(frequencyTable.getAttribute(i));
			if(total != 0){
				float sum = 0;
				for(int j = 0; j < frequencyTable.getTypesSize(); j++){
					float temp = frequencyTable.getValue(i, j)/total;
					sum += temp*temp;
				}
				giniValues[i] = 1 - sum;
			}
		}
		float minGini = giniValues[0];
		int index = 0;
		for(int i = 1; i < giniValues.length; i++){
			if(giniValues[i] > minGini){
				minGini = giniValues[i];
				index = i;
			}
		}
		return (String) frequencyTable.getAttribute(index);
	}
	
	private int findBestSplitBin(int column, float min, float binNum) {
		frequencyTable = getFrequencyTable(column);
		for(int j = 0; j < dataList.getNumRows(); j++){			
			frequencyTable.increment((int)((Float.parseFloat((String) dataList.getRow(j).getDataVal(column))-min)/binNum), dataList.getRow(j).getClassification());
		}
		float giniValues[] = new float[frequencyTable.getAttributesSize()];
		for(int i = 0; i < giniValues.length; i++){
			giniValues[i] = 1;
		}
		for(int i = 0; i < giniValues.length; i++){
			int total = frequencyTable.getTotal(frequencyTable.getAttribute(i));
			if(total != 0){
				float sum = 0;
				for(int j = 0; j < frequencyTable.getTypesSize(); j++){
					float temp = frequencyTable.getValue(i, j)/total;
					sum += temp*temp;
				}
				giniValues[i] = 1 - sum;
			}
		}
		float minGini = giniValues[0];
		int index = 0;
		for(int i = 1; i < giniValues.length; i++){
			if(giniValues[i] > minGini){
				minGini = giniValues[i];
				index = i;
			}
		}
		return (int) frequencyTable.getAttribute(index);
	}
	private float gini(){
		float gini = 1;
		float sum = 0;
		for(int i = 0; i < frequencyTable.getAttributesSize(); i++){
			float temp = (float)frequencyTable.getTotal(frequencyTable.getAttribute(i))/(float)dataList.getNumRows();
			sum += temp*temp;
		}
		return gini - sum;
	}
	private int findBestColumn(){
		float giniValues[] = new float[dataList.getLength()];
		for(int i = 0; i < giniValues.length; i++){
			frequencyTable = getFrequencyTable(i);
			boolean isString = false;
			float min = 0;
			float max = 0;
			float sum = 0;
			float diff = 0;
			float binNum = 0;
			try{
				Float.parseFloat((String) dataList.getRow(0).getDataVal(i));
			}
			catch(NumberFormatException e)
			{
			  isString = true;
			}
			if(!isString){
				min = Float.parseFloat((String) dataList.getRow(0).getDataVal(i));
				max = Float.parseFloat((String) dataList.getRow(0).getDataVal(i));
				for(int k = 1; k < dataList.getNumRows(); k++){
					float s = Float.parseFloat((String) dataList.getRow(k).getDataVal(i));
					if(s < min){
						min = s;
					}
					if(s > max){
						max = s;
					}
				}
				diff = max-min;
				binNum = diff/(numBins-1);
			}
			for(int j = 0; j < dataList.getNumRows(); j++){			
				if(!isString){
					frequencyTable.increment((int)((Float.parseFloat((String) dataList.getRow(j).getDataVal(i))-min)/binNum), dataList.getRow(j).getClassification());
				}
				else{
					frequencyTable.increment((String)dataList.getRow(j).getDataVal(i), dataList.getRow(j).getClassification());
				}
			}
			giniValues[i] = gini();
		}
		float minGini = giniValues[0];
		int minGiniIndex = 0;
		for(int i = 1; i < giniValues.length; i++){
			if(minGini > giniValues[i]){
				minGini = giniValues[i];
				minGiniIndex = i;
			}
		}
		return minGiniIndex;
	}

}
