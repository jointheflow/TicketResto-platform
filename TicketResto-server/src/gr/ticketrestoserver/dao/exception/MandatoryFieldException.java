package gr.ticketrestoserver.dao.exception;

public class MandatoryFieldException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MandatoryFieldException(String msg) {
		
		super(msg);
	}

}
