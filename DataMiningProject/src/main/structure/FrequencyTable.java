package main.structure;
import java.util.*;
public class FrequencyTable {
	Hashtable frequency;
	Vector types;
	Vector attributes;
	public FrequencyTable(){
		frequency = new Hashtable<Object, Hashtable>();
		types = new Vector<String>();
		attributes = new Vector<Object>();
	}
	
	public void setTypes(Vector<String> t){
		types = t;
	}
	
	public void setAttributes(Vector<Object> a){
		attributes = a;
		for(int i = 0; i < attributes.size(); i++){
			Hashtable temp = new Hashtable<String, Integer>();
			for(int j = 0 ; j < types.size(); j++){
				temp.put(types.get(i), 0);
			}
			frequency.put(attributes.get(i), temp);
		}
	}
	
	public void increment(Object a, String t){
		((Hashtable)frequency.get(a)).put(t,((Integer)((Hashtable) frequency.get(a)).get(t)) + 1);
	}
	
	public Integer getValue(Object a, String t){
		return ((Integer)((Hashtable) frequency.get(a)).get(t));
	}
	
	public Integer getValue(int a, int t){
		
		return ((Integer)((Hashtable) frequency.get(attributes.get(a))).get(types.get(t)));
	}
	public Integer getTypesSize(){
		return types.size();
	}
	public Integer getAttributesSize(){
		return attributes.size();
	}
	public String getType(int i){
		return (String) types.get(i);
	}
	public Object getAttribute(int i){
		return attributes.get(i);
	}
	public Integer getTotal(Object a){
		int sum = 0;
		Enumeration e = ((Hashtable) frequency.get(a)).keys();
		while(e.hasMoreElements()){
			sum += ((Integer)((Hashtable) frequency.get(a)).get(e.nextElement()));
		}
		return sum;
	}
	
	public void clear(){
		frequency.clear();
		types.clear();
		attributes.clear();
	}
}
