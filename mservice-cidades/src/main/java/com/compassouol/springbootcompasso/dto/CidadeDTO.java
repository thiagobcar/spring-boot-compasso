package com.compassouol.springbootcompasso.dto;

import com.compassouol.springbootcompasso.domain.Cidade;
import com.compassouol.springbootcompasso.domain.Estado;

public class CidadeDTO extends MessageDTO {

	private Long id;
	private String nome;
	private String estado;

	public CidadeDTO() {
		super();
	}

	public CidadeDTO(String message) {
		super(message);
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

	public static Cidade toDomain(CidadeDTO dto) {
		if (dto == null) {
			return null;
		}
		return new Cidade(dto.getId(), dto.getNome(), Estado.valueOf(dto.getEstado()));
	}

	public static CidadeDTO fromDomain(Cidade domain) {
		if (domain == null) {
			return null;
		}

		return new CidadeDTO(domain.getId(), domain.getNome(),
				domain.getEstado() != null ? domain.getEstado().name() : null);
	}

}
