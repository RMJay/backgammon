package test;

import java.util.List;

import model.Game;
import model.Input;
import model.InputFormatException;

public class MovesTest {

	static int[] gameState;
	
	public static void main(String[] args) throws InputFormatException {
		
		gameState = Game.getGameState();
		
		List<int[]> moves;
		moves = Input.receiveMoves();

	}

}
