package gr.ticketrestoserver.dao.exception;

public class InvalidTokenForUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTokenForUserException(String msg) {
		
		super(msg);
	}

}
