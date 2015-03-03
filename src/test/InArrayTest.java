package test;

import java.util.Arrays;

public class InArrayTest {
	
	public static void main(String[] args){
		
		int[] test = new int[] {11,21,13,54,58,6,74,81,90,100};
		int value = 5;
		System.out.println(Arrays.toString(test));
		System.out.print("does this contain " + value + "?: ");
		System.out.println(inArray(test,value));
	}
	
	
	protected static boolean inArray(int[] array, int value){
		boolean contains = false;
		for (int element: array){
			if (element == value) {
				contains = true;
				break;
			}
		}
		return contains;
	}
}
