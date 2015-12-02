package main.structure;

/**
 * Author: Zachary Kearney
 * Last Edited: 11/30/2015
 * Holds the attribute data of a column.
 * This includes the name, type, min, max, mean, stdDev, the unique values, and the number of unique values
 */

import java.util.Iterator;

public class Attribute {

	private double min, max, mean, stdDev;
	private DMArrayList<CategorialPoint> dataStore;
	private DMArrayList<Double> values;
	private String type;
	private String name;
	private int uniqueVals;
	private boolean ignored = false;
	
	/**
	 * Constructor for a numeric Attribute with predefined values
	 * @param name
	 * @param type
	 * @param min
	 * @param max
	 * @param mean
	 * @param stdDev
	 */
	
	public Attribute(String name, String type, double min, double max, double mean, double stdDev){
		this.name = name;
		this.type = type;
		this.min = min;
		this.max = max;
		this.mean = mean;
		this.stdDev = stdDev;
	}
	
	/**
	 * Constructor to clone existing attribute
	 * @param att
	 */
	
	public Attribute(Attribute att){
		this.name = att.name;
		this.type = att.type;
		this.min = att.min;
		this.max = att.max;
		this.mean = att.mean;
		this.stdDev = att.stdDev;
		if(type.equals("Categorial")){
		this.dataStore = new DMArrayList<CategorialPoint>();
		this.uniqueVals = att.uniqueVals;
		for(int i = 0; i < att.getData().size(); i ++){
			dataStore.add(new CategorialPoint(att.getData().get(i)));
		}
	}
	}
	
	/**
	 * Constructor for an attribute that accepts a name, type, and an arraylist of data
	 * @param name
	 * @param type
	 * @param data
	 */
	
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
	
	/**
	 * If attribute is numeric, calculates the min, max, mean, and stdDev
	 * @param data
	 */
	
	private void numeric(DMArrayList<Double> data){
		min = MathFunctions.min(data);
		max = MathFunctions.max(data);
		mean = MathFunctions.mean(data);
		stdDev = MathFunctions.stdDev(mean, data);
	}
	
	/**
	 * If attribute is categorial, calculates the unique values, then the min, max, mean, and stdDev of the unique values
	 * @param data
	 */
	
	private void categorial(DMArrayList<String> data){
		calculateData(data);
		this.uniqueVals = dataStore.size();
		min = MathFunctions.min(values);
		max = MathFunctions.max(values);
		mean = MathFunctions.mean(values);
		stdDev = MathFunctions.stdDev(mean, values);
	}
	
	/**
	 * Used by categorial method to calculate the unique values
	 * @param data
	 */
	
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
	
	/**
	 * Returns the minimum value
	 * @return
	 */
	
	public double getMin(){
		return min;
	}
	
	/**
	 * Returns the maximum value
	 * @return
	 */
	
	public double getMax(){
		return max;
	}
	
	/**
	 * Returns the mean of the values
	 * @return
	 */
	
	public double getMean(){
		return mean;
	}
	
	/**
	 * Returns the standard deviation of the values
	 * @return
	 */
	
	public double getStdDev(){
		return stdDev;
	}
	
	/**
	 * Returns the number of unique values
	 * @return
	 */
	
	public int getUniqueVals(){
		return uniqueVals;
	}
	
	/**
	 * Returns the unique values as an ArrayList of categorial points containing the name of the values and the number of instances
	 * @return
	 */
	
	public DMArrayList<CategorialPoint> getData(){
		return dataStore;
	}
	
	/**
	 * Returns the type of the Attribute
	 * @return
	 */
	
	public String getType(){
		return type;
	}
	
	/**
	 * Returns the name of the values
	 * @return
	 */
	
	public String getName(){
		return name;
	}
	
	/**
	 * Used to signify is attribute is to be ignored
	 * @param t
	 */
	
	public void ignore(boolean t){
		ignored = t;
	}
	
	/**
	 * Returns ignore value
	 * @return
	 */
	
	public boolean getIgnore(){
		return ignored;
	}
	
	public String toString(){
		StringBuilder stringBuilder = new StringBuilder();
		String COMMA = ",";
		stringBuilder.append(name);
		stringBuilder.append(COMMA);
		stringBuilder.append(type);
		stringBuilder.append(COMMA);
		stringBuilder.append(uniqueVals);
		stringBuilder.append(COMMA);
		stringBuilder.append(min);
		stringBuilder.append(COMMA);
		stringBuilder.append(max);
		stringBuilder.append(COMMA);
		stringBuilder.append(mean);
		stringBuilder.append(COMMA);
		stringBuilder.append(stdDev);
		stringBuilder.append("\n");
		return stringBuilder.toString();
		
	}
	
}
