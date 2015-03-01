package board;


public class Spike
{
	public int counter = 0;
	public static Spike[] totalSpikes;
	public final static int SPIKESIZE = 5;
	public static int whiteOffBoard = 0;
	public static int redOffBoard = 0;
	static
	{
		totalSpikes = new Spike[26];
		for(int i = 0; i<totalSpikes.length; i++)
			totalSpikes[i] = new Spike();
		//0th element stores red captured, 25th stores white captured
		//1 through 24 will coordinate with spikes in visual representation
		
		resetSpikes();
	}
	public static void resetSpikes()
	{
		//totalSpikes[24].counter = -1;
		totalSpikes[24].counter = 2; //white is positive
		totalSpikes[1].counter = -2; // red is negative
		totalSpikes[6].counter = 10;
		totalSpikes[8].counter = 3;
		totalSpikes[12].counter = -5;
		totalSpikes[13].counter = 5;
		totalSpikes[17].counter = -3;
		totalSpikes[19].counter = -5;
		//totalSpikes[25].counter = 1;
		//totalSpikes[0].counter = -1;
	}
}
