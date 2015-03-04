package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AiTest {
	
	private static int[] gameState;
	private static int[] dicePair;
	
	public static void main(String[] args){
		initialize();
		System.out.println("gameState: " + Arrays.toString(gameState));
		System.out.println("dicePair: " + Arrays.toString(dicePair));
		System.out.print("spikes with white pieces: ");
		System.out.println(AI.spikesWithWhitePieces(gameState));
		System.out.print("spikes with red pieces: ");
		System.out.println(AI.spikesWithRedPieces(gameState));
		System.out.println("HashMap: ");
		
		Map<int[], Integer> hashMap = AI.mapFirstMovesWhite(gameState, dicePair);
		Iterator<Map.Entry<int[], Integer>> i = hashMap.entrySet().iterator();       
		while(i.hasNext()){
		    int[] key = i.next().getKey();
		    System.out.println(Arrays.toString(key) + ", "+ hashMap.get(key));
		}
	
	
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
			dicePair = new int[] {2,3};
	}
}
