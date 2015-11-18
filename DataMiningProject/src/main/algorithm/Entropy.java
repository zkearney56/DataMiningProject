package main.algorithm;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import main.structure.DataList;
import main.structure.DataPoint;
import main.structure.FrequencyTable;
import main.node.Node;

public class Entropy extends Algorithm{

	public Entropy(DataList dataList){
		super(dataList);
	}
	
	FrequencyTable frequencyTable;
	public Node getBestNode() {
		return null;
	}
	
	public int test(){
		return findBestColumn();
	}
	
	private int findBestColumn(){
		float gains[] = new float[dataList.getLength()];
		String split[] = new String[dataList.getLength()];
		for(int i = 0; i < gains.length; i++){
			//create a frequency table with all of the types and attributes
			frequencyTable = getFrequencyTable(i);
			for(int j = 0; j < dataList.getNumRows(); j++){
				//if something is broken check here first
				if(!(dataList.getRow(j).getDataVal(i) instanceof String)){
					float sum = 0;
					for(int k = 0; k < dataList.getNumRows(); k++){
						sum = sum + (float)dataList.getRow(j).getDataVal(i);
					}
					frequencyTable.increment((int)dataList.getRow(j).getDataVal(i)/sum, dataList.getRow(j).getClassification());
				}
				else{
					frequencyTable.increment(dataList.getRow(j).getDataVal(i), dataList.getRow(j).getClassification());
				}
			}
			split[i] = "";
			for(int j = 0; j < dataList.getNumRows(); j++){
				if(!(dataList.getRow(j).getClassification().equals(split[i]))){
					
					int matchCount = 0;
					for(int k = 0; k < dataList.getLength(); k++){
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
				ent += percent(frequencyTable.getAttribute(i))*e(typeCount, restCount);
				typeCount = 0;
				restCount = 0;
			}
		}
		return currentDataPoint;
	}
	private FrequencyTable getFrequencyTable(int col){
		FrequencyTable table = new FrequencyTable();
		Vector<Object> a = new Vector<Object>();
		Vector<String> t = new Vector<String>();
		
		if(dataList.getRow(0).getDataVal(col) instanceof String){
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