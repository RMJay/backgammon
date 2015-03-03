package Exceptions;

public class InvalidMoveException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public InvalidMoveException(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
