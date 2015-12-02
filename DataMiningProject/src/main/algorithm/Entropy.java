package main.algorithm;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import main.structure.DataList;
import main.structure.DataPoint;
import main.structure.FrequencyTable;
import main.node.Leaf;
import main.node.Node;
import main.node.NominalDecisionNode;
import main.node.OrdinalDecisionNode;

/**
 * This class extends from the Algorithm class and uses the Entropy/Information Gain algorithm
 * to create a decision node for a decision tree from a dataList.
 * 
 * @author Dan Martin
 *
 */
public class Entropy extends Algorithm{
	
	private FrequencyTable frequencyTable;
	private String split[];
	private float gains[];

	/**
	 * Constructs an Entropy algorithm by construction its parent Algorithm object with the DataList passed to it.
	 * @param dataList the DataList that will be used and managed by the algorithm.
	 */
	public Entropy(DataList dataList){
		super(dataList);
	}
	/**
	 * Constructs an Entropy algorithm with no DataList. A DataList must be added to the object before use.
	 */
	public Entropy(){
		super();
	}
	/**
	 * Uses the Entropy algorithm to construct the best decision node that it can.
	 * @return either an OrdinalDecisionNode or NominalDecisionNode to insert into a DecisionTree.
	 */
	public Node getBestNode() {
		//Find the best column to split on.
		int column = findBestColumn();
		float maxGains = 0;
		int maxIndex = 0;
		float min = 0;
		float max = 0;
		float sum = 0;
		Node n;
		//If the column that is picked in String data it must be handled differently than Numeric data.
		boolean isString = false;
		try{
			Float.parseFloat((String) dataList.getRow(0).getDataVal(column));
		}
		catch(NumberFormatException e)
		{
		  isString = true;
		}
		if(!isString){
			//This process is for dealing with the binning process that is done for numeric data.
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
			//Finds where to split on some attribute using entropy. 
			int splitPoint = findBestSplitBin(column, min, max);
			//Create a new OrdinalDecisionNode because the data is numeric.
			//min + splitPoint*binNum is used to convert the split bin from an integer to a float 
			//that can be used for making comparisons with the data
			n = new OrdinalDecisionNode(min + splitPoint*binNum, column, (String)dataList.getHead(column));
		}
		else{
			//Find the best place to split
			String splitPoint = findBestSplitBin(column);
			//Create a NominalDecisionNode with that split because the data is a String.
			n = new NominalDecisionNode(splitPoint, column, (String)dataList.getHead(column));
		}	
		//Figure out which attributes are used most frequently so in order to pick
		//what value to use when making the left leaf.
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
		//This is to pick a starting value for the left leaf which should be second highest value.
		secondIndex = (firstIndex + 1) % attributes.length;
		second = attributes[secondIndex];
		for(int i = 0; i < attributes.length; i++){
			if(attributes[i] > second && i != firstIndex){
				second = attributes[i];
				secondIndex = i;
			}
		}
		//Set the left and right nodes to their proper leafs and set the leaf headers.
		n.setLeft(new Leaf(frequencyTable.getType(secondIndex), new Entropy()));
		n.setRight(new Leaf(split[column], new Entropy()));
		((Leaf)n.getLeft()).setHeaders(dataList.getHeaders());
		((Leaf)n.getRight()).setHeaders(dataList.getHeaders());
		return n;
	}
	
	/**
	 * Finds the best place to split for columns that use String data using the Entropy algorithm.
	 * @param column the column in the DataList that needs to be split.
	 * @return a String used for creating a NominalDecisionNode.
	 */
	private String findBestSplitBin(int column) {
		//Create a frequencyTable on the column
		frequencyTable = getFrequencyTable(column);
		//Fill the frequencytable with data
		for(int j = 0; j < dataList.getNumRows(); j++){			
			frequencyTable.increment(dataList.getRow(j).getDataVal(column), dataList.getRow(j).getClassification());
		}
		float splitGains[] = new float[frequencyTable.getAttributesSize()];
		for(int i = 0; i < splitGains.length; i++){
			splitGains[i] = 0;
		}
		//find the gain for each possible split in the column using entropy
		for(int i = 0; i < splitGains.length; i++){
			int total = frequencyTable.getTotal(frequencyTable.getAttribute(i));
			float temp = entropy((String)dataList.getHead(column)) - entropy( total, dataList.getNumRows());
			if(temp > splitGains[i] && temp > 0 && temp < 1){
				splitGains[i] = temp;
			}
		}
		//find the index highest gain value in the frequencyTable
		float maxGains = splitGains[0];
		int index = 0;
		for(int i = 1; i < splitGains.length; i++){
			if(splitGains[i] > maxGains){
				maxGains = splitGains[i];
				index = i;
			}
		}
		//return the type that corresponds to that index in the frequencyTable.
		return (String) frequencyTable.getAttribute(index);
	}
	/**
	 * Finds the best bin to split on for a certain column for numeric data using Entropy.
	 * @param column the column of the DataList used for finding the split.
	 * @param min the minimum value of that column
	 * @param max the max value of the column
	 * @return an integer that corresponds to which bin in the frequencyTable to split on.
	 */
	private int findBestSplitBin(int column, float min, float max) {
		float binNum = (max-min)/(numBins-1);
		frequencyTable = getFrequencyTable(column);
		for(int j = 0; j < dataList.getNumRows(); j++){			
			frequencyTable.increment((int)((Float.parseFloat((String) dataList.getRow(j).getDataVal(column))-min)/binNum), dataList.getRow(j).getClassification());
		}
		float splitGains[] = new float[frequencyTable.getAttributesSize()];
		for(int i = 0; i < splitGains.length; i++){
			splitGains[i] = 0;
		}
		for(int i = 0; i < splitGains.length; i++){
			int total = frequencyTable.getTotal(frequencyTable.getAttribute(i));
			float temp = entropy((String)dataList.getHead(column)) - entropy( total, dataList.getNumRows());
			if(temp > splitGains[i] && temp > 0 && temp < 1){
				splitGains[i] = temp;
			}
		}
		float maxGains = splitGains[0];
		int index = 0;
		for(int i = 1; i < splitGains.length; i++){
			if(splitGains[i] > maxGains){
				maxGains = splitGains[i];
				index = i;
			}
		}
		return (int) frequencyTable.getAttribute(index);
	}

