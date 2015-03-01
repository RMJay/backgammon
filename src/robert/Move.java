package robert;

import java.util.List;

import robert.Game.player;

public class Move {

	private static int[] gameState;
	
	private enum moveType {
		NORMAL, BEAR_OFF, CAPTURE
	}
	
	protected static int[] nextMove() {

		do {
			List<int[]> moves = receiveMoves();
		} while (!checkValid(moves));

		makeMoves(moves);
		return gameState;
	}

	private static makeMoves(List<int[]> moves) {
		for (int[] move : moves) {
			makeMove(move);
		}
	}

	private static makeMove(int[] move) {
		// (StartPosition | EndPosition)
		switch(moveType(move)) {
		case NORMAL: normalMove(move);
		case BEAR_OFF: bearOffMove(move);
		}

	}
	
	private static moveType moveType(int[] move){
		// TODO 
		// add other move types
		return moveType.NORMAL;
	}
	
	private static void normalMove(int[] move) {
		switch (Game.getturn()) {
		case WHITE: 
			gameState[move[0]] = gameState[move[0]] --;
			gameState[move[1]] = gameState[move[1]] ++;
		case RED:
			gameState[move[0]] = gameState[move[0]] ++;
			gameState[move[1]] = gameState[move[1]] --;
		}
}
