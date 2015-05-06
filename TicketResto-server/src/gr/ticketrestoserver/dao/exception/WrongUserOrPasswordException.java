package gr.ticketrestoserver.dao.exception;

public class WrongUserOrPasswordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongUserOrPasswordException(String msg) {
		
		super(msg);
	}

}
