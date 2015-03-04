package model;

import java.util.Arrays;
import java.util.List;

import Exceptions.InvalidMoveException;

public class Rules {
	
	protected enum MoveType {
		NORMAL, BEAR_OFF, CAPTURE
	}

	protected static void checkMoves(List<int[]> moves, int[] state) throws InvalidMoveException{
		
		//moves = removeForfeitTurn(moves);
		checkMovesOutOfBounds(moves);
		checkMovesCount(moves);
		checkZeroMoves(moves);
		checkWrongDirections(moves);
		checkDistances(moves);
		
		int[] tempGameState = state.clone();
		for (int[] move : moves) {
			checkMove(move, tempGameState);
			tempGameState = Move.makeMove(move, tempGameState);
		}
	}
	
	protected static void checkMove(int[] move, int[] state) throws InvalidMoveException{
		checkMoveRightPiece(move, state);
		switch(getMoveType(move, state)) {
		case NORMAL: break; //checkNormalMove(move, state); 
		case BEAR_OFF: checkBearOffMove(state); break;
		case CAPTURE: checkCaptureMove(move, state); break;
		}
	}
	
	protected static MoveType getMoveType(int[] move, int[] state){
		switch(Game.getTurn()){
		case WHITE: {
			if(move[1] == 0){ //is destination spike white's home?
				return MoveType.BEAR_OFF;
			} else if (state[move[1]] < 0){ //are there red pieces at the destination spike?
				return MoveType.CAPTURE;
			} else {
				return MoveType.NORMAL;
			}
		}
		case RED: {
			if(move[1] == 25){ //is destination spike reds's home?
				return MoveType.BEAR_OFF;
			} else if (state[move[1]] > 0){ //are there white pieces at the destination spike?
				return MoveType.CAPTURE;
			} else {
				return MoveType.NORMAL;
			}
		}
		default: return null;
		}

	}
	
	protected static void checkNormalMove(int[] move, int[] state) throws InvalidMoveException{
		//intentionally empty
	}
	
	protected static void checkBearOffMove(int[] state) throws InvalidMoveException{{
		switch (Game.getTurn()){
		case WHITE: checkWhiteCanBearOff(state); break;
		case RED: checkRedCanBearOff(state); break;
		}
		}
	}
	
	protected static void checkCaptureMove(int[] move, int[] state) throws InvalidMoveException{
		int piecesToCapture = Math.abs(state[move[1]]);
		if (piecesToCapture > 1){
			throw new InvalidMoveException("you can't capture a stack of more than 1 piece");
		}
	}
	
	protected static void checkZeroMoves(List<int[]> moves) throws InvalidMoveException{
		int moveNo = 1;
		for (int[] move: moves){
			if (move[0] == move[1]) throw new InvalidMoveException("move" + moveNo + " zeroMove");
			moveNo++;
		}
	}
	
	protected static void checkWrongDirections(List<int[]> moves) throws InvalidMoveException{
		int moveNo = 1;
		for (int[] move: moves){
			int distance = move[1] - move[0];
			switch(Game.getTurn()){
			case WHITE: if (distance > 0) throw new InvalidMoveException("move" + moveNo + " wrong direction"); break;
			case RED: if (distance < 0) throw new InvalidMoveException("move" + moveNo + " wrong direction"); break;
			}
			moveNo++;
		}
	}
	
	protected static void checkDistances(List<int[]> moves) throws InvalidMoveException{
		switch (Move.getDiceType()){
		case SINGLES: checkSinglesDistances(moves); break;
		case DOUBLES: checkDoublesDistances(moves); break;
		}
	}
	
