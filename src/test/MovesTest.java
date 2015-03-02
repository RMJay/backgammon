package test;

import java.util.List;

import robert.Game;
import robert.Input;
import robert.InputFormatException;

public class MovesTest {

	static int[] gameState;
	
	public static void main(String[] args) throws InputFormatException {
		
		gameState = Game.getGameState();
		
		List<int[]> moves;
		moves = Input.receiveMoves();

	}

}
