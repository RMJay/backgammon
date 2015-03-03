package test;

import model.Board;
import model.Game;

public class Test {

	public static void main(String[] args) {
		Game.initialize();
		Game.printState();
		System.out.println();
		Board.printBoard(Game.getGameState());
		System.out.println();
		//System.out.println("There are " + Game.countWhitePieces() + " white pieces remaining and " + Game.countRedPieces() + " red pieces remaining");
	}

}
