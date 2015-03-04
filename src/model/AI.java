package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Exceptions.InvalidMoveException;

public class AI {
	
	public static int[] nextAIMove(int[] state){
		return null;
	}

	public static List<List<int[]>> allValidMoves(int[] state){
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

	protected static Map<int[], Integer> mapFirstMovesWhite(int[] state, int[] dicePair){
		Map<int[], Integer> movesMap = new HashMap<int[], Integer>();
		
		for(Integer spike: AI.spikesWithWhitePieces(state)){
			int[] move = new int[2];
			move[0] = spike;
			move[1] = spike - dicePair[0];
			movesMap.put(move, dicePair[1]); //put the move and the remaining dice to be used
		}
		for(Integer spike: AI.spikesWithWhitePieces(state)){
			int[] move = new int[2];
			move[0] = spike;
			move[1] = spike - dicePair[1];
			movesMap.put(move, dicePair[0]); //put the move and the remaining dice to be used
		}
		
		return movesMap;
	}
	
	protected static Map<int[], Integer> mapFirstMovesRed(int[] state, int[] dicePair){
		Map<int[], Integer> movesMap = new HashMap<int[], Integer>();
		
		for(Integer spike: AI.spikesWithRedPieces(state)){
			int[] move = new int[2];
			move[0] = spike;
			move[1] = spike + dicePair[0];
			movesMap.put(move, dicePair[1]); //put the move and the remaining dice to be used
		}
		for(Integer spike: AI.spikesWithRedPieces(state)){
			int[] move = new int[2];
			move[0] = spike;
			move[1] = spike + dicePair[1];
			movesMap.put(move, dicePair[0]); //put the move and the remaining dice to be used
		}
		
		return movesMap;
	}

	protected static void validSinglesMoves(int[] state, int[] dicePair, Game.PlayerColour turn){
		int[] tempState = Arrays.copyOf(state, state.length);
		Map<int[], Integer> hashMap;
		switch(turn){
		case WHITE: hashMap = mapFirstMovesWhite(state, dicePair); break;
		case RED: hashMap = mapFirstMovesRed(state, dicePair); break;
		}
		
		Iterator<Map.Entry<int[], Integer>> i = hashMap.entrySet().iterator();   
		while(i.hasNext()){
			int[] firstMove = i.next().getKey();
			int diceRemaining = hashMap.get(firstMove);
			try{
				tempState = Move.makeMove(move, tempState)
			} catch (InvalidMoveException e) {
		    	//??
			}
		}
	}
}
	