	/**
	 * Uses the Entropy algorithm to find the best column in the dataList to split on.
	 * @return an int corresponding to the index of the dataList to split on.
	 */
	private int findBestColumn(){
		//find the information gain for each attribute in the dataList.
		gains = new float[dataList.getLength()];
		split = new String[dataList.getLength()];
		for(int i = 0; i < gains.length; i++){
			//create a frequency table with all of the types and attributes
			frequencyTable = getFrequencyTable(i);
			float min = 0;
			float max = 0;
			float sum = 0;
			float diff = 0;
			float binNum = 0;
			//String and numeric data must be handled differently.
			boolean isString = false;
			try{
				Float.parseFloat((String) dataList.getRow(0).getDataVal(i));
			}
			catch(NumberFormatException e)
			{
			  isString = true;
			}
			if(!isString){
				//seperate the numberic data into bins.
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
			//fill the frequency table with data.
			for(int j = 0; j < dataList.getNumRows(); j++){			
				if(!isString){
					//this uses min and binNum to put the numeric data into bins.
					frequencyTable.increment((int)((Float.parseFloat((String) dataList.getRow(j).getDataVal(i))-min)/binNum), dataList.getRow(j).getClassification());
				}
				else{
					frequencyTable.increment((String)dataList.getRow(j).getDataVal(i), dataList.getRow(j).getClassification());
				}
			}
			
			split[i] = "";
			Vector<String> used = new Vector<String>();
			//Counts the number of times some attribute implies some type and runs entropy using those numbers
			//to find information gain for a split on that column.
			for(int j = 0; j < dataList.getNumRows(); j++){
				if(!(used.contains(dataList.getRow(j).getClassification()))){
					used.add(dataList.getRow(j).getClassification());
					int matchCount = 0;
					for(int k = 0; k < dataList.getNumRows(); k++){
						if(dataList.getRow(k).getClassification().equals((dataList.getRow(j).getClassification()))){
							matchCount++;
						}
					}
					
					float temp = entropy(matchCount,dataList.getNumRows()) - entropy(dataList.getRow(j).getClassification());
					if(j == 0){
						gains[i] = temp;
						split[i] = dataList.getRow(j).getClassification();
					}
					if(temp > gains[i]){
						gains[i] = temp;
						split[i] = dataList.getRow(j).getClassification();
					}
				}
			}
		}
		//finds the index of the column with the highest information gain
		float max = gains[0];
		int maxIndex = 0;
		for(int i = 1; i < gains.length; i++){
			if(gains[i] > max){
				max = gains[i];
				maxIndex = i;
			}
		}
		return maxIndex;
	}
	/**
	 * Calculates what percent of the data is a specific instance of an attribute.
	 * i.e. The attribute is color, it will find the percent of red/green/blue depending on what it is passed.
	 * @param a the attribute that will be used from the frequency table.
	 * @return the percent of the data that is that attribute.
	 */
	private float percent(Object a){
		Integer total = frequencyTable.getTotal(a);
		return (float)total/(float)dataList.getNumRows();
	}

	/**
	 * Applies the entropy equation Sum(-P(i) * log2(p(i)))
	 * @param numberOfOccurences number of times some attribute occurs within a column
	 * @param numberOfOther total number of attributes in a column
	 * @return the calculated entropy.
	 */
	private float entropy(int numberOfOccurences, int numberOfOther){
		float total = 0;
		float sum = numberOfOccurences + numberOfOther;
		total = total - (float)((numberOfOccurences/sum) * (Math.log((numberOfOccurences/sum))/Math.log(2)));
		total = total - (float)((numberOfOther/sum) * (Math.log((numberOfOther/sum))/Math.log(2)));
		return total;
	}
	/**
	 * Calculates entropy using the equation sum(P(c)*entropy(c));
	 * @param type the type that is being looked at.
	 * @return the calculated entropy.
	 */
	private float entropy(String type){
		int typeCount = 0;
		int restCount = 0;
		float ent = 0;
		int total = frequencyTable.getTypesSize() * frequencyTable.getAttributesSize();
		
		for(int i = 0; i < frequencyTable.getAttributesSize(); i++){
			for(int j = 0; j < frequencyTable.getTypesSize(); j++){
				if(frequencyTable.getType(j).equals(type)){
					typeCount += frequencyTable.getValue(i, j);
				}
				else{
					restCount += frequencyTable.getValue(i, j);
				}
			}
			if(typeCount != 0 && restCount != 0){
				ent += percent(frequencyTable.getAttribute(i))*entropy(typeCount, restCount);
			}
			typeCount = 0;
			restCount = 0;
		}
		return ent;
	}
}