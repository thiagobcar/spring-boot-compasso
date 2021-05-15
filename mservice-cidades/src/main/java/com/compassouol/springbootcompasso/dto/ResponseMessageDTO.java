package com.compassouol.springbootcompasso.dto;

public class ResponseMessageDTO<T> {

	private T entidade;
	private String mensagem;

	public ResponseMessageDTO(T entidade, String mensagem) {
		super();
		this.entidade = entidade;
		this.mensagem = mensagem;
	}

	public T getEntidade() {
		return entidade;
	}

	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
