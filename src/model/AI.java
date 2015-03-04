package model;

import java.util.*;

import Exceptions.InvalidMoveException;
import Exceptions.NoAIMoveException;

public class AI {
	
	public static int[] nextAIMove(int[] inputState){
		int[] state = inputState.clone();
		int[] returnState = inputState.clone();
		List<int[]> moves;
		try {
			moves = rankedMoves(state).keySet().iterator().next();
		} catch (NoAIMoveException e) {
			System.out.println("No valid move, turn forfeight");
			return inputState;
		}
		//System.out.print(Move.stringDicePair());
		for (int[] move : moves) {
			returnState = Move.makeMove(move, inputState);
		}
		return returnState;
	} 
	
	public static Map<List<int[]>, Integer> rankedMoves(int[] state) throws NoAIMoveException{
		Map<List<int[]>,Integer> hashMap = new HashMap<List<int[]>,Integer>();
		List<List<int[]>> allValidMoves = allValidMoves(state);
		for(List<int[]> moves: allValidMoves){
			int score = calculateScore(moves, state);
			hashMap.put(moves,score);	
		}
		ValueComparator vc = new ValueComparator(hashMap);
		Map<List<int[]>, Integer> valueSorted = new TreeMap<List<int[]>, Integer>(vc);
		valueSorted.putAll(hashMap);
		return valueSorted; 	
	} 
	
	public static List<List<int[]>> allValidMoves(int[] state) throws NoAIMoveException{
		switch (Move.getDiceType()){
		case SINGLES: return allValidSinglesMoves(state); 
		case DOUBLES: return allValidDoublesMoves(state); 
		default: return null;
		}
	}
		
	protected static List<Integer> spikesWithWhitePieces(int[] state){
		List<Integer> spikes = new ArrayList<Integer>();
		for (int spike = 0; spike < state.length; spike++){
			if (state[spike] > 0) {
				spikes.add(spike);
			}
		}
		return spikes;
	}
	
	protected static List<Integer> spikesWithRedPieces(int[] state){
		List<Integer> spikes = new ArrayList<Integer>();
		for (int spike = 25; spike >= 0; spike--){
			if (state[spike] < 0) {
				spikes.add(spike);
			}
		}
		return spikes;
	}

	protected static Map<int[], Integer> mapFirstSinglesMovesWhite(int[] state){
		Map<int[], Integer> movesMap = new HashMap<int[], Integer>();
		
		for(Integer spike: spikesWithWhitePieces(state)){
			int[] move = new int[2];
			move[0] = spike;
			move[1] = spike - Move.getDicePair()[0];
			movesMap.put(move, Move.getDicePair()[1]); //put the move and the remaining dice to be used
		}
		for(Integer spike: spikesWithWhitePieces(state)){
			int[] move = new int[2];
			move[0] = spike;
			move[1] = spike - Move.getDicePair()[1];
			movesMap.put(move, Move.getDicePair()[0]); //put the move and the remaining dice to be used
		}
		
		return movesMap;
	}
	
	protected static Map<int[], Integer> mapFirstSinglesMovesRed(int[] state){
		Map<int[], Integer> movesMap = new HashMap<int[], Integer>();
		
		for(Integer spike: spikesWithRedPieces(state)){
			int[] move = new int[2];
			move[0] = spike;
			move[1] = spike + Move.getDicePair()[0];
			movesMap.put(move, Move.getDicePair()[1]); //put the move and the remaining dice to be used
		}
		for(Integer spike: spikesWithRedPieces(state)){
			int[] move = new int[2];
			move[0] = spike;
			move[1] = spike + Move.getDicePair()[1];
			movesMap.put(move, Move.getDicePair()[0]); //put the move and the remaining dice to be used
		}
		
		return movesMap;
	}

	protected static List<int[]> secondSinglesMovesWhite(int[] state, int diceRemaining){
		List<int[]> secondMoves = new ArrayList<int[]>();

		for(Integer spike: spikesWithWhitePieces(state)){
			int[] move2 = new int[2];
			move2[0] = spike;
			move2[1] = spike - diceRemaining;
			secondMoves.add(move2);
		}
		return secondMoves;
	}
	
	protected static List<int[]> secondSinglesMovesRed(int[] state, int diceRemaining){
		List<int[]> secondMoves = new ArrayList<int[]>();

		for(Integer spike: spikesWithRedPieces(state)){
			int[] move2 = new int[2];
			move2[0] = spike;
			move2[1] = spike + diceRemaining;
			secondMoves.add(move2);
		}
		
		return secondMoves;
	}
	
	protected static List<List<int[]>> allPossibleSinglesMoves(int[] state){
		int[] tempState;
		Map<int[], Integer> hashMapA  = new HashMap<int[], Integer>();
		
		List<List<int[]>> allMoves = new ArrayList<List<int[]>>();
		List<int[]> secondMoves;
		
		switch(Game.getTurn()){
		case WHITE: hashMapA = mapFirstSinglesMovesWhite(state); break;
		case RED: hashMapA = mapFirstSinglesMovesRed(state); break;
		}
		
		Iterator<Map.Entry<int[], Integer>> i = hashMapA.entrySet().iterator();   
		while(i.hasNext()){
			
			int[] move1 = i.next().getKey();
			int diceRemaining = hashMapA.get(move1);
			try{
			tempState = Move.makeMove(move1, state).clone();
			} catch (ArrayIndexOutOfBoundsException e){
				continue;
			}
			switch(Game.getTurn()){
			case WHITE: secondMoves = secondSinglesMovesWhite(tempState, diceRemaining); break;
			case RED: secondMoves = secondSinglesMovesRed(tempState, diceRemaining); break;
			default: secondMoves = null;
			}
			for(int[] move2: secondMoves){
				try{
					Move.makeMove(move2, tempState);
				} catch (ArrayIndexOutOfBoundsException e){
					continue;
				}
				List<int[]> moves = new ArrayList<int[]>();
				moves.add(move1);
				moves.add(move2);
				allMoves.add(moves);
			}
		}
		return allMoves;
	}

