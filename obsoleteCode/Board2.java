package board;

import game.*;
import game.Visualisation.Parameters;

public class Board
{
	static int[] board = new int[26];
	
	public static int boardState[][];
	
	public Board() 
	{
		//instantiates board with empty spaces
		boardState = new int[Parameters.LENGTH][Parameters.WIDTH];
		
		for (int row = 0; row < Parameters.LENGTH; row++)
		{
			for (int column = 0; column < Parameters.WIDTH; column++)
			{
				boardState[row][column] = Parameters.EMPTY;
				printBoardShape(row, column);
				printPieces(row,column);
				//printCoordinates(row, column);
			}
		}
	}
	public void playGame()
	{	
		// while(!gameOver)
		{
			Visualisation.printBoard(boardState);
			//alternateTurn();
		}
	}	
	public void printBoardShape(int row, int column)
	{
		if (column == 7)
		{
			boardState[row][column] = Parameters.NULL;
		}
		if ((row > 5 && row < 9) || column == 0 || row == 0 || column ==14)
		{
			boardState[row][column] = Parameters.SPACE;
		}
	}
	public void printPieces(int row, int column)
	{
		/*
		 * prints pieces in their respective starting positions
		*/
		if(row != 0)
		{	
			if ((column == 13 && row < 3) || (column == 5 && row > 10)
					|| (column == 8 && row > 8) || (column == 1 && row < 6))
			{
				new WhitePiece(row, column);
			}
			if ((column == 1 && row > 8) || (column == 5 && row < 4)
					|| (column == 13 && row > 11) || (column == 8 && row < 6))
			{
				new RedPiece(row, column);
			}
		}
	}
	public void resetBoard()
	{
		/**
		 * INTERNAL RESPRESENTATION
		 */
		//0th element stores red captured, 25th stores white captured
		//1 through 24 will coordinate with spikes in visual representation
		
		board[24] = 2; //white is positive
		board[1] = -2; // red is negative
		board[6] = 5;
		board[8] = 3;
		board[12] = -5;
		board[13] = 5;
		board[17] = -3;
		board[19] = -5;
		
	}
	public static void printCoordinates(int row, int column)
	{
		int counter = 0;
		for (int i = 0; i < Parameters.LENGTH; ++i)
		{
			if (row != 0 && column == 0 && (row > 8 || row < 6))
			{
				boardState[row][column] = counter;
				counter++;
			}
		}
	}
}
