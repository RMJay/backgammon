package robert;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {

	static BufferedReader _keyboard;

	static void error(String s) {
		System.err.println("-!- " + s);
	}

	protected static List<int[]> receiveMoves() throws InputFormatException{
		List<int[]> moves;
		String input = readKeyboard();
		
		int count = 0;
		int maxTries = 3;
		while(count <= maxTries) {
		    try {
		    	moves = parseMoves(input);
		    	return moves;
		    } catch (InputFormatException e) {
		        System.err.println("Invalid input, please try again");
		        if (++count == maxTries) throw e;
		    } 
		}
		return null;
	
		
	}

	private static List<int[]> parseMoves(String input)
			throws InputFormatException {

		List<int[]> moves = new ArrayList<int[]>();

		input = input.replaceAll("\\s", "");
		String[] split = input.split(":");

		String stringDice = split[0];
		String stringMoves = split[1];

		if (!stringDice.matches("\\d-\\d")) {
			throw new InputFormatException();
		}

		if (!stringMoves.matches("(\\([\\d]+\\|[\\d]+\\),?)+;?")) {
			throw new InputFormatException();
		}

		List<String> listStringMoves = new ArrayList<String>();
		Pattern p = Pattern.compile("(\\([\\d]+\\|[\\d]+\\))");
		Matcher m = p.matcher(stringMoves);
		while (m.find()) {
			listStringMoves.add(m.group());
		}

		for (String stringMove : listStringMoves) {
			int[] move = parseMove(stringMove);
			moves.add(move);
		}

		return moves;
	}

	private static int[] parseMove(String stringMove) {
		stringMove = stringMove.replaceAll("[()]", "");
		String[] stringArrayMove = stringMove.split("\\|");
		int spikeFrom = Integer.parseInt(stringArrayMove[0]);
		int spikeTo = Integer.parseInt(stringArrayMove[1]);
		return new int[] { spikeFrom, spikeTo };
	}

	protected static String readKeyboard() {

		String line = null;

		try {
			if (_keyboard.ready())
				line = _keyboard.readLine();
		} catch (java.io.IOException e) {
			error("readKeyboard(): problem reading! " + e.getClass().getName());
			System.exit(-1);
		}

		return line;

	}

}
