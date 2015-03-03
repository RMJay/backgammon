package model;

import java.util.Arrays;

import Exceptions.InputFormatException;

public class Game {

	//From protocol server is always RED, client is always WHITE
	public enum PlayerColour{
		WHITE, RED
	}
	
	public enum InputType{
		NETWORK,MANUAL
	}
	
	public enum PlayerType{
		HUMAN,AI
	}
	
	private static int[] gameState = new int[26];
	private static PlayerColour turn;
	private static InputType whiteInputType;
	private static InputType redInputType;
	private static PlayerType whitePlayerType;
	private static PlayerType redPlayerType;
	private static boolean gameOver = false;
	private static PlayerColour winner;
	
	public static int[] getGameState(){
		return gameState;
	}
	
	protected static PlayerColour getTurn(){
		return turn;
	}

	protected static InputType getRedInputType(){
		return redInputType;
	}
	
	protected static InputType getWhiteInputType(){
		return whiteInputType;
	}
	
	protected static PlayerType getRedPlayerType(){
		return redPlayerType;
	}
	
	protected static PlayerType getWhitePlayerType(){
		return whitePlayerType;
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
		whiteInputType = InputType.MANUAL;
		redInputType = InputType.MANUAL;
		whitePlayerType = PlayerType.HUMAN;
		redPlayerType = PlayerType.AI;
	}
	
	public static void play() throws InputFormatException{
		
		flipForFirstTurn();
		
		while(!gameOver)
		{
			printState(gameState);
			System.out.println();
			Board.printBoard(gameState);
			System.out.println();
			System.out.println(turn + " make your move: ");
			
			gameState = Move.nextMove(gameState);
			
			updateGameOver(); 
			
			if(gameOver){
				System.out.println("Game over, the winner is: " + winner);
				break;
			}
			
			switch(turn) {
			case WHITE: turn = PlayerColour.RED; break;
			case RED: turn = PlayerColour.WHITE; break;
			}
			
		}
				
	}
	
	protected static void printState(int[] state){
		System.out.print("gameState variable: ");
		System.out.println(Arrays.toString(state));
	}

	private static void updateGameOver(){
		if (countWhitePieces() == 0){
			gameOver = true;
			winner = PlayerColour.WHITE;
		} else if (countRedPieces() == 0){
			gameOver = true;
			winner = PlayerColour.RED;
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

	protected static void flipForFirstTurn(){
		if ((int)(Math.random()*2) == 0){
			turn = PlayerColour.WHITE;
		} else { turn = PlayerColour.RED;
		}
	}
}
