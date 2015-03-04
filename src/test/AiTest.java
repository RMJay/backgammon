package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import model.Game;
import model.Move;
import model.Rules;
import model.Game.PlayerColour;
import Exceptions.InvalidMoveException;

public class AiTest {
	
	private static int[] gameState;
	private static int[] dice_Pair;
	private static Game.PlayerColour turn;
	
	public static void main(String[] args){
		initialize();
		
		System.out.println("elementNo: [0,  1, 2, 3, 4, 5, 6, 7, 8, 9,10,11, 12,13,14,15,16, 17,18, 19,20,21,22,23,24,25]");
		System.out.println("gameState: " + Arrays.toString(gameState));
		System.out.println("turn: " + turn);
		System.out.print("dicePair: " + Arrays.toString(Move.getDicePair()));
		System.out.println(" " + Move.getDiceType());
		System.out.print("spikes with white pieces: ");
		System.out.println(spikesWithWhitePieces(gameState));
		System.out.print("spikes with red pieces: ");
		System.out.println(spikesWithRedPieces(gameState));

		switch(Move.getDiceType()){
		case SINGLES:{
			System.out.println();
			List<List<int[]>> allPossibleSingles = allPossibleSinglesMoves(gameState);
			int noPossSingles = allPossibleSingles.size();
			System.out.print("There are " + noPossSingles + " possible singles moves, ");
			List<List<int[]>> allValidSingles = allValidSinglesMoves(gameState);
			int noValidSingles = allValidSingles.size();
			System.out.println("and " + noValidSingles + " valid singles moves");
			/* for(List<int[]> moves: allValidSingles){
				for(int[] move: moves){
					System.out.print(Arrays.toString(move) + ",");
				}
				System.out.println();
			} */
		} break;
		case DOUBLES:{
			List<List<int[]>> allPossibleDoubles = allPossibleDoublesMoves(gameState);
			int noPossDoubles = allPossibleDoubles.size();
			System.out.print("There are " + noPossDoubles + " possible doubles moves");
			/* for(List<int[]> moves: allPossibleDoubles){
				for(int[] move: moves){
					System.out.print(Arrays.toString(move) + ",");
				}
				System.out.println();
			}	*/	
			List<List<int[]>> allValidDoubles = allValidDoublesMoves(gameState);
			int noValidDoubles = allValidDoubles.size();
			System.out.println(" and " + noValidDoubles + " valid doubles moves");
			/* for(List<int[]> moves: allValidDoubles){
				for(int[] move: moves){
					System.out.print(Arrays.toString(move) + ",");
				}
				System.out.println();
			} */
		} break;
		}	

		allPossibleDoublesMoves(gameState);
		
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
			turn = Game.PlayerColour.WHITE;
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

	protected static List<List<int[]>> allValidSinglesMoves(int[] state){
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
		int length = state.length;
		
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
						moves.add(move1); //System.out.print(Arrays.toString(move1));
						moves.add(move2); //System.out.print(Arrays.toString(move2));
						moves.add(move3); //System.out.print(Arrays.toString(move3));
						moves.add(move4); //System.out.println(Arrays.toString(move4));
						//System.out.println(Arrays.toString(tempState1));
						//System.out.println(Arrays.toString(tempState2));
						//System.out.println(Arrays.toString(tempState3));
						//System.out.println(Arrays.toString(tempState4));
						allMoves.add(moves);
					}
				}
			}
		}
		return allMoves;
	}

	protected static List<List<int[]>> allValidDoublesMoves(int[] state){
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
		return allValid;
	}

}
