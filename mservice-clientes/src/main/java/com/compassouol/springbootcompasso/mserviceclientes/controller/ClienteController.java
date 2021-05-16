package com.compassouol.springbootcompasso.mserviceclientes.controller;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

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
			salvo.setMessage("Cliente salvo com sucesso.");
			return ResponseEntity.ok(salvo);
		} catch (ClienteServiceException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ClienteDTO(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ClienteDTO(e.getMessage()));
		}
	}

	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<ClienteDTO> removerCliente(@PathVariable("id") Long id) {
		try {
			ClienteDTO removido = clienteService.removerCliente(id);
			if(removido == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ClienteDTO("Nenhum cliente encontrado com id '" + id + "'."));
			}
			removido.setMessage("Cliente removido com sucesso.");
			return ResponseEntity.ok(removido);
		} catch (ClienteServiceException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ClienteDTO(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ClienteDTO(e.getMessage()));
		}
	}

	@PostMapping("/cliente/alterarNome/{id}/{nome}")
	public ResponseEntity<ClienteDTO> alterarNomeCliente(@PathVariable("id") Long id,
			@PathVariable("nome") String nome) {
		try {
			ClienteDTO alterado = clienteService.alterarNomeCliente(id, nome);
			if(alterado == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ClienteDTO("Nenhum cliente encontrado com id '" + id + "'."));
			}
			alterado.setMessage("Nome do cliente alterado com sucesso.");
			return ResponseEntity.ok(alterado);
		} catch (ClienteServiceException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ClienteDTO(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ClienteDTO(e.getMessage()));
		}
	}

	@GetMapping("/cliente/buscarPorNome/{nome}")
	public ResponseEntity<List<ClienteDTO>> buscarClientePorNome(@PathVariable("nome") String nome) {
		try {
			List<ClienteDTO> listaClientes = clienteService.buscarClientePorNome(nome);
			if (listaClientes.size() == 0) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Stream
						.of(new ClienteDTO("Nenhum cliente encontrado com nome '" + nome + "'.")).collect(toList()));
			}
			return ResponseEntity.ok(listaClientes);
		} catch (ClienteServiceException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(Stream.of(new ClienteDTO(e.getMessage())).collect(toList()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Stream.of(new ClienteDTO(e.getMessage())).collect(toList()));
		}
	}

	@GetMapping("/cliente/{id}")
	public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable("id") Long id) {
		try {
			ClienteDTO clientePorId = clienteService.buscarClientePorId(id);
			if (clientePorId == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ClienteDTO("Nenhum cliente encontrado com id '" + id + "'."));
			}
			return ResponseEntity.ok(clientePorId);
		} catch (ClienteServiceException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ClienteDTO(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ClienteDTO(e.getMessage()));
		}
	}

}
