package com.compassouol.springbootcompasso.mserviceclientes.test.integracao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.compassouol.springbootcompasso.mserviceclientes.domain.Cliente;
import com.compassouol.springbootcompasso.mserviceclientes.domain.Sexo;
import com.compassouol.springbootcompasso.mserviceclientes.repository.ClienteRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClienteRepositoryTest {

	@Autowired
	private ClienteRepository clienteRepository;

	@PersistenceContext
	private EntityManager entityManager;

	private Cliente cliente;

	@Before
	public void setup() {
		Long idCidade = 1L;
		cliente = new Cliente(null, "Thiago Bezerra de Carvalho", Sexo.M, LocalDate.of(1986, 12, 25), idCidade);
	}

	@Test
	public void testSuccessInsertCliente() {
		try {
			Cliente cliente = clienteRepository.save(this.cliente);
			Optional<Cliente> optCliente = clienteRepository.findById(cliente.getId());
			optCliente.ifPresentOrElse(c -> {
				assertThat(c).isEqualTo(cliente);
			}, () -> {
				fail("Não foi possível recuperar cliente.");
			});
		} catch (Exception e) {
			fail("Falha ao incluir cliente.");
		}
	}

	@Test
	public void testSuccessBuscarClientePorNome() {
		try {
			Long idCidade = 2L;
			Cliente walter = new Cliente(null, "Walter Hartwell White Sr.", Sexo.M, LocalDate.of(1958, 9, 7),
					idCidade);
			Cliente walterSalvo = clienteRepository.save(walter);
			List<Cliente> listCliente = clienteRepository.findByNome(walterSalvo.getNome());

			if (listCliente.size() > 0) {
				Optional<Cliente> optCliente = listCliente.stream().filter(c -> walterSalvo.getId().equals(c.getId()))
						.findFirst();
				optCliente.ifPresentOrElse(c -> {
					assertThat(c).isEqualTo(walterSalvo);
				}, () -> {
					fail("Não foi possível recuperar cliente por nome.");
				});
			} else {
				fail("Não foi possível recuperar cliente por nome.");
			}
		} catch (Exception e) {
			fail("Falha ao recuperar cliente por nome.");
		}
	}

	@Test
	public void testSuccessBuscarClientePorId() {
		try {
			Cliente cliente = clienteRepository.save(this.cliente);
			Optional<Cliente> optCliente = clienteRepository.findById(cliente.getId());
			optCliente.ifPresentOrElse(c -> {
				assertThat(c).isEqualTo(cliente);
			}, () -> {
				fail("Não foi possível recuperar cliente por id.");
			});
		} catch (Exception e) {
			fail("Falha ao recuperar cliente por id.");
		}
	}

	@Test
	public void testSuccessRemoverCliente() {
		try {
			Cliente cliente = clienteRepository.save(this.cliente);
			Optional<Cliente> optCliente = clienteRepository.findById(cliente.getId());
			optCliente.ifPresentOrElse(c -> {
				clienteRepository.delete(c);
				Optional<Cliente> optClienteRemovido = clienteRepository.findById(c.getId());
				optClienteRemovido.ifPresent(cRemovido -> {
					fail("Falha ao remover cliente.");
				});
			}, () -> {
				fail("Não foi possível recuperar cliente por id.");
			});
		} catch (Exception e) {
			fail("Falha ao recuperar cliente por id.");
		}
	}

	@Test
	public void testSuccessAlterarCliente() {
		try {
			String nomeOriginal = this.cliente.getNome();
			Integer idadeOriginal = this.cliente.getIdade();
			Cliente cliente = clienteRepository.save(this.cliente);
			Optional<Cliente> optCliente = clienteRepository.findById(cliente.getId());
			optCliente.ifPresentOrElse(c -> {
				c.setNome(c.getNome() + " Alterado");
				c.setDataNascimento(ChronoUnit.YEARS.addTo(c.getDataNascimento(), 1L));
				clienteRepository.save(c);
				Optional<Cliente> optClienteAlterado = clienteRepository.findById(c.getId());
				optClienteAlterado.ifPresentOrElse(cAlterado -> {
					assertThat(cAlterado.getNome()).isNotEqualTo(nomeOriginal);
					assertThat(cAlterado.getIdade()).isNotEqualTo(idadeOriginal);
				}, () -> {
					fail("Falha ao recuperar cliente alterado.");
				});
			}, () -> {
				fail("Não foi possível recuperar cliente por id.");
			});
		} catch (Exception e) {
			fail("Falha ao recuperar cliente por id.");
		}
	}

	@Test
	public void testSuccessGetIdadeDepoisDaSalva() {
		try {
			Integer idadeOriginal = this.cliente.getIdade();
			Cliente cliente = clienteRepository.save(this.cliente);
			Optional<Cliente> optCliente = clienteRepository.findById(cliente.getId());
			optCliente.ifPresentOrElse(c -> {
				assertThat(c.getIdade()).isEqualTo(idadeOriginal);
			}, () -> {
				fail("Não foi possível recuperar cliente por id.");
			});
		} catch (Exception e) {
			fail("Falha ao recuperar cliente por id.");
		}
	}

}
