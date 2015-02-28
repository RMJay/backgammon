package game;
import java.util.*;

import board.*;

public class WhitePiece extends Piece
{
	static List<WhitePiece> whitePieces = new ArrayList<WhitePiece>();
	public WhitePiece(int row, int column)
	{
		super(row, column);
		whitePieces.add(this);
	}

	public static void chooseMove() throws Exception
	{
		System.out.println("Dice 1: " + rolls[0] + "\nDice 2: " + rolls[1]);
		int diceChoice = 0;
		Spike newSpike = null;
		
		
		if (rolls[0] != rolls[1])
		{
			diceChoice = chooseDiceRoll();
			choosePiece(rolls[diceChoice]);
			while (true) // continuous loop until valid input is received
			{
				int desiredSpike = chosenPiece.spike - rolls[diceChoice];
				newSpike = Spike.totalSpikes[desiredSpike];
				if (newSpike.counter >= -1) // if space is empty, has
											// whites, or 1 red, allow move
				{
					movePiece(chosenPiece, (rolls[diceChoice]));
					break;
				} else
				{
					System.out.println("This space is occupied by "
							+ Math.abs(newSpike.counter) + " red pieces.");
					diceChoice = chooseDiceRoll();
					choosePiece(rolls[diceChoice]);
				}
			}
			chooseSecondMove(diceChoice);

			lastMove = 'W';
		} else
		{
			doublesMove(rolls[0]);
			lastMove = 'W';
		}
	}
	public static void doublesMove(int roll) throws Exception
	{
		System.out.println("\nDOUBLES!!!! Choose 4 points to move: ");
		int numOfMoves = 0;
		Spike newSpike = null;
		while(numOfMoves != 4)
		{
			choosePiece(roll);
			while(true) //continuous loop until valid input is received
			{
				newSpike = Spike.totalSpikes[chosenPiece.spike-rolls[0]];
				if(newSpike.counter>=-1)
				{	
					movePiece(chosenPiece, rolls[0]);
					numOfMoves++;
					break;
				}
				else
				{
					System.out.println("This space is occupied by "+Math.abs(newSpike.counter)+" red pieces.");
					choosePiece(roll);
				}
			}
		}
	}
	public static void chooseSecondMove(int diceChoice) throws Exception
	{
		int roll = 0;
		if(diceChoice == 1)
			roll = rolls[0];
		else if (diceChoice == 0)
			roll = rolls[1];
		Spike newSpike = null;
		while (true) // continuous loop until valid input is received
		{

			choosePiece(roll);
			int desiredSpace = chosenPiece.spike - roll;
			newSpike = Spike.totalSpikes[desiredSpace];
			if (newSpike.counter >= -1 && desiredSpace >= 0) // if space is empty,has whites, or 1 red, allow move
			{
				movePiece(chosenPiece, roll);
				break;
			} else
			// else repeatedly ask for input
			{
				System.out.println("This space is occupied by "
						+ Math.abs(newSpike.counter) + " red pieces.");
				choosePiece(roll);
			}
		}
	}
	public static void movePiece(Piece currentPiece, int roll) throws Exception
	{
		Spike currentSpike = Spike.totalSpikes[currentPiece.spike];
		int index = whitePieces.indexOf(currentPiece);
		whitePieces.get(index).spike = currentPiece.spike-roll;
		//Spike currentSpike = Spike.totalSpikes[currentPiece.spike];
		//currentPiece.spike =currentPiece.spike-roll;
		Spike newSpike = Spike.totalSpikes[currentPiece.spike];

		if (currentPiece instanceof WhitePiece&& currentSpike.counter>0)
		{
			//if spike is empty or has another white piece, allow move
			if(newSpike.counter >=0 && currentPiece.spike >0)
			{
				System.out.println(currentPiece.spike);
				updateBoard(currentPiece, newSpike, currentSpike);
			}
			else if(newSpike.counter == -1 && currentPiece.spike != 0) //it has a red piece
			{
				capturePiece(currentPiece, newSpike, currentSpike);
			}
			else if(currentPiece.spike == 0) //piece can bear off if it rolls exactly off board
			{
				score(currentPiece,newSpike, currentSpike);
			}
		}
	}

}
