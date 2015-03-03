package model;

import java.util.*;
import java.util.regex.*;

import Exceptions.InputFormatException;

public class Input {

	static Scanner _keyboard = new Scanner(System.in);
	
	public static List<int[]> receiveNetwork() throws InputFormatException{
				
	    	String inputString = _keyboard.nextLine();
	    	inputString = inputString.replaceAll("\\s", "");
	    	return parseNetworkInput(inputString);   			
	}
	
	public static List<int[]> receiveManual() throws InputFormatException{
		
	    	System.out.print(Move.stringDicePair());
	    	String inputString = _keyboard.nextLine();
			inputString = inputString.replaceAll("\\s", "");
	    	return parseManualInput(inputString);   	
	}

	protected static List<int[]> parseNetworkInput(String inputString) throws InputFormatException{
		
		if (!checkNetworkInputString(inputString)){
			throw new InputFormatException();
		}
		
		List<int[]> inputs = new ArrayList<int[]>();
		
		inputString = inputString.replaceAll("\\s", "");
		String[] split = inputString.split(":");
		
		inputs.add(parseDice(split[0]));
		
		List<String> listStringMoves = listStringMoves(split[1]);
		
		for (String stringMove: listStringMoves){
			inputs.add(parseMove(stringMove));
		}
		
		return inputs;
		
	}
	
	protected static List<int[]> parseManualInput(String inputString) throws InputFormatException{
		
		if (!checkInputStringMoves(inputString)){
			throw new InputFormatException();
		}
		
		List<int[]> inputs = new ArrayList<int[]>();
		
		List<String> listStringMoves = listStringMoves(inputString);
		
		for (String stringMove: listStringMoves){
			inputs.add(parseMove(stringMove));
		}
		
		return inputs;
		
	}

	protected static int[] parseDice(String inputStringDice){
		String[] stringArray = inputStringDice.split("-");
		int dice1 = Integer.parseInt(stringArray[0]);
		int dice2 = Integer.parseInt(stringArray[1]);
		return new int[]{dice1, dice2};
	}
	
	protected static List<String> listStringMoves(String inputStringMoves){
		List<String> listStringMoves = new ArrayList<String>();
		Pattern p = Pattern.compile("\\([\\d]+\\|[\\d]+\\)");
		Matcher m = p.matcher(inputStringMoves);
		while (m.find()) {
			listStringMoves.add(m.group());
		}
		return listStringMoves;
	}
	
	protected static int[] parseMove(String stringMove) {
		stringMove = stringMove.replaceAll("[()]", "");
		String[] stringArrayMove = stringMove.split("\\|");
		int spikeFrom = Integer.parseInt(stringArrayMove[0]);
		int spikeTo = Integer.parseInt(stringArrayMove[1]);
		return new int[] { spikeFrom, spikeTo };
	}

	protected static boolean checkNetworkInputString(String inputString){

		if(countChar(inputString,':') != 1){
			return false;
		}
		
		String[] split = inputString.split(":");
		String inputStringDice = split[0];
		String inputStringMoves = split[1];
		
		if(!checkInputStringDice(inputStringDice)){
			return false;
		}
		
		if(!checkInputStringMoves(inputStringMoves)){
			return false;
		}
			
		return true;
	}
	
	protected static boolean checkInputStringDice(String inputStringDice){
		return inputStringDice.matches("\\d-\\d");
	}
	
	protected static boolean checkInputStringMoves(String inputStringMoves){
		return inputStringMoves.matches("(\\([\\d]+\\|[\\d]+\\),?)+;?");
	}

	protected static int countChar(String s, char counted){
		int counter = 0;
		for( int i=0; i<s.length(); i++ ) {
		    if(s.charAt(i) == ':') {
		        counter++;
		    } 
		}
		return counter;
	}

}
