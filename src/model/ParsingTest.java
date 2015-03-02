package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsingTest {

	public static void main(String[] args) throws InputFormatException{
	
		System.out.println("Please enter string: ");
		List<int[]> inputs = Input.receiveInput();
		printInputs(inputs);
					
	}
		
	public static void printInputs(List<int[]> inputs) throws InputFormatException{
				
		ListIterator<int[]> iterator = inputs.listIterator(); 
		
		for (int i = 1; i <= inputs.size(); i++){
			if (i == 1){
				System.out.print("dice: ");
			} else {
				int no = i - 1;
				System.out.print("move" + no + ": ");
			}
			System.out.println(Arrays.toString(iterator.next()));
		}
	}

}
