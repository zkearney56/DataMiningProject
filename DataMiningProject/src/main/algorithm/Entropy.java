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

public class Entropy extends Algorithm{
	
	FrequencyTable frequencyTable;
	String split[];
	float gains[];

	public Entropy(DataList dataList){
		super(dataList);
	//	preprocessData();
	}
	//START HERE THIS NEEDS A LOT OF CLEANUP
	public Node getBestNode() {
		int column = findBestColumn();
		float maxGains = 0;
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
		//This needs improved, probably wont work on other data sets/training sets
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
		secondIndex = (firstIndex + 1) % attributes.length;
		second = attributes[secondIndex];
		for(int i = 0; i < attributes.length; i++){
			if(attributes[i] > second && i != firstIndex){
				second = attributes[i];
				secondIndex = i;
			}
		}
		
		n.setLeft(new Leaf(frequencyTable.getType(secondIndex)));
		n.setRight(new Leaf(split[column]));
		((Leaf)n.getLeft()).setHeaders(dataList.getHeaders());
		((Leaf)n.getRight()).setHeaders(dataList.getHeaders());
		return n;
	}
	
	private String findBestSplitBin(int column) {
		frequencyTable = getFrequencyTable(column);
		for(int j = 0; j < dataList.getNumRows(); j++){			
			frequencyTable.increment(dataList.getRow(j).getDataVal(column), dataList.getRow(j).getClassification());
		}
		float splitGains[] = new float[frequencyTable.getAttributesSize()];
		for(int i = 0; i < splitGains.length; i++){
			splitGains[i] = 0;
		}
		for(int i = 0; i < splitGains.length; i++){
			int total = frequencyTable.getTotal(frequencyTable.getAttribute(i));
			float temp = e((String)dataList.getHead(column)) - e( total, dataList.getNumRows());
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
		return (String) frequencyTable.getAttribute(index);
	}
	
	private int findBestSplitBin(int column, float min, float binNum) {
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
			float temp = e((String)dataList.getHead(column)) - e( total, dataList.getNumRows());
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

	public int test(){
		return findBestColumn();
	}
	
	private int findBestColumn(){
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
			boolean isString = false;
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
			split[i] = "";
			Vector<String> used = new Vector<String>();
			for(int j = 0; j < dataList.getNumRows(); j++){
				if(!(used.contains(dataList.getRow(j).getClassification()))){
					used.add(dataList.getRow(j).getClassification());
					int matchCount = 0;
				//	System.out.println(dataList.getNumRows());
					for(int k = 0; k < dataList.getNumRows(); k++){
						if(dataList.getRow(k).getClassification().equals((dataList.getRow(j).getClassification()))){
							matchCount++;
						}
					}
					
					float temp = e(matchCount,dataList.getNumRows()) - e(dataList.getRow(j).getClassification());
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
	private float percent(Object a){
		Integer total = frequencyTable.getTotal(a);
		return (float)total/(float)dataList.getNumRows();
	}

	private float e(int i1, int i2){
		float total = 0;
		float sum = i1 + i2;
		total = total - (float)((i1/sum) * (Math.log((i1/sum))/Math.log(2)));
		total = total - (float)((i2/sum) * (Math.log((i2/sum))/Math.log(2)));
		return total;
	}
	
	private float e(String type){
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
				ent += percent(frequencyTable.getAttribute(i))*e(typeCount, restCount);
			}
			typeCount = 0;
			restCount = 0;
		}
		return ent;
	}
	private FrequencyTable getFrequencyTable(int col){
		FrequencyTable table = new FrequencyTable();
		Vector<Object> a = new Vector<Object>();
		Vector<String> t = new Vector<String>();
		
		boolean isString = false;
		try{
			Float.parseFloat((String) dataList.getRow(0).getDataVal(col));
		}
		catch(NumberFormatException e)
		{
		  isString = true;
		}
		if(isString){
			for(int j = 0; j < dataList.getNumRows(); j++){
				if(!t.contains(((DataPoint)dataList.getRow(j)).getClassification())){
					t.add(((DataPoint)dataList.getRow(j)).getClassification());
				}
				if(!a.contains(dataList.getRow(j).getDataVal(col))){
					a.add(dataList.getRow(j).getDataVal(col));
				}
			}
			table.setTypes(t);
			table.setAttributes(a);
		}
		else{
			//This might mess stuff up because float
			/*float sum = 0;
			for(int i = 0; i < dataList.getNumRows(); i++){
				sum = sum + (float)dataList.getRow(i).getDataVal(col);
			}
			sum = sum/numBins;*/
			for(int j = 0; j < dataList.getNumRows(); j++){
				try{
					if(!t.contains(((DataPoint)dataList.getRow(j)).getClassification())){
						t.add(((DataPoint)dataList.getRow(j)).getClassification());
					}
				}
				catch(Exception e){
					
				}
			}
			for(int i = 0; i < numBins; i++){
				a.add(i);
			}
			table.setTypes(t);
			table.setAttributes(a);
		}
		return table;
	}
}