	protected static List<List<int[]>> allValidSinglesMoves(int[] state) throws NoAIMoveException{
		List<List<int[]>> allValid = new ArrayList<List<int[]>>();
		
		List<List<int[]>> allPossible = allPossibleSinglesMoves(state);
		for(List<int[]> moves : allPossible){
			try{
				Rules.checkMoves(moves, state);
			}  catch (InvalidMoveException e) {
				continue;
			}
			allValid.add(moves);
		}
		if (allValid.size() == 0){
			throw new NoAIMoveException();
		}
		return allValid;
	}
	
	protected static List<int[]> doublesMoveWhite(int[] state){
		List<int[]> moves = new ArrayList<int[]>();
		
		for(Integer spike: spikesWithWhitePieces(state)){
			int[] move = new int[2];
			move[0] = spike;
			move[1] = spike - Move.getDicePair()[0];
			moves.add(move);
		}
		
		return moves;
	}
	
	protected static List<int[]> doublesMoveRed(int[] state){
		List<int[]> moves = new ArrayList<int[]>();
		
		for(Integer spike: spikesWithWhitePieces(state)){
			int[] move = new int[2];
			move[0] = spike;
			move[1] = spike + Move.getDicePair()[0];
			moves.add(move);
		}
		
		return moves;
	}	
	
	protected static List<List<int[]>> allPossibleDoublesMoves(int[] state){
		List<List<int[]>> allMoves = new ArrayList<List<int[]>>();
		
		List<int[]> possibilities1 = null;
		List<int[]> possibilities2 = null;
		List<int[]> possibilities3 = null;
		List<int[]> possibilities4 = null;
		
		int[] tempState1 = state.clone();
		int[] tempState2;
		int[] tempState3;
		int[] tempState4;
		
		switch(Game.getTurn()){
		case WHITE: possibilities1 = doublesMoveWhite(tempState1); break;
		case RED:  possibilities1 = doublesMoveRed(tempState1); break;
		}
		for(int[] move1: possibilities1){
			try{
				tempState2 = Move.makeMove(move1, tempState1).clone();
			} catch (ArrayIndexOutOfBoundsException e){
				continue;
			}
			switch(Game.getTurn()){
			case WHITE: possibilities2 = doublesMoveWhite(tempState2); break;
			case RED:  possibilities2 = doublesMoveRed(tempState2); break;
			}
			for(int[] move2: possibilities2){
				try{
					tempState3 = Move.makeMove(move2, tempState2).clone();
				} catch (ArrayIndexOutOfBoundsException e){
					continue;
				}
				switch(Game.getTurn()){
				case WHITE: possibilities3 = doublesMoveWhite(tempState3); break;
				case RED:  possibilities3 = doublesMoveRed(tempState3); break;
				}
				for(int[] move3: possibilities3){
					try{
						tempState4 = Move.makeMove(move3, tempState3).clone();
					} catch (ArrayIndexOutOfBoundsException e){
						continue;
					}
					switch(Game.getTurn()){
					case WHITE: possibilities4 = doublesMoveWhite(tempState4); break;
					case RED:  possibilities4 = doublesMoveRed(tempState4); break;
					}
					for(int[] move4: possibilities4){
						try{
							Move.makeMove(move4, tempState4);
						} catch (ArrayIndexOutOfBoundsException e){
							continue;
						}
						List<int[]> moves = new ArrayList<int[]>();
						moves.add(move1); 
						moves.add(move2); 
						moves.add(move3); 
						moves.add(move4); 
						
						allMoves.add(moves);
					}
				}
			}
		}
		return allMoves;
	}

	protected static List<List<int[]>> allValidDoublesMoves(int[] state) throws NoAIMoveException{
		List<List<int[]>> allValid = new ArrayList<List<int[]>>();
		List<List<int[]>> allPossible = allPossibleDoublesMoves(state);
		
		for(List<int[]> moves : allPossible){
			try{
				Rules.checkMoves(moves, state);
			}  catch (InvalidMoveException e) {
				continue;
			}
			allValid.add(moves);
		}
		if (allValid.size() == 0){
			throw new NoAIMoveException();
		}
		return allValid;
	}

	protected static int calculateScore(List<int[]> moves, int[] state){
		int score = 0;
		for (int[] move: moves){
			if(Rules.captureMove(move, state)) score++;
			if(Rules.bearOffMove(move, state)) score = score + 2;
		}
		return score;
	}
	
	protected static String stringMove(int[] move){
		return "("+ Integer.toString(move[0]) + "|" + Integer.toString(move[1]) + ")";
	}
}

class ValueComparator implements Comparator<List<int[]>> {
	Map<List<int[]>, Integer> base; // base is the original HashMap (nGramData) that needs to be sorted

	public ValueComparator(Map<List<int[]>, Integer> base) {
		this.base = base; 
	}

	public int compare(List<int[]> a, List<int[]> b) {
		if (base.get(a) > base.get(b)) { // the values (occurrence) from the base HashMap
			return -1;
		} else {
			return 1;
		}
	}
}


	
