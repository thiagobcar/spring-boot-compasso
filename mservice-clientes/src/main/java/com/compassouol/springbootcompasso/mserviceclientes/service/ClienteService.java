package com.compassouol.springbootcompasso.mserviceclientes.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compassouol.springbootcompasso.mserviceclientes.domain.Cliente;
import com.compassouol.springbootcompasso.mserviceclientes.dto.ClienteDTO;
import com.compassouol.springbootcompasso.mserviceclientes.repository.ClienteRepository;
import com.compassouol.springbootcompasso.mserviceclientes.service.exception.ClienteServiceException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	private Logger logger = LoggerFactory.getLogger(ClienteService.class);

	public ClienteDTO buscarClientePorId(Long id) throws ClienteServiceException {
		logger.debug("Inicio busca cliente por id '" + id + "'");
		AtomicReference<Cliente> cliente = new AtomicReference<>();
		try {

			Optional<Cliente> optCliente = clienteRepository.findById(id);

			optCliente.ifPresentOrElse(c -> {
				cliente.set(c);
				logger.debug("Cliente com id '" + id + "' encontrado");
				logger.debug(c.toString());
			}, () -> logger.debug("Cliente com id '" + id + "' nao encontrado"));

			logger.debug("Fim busca cliente por id '" + id + "'");
		} catch (Exception e) {
			logger.error("Falha ao buscar cliente por id '" + id + "'", e);
			throw new ClienteServiceException("Falha ao buscar cliente por id '" + id + "'. " + e.getMessage(), e);
		}

		return ClienteDTO.fromDomain(cliente.get());
	}

	public List<ClienteDTO> buscarClientePorNome(String nome) throws ClienteServiceException {
		if (nome == null) {
			logger.error("Campo 'nome' é obrigatório para esta busca.");
			throw new ClienteServiceException("Campo 'nome' é obrigatório para esta busca.");
		}
		
		try {
			logger.debug("Inicio busca cliente por nome: '" + nome + "'");

			List<Cliente> listClientes = clienteRepository.findByNome(nome);

			if (listClientes.size() > 0) {
				logger.debug("Cliente com nome: '" + nome + "' encontrado");
				listClientes.forEach(c -> logger.debug(c.toString()));
			} else {
				logger.debug("Cliente com nome: '" + nome + "' nao encontrado");
			}

			logger.debug("Fim busca cliente por nome: '" + nome + "'");

			List<ClienteDTO> listDto = listClientes.stream().map(c -> ClienteDTO.fromDomain(c))
					.collect(Collectors.toList());

			return listDto;
		} catch (Exception e) {
			logger.error("Falha ao buscar cliente por nome: '" + nome + "'", e);
			throw new ClienteServiceException("Falha ao buscar cliente por nome: '" + nome + "'. " + e.getMessage(), e);
		}
	}

	public ClienteDTO removerCliente(Long id) throws ClienteServiceException {
		AtomicReference<Cliente> clienteRemovido = new AtomicReference<>();
		try {
			logger.debug("Inicio remoção cliente id '" + id + "'");
			Optional<Cliente> optCliente = clienteRepository.findById(id);
			optCliente.ifPresentOrElse(c -> {
				clienteRepository.delete(c);
				clienteRemovido.set(c);
				logger.debug("Cliente " + c + " removido.");
			}, () -> {
				logger.debug("Cliente com id '" + id + "' não encontrado.");
			});
			logger.debug("Fim remoção cliente id '" + id + "'");

			return ClienteDTO.fromDomain(clienteRemovido.get());
		} catch (Exception e) {
			logger.error("Falha ao remover cliente de id '" + id + "'", e);
			throw new ClienteServiceException("Falha ao remover cliente de id '" + id + "'. " + e.getMessage(), e);
		}
	}

	public ClienteDTO alterarNomeCliente(Long id, String nome) throws ClienteServiceException {
		if (id == null) {
			logger.error("Campo 'id' é obrigatório para alteração do nome do cliente.");
			throw new ClienteServiceException("Campo 'id' é obrigatório para alteração do nome do cliente.");
		}

		if (nome == null) {
			logger.error("Campo 'nome' é obrigatório para alteração do nome do cliente.");
			throw new ClienteServiceException("Campo 'nome' é obrigatório para alteração do nome do cliente.");
		}

		Optional<Cliente> optCliente = Optional.ofNullable(null);
		try {
			optCliente = clienteRepository.findById(id);
		} catch (Exception e) {
			logger.error("Falha ao buscar cliente de id '" + id + "'", e);
			throw new ClienteServiceException("Falha ao buscar cliente de id '" + id + "'. " + e.getMessage(), e);
		}

		if (optCliente.isEmpty()) {
			logger.error("Cliente de id '" + id + "' não encontrado.");
			throw new ClienteServiceException("Cliente de id '" + id + "' não encontrado.");
		}

		Cliente cliente = optCliente.get();
		cliente.setNome(nome);

		return ClienteDTO.fromDomain(salvarCliente(cliente));
	}

	public ClienteDTO alterarCliente(ClienteDTO clienteDTO) throws ClienteServiceException {
		Cliente cliente = ClienteDTO.toDomain(clienteDTO);

		if (cliente.getId() == null) {
			logger.error("Campo Id é obrigatório para alteração do " + cliente);
			throw new ClienteServiceException("Campo Id é obrigatório para alteração do " + cliente);
		}

		Optional<Cliente> optCliente = Optional.ofNullable(null);
		try {
			optCliente = clienteRepository.findById(cliente.getId());
		} catch (Exception e) {
			logger.error("Falha ao buscar cliente " + cliente, e);
			throw new ClienteServiceException("Falha ao buscar cliente " + cliente + ". " + e.getMessage(), e);
		}

		if (optCliente.isEmpty()) {
			logger.error("Cliente " + cliente + " não encontrado.");
			throw new ClienteServiceException("Cliente " + cliente + " não encontrado.");
		}

		return ClienteDTO.fromDomain(salvarCliente(optCliente.get()));
	}

	public ClienteDTO salvarCliente(ClienteDTO clienteDTO) throws ClienteServiceException {
		Cliente clienteSalvar = ClienteDTO.toDomain(clienteDTO);
		clienteSalvar.setId(null);
		Cliente clienteSalvo = salvarCliente(clienteSalvar);
		return ClienteDTO.fromDomain(clienteSalvo);
	}

	public Cliente salvarCliente(Cliente cliente) throws ClienteServiceException {
		try {
			logger.debug("Inicio salva " + cliente);
			Cliente salva = clienteRepository.save(cliente);
			logger.debug("Fim salva " + cliente);
			return salva;
		} catch (Exception e) {
			logger.error("Falha ao salvar " + cliente, e);
			throw new ClienteServiceException("Falha ao salvar " + cliente + ". " + e.getMessage(), e);
		}
	}

}