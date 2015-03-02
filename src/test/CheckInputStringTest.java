package test;

import java.util.Arrays;

import model.Input;

import com.sun.xml.internal.ws.util.StringUtils;

public class CheckInputStringTest {

	public static void main(String[] args){
		
		/*
		System.out.println("Please enter input string");
		String input = EasyIn.getString();
		if(Input.checkInputString(input)){
			System.out.println("valid");
		} else {
			System.out.println("false");
		}
		*/
		
		String inputStringDice = "4-7";
		int[] dice = Input.parseDice(inputStringDice);
		System.out.println(Arrays.toString(dice));
		
	}

}
