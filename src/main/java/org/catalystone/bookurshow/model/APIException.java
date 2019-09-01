package org.catalystone.bookurshow.model;

public class APIException extends Exception {
	private String errorCode;
	private String message;
	private String detail;
	
	public APIException(Throwable ex) {
		super(ex);
		this.detail = ex.getMessage();
	}
	
	public APIException(String errorMessage) {
		super();
		this.message = errorMessage;
	}

	public APIException(String errorCode,String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.message = errorMessage;
	}
	
	public APIException() {
		super();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
