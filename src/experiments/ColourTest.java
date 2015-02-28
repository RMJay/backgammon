package experiments;


public class ColourTest {

	public static final String ANSI_RESET = "\u001B[0m";
	
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	public static final String ANSI_BOLD = "\u001B[1m";
	
	public static void main(String[] args){
		
		System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);
		System.out.println(ANSI_GREEN + "This text is green!" + ANSI_RESET);
		System.out.println(ANSI_YELLOW + "This text is yellow!" + ANSI_RESET);
		System.out.println(ANSI_BLUE + "This text is blue!" + ANSI_RESET);
		System.out.println(ANSI_PURPLE + "This text is purple!" + ANSI_RESET);
		System.out.println(ANSI_CYAN + "This text is cyan!" + ANSI_RESET);
		
		System.out.println(ANSI_BLACK_BACKGROUND + ANSI_WHITE + "This text has a black background!" + ANSI_RESET);
		System.out.println(ANSI_RED_BACKGROUND + ANSI_WHITE + "This text has a red background!" + ANSI_RESET);
		System.out.println(ANSI_GREEN_BACKGROUND + "This has a green background!" + ANSI_RESET);
		System.out.println(ANSI_YELLOW_BACKGROUND + "This has a yellow background!" + ANSI_RESET);
		System.out.println(ANSI_BLUE_BACKGROUND + ANSI_WHITE + "This text has a blue background!" + ANSI_RESET);
		System.out.println(ANSI_PURPLE_BACKGROUND + ANSI_WHITE + "This text has a purple background!" + ANSI_RESET);
		System.out.println(ANSI_CYAN_BACKGROUND + "This text has a cyan background!" + ANSI_RESET);
		
		System.out.println(ANSI_BOLD + "This text is bold!" + ANSI_RESET);
		
	}
	
}
		
