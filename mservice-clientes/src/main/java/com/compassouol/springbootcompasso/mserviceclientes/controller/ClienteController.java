package com.compassouol.springbootcompasso.mserviceclientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.compassouol.springbootcompasso.mserviceclientes.dto.ClienteDTO;
import com.compassouol.springbootcompasso.mserviceclientes.service.ClienteService;
import com.compassouol.springbootcompasso.mserviceclientes.service.exception.ClienteServiceException;

@RestController
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PutMapping("/cliente")
	public ResponseEntity<ClienteDTO> salvarCliente(@RequestBody ClienteDTO dto) {
		try {
			ClienteDTO salvo = clienteService.salvarCliente(dto);
			return ResponseEntity.ok(salvo);
		} catch (ClienteServiceException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<ClienteDTO> removerCliente(@PathVariable("id") Long id) {
		try {
			ClienteDTO removido = clienteService.removerCliente(id);
			if (removido == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Nenhum cliente encontrado com id '" + id + "'.");
			}
			return ResponseEntity.ok(removido);
		} catch (ResponseStatusException e) {
			throw e;
		} catch (ClienteServiceException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@PostMapping("/cliente/alterarNome/{id}/{nome}")
	public ResponseEntity<ClienteDTO> alterarNomeCliente(@PathVariable("id") Long id,
			@PathVariable("nome") String nome) {
		try {
			ClienteDTO alterado = clienteService.alterarNomeCliente(id, nome);
			if (alterado == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Nenhum cliente encontrado com id '" + id + "'.");
			}
			return ResponseEntity.ok(alterado);
		} catch (ResponseStatusException e) {
			throw e;
		} catch (ClienteServiceException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@GetMapping("/cliente/buscarPorNome/{nome}")
	public ResponseEntity<List<ClienteDTO>> buscarClientePorNome(@PathVariable("nome") String nome) {
		try {
			List<ClienteDTO> listaClientes = clienteService.buscarClientePorNome(nome);
			if (listaClientes.size() == 0) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Nenhum cliente encontrado com nome '" + nome + "'.");
			}
			return ResponseEntity.ok(listaClientes);
		} catch (ResponseStatusException e) {
			throw e;
		} catch (ClienteServiceException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@GetMapping("/cliente/{id}")
	public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable("id") Long id) {
		try {
			ClienteDTO clientePorId = clienteService.buscarClientePorId(id);
			if (clientePorId == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Nenhum cliente encontrado com id '" + id + "'.");
			}
			return ResponseEntity.ok(clientePorId);
		} catch (ResponseStatusException e) {
			throw e;
		} catch (ClienteServiceException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

}
