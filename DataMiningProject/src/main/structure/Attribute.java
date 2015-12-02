package main.structure;

/**
 * @author Zachary Kearney
 * Last Edited: 11/30/2015
 * Holds the attribute data of a column.
 * This includes the name, type, min, max, mean, stdDev, the unique values, and the number of unique values
 */

import java.util.Iterator;

public class Attribute implements AttributeInterface {

	private double min, max, mean, stdDev;
	private DMArrayList<CategorialPoint> dataStore;
	private DMArrayList<Double> values;
	private String type;
	private String name;
	private int uniqueVals;
	private boolean ignored;

	/**
	 * Constructor for a numeric Attribute with predefined values
	 * 
	 * @param name
	 * @param type
	 * @param min
	 * @param max
	 * @param mean
	 * @param stdDev
	 */

	public Attribute(String name, String type, double min, double max, double mean, double stdDev) {
		this.name = name;
		this.type = type;
		this.min = min;
		this.max = max;
		this.mean = mean;
		this.stdDev = stdDev;
		this.ignored = false;
	}

	/**
	 * Constructor to clone existing attribute
	 * 
	 * @param att
	 *            - The attribute to clone.
	 */

	public Attribute(Attribute att) {
		this.name = att.getName();
		this.type = att.getType();
		this.min = att.getMin();
		this.max = att.getMax();
		this.mean = att.getMean();
		this.stdDev = att.getStdDev();
		this.ignored = att.getIgnore();
		if (type.equals("Categorial")) {
			this.dataStore = new DMArrayList<CategorialPoint>();
			this.uniqueVals = att.getUniqueVals();
			for (int i = 0; i < att.getData().size(); i++) {
				dataStore.add(new CategorialPoint(att.getData().get(i)));
			}
		}
	}

	/**
	 * Constructor for an attribute that accepts a name, type, and an arraylist
	 * of data
	 * 
	 * @param name
	 *            - The name of the Attribute.
	 * @param type
	 *            - The type of the Attribute. "Categorical" or "Numeric"
	 * @param data
	 *            - The DMArrayList containing Attribute data.
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Attribute(String name, String type, DMArrayList data) {
		this.ignored = false;
		this.name = name;
		this.type = type;
		if (type.equals("Numeric")) {
			numeric(data);
		} else if (type.equals("Categorial")) {
			categorial(data);
		}

	}

	/**
	 * If attribute is numeric, calculates the min, max, mean, and stdDev
	 * 
	 * @param data-
	 *            The DMArrayList containing all data.
	 */

	private void numeric(DMArrayList<Double> data) {
		min = MathFunctions.min(data);
		max = MathFunctions.max(data);
		mean = MathFunctions.mean(data);
		stdDev = MathFunctions.stdDev(mean, data);
	}

	/**
	 * If attribute is categorial, calculates the unique values, then the min,
	 * max, mean, and stdDev of the unique values
	 * 
	 * @param -
	 *            data The DMArrayList containing all data.
	 */

	private void categorial(DMArrayList<String> data) {
		calculateData(data);
		this.uniqueVals = dataStore.size();
		min = MathFunctions.min(values);
		max = MathFunctions.max(values);
		mean = MathFunctions.mean(values);
		stdDev = MathFunctions.stdDev(mean, values);
	}

	/**
	 * Used by categorial method to calculate the unique values
	 * 
	 * @param data
	 *            - The DMArrayList containing all data.
	 */

	private void calculateData(DMArrayList<String> data) {

		dataStore = new DMArrayList<CategorialPoint>();
		Iterator<String> itr = data.iterator();
		String current = (String) itr.next();
		CategorialPoint point = new CategorialPoint(current);
		dataStore.add(point);
		while (itr.hasNext()) {
			boolean found = false;
			current = (String) itr.next();
			Iterator<CategorialPoint> itr2 = dataStore.iterator();
			int i = 0;
			while (itr2.hasNext() && !found) {
				point = itr2.next();
				if (point.getName().equals(current)) {
					point.increment();
					found = true;
				}

			}
			if (found) {
				dataStore.set(i, point);
			} else {
				dataStore.add(new CategorialPoint(current));
			}
			i++;
		}
		values = new DMArrayList<Double>();
		Iterator<CategorialPoint> itr3 = dataStore.iterator();
		while (itr3.hasNext()) {
			point = (CategorialPoint) itr3.next();
			values.add((double) point.getCount());

		}
	}

	/* (non-Javadoc)
	 * @see main.structure.AttributeInterface#getMin()
	 */

	@Override
	public double getMin() {
		return min;
	}

	/* (non-Javadoc)
	 * @see main.structure.AttributeInterface#getMax()
	 */

	@Override
	public double getMax() {
		return max;
	}

	/* (non-Javadoc)
	 * @see main.structure.AttributeInterface#getMean()
	 */

	@Override
	public double getMean() {
		return mean;
	}

	/* (non-Javadoc)
	 * @see main.structure.AttributeInterface#getStdDev()
	 */

	@Override
	public double getStdDev() {
		return stdDev;
	}

	/* (non-Javadoc)
	 * @see main.structure.AttributeInterface#getUniqueVals()
	 */

	@Override
	public int getUniqueVals() {
		return uniqueVals;
	}

	/* (non-Javadoc)
	 * @see main.structure.AttributeInterface#getData()
	 */

	@Override
	public DMArrayList<CategorialPoint> getData() {
		return dataStore;
	}

	/* (non-Javadoc)
	 * @see main.structure.AttributeInterface#getType()
	 */

	@Override
	public String getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see main.structure.AttributeInterface#getName()
	 */

	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see main.structure.AttributeInterface#ignore(boolean)
	 */

	@Override
	public void ignore(boolean t) {
		ignored = t;
	}

	/* (non-Javadoc)
	 * @see main.structure.AttributeInterface#getIgnore()
	 */

	@Override
	public boolean getIgnore() {
		return ignored;
	}

	/* (non-Javadoc)
	 * @see main.structure.AttributeInterface#toString()
	 */

	@Override
	public String toString() {
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
