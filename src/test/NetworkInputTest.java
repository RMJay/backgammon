package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Input;
import Exceptions.InputFormatException;

public class NetworkInputTest {

	public static void main(String[] args) throws InputFormatException{
	
		System.out.println("Please enter network input string: ");
		List<int[]> inputs = Input.receiveNetwork();
		printInputs(inputs);
					
	}
		
	public static void printInputs(List<int[]> inputs) throws InputFormatException{
				
		ListIterator<int[]> iterator = inputs.listIterator(); 
		
		int no = 1;
		for (int i = 1; i <= inputs.size(); i++){
			if (i == 1){
				System.out.print("dice: ");
			} else {
				System.out.print("move" + no + ": ");
				no = no++;
			}
			System.out.println(Arrays.toString(iterator.next()));
		}
	}

	//3-5:(1|2),(2|1),(3|1);
}
