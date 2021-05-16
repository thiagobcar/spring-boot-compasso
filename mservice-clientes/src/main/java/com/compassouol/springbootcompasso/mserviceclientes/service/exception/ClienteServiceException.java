package com.compassouol.springbootcompasso.mserviceclientes.service.exception;

public class ClienteServiceException extends Exception {

	private static final long serialVersionUID = 4256468091777607026L;

	public ClienteServiceException(String message) {
		super(message);
	}

	public ClienteServiceException(Exception e) {
		super(e);
	}

	public ClienteServiceException(String message, Exception e) {
		super(message, e);
	}

}
