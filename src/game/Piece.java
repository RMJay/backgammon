package game;

import java.util.*;

import board.*;

public abstract class Piece
{
	protected int row;
	protected int spike; //spike it resides on
	public static char lastMove;
	public static int[] rolls;
	static boolean validChoice = false;
	
	public static Piece chosenPiece;
	
	static List<Piece> pieces = new ArrayList<Piece>();
	
	public Piece(int row, int spike)
	{
		this.spike = spike;
		this.row = row;
		
		//adds pieces with specific spike number to spike object's List
		pieces.add(this);
	}

	public static void rollDie()
	{
		/**
		 * "Rolls the dice" by returning a int[] with two random
		 * numbers between 1 and 6
		 */
		int roll1 =randNum();
		int roll2 =randNum();
	
		
		//repeat rolls if over 6 or below 1
		while(roll1 >6 || roll1 < 1)
			roll1 = randNum();
		
		while(roll2 >6 || roll2 < 1)
			roll2 = randNum();
		
		int[] i = {roll1, roll2};
		rolls = i;
	}
	public static int randNum()
	{
		return (int) (Math.random()*10);
	}
	public static void findPiece(int spikeNum)
	{
		/**
		 * Based on user input, this function can 
		 * find the pieces that occur on the specified spike
		 */
		ListIterator<Piece> li = pieces.listIterator();
		while(li.hasNext())
		{
			Piece p = li.next();
			if(p.spike == spikeNum)
			{
				chosenPiece = p;
				break;
			}
		}
	}

	public static void choosePiece(int roll) throws Exception
	{
		int spikeNum = -1;
		Scanner input;
			while ((spikeNum < 0 || !validChoice)) // if chosen spike is empty or the move is illegal
			{
			
				System.out.println("\nChoose a point to move a piece from.");
				input = new Scanner(System.in);
				String coord;
				coord = input.nextLine();
				spikeNum = Integer.parseInt(coord);
				if (Spike.totalSpikes[spikeNum].counter != 0) // checks if spike is empty or not
					validChoice = true;
				else
				{
					System.out.println("This point is empty.");
					validChoice = false;
					continue;
				}
				if (!isValidMove(spikeNum))
				{
					continue;
				}
				findPiece(spikeNum);
				if (!checkValidMoves(roll))
				{
					System.out.println("No more valid moves.");
					break;
				}
				if ((chosenPiece instanceof RedPiece
						&& (chosenPiece.spike + roll == 25) || (chosenPiece instanceof WhitePiece && chosenPiece.spike
						- roll == 0))
						&& !canBearOff())
				{
					System.out
							.println("Can't bear off until all of your pieces are on the home board.");
					continue;
				}
				if (!isOnBoard(roll))
				{
					System.out.println("Must roll exactly to get off board.");
				}
			}
		validChoice = false; //reset variable
	}
	public static int chooseDiceRoll()
	{
		String s = "0";
		int diceChoice;
		System.out.println("\nChoose roll by entering '1' or '2'.");
		Scanner input = new Scanner(System.in);
		
		while (true)
		{
			if(hasWon())
				return 1;
			input = new Scanner(System.in);
			s = input.nextLine();
			s = s.trim();
			if (s.equals("1") || s.equals("2"))
			{
				diceChoice = Integer.parseInt(s);
				return diceChoice-1;
			}
			else
				System.out.println("Invalid input. Enter a '1' or a '2'");
		}
	}
	public static void capturePiece(Piece currentPiece,Spike newSpike, Spike currentSpike)
	{
		System.out.println("\nCAPTURE!!!!\n");
		if( currentPiece instanceof WhitePiece) //white is capturing red
		{
			currentSpike.counter--;
			newSpike.counter =1;
			Spike.totalSpikes[0].counter--; // storing how many red are captured
			System.out.println("Number of red captured: " +Math.abs(Spike.totalSpikes[0].counter) );
		}
		if( currentPiece instanceof RedPiece) //red is capturing white
		{
			currentSpike.counter++;
			newSpike.counter =-1;
			Spike.totalSpikes[25].counter++; // storing how many white are captured
			System.out.println("Number of white captured: " +Spike.totalSpikes[25].counter );
		}
	}
	public static void score(Piece currentPiece,Spike newSpike, Spike currentSpike)
	{
		if(currentPiece instanceof WhitePiece) //white is capturing red
		{
			currentSpike.counter--;
			Spike.whiteOffBoard++;
			WhitePiece.whitePieces.remove(currentPiece);
			System.out.println("White Scores! Score: " + Spike.whiteOffBoard);
		}
		if(currentPiece instanceof RedPiece) //red is capturing white
		{
			RedPiece.redPieces.remove(currentPiece);
			currentSpike.counter++;
			Spike.redOffBoard++;
			System.out.println("Red Scores! Score: "+ Spike.redOffBoard);
		}
	}
	public static void updateBoard(Piece currentPiece, Spike newSpike, Spike currentSpike)
	{
		if( currentPiece instanceof WhitePiece) //white is capturing red
		{
			currentSpike.counter--;
			newSpike.counter++;
		}
		if( currentPiece instanceof RedPiece) //red is capturing white
		{
			currentSpike.counter++;
			newSpike.counter--;
		}
	}
	public static boolean isValidMove(int spikeNum)
	{
		//checks if player is choosing a spike with pieces of opposite colour
		if(lastMove == 'R' && Spike.totalSpikes[spikeNum].counter < 0)
		{
			System.out.println("It's White's turn. Choose Again.");
			validChoice = false;
		}
		else if(lastMove == 'R' && Spike.totalSpikes[spikeNum].counter > 0)
		{
			validChoice = true;
		}
		//checks if player is choosing a spike with pieces of opposite colour
		if(lastMove == 'W'&& Spike.totalSpikes[spikeNum].counter > 0) 
		{
			System.out.println("It's Red's turn. Choose Again.");
			validChoice = false;
		}
		else if(lastMove == 'W' && Spike.totalSpikes[spikeNum].counter < 0)
		{
			validChoice = true;
		}
		if(validChoice)
		{
			return true;
		}
		else
			return false;
	}
	public static boolean isOnBoard(int roll)
	{
		if (chosenPiece instanceof RedPiece)
		{
			int desiredSpike =chosenPiece.spike + roll;
			if (desiredSpike >25)
			{
				validChoice = false;
			}
			else 
				return true;
		}
		if (chosenPiece instanceof WhitePiece)
		{
			int desiredSpike =chosenPiece.spike - roll;
			if (desiredSpike <0)
			{
				validChoice = false;
			}
			else
				return true;
		}
		if(validChoice)
		{
			return true;
		}
		return false;
	}
	protected static boolean canBearOff()
	{
		/**
		 * Checks to see that all pieces are in the home board before
		 * one can bear pieces off
		 */
		boolean inHomeBoard = false;
		if (lastMove == 'W')
		{
			ListIterator<RedPiece> redPieces = RedPiece.redPieces.listIterator();
			while (redPieces.hasNext())
			{
				if (redPieces.next().spike >= 19)
				{
					inHomeBoard = true;
				} 
				else
				{
					inHomeBoard = false;
					break;
				}
			}
		}
		else
		{
			ListIterator<WhitePiece> whitePieces = WhitePiece.whitePieces.listIterator();
			while (whitePieces.hasNext())
			{
				if (whitePieces.next().spike <= 6)
				{
					inHomeBoard = true;
				} 
				else
				{
					inHomeBoard = false;
					break;
				}
			}
		}
		if(inHomeBoard)
		{
			validChoice = true;
			return true;
		}
		else
		{
			validChoice = false;
			return false;
		}
	}

