package com.compassouol.springbootcompasso.mservicecidades.test.integracao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.compassouol.springbootcompasso.mservicecidades.domain.Cidade;
import com.compassouol.springbootcompasso.mservicecidades.domain.Estado;
import com.compassouol.springbootcompasso.mservicecidades.repository.CidadeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CidadeRepositoryTest {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Test
	public void testSuccessInsertCidade() {
		Cidade iguatu = cidadeRepository.save(new Cidade("Iguatu", Estado.CE));
		Cidade fortaleza = cidadeRepository.save(new Cidade("Fortaleza", Estado.CE));
		
		Optional<Cidade> optIguatu = cidadeRepository.findById(iguatu.getId());
		optIguatu.ifPresentOrElse(c -> {
			assertThat(c.getNome()).isEqualTo(iguatu.getNome());
			assertThat(c.getEstado()).isEqualTo(iguatu.getEstado());
		}, () -> {
			fail("Não foi possível recuperar cidade.");
		});
		
		Optional<Cidade> optFortaleza = cidadeRepository.findById(fortaleza.getId());
		optFortaleza.ifPresentOrElse(c -> {
			assertThat(c.getNome()).isEqualTo(fortaleza.getNome());
			assertThat(c.getEstado()).isEqualTo(fortaleza.getEstado());
		}, () -> {
			fail("Não foi possível recuperar cidade.");
		});
	}
	
	@Test
	public void testSuccessFindCidadePorNome() {
		Cidade santaElenaMA = cidadeRepository.save(new Cidade("Santa Helena", Estado.MA));
		
		// Incluindo cidades com nomes iguais e estados diferentes
		cidadeRepository.save(new Cidade("Santa Helena", Estado.SC));
		cidadeRepository.save(new Cidade("Santa Helena", Estado.PR));
		cidadeRepository.save(new Cidade("Santa Helena", Estado.PB));
		
		List<Cidade> listaCidades = cidadeRepository.findByNome(santaElenaMA.getNome());
		if (listaCidades.size() > 0) {
			// Filtrando por Id da cidade SantaElena do MA
			Optional<Cidade> optCidade = listaCidades.stream().filter(c -> c.getId().equals(santaElenaMA.getId())).findFirst();
			optCidade.ifPresentOrElse(c -> {				
				assertThat(c.getNome()).isEqualTo(santaElenaMA.getNome());
				assertThat(c.getEstado()).isEqualTo(santaElenaMA.getEstado());
			}, () -> {
				fail("Não foi possível recuperar cidade.");
			});
		} else {
			fail("Não foi possível recuperar cidade.");
		}
	}
	
	@Test
	public void testSuccessFindCidadePorEstado() {
		cidadeRepository.save(new Cidade("Iguatu", Estado.CE));
		cidadeRepository.save(new Cidade("Fortaleza", Estado.CE));
		
		List<Cidade> cidadesPorEstado = cidadeRepository.findByEstado(Estado.CE);
		
		assertThat(cidadesPorEstado.size()).isGreaterThan(0);
	}
	
	@Test
	public void testSuccessFindCidadePorNomeEstado() {
		Cidade iguatu = cidadeRepository.save(new Cidade("Iguatu", Estado.CE));
		
		List<Cidade> cidadesPorNomeEstado = cidadeRepository.findByNomeAndEstado(iguatu.getNome(), iguatu.getEstado());
		
		assertThat(cidadesPorNomeEstado.size()).isGreaterThan(0);
	}

}
