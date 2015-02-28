package board;
import game.*;

public class Board
{
	public static boolean gameOver;
	public static String winningColour;

	public void playGame()
	{	
		/**
		 * Continuously prints the new board state after each turn
		 */
		flipForFirstMove();
		while(!gameOver)
		{
			Visualisation.printBoard(Spike.totalSpikes);
			alternateTurn();
			if(gameOver || Piece.hasWon())
			{
				System.out.println("\n\n\n"+winningColour + " HAS WON!!!!");
				break;
			}
		}
	}	
	private void alternateTurn()
	{
		/**
		 alternates turn and allows the next player to move.
		 */
		try
		{
			Piece.rollDie();
			if(Piece.lastMove == 'W')
			{
				System.out.println("Red's Turn");
				RedPiece.chooseMove();
			}
			else if(Piece.lastMove == 'R')
			{
				System.out.println("White's Turn");
				WhitePiece.chooseMove();
			}
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	}
	public static void flipForFirstMove()
	{
		/**
		 * This method decides the first move.
		 */
		System.out.println("Red is dice 1, white is dice 2. Deciding who goes first...");
		Piece.rollDie();
		System.out.println("Dice 1: " + Piece.rolls[0] + "\nDice 2: " + Piece.rolls[1]);
		if(Piece.rolls[0] >= Piece.rolls[1])
		{
			System.out.println("Red goes first.\n\n\n");
			Piece.lastMove = 'W';
		}
		else
		{
			System.out.println("White goes first.\n\n\n");
			Piece.lastMove = 'R';
		}
	}

}
