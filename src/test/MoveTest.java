package test;

import java.util.Arrays;

import model.Game;

public class MoveTest {
	
	public enum PlayerColour {
		WHITE, RED
	}
	
	static PlayerColour turn = PlayerColour.WHITE;
	
	public static void main(String[] args){
	
		int[] gameState = new int[] {0, -2, 0, 0, 0, 0, 5, 0, 3, 0, 0, 0, -5, 5, 0, 0, 0, -3, 0, -5, 0, 0, 0, 0, 2, 0};
		System.out.println(Arrays.toString(gameState));
		int[] move = new int[] {6,10};
		gameState = normalMove(move, gameState);
		System.out.println(Arrays.toString(gameState));
	}
	
	private static int[] normalMove(int[] move, int[] state) {
		switch (turn) {
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
	
}
