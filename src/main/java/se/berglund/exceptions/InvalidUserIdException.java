package se.berglund.exceptions;

public final class InvalidUserIdException extends RuntimeException{
	
	private static final long serialVersionUID = 7368446675055188123L;

	public InvalidUserIdException(Long id){
		super(id.toString());
	}
}
