package main.structure;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MathFunctions {

	public static double min(DMArrayList<Double> vals){
		double min = vals.get(0);
		for(int i = 0; i < vals.size(); i++){
			if(vals.get(i) < min) min = vals.get(i);
		}
		return min;
	}
	
	public static double max(DMArrayList<Double> vals){
		double max = vals.get(0);
		for(int i = 0; i < vals.size(); i++){
			if(vals.get(i) > max) max = vals.get(i);
		}
		return max;
	}
	
	public static double mean(DMArrayList<Double> vals){
		BigDecimal mean = new BigDecimal(0.0);
		for(int i = 0; i < vals.size(); i++){
			mean = mean.add(BigDecimal.valueOf(vals.get(i)));
		}
		mean = mean.divide(BigDecimal.valueOf(vals.size()), 2, RoundingMode.HALF_UP);
		return Double.parseDouble(mean.toString());
	}
	
	public static double stdDev(double mean, DMArrayList<Double> vals){
		BigDecimal stdDev = new BigDecimal("0.0");
		double val;
		for(int i = 0; i < vals.size(); i++){
			val = vals.get(i) - mean;
			val = val * val;
			stdDev = stdDev.add(BigDecimal.valueOf(val));
		}
		stdDev = stdDev.divide(BigDecimal.valueOf(vals.size()), 2, RoundingMode.HALF_UP);
		return Double.parseDouble(stdDev.toString());
	}
}
