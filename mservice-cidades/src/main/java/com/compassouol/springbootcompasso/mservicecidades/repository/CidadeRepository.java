package com.compassouol.springbootcompasso.mservicecidades.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.compassouol.springbootcompasso.mservicecidades.domain.Cidade;
import com.compassouol.springbootcompasso.mservicecidades.domain.Estado;

@Repository
public interface CidadeRepository extends CrudRepository<Cidade, Long> {

	List<Cidade> findByNome(String nome);
	
	List<Cidade> findByEstado(Estado estado);

	List<Cidade> findByNomeAndEstado(String nome, Estado estado);

}
