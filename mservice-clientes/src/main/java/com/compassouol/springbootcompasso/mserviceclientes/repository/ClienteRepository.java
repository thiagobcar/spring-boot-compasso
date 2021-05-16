package com.compassouol.springbootcompasso.mserviceclientes.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.compassouol.springbootcompasso.mserviceclientes.domain.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
	
	List<Cliente> findByNome(String nome);

}
