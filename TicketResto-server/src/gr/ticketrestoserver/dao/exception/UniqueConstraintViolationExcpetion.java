package gr.ticketrestoserver.dao.exception;

public class UniqueConstraintViolationExcpetion extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UniqueConstraintViolationExcpetion(String msg) {
		
		super(msg);
	}

}
