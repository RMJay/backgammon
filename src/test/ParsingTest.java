package test;

public class ParsingTest {

	public static void main(String[] args) {
		String input = " 3- 6:(  4|5),( \t 4|5),(7,8);";
		input = input.replaceAll("\\s", "");
		String[] split1 = input.split(":");
		
		String dice = split1[0];
		String moves = split1[1];
		
		System.out.print(dice + "\t");
		
		if(dice.matches("\\d-\\d")){
			System.out.println("yes");
		} else {
			System.out.println("no");
		}

		System.out.print(moves + "\t");
		
		if(dice.matches(" ")){
			System.out.println("yes");
		} else {
			System.out.println("no");
		}
	}

}
