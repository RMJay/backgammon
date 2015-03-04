package model;

import java.util.Arrays;
import java.util.List;

import Exceptions.InputFormatException;
import Exceptions.InvalidMoveException;

public class Move {

	protected enum DiceType {
		DOUBLES, SINGLES;
	}
	
	private static int[] dicePair;
	private static DiceType diceType;
	private static Game.InputLocation inputLocation;
	private static Game.PlayerType inputType;
	
	protected static int[] getDicePair(){
		return dicePair;
	}
	
	protected static DiceType getDiceType(){
		return diceType;
	}
	
	protected static Game.PlayerType getInputType(){
		return inputType;
	}
	
	protected static int[] nextMove(int[] state){
		//initializeMove();
		switch(inputLocation){
		case SERVER:
			return nextServerMove(state);
		case CLIENT:
			return nextClientMove(state);
		}
		return null;
	}

	protected static int[] nextServerMove(int[] state){
		dicePair = diceRoll();
		//dicePair = new int[] {2,4}; useful to fix dice for debugging
		setDiceType();
		System.out.print("dice roll is: ");
		System.out.print(Arrays.toString(dicePair)); //TODO remove
		System.out.print(" ");
		System.out.println(diceType);
		switch(inputType){
		case HUMAN: {
			return nextHumanMove(state);
		} 
		case AI: {
			return AI.nextAIMove(state);
		}
		default: return null;
		}
		
	}
	
	protected static int[] nextHumanMove(int[] state){
		do {
			try {
				List<int[]> moves = Input.receiveManual();
				return makeMoves(moves, state);	
			} catch (InputFormatException e) {
				System.err.println("Invalid input format, please try again");	
			} catch (InvalidMoveException e) {
		    	System.err.println("Invalid moves, please try again: " + e.getMessage());
			}
		} while(true); 
	}
	
	protected static int[] nextClientMove(int[] state){
		List<int[]> moves;
		do {
			try {
				moves = Input.receiveNetwork();
				dicePair = moves.remove(0);
				setDiceType();
		    	return makeMoves(moves,state);  	
			} catch (InputFormatException e) {
				System.err.println("Invalid input format, please try again");	
			} catch (InvalidMoveException e) {
		    	System.err.println("Invalid moves, please try again: " + e.getMessage());
			}
		} while (true);
	}
	
 	protected static void initializeMove(){
		switch(Game.getTurn()){
		case WHITE:{
			inputLocation = Game.getWhiteInputLocation();
			inputType = Game.getWhitePlayerType();
		} break;
		case RED:{
			inputLocation = Game.getRedInputLocation();
			inputType = Game.getRedPlayerType();
		} break;
		}
	}
		
	private static int[] diceRoll(){
		int dice1 = (int)(Math.random()*6 +1);
		int dice2 = (int)(Math.random()*6 +1);
		return new int[] {dice1,dice2};
	}

	private static int[] makeMoves(List<int[]> moves, int[] state) throws InvalidMoveException {
		Rules.checkMoves(moves, state);
		
		for (int[] move : moves) {
			state = makeMove(move, state);
			//Board.printBoard(state);
		}
		return state;
	}

	protected static int[] makeMove(int[] move, int[] state) {
		// (StartPosition | EndPosition)
				
		switch(Rules.getMoveType(move, state)) {
		case NORMAL: return normalMove(move, state); 
		case BEAR_OFF: return bearOffMove(move, state); 
		case CAPTURE: return captureMove(move, state);
		}
		return null;
	}
	
	private static int[] normalMove(int[] move, int[] state) {
		switch (Game.getTurn()) {
		case WHITE:{ 
			state[move[0]] --;
			state[move[1]] ++;
		} break;
		case RED:{
			state[move[0]] ++;
			state[move[1]] --;
		} break;
		}
		return state;
	}
		
	private static int[] bearOffMove(int[] move, int[] state){
		switch (Game.getTurn()) {
		case WHITE:{
			state[move[0]] --;
		} break;
		case RED:{
			state[move[0]] ++;
		} break;
		}
		return state;
	}
	
	private static int[] captureMove(int[] move, int[] state){
		switch (Game.getTurn()) {
		case WHITE:{
			state[move[0]] --;
			state[move[1]] = 1;
			state[0] --;
		} break;
		case RED:{
			state[move[0]] ++;
			state[move[1]] = -1;
			state[25] ++;	
		} break;
		}
		return state;
	}
	
	protected static void setDiceType(){
		if(dicePair[0] != dicePair[1]){
			diceType = DiceType.SINGLES;
		}
		if(dicePair[0] == dicePair[1]){
			diceType = DiceType.DOUBLES;
		}
	}

	protected static String stringDicePair(){
	
		return Integer.toString(dicePair[0]) + "-" + Integer.toString(dicePair[1]) + ":";
	}
			
}
