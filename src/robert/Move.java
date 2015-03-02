package robert;

import java.util.Arrays;
import java.util.List;

import robert.Game.player;

public class Move {

	private static int[] gameState;
	
	private enum moveType {
		NORMAL, BEAR_OFF, CAPTURE
	}
	
	protected static int[] nextMove() throws InputFormatException {

		//bullshit
		gameState = Game.getGameState();
		
		List<int[]> moves;
		moves = Input.receiveMoves();
		/*
		do {
			moves = Input.receiveMoves();
		} while (!checkValid(moves));

		for (int[] move : moves){
			System.out.println(Arrays.toString(move));
		}*/

		//makeMoves(moves);
		
		return gameState;
	}

	private static void makeMoves(List<int[]> moves) {
		for (int[] move : moves) {
			makeMove(move);
		}
	}

	private static void makeMove(int[] move) {
		// (StartPosition | EndPosition)
				
		switch(moveType(move)) {
		case NORMAL: normalMove(move);
		case BEAR_OFF: bearOffMove(move);
		case CAPTURE: captureMove(move);
		}

	}
	
	private static moveType moveType(int[] move){
		// TODO 
		// add other move types
		return moveType.NORMAL;
	}
	
	private static void normalMove(int[] move) {
		switch (Game.getTurn()) {
		case WHITE: 
			gameState[move[0]] = gameState[move[0]] --;
			gameState[move[1]] = gameState[move[1]] ++;
		case RED:
			gameState[move[0]] = gameState[move[0]] ++;
			gameState[move[1]] = gameState[move[1]] --;
		}
	}
		
	private static void bearOffMove(int[] move){
		switch (Game.getTurn()) {
		case WHITE:
			gameState[move[0]] = gameState[move[0]] --;
		case RED:
			gameState[move[0]] = gameState[move[0]] ++;
		}
	}
	
	private static void captureMove(int[] move){
		switch (Game.getTurn()) {
		case WHITE:
			gameState[move[0]] = gameState[move[0]] --;
			gameState[move[1]] = 1;
			gameState[0] = gameState[0] --;
		case RED:
			gameState[move[0]] = gameState[move[0]] ++;
			gameState[move[1]] = -1;
			gameState[25] = gameState[25] ++;	
		}
	}

	private static boolean checkValid(List<int[]> moves){
		// TODO 
		// add proper implementation
		return true;
	}
		
}
