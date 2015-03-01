package robert;

public class Test {

	public static void main(String[] args) {
		Game.initialize();
		Game.printState();
		System.out.println();
		Board.printBoard();
		System.out.println();
		//System.out.println("There are " + Game.countWhitePieces() + " white pieces remaining and " + Game.countRedPieces() + " red pieces remaining");
	}

}