	public static boolean hasWon()
	{
		/**
		 * Checks to see if all pieces of one colour have bore off
		 */
		int redCaptured = Spike.totalSpikes[0].counter;
		int whiteCaptured = Spike.totalSpikes[25].counter;
		if (RedPiece.redPieces.size() == 0
				&& redCaptured == 0)
		{
			Board.winningColour = "RED";
			Board.gameOver = true;
			return true;
		}
	
		if (WhitePiece.whitePieces.size() == 0
				&& whiteCaptured == 0)
		{
			Board.winningColour = "WHITE";
			Board.gameOver = true;
			return true;
		} 
		return false;
	}
	public static boolean checkValidMoves(int roll)
	{
		if(chosenPiece instanceof RedPiece)
		{
			ListIterator<RedPiece> rPieces = RedPiece.redPieces.listIterator();
			boolean movesAvailable = false;
			while (rPieces.hasNext())
			{
				RedPiece nextPiece = rPieces.next();
				int possibleMove = nextPiece.spike + roll;
				if (possibleMove <= 25)
				{
					if (Spike.totalSpikes[possibleMove].counter <= 1)
					{
						validChoice = true;
						movesAvailable = true;
						break;
					} else
					{
						validChoice = false;
						movesAvailable = false;
					}
				}
			}
			if (!movesAvailable)
			{
				lastMove = 'R';
				return false;
			}
		}
		else if(chosenPiece instanceof WhitePiece)
		{
			ListIterator<WhitePiece> pieces = WhitePiece.whitePieces.listIterator();
			boolean movesAvailable = false;
			while (pieces.hasNext())
			{
				WhitePiece nextPiece = pieces.next();
				int possibleMove = nextPiece.spike - roll;
				if (possibleMove >= 0)
				{
					if (Spike.totalSpikes[possibleMove].counter >= -1)
					{
						validChoice = true;
						movesAvailable = true;
						break;
					} else
					{
						validChoice = false;
						movesAvailable = false;
					}
				}
			}
			if(!movesAvailable)
			{
				lastMove = 'W';
				return false;
			}
		}
		return true;
			
	}
}
