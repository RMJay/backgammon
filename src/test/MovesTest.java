package test;

import java.util.List;

import Exceptions.InputFormatException;
import model.Game;
import model.Input;

public class MovesTest {

	static int[] gameState;
	
	public static void main(String[] args) throws InputFormatException {
		
		gameState = Game.getGameState();
		
		List<int[]> moves;
		moves = Input.receiveMoves();

	}

}
