package game;
import board.*;

public class Visualisation
{
	public static void printBoard(Spike[] spikes)
	{	
		Piece.pieces.removeAll(Piece.pieces);
		
		int size = dynamicReSize();
		
		System.out.println("13 14 15 16 17 18 19 20 21 22 23 24");
		System.out.println("***********************************");
		for(int j = 0; j < size; j++) //loops for top half of board
		{
			for(int i = 13; i <Spike.totalSpikes.length; ++i)
			{
				if (spikes[i].counter == 0&& i != Spike.totalSpikes.length-1)
					System.out.print(" _ ");
				if (spikes[i].counter > 0 && i != Spike.totalSpikes.length-1)//white
				{
					if (spikes[i].counter > j)
					{
						new WhitePiece(j,i);
						System.out.print(" W ");
					}
					else
						System.out.print(" _ ");
				}
				else if(spikes[i].counter > j && i == Spike.totalSpikes.length-1)
				{
					new WhitePiece(j,i);
				}
				if (spikes[i].counter < 0 && i != Spike.totalSpikes.length-1)//red
				{
					if (Math.abs(spikes[i].counter) > j)
					{
						new RedPiece(j,i);
						System.out.print(" R ");
					}
					else
						System.out.print(" _ ");
				}
			}
			System.out.println("\n");
		}
		System.out.println("*********************************** Red Captured: " + Math.abs(Spike.totalSpikes[0].counter));
		for(int j = size; j > 0; j--) //loops for bottom half of board. 
		{
			for(int i = 12; i >=0; i--)
			{
				if (spikes[i].counter == 0 && i != 0)
					System.out.print(" _ ");
				if (spikes[i].counter > 0)//white
				{
					if (spikes[i].counter >= j && i != 0)
					{
						new WhitePiece(j,i);
						System.out.print(" W ");
					}
					else
						System.out.print(" _ ");
				}
				if (spikes[i].counter < 0 && i != 0)//red
				{
					if (Math.abs(spikes[i].counter) >= j)
					{
						new RedPiece(j,i);
						System.out.print(" R ");
					}
					else
						System.out.print(" _ ");
				}
				else if(Math.abs(spikes[i].counter) >j-1 && i == 0)
				{
					new RedPiece(j,i);
				}
			}
			System.out.println("\n");
		}
		System.out.println("*********************************** White Captured: " + Spike.totalSpikes[25].counter);
		System.out.println("12 11 10  9  8  7  6  5  4  3  2  1");
	}
	public static int dynamicReSize()
	{
		/**
		 * If a spike has more than 5 pieces, this method will
		 * return that number to print the
		 * corresponding number of rows in the printBoard() method
		 */
		int newSize = Spike.SPIKESIZE;
		for(Spike spike : Spike.totalSpikes)
		{
			if(Math.abs(spike.counter) > newSize)
			{
				newSize = Math.abs(spike.counter);
			}
		}
		return newSize;
	}
}
