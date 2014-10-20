package se.berglund.exceptions;


public final class InvalidUserFormatException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public InvalidUserFormatException(String format){
		super(format);
	}
}
