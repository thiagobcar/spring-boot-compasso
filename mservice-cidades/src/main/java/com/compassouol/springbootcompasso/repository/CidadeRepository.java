package com.compassouol.springbootcompasso.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.compassouol.springbootcompasso.domain.Cidade;
import com.compassouol.springbootcompasso.domain.Estado;

@Repository
public interface CidadeRepository extends CrudRepository<Cidade, Long> {

	List<Cidade> findByNome(String nome);
	
	List<Cidade> findByEstado(Estado estado);

}
