package main.structure;

public class ArrayListTest {

	public static void main(String args[]){
		
		DMArrayList<Object> test = new DMArrayList<Object>();

		for(int i = 0; i < 100; i++){
			test.add(i);
		}
		test.add(25, 100);
		test.remove(50);
		test.remove(4);
		test.remove(4);
		for(int i = 0; i < test.size(); i++){
			System.out.println(test.get(i));
		}
		test.clear();
		test.add("New");
		test.add("Fuck");
		for(int i = 0; i< test.size(); i++){
			System.out.println(test.get(i));
		}
	}
}