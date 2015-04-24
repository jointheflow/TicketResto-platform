package gr.ticketrestoserver.dao.exception;

public class InvalidTokenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTokenException(String msg) {
		
		super(msg);
	}

}
