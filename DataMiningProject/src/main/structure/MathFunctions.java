package main.structure;


import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 * Math functions for use with large amounts of data.
 * 
 * @author Zachary Kearney
 * 
 * Last Edited: 11/30/2015
 */
public abstract class MathFunctions {

	/**
	 * Returns the min from the arraylist of doubles.
	 * 
	 * @param vals
	 *            DMArrayList containing all values
	 * @return returns the maximum
	 */

	public static double min(DMArrayList<Double> vals) {
		double min = vals.get(0);
		for (int i = 0; i < vals.size(); i++) {
			if (vals.get(i) < min)
				min = vals.get(i);
		}
		return min;
	}

	/**
	 * Returns the max from the arraylist of doubles.
	 * 
	 * @param vals
	 *            DMArrayList containing all values
	 * @return returns the minimum
	 */

	public static double max(DMArrayList<Double> vals) {
		double max = vals.get(0);
		for (int i = 0; i < vals.size(); i++) {
			if (vals.get(i) > max)
				max = vals.get(i);
		}
		return max;
	}

	/**
	 * Returns the mean of the arraylist of doubles.
	 * 
	 * @param vals
	 *            DMArrayList containing all values
	 * @return returns the mean
	 */

	public static double mean(DMArrayList<Double> vals) {
		BigDecimal mean = new BigDecimal(0.0);
		for (int i = 0; i < vals.size(); i++) {
			mean = mean.add(BigDecimal.valueOf(vals.get(i)));
		}
		mean = mean.divide(BigDecimal.valueOf(vals.size()), 2, RoundingMode.HALF_UP);
		return Double.parseDouble(mean.toString());
	}

	/**
	 * Returns the stdDev from the arraylist of doubles
	 * 
	 * @param mean
	 *            Mean of the values
	 * @param vals
	 *            DMArrayList containing all values
	 * @return returns the standard deviation
	 */

	public static double stdDev(double mean, DMArrayList<Double> vals) {
		BigDecimal stdDev = new BigDecimal("0.0");
		double val;
		for (int i = 0; i < vals.size(); i++) {
			val = vals.get(i) - mean;
			val = val * val;
			stdDev = stdDev.add(BigDecimal.valueOf(val));
		}
		stdDev = stdDev.divide(BigDecimal.valueOf(vals.size()), 2, RoundingMode.HALF_UP);
		return Double.parseDouble(stdDev.toString());
	}

	/**
	 * Returns the stdDev from the ArrayList of doubles. Faster to use if mean
	 * is already known.
	 * 
	 * @param vals
	 *            DMArrayList containing the values
	 * @return returns the standard deviation
	 */
	public static double stdDev(DMArrayList<Double> vals) {
		double mean = mean(vals);
		BigDecimal stdDev = new BigDecimal("0.0");
		double val;
		for (int i = 0; i < vals.size(); i++) {
			val = vals.get(i) - mean;
			val = val * val;
			stdDev = stdDev.add(BigDecimal.valueOf(val));
		}
		stdDev = stdDev.divide(BigDecimal.valueOf(vals.size()), 2, RoundingMode.HALF_UP);
		return Double.parseDouble(stdDev.toString());
	}
}
