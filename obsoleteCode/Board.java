package game;

public class Board
{
	static final int[] INITIAL = new int[26];
	static int[] state = new int[26];

	static
	{

		INITIAL[1] = -2; // red is negative
		INITIAL[6] = 5;
		INITIAL[8] = 3;
		INITIAL[12] = -5;
		INITIAL[13] = 5;
		INITIAL[17] = -3;
		INITIAL[19] = -5;
		INITIAL[24] = 2; // white is positive

	}
	public void playGame()
	{	
		// while(!gameOver)
		{
			//Visualisation.printBoard(state);
			//alternateTurn();
		}
	}	
	public static void reset()
	{
		state = INITIAL;
	}
}
