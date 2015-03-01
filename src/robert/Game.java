package robert;

import java.util.Arrays;

public class Game {

	public enum player{
		WHITE, RED
	}
	//From protocol server is always RED, client is always WHITE
	
	private static int[] gameState = new int[26];
	private static player turn;
	private static int[] dicePair;
	private static boolean gameOver = false;
	private static player winner;
	
	protected static int[] getGameState(){
		return gameState;
	}
	
	protected static player getTurn(){
		return turn;
	}
	
	protected static int[] getDicePair(){
		return dicePair;
	}
	
	public static void play(){
		
		do {
			dicePair = diceRoll();
		} while (dicePair[0] == dicePair[1]);
		
		if (dicePair[0] > dicePair[1]) {
			turn = player.WHITE;
		} else {
			turn = player.RED;
		}
		
		while(!gameOver)
		{
			printState();
			System.out.println();
			Board.printBoard();
			System.out.println();
			System.out.println("The dice roll is: " + stringDicePair());
			System.out.print(turn + " make your move: ");
			//break;
			
			gameState = Move.nextMove();
			updateGameOver(); 
			
			if(gameOver){
				System.out.println("Game over, the winner is: " + winner);
				break;
			}
			
			switch(turn) {
			case WHITE: turn = player.RED; break;
			case RED: turn = player.WHITE; break;
			}
			
			dicePair = diceRoll();
		}
				
	}
	
	protected static void printState(){
		System.out.print("gameState variable: ");
		System.out.println(Arrays.toString(gameState));
	}

	public static void initialize(){
	
		int[] initialState = new int[26];
		initialState[1] = -2;
		initialState[6] = 5;
		initialState[8] = 3;
		initialState[12] = -5;
		initialState[13] = 5;
		initialState[17] = -3;
		initialState[19] = -5;
		initialState[24] = 2;
		
		gameState = initialState;
		
	}

	private static int[] diceRoll(){
		int dice1 = (int)(Math.random()*6 +1);
		int dice2 = (int)(Math.random()*6 +1);
		return new int[] {dice1,dice2};
	}

	private static void updateGameOver(){
		if (countWhitePieces() == 0){
			gameOver = true;
			winner = player.WHITE;
		} else if (countRedPieces() == 0){
			gameOver = true;
			winner = player.RED;
		}
	}
	
	private static int countWhitePieces(){ // including captured pieces
		int count = 0;
		for (int spike = 1; spike <= 24; spike++){
			if (gameState[spike] > 0){
				count = count + Math.abs(gameState[spike]);
			}
		}
		count = count + Math.abs(gameState[25]);
		return count;
	}
	
	private static int countRedPieces(){ // including captured pieces
		int count = 0;
		for (int spike = 1; spike <= 24; spike++){
			if (gameState[spike] < 0){
				count = count + Math.abs(gameState[spike]);
			}
		}
		count = count + Math.abs(gameState[0]);
		return count;
	}

	protected static String stringDicePair(){
		
		return Integer.toString(dicePair[0]) + "-" + Integer.toString(dicePair[1]);
	}
}
