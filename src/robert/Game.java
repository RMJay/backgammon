package robert;

import game.Piece;
import game.Visualisation;

import java.util.Arrays;

import board.Spike;

public class Game {

	public enum player{
		WHITE, RED
	}
	
	protected static int[] gameState = new int[26];
	protected static boolean gameOver = false;
	protected static player winner;
	
	public static void play(){

		int[] dicePair;
		player turn;
		
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
			Board.printBoard(gameState);
			System.out.println();
			System.out.println("It is " + turn +"s turn, with a dice roll of: " + stringDicePair(dicePair));
			
			break;
			/*
			gameState = Move.nextMove(turn,dicePair, gameState);
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
			*/
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

	protected static int[] diceRoll(){
		int dice1 = (int)(Math.random()*6 +1);
		int dice2 = (int)(Math.random()*6 +1);
		return new int[] {dice1,dice2};
	}

	protected static void updateGameOver(){
		if (countWhitePieces() == 0){
			gameOver = true;
			winner = player.WHITE;
		} else if (countRedPieces() == 0){
			gameOver = true;
			winner = player.RED;
		}
	}
	
	protected static int countWhitePieces(){ // including captured pieces
		int count = 0;
		for (int spike = 1; spike <=24; spike++){
			if (gameState[spike] > 0){
				count = count + Math.abs(gameState[spike]);
			}
		}
		count = count + gameState[25];
		return count;
	}
	
	protected static int countRedPieces(){ // including captured pieces
		int count = 0;
		for (int spike = 1; spike <=24; spike++){
			if (gameState[spike] < 0){
				count = count + Math.abs(gameState[spike]);
			}
		}
		count = count + gameState[0];
		return count;
	}

	protected static String stringDicePair(int[] dicePair){
		return Integer.toString(dicePair[0]) + "-" + Integer.toString(dicePair[1]);
	}
}
