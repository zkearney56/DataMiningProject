package main.structure;

/**
 * Author: Zachary Kearney
 * Last Edited: 11/30/2015
 * Container for a CategorialPoint in an array list.
 */

public class CategorialPoint {

		private String name;
		private int count;
		
		/**
		 * Initial constructor for the point, count starts at 1
		 * @param name
		 */
		
		public CategorialPoint(String name){
			this.name = name;
			count = 1;
		}
		
		/**
		 * Constructor to clone an existing point
		 * @param point
		 */
		
		public CategorialPoint(CategorialPoint point){
			this.name = point.name;
			this.count = point.count;
		}
		
		/**
		 * Increments the count by 1
		 */
		
		public void increment(){
			count++;
		}
		
		/**
		 * Returns the count
		 * @return
		 */
		
		public int getCount(){
			return count;
		}
		
		/**
		 * Returns the name
		 * @return
		 */
		
		public String getName(){
			return name;
		}
	}