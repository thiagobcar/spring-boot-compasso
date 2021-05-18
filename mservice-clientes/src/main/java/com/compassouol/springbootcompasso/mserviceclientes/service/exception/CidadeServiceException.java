package com.compassouol.springbootcompasso.mserviceclientes.service.exception;

public class CidadeServiceException extends Exception {

	private static final long serialVersionUID = -2440587625416559804L;

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
