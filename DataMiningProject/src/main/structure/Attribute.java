package main.structure;

public class Attribute {

	private double min, max, mean, stdDev;
	private Object[] data;
	private String type;
	private String name;
	
	public Attribute(String name, String type, double min, double max, double mean, double stdDev){
		this.name = name;
		this.type = type;
		this.min = min;
		this.max = max;
		this.mean = mean;
		this.stdDev = stdDev;
	}
	
	public Attribute(String name, String type){
		this.name = name;
		this.type = type;

	}
	
	private void calculateData(DMArrayList<String> data){
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
	
	public String getType(){
		return type;
	}
	
	public String getName(){
		return name;
	}
	
}
