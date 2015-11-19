package main.algorithm;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import main.structure.DataList;
import main.structure.DataPoint;
import main.structure.FrequencyTable;
import main.node.Leaf;
import main.node.Node;
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
		int splitPoint = findBestSplitBin(column);
		Node n = new OrdinalDecisionNode(dataMin + splitPoint*dataBinNum, column);
		n.setRight(new Leaf(split[column]));
		float maxGains = 0;
		int maxIndex = 0;
		float min = Float.parseFloat((String) dataList.getRow(0).getDataVal(i));
		float max = Float.parseFloat((String) dataList.getRow(0).getDataVal(i));
		float sum = 0;
		for(int k = 1; k < dataList.getNumRows(); k++){
			float s = Float.parseFloat((String) dataList.getRow(k).getDataVal(i));
			if(s < min){
				min = s;
			}
			if(s > max){
				max = s;
			}
		}
		float diff = max-min;
		float binNum = diff/(numBins-1);
		for(int i = 1; i < gains.length; i++){
			if(gains[i] != gains[column] && gains[i] > maxGains){
				maxGains = gains[i];
				maxIndex = i;
			}
		}
		n.setLeft(new Leaf(split[maxIndex]));
		return n;
	}
	
	private int findBestSplitBin(int column) {
		frequencyTable = getFrequencyTable(column);
		float splitGains[] = new float[frequencyTable.getAttributesSize()];
		for(int i = 0; i < gains.length; i++){
			int total = frequencyTable.getTotal(frequencyTable.getAttribute(i));
			float temp = e( total, dataList.getNumRows());
			if(temp > gains[i]){
				gains[i] = temp;
			}
		}
		float maxGains = gains[0];
		int index = 0;
		for(int i = 1; i < gains.length; i++){
			if(gains[i] > maxGains){
				maxGains = gains[i];
				index = i;
			}
		}
		return (int) frequencyTable.getAttribute(index);
	}

	public int test(){
		return findBestColumn();
	}
	
	private int findBestColumn(){
		gains = new float[dataList.getLength() - 1];
		split = new String[dataList.getLength() - 1];
		for(int i = 0; i < gains.length; i++){
			//create a frequency table with all of the types and attributes
			frequencyTable = getFrequencyTable(i);
			for(int j = 0; j < dataList.getNumRows(); j++){			
				boolean isString = false;
				try{
					Float.parseFloat((String) dataList.getRow(j).getDataVal(i));
				}
				catch(NumberFormatException e)
				{
				  isString = true;
				}
				if(!isString){
					float min = Float.parseFloat((String) dataList.getRow(0).getDataVal(i));
					float max = Float.parseFloat((String) dataList.getRow(0).getDataVal(i));
					float sum = 0;
					for(int k = 1; k < dataList.getNumRows(); k++){
						float s = Float.parseFloat((String) dataList.getRow(k).getDataVal(i));
						if(s < min){
							min = s;
						}
						if(s > max){
							max = s;
						}
					}
					float diff = max-min;
					float binNum = diff/(numBins-1);
					dataMin = min;
					dataMax = max;
					dataBinNum = binNum;
					frequencyTable.increment((int)((Float.parseFloat((String) dataList.getRow(j).getDataVal(i))-min)/binNum), dataList.getRow(j).getClassification());
				}
				else{
					frequencyTable.increment((String)dataList.getRow(j).getDataVal(i), dataList.getRow(j).getClassification());
				}
			}
			split[i] = "";
			for(int j = 0; j < dataList.getNumRows(); j++){
				if(!(dataList.getRow(j).getClassification().equals(split[i]))){
					
					int matchCount = 0;
					System.out.println(dataList.getNumRows());
					for(int k = 0; k < dataList.getNumRows(); k++){
						if(dataList.getRow(k).getClassification().equals((dataList.getRow(i).getClassification()))){
							matchCount++;
						}
					}
					
					float temp = e(matchCount,dataList.getNumRows()) - e(dataList.getRow(i).getClassification());
					if(temp > gains[i]){
						gains[i] = temp;
						split[i] = dataList.getRow(i).getClassification();
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
				if(!t.contains(((DataPoint)dataList.getRow(j)).getClassification())){
					t.add(((DataPoint)dataList.getRow(j)).getClassification());
				}
			}
			for(int i = 0; i < 10; i++){
				a.add(i);
			}
			table.setTypes(t);
			table.setAttributes(a);
		}
		return table;
	}

	public static void main(String args[]){
		//Entropy e = new Entropy();
	}
}