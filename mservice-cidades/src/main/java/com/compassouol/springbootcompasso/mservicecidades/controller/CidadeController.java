package com.compassouol.springbootcompasso.mservicecidades.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.compassouol.springbootcompasso.mservicecidades.domain.Cidade;
import com.compassouol.springbootcompasso.mservicecidades.dto.CidadeDTO;
import com.compassouol.springbootcompasso.mservicecidades.service.CidadeService;
import com.compassouol.springbootcompasso.mservicecidades.service.exception.CidadeServiceException;

@RestController
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;

	@PostMapping(value = "/cidade")
	public ResponseEntity<CidadeDTO> salvarCidade(@RequestBody CidadeDTO cidade) {
		try {
			CidadeDTO salva = cidadeService.salvarCidade(cidade);
			return ResponseEntity.ok(salva);
		} catch (CidadeServiceException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping(value = "/cidade/{id}")
	public ResponseEntity<CidadeDTO> buscarCidadePorId(@PathVariable("id") Long id) {
		try {
			CidadeDTO cidade = cidadeService.buscarCidadePorId(id);
			if (cidade == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrada.");
			}
			return ResponseEntity.ok(cidade);
		} catch (ResponseStatusException e) {
			throw e;
		} catch (CidadeServiceException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@GetMapping(value = "/cidade/buscarPorNome/{nome}")
	public ResponseEntity<List<CidadeDTO>> buscarPorNome(@PathVariable("nome") String nome) {
		try {
			List<CidadeDTO> cidadesPorNome = cidadeService.buscarCidadePorNome(nome);
			if (cidadesPorNome.size() == 0) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrada.");
			} else {
			}
			return ResponseEntity.ok(cidadesPorNome);
		} catch (ResponseStatusException e) {
			throw e;
		} catch (CidadeServiceException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@GetMapping(value = "/cidade/buscarPorEstado/{estado}")
	public ResponseEntity<List<CidadeDTO>> buscarPorEstado(@PathVariable("estado") String estado) {
		try {
			List<CidadeDTO> cidadesPorEstado = cidadeService.buscarCidadePorEstado(estado);
			if (cidadesPorEstado.size() == 0) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrada.");
			}
			return ResponseEntity.ok(cidadesPorEstado);
		} catch (ResponseStatusException e) {
			throw e;
		} catch (CidadeServiceException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

}
