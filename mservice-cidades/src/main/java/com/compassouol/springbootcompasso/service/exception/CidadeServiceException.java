package com.compassouol.springbootcompasso.service.exception;

public class CidadeServiceException extends Exception {

	private static final long serialVersionUID = 4256468091777607026L;

	public CidadeServiceException(String message) {
		super(message);
	}

	public CidadeServiceException(Exception e) {
		super(e);
	}

	public CidadeServiceException(String message, Exception e) {
		super(message, e);
	}

}
