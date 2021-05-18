package com.compassouol.springbootcompasso.mserviceclientes.dto;

public class CidadeDTO {

	private Long id;
	private String nome;
	private String estado;

	public CidadeDTO() {
		super();
	}

	public CidadeDTO(Long id, String nome, String estado) {
		super();
		this.id = id;
		this.nome = nome;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
