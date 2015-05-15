package gr.ticketrestoserver.rest.resource;

public class ErrorResource {
	public static final int TOKEN_ERROR = -4;
	public static final int NUMBER_FORMAT_ERROR=-1;
	public static final int SERIALIZING_JSON_ERROR=-2;
	public static final int PARAMETER_UNKNOWN=-3;
	public static final int IO_ERROR = -5;
	public static final int UNIQUE_CONSTRAINT_VIOLATION = -6;
	public static final int WRONG_USER_OR_PASSWORD = -7;
	public static final int MANDATORY_FIELD_MISSING = -8;
	
	
	private int errorCode;
	private String errorMessage;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
