package com.compassouol.springbootcompasso.mserviceclientes.dto;

import java.time.LocalDate;

import com.compassouol.springbootcompasso.mserviceclientes.domain.Cliente;
import com.compassouol.springbootcompasso.mserviceclientes.domain.Sexo;

public class ClienteDTO extends MessageDTO {

	private Long id;
	private String nome;
	private Sexo sexo;
	private LocalDate dataNascimento;
	private Integer idade;
	private CidadeDTO cidade;

	public ClienteDTO() {
		super();
	}

	public ClienteDTO(Long id, String nome, Sexo sexo, LocalDate dataNascimento, Integer idade, CidadeDTO cidade) {
		super();
		this.id = id;
		this.nome = nome;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.idade = idade;
		this.cidade = cidade;
	}
	
	public ClienteDTO(String message) {
		super(message);
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

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public CidadeDTO getCidade() {
		return cidade;
	}

	public void setCidade(CidadeDTO cidade) {
		this.cidade = cidade;
	}

	public static Cliente toDomain(ClienteDTO dto) {
		if (dto == null) {
			return null;
		}
		return new Cliente(dto.getId(), dto.getNome(), dto.getSexo(), dto.getDataNascimento(),
				dto.getCidade() != null ? dto.getCidade().getId() : null);
	}

	public static ClienteDTO fromDomain(Cliente domain) {
		if (domain == null) {
			return null;
		}

		CidadeDTO cidadeDTO = new CidadeDTO(domain.getCidade(), null, null);

		return new ClienteDTO(domain.getId(), domain.getNome(), domain.getSexo(), domain.getDataNascimento(), domain.getIdade(), cidadeDTO);
	}

}
