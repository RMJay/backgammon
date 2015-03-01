package robert;

import java.io.BufferedReader;
import java.util.List;

public class Input {

	static BufferedReader _keyboard;
	static void error(String s) { System.err.println("-!- " + s); }

	protected static List<int[]> receiveMoves() {
		List<int[]> moves = parseMoves(readKeyboard());
		return moves;
	}

	private static List<int[]> parseMoves(String movesInput) {
		
		String[] split = movesInput.split(":");
		if split[0]

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

