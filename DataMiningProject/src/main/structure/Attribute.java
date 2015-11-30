package main.structure;

import java.util.Iterator;

public class Attribute {

	private double min, max, mean, stdDev;
	private DMArrayList<CategorialPoint> dataStore;
	private DMArrayList<Double> values;
	private String type;
	private String name;
	private int uniqueVals;
	
	public Attribute(String name, String type, double min, double max, double mean, double stdDev){
		this.name = name;
		this.type = type;
		this.min = min;
		this.max = max;
		this.mean = mean;
		this.stdDev = stdDev;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Attribute(String name, String type, DMArrayList data){
		this.name = name;
		this.type = type;
		if(type.equals("Numeric")){
			numeric(data);
		}
		else if(type.equals("Categorial")){
			categorial(data);
		}


	}
	
	private void numeric(DMArrayList<Double> data){
		min = MathFunctions.min(data);
		max = MathFunctions.max(data);
		mean = MathFunctions.mean(data);
		stdDev = MathFunctions.stdDev(mean, data);
	}
	
	private void categorial(DMArrayList<String> data){
		calculateData(data);
		this.uniqueVals = dataStore.size();
		min = MathFunctions.min(values);
		max = MathFunctions.max(values);
		mean = MathFunctions.mean(values);
		stdDev = MathFunctions.stdDev(mean, values);
	}
	
	private void calculateData(DMArrayList<String> data){
		
		dataStore = new DMArrayList<CategorialPoint>();
		Iterator<String> itr = data.iterator();
		String current = (String)itr.next();
		CategorialPoint point = new CategorialPoint(current);
		dataStore.add(point);
		while(itr.hasNext()){
			boolean found = false;
			current = (String)itr.next();
			Iterator<CategorialPoint> itr2 = dataStore.iterator();
			int i = 0;
			while(itr2.hasNext() && !found){
				point = itr2.next();
				if(point.getName().equals(current)){
					point.increment();
					found = true;
				}
				
			}
			if(found){
				dataStore.set(i, point);
			}
			else{
				dataStore.add(new CategorialPoint(current));
			}
			i++;
		}
		values = new DMArrayList<Double>();
		Iterator<CategorialPoint> itr3 = dataStore.iterator();
		while(itr3.hasNext()){
			point = (CategorialPoint)itr3.next();
			values.add((double)point.getCount());
			
		}
	}
	
	public double getMin(){
		return min;
	}
	
	public double getMax(){
		return max;
	}
	
	public double getMean(){
		return mean;
	}
	
	public double getStdDev(){
		return stdDev;
	}
	
	public int getUniqueVals(){
		return uniqueVals;
	}
	
	public DMArrayList<CategorialPoint> getData(){
		return dataStore;
	}
	
	public String getType(){
		return type;
	}
	
	public String getName(){
		return name;
	}
	
}
