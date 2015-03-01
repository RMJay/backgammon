package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParsingTest {

	public static void main(String[] args) {
		String input = " 3-6:(24|5),(4|5),(7|81),(9|1);";
		input = input.replaceAll("\\s", "");
		String[] split1 = input.split(":");
		
		String stringDice = split1[0];
		String stringMoves = split1[1];
		
		System.out.print(stringDice + "\t");
		
		if(stringDice.matches("\\d-\\d")){
			System.out.println("yes");
		} else {
			System.out.println("no");
		}

		System.out.print(stringMoves + "\t");
		
		if(stringMoves.matches("(\\([\\d]+\\|[\\d]+\\),?)+;?")){
			System.out.println("yes");
		} else {
			System.out.println("no");
		}
		
		List<String> listStringMoves = new ArrayList<String>();
		Pattern p = Pattern.compile("(\\([\\d]+\\|[\\d]+\\))");
		Matcher m = p.matcher(stringMoves);
		while (m.find()) {
		listStringMoves.add(m.group());
		}
		
		for (String stringMove: listStringMoves){
			int[] move = parseMove(stringMove);
			System.out.println(Arrays.toString(move));
		}
		
	}


	private static int[] parseMove(String stringMove){
		stringMove = stringMove.replaceAll("[()]", "");
		String[] stringArrayMove = stringMove.split("\\|");
		int spikeFrom = Integer.parseInt(stringArrayMove[0]);
		int spikeTo = Integer.parseInt(stringArrayMove[1]);
		return new int[] {spikeFrom, spikeTo};
		
	} 

}
