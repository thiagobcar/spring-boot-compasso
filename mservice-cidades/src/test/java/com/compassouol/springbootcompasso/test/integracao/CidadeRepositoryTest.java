package com.compassouol.springbootcompasso.test.integracao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.compassouol.springbootcompasso.domain.Cidade;
import com.compassouol.springbootcompasso.domain.Estado;
import com.compassouol.springbootcompasso.repository.CidadeRepository;

@DataJpaTest
public class CidadeRepositoryTest {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Test
	private void testSuccessInsert() {
		Cidade iguatu = cidadeRepository.save(new Cidade("Iguatu", Estado.CE));
		Cidade fortaleza = cidadeRepository.save(new Cidade("Fortaleza", Estado.CE));
		
		Optional<Cidade> optIguatu = cidadeRepository.findById(iguatu.getId());
		optIguatu.ifPresentOrElse(c -> {
			assertThat(c.getNome()).isEqualTo(iguatu.getNome());
			assertThat(c.getEstado()).isEqualTo(iguatu.getEstado());
		}, fail("Não foi possível recuperar cidade."));
		
		Optional<Cidade> optFortaleza = cidadeRepository.findById(fortaleza.getId());
		optFortaleza.ifPresentOrElse(c -> {
			assertThat(c.getNome()).isEqualTo(fortaleza.getNome());
			assertThat(c.getEstado()).isEqualTo(fortaleza.getEstado());
		}, fail("Não foi possível recuperar cidade."));
	}

}
