package main.structure;
/**
 * Author: Zachary Kearney
 * Last Edited: 11/30/2015
 * Container for a CategorialPoint in an array list.
 */
public class CategorialPoint {

		private String name;
		private int count;
		
		public CategorialPoint(String name){
			this.name = name;
			count = 1;
		}
		
		public void increment(){
			count++;
		}
		
		public int getCount(){
			return count;
		}
		
		public String getName(){
			return name;
		}
	}