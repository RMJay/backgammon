package model;

public class Board {

	protected static void printBoard(int[] state) {
		
		int bottomHalfRows = dynamicReSize(state)[0];
		int topHalfRows = dynamicReSize(state)[1];

		System.out.println("13 14 15 16 17 18  19 20 21 22 23 24");
		System.out.println("************************************");
		for (int row = 1; row <= topHalfRows; row++) {
			for (int spike = 13; spike <= 24; spike++) {
				if (spike == 19){
					System.out.print("║");
				}
				if (Math.abs(state[spike]) < row) {
					System.out.print(" _ ");
				} else {
					if (state[spike] < 0) {
						System.out.print(" R ");
					} else if (state[spike] > 0) {
						System.out.print(" W ");
					}
				}
			}
			System.out.println();
		}
		System.out.println("************************************ Red Captured: "+ Math.abs(state[0]));
		for (int row = 1; row <= bottomHalfRows; row++) {
			for (int spike = 12; spike >= 1; spike--) {
				if (spike == 6){
					System.out.print("║");
				}
				if (Math.abs(state[spike]) <= (bottomHalfRows - row)) {
					System.out.print(" _ ");
				} else {
					if (state[spike] < 0) {
						System.out.print(" R ");
					} else if (state[spike] > 0) {
						System.out.print(" W ");
					}
				}
			}
			System.out.println();
		}
		System.out.println("************************************ White Captured: " + Math.abs(state[25]));
		System.out.println("12 11 10  9  8  7   6  5  4  3  2  1");

	}

	private static int[] dynamicReSize(int[] gameState) {
		int[] sizes = new int[] { 6, 6 };
		int topLargest = 0;
		int bottomLargest = 0;

		for (int i = 1; i <= 12; i++) {
			int spikeSize = Math.abs(gameState[i]);
			if (spikeSize > bottomLargest) {
				bottomLargest = spikeSize;
			}
		}

		for (int i = 13; i <= 24; i++) {
			int spikeSize = Math.abs(gameState[i]);
			if (spikeSize > topLargest) {
				topLargest = spikeSize;
			}
		}

		if (bottomLargest > 5) {
			sizes[0] = bottomLargest + 1;
		}

		if (topLargest > 5) {
			sizes[1] = topLargest + 1;
		}

		return sizes;
	}

}