	protected static void checkSinglesDistances(List<int[]> moves) throws InvalidMoveException{
		int moveNo = 1;
		int distance;
		int previousDistance = 0;
		for (int[] move: moves){
			if (moveNo == 1){
				distance = Math.abs(move[1] - move[0]);
				if (!inArray(Move.getDicePair(), distance)){
					throw new InvalidMoveException("move" + moveNo + " invalid distance for dice roll");
				}
				previousDistance = distance;
			} else {
				distance = Math.abs(move[1] - move[0]);
				if (!inArray(Move.getDicePair(), distance)){
					throw new InvalidMoveException("move" + moveNo + " invalid distance for dice roll");
				}
				if (distance == previousDistance){
					throw new InvalidMoveException("move" + moveNo + " invalid repetition");
				}
			}
			moveNo++;
		}
	}
	
	protected static void checkDoublesDistances(List<int[]> moves) throws InvalidMoveException{
		int moveNo = 1;
		for (int[] move: moves){
			int distance = Math.abs(move[1] - move[0]);
			if (distance != Move.getDicePair()[0]){
				throw new InvalidMoveException("move" + moveNo + " invalid distance for dice roll");
			}
			moveNo++;
		}
	}

	protected static void checkMovesCount(List<int[]> moves) throws InvalidMoveException{
		switch(Move.getDiceType()){
		case SINGLES: if (moves.size() != 2) throw new InvalidMoveException("invalid number of moves, 2 expected"); break;
		case DOUBLES: if (moves.size() != 4) throw new InvalidMoveException("invalid number of moves, 4 expected"); break;
		}
	}

	protected static boolean inArray(int[] array, int value){
		boolean contains = false;
		for (int element: array){
			if (element == value) {
				contains = true;
				break;
			}
		}
		return contains;
	}

	protected static void checkWhiteCanBearOff(int[] state) throws InvalidMoveException{
		for(int spike = 7; spike <= 25; spike++){ //for all of the non-home spikes
			if (state[spike] > 0) { //are there any white pieces?
				throw new InvalidMoveException("Cannot bear-off, not all pieces in the home quadrant");
			}
		}
	}
	
	protected static void checkRedCanBearOff(int[] state) throws InvalidMoveException{
		for(int spike = 18; spike >= 0; spike--){ //for all of the non-home spikes
			if (state[spike] < 0) { //are there any red pieces?
				throw new InvalidMoveException("Cannot bear-off, not all pieces in the home quadrant");
			}
		}
	}

	protected static void checkMoveRightPiece(int[] move, int[] state) throws InvalidMoveException{
		
		switch(Game.getTurn()){
		case WHITE: {
			if (state[move[0]] == 0) {
				throw new InvalidMoveException("no piece to move");
			} if (state[move[0]] < 0) {
				throw new InvalidMoveException("trying to move the wrong piece");
			} break;
		}
		case RED:{
			if (state[move[0]] == 0) {
				throw new InvalidMoveException("no piece to move");
			} if (state[move[0]] > 0) {
				throw new InvalidMoveException("trying to move the wrong piece");
			} break;
		}
		}
		
	}

	protected static void checkMovesOutOfBounds(List<int[]> moves) throws InvalidMoveException{
		int moveNo = 1;
		for (int[] move: moves){
			if((move[0] < 0)||(move[0] > 25)||(move[1] < 0)||(move[1] > 25)){
			throw new InvalidMoveException("Move" + moveNo + " out of bounds"); 
			}
			moveNo++;
		}
	}

	protected static boolean captureMove(int[] move, int[] state){
		boolean answer = false;
		switch(Game.getTurn()){
		case WHITE: if (state[move[1]] == -1) return true; //is there a red piece at the destination spike?
		case RED: if (state[move[1]] == 1) return true; //is there a white piece at the destination spike?
		}
		return answer;
	}
	
	protected static boolean bearOffMove(int[] move, int[] state){
		boolean answer = false;
		switch(Game.getTurn()){
		case WHITE: if(move[1] == 0) return true; //is there a red piece at the destination spike?
		case RED: if(move[1] == 25) return true; //is there a white piece at the destination spike?
		}
		return answer;
	}
}
