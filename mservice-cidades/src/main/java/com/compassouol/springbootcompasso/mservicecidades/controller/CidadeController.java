package com.compassouol.springbootcompasso.mservicecidades.controller;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.compassouol.springbootcompasso.mservicecidades.domain.Cidade;
import com.compassouol.springbootcompasso.mservicecidades.dto.CidadeDTO;
import com.compassouol.springbootcompasso.mservicecidades.service.CidadeService;
import com.compassouol.springbootcompasso.mservicecidades.service.exception.CidadeServiceException;

@RestController
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;

	@PostMapping(value = "/cidade")
	public ResponseEntity<CidadeDTO> salvarCidade(@RequestBody Cidade cidade) {
		try {
			CidadeDTO salva = cidadeService.salvarCidade(cidade);
			salva.setMessage("Cidade salva com sucesso.");
			return ResponseEntity.ok(salva);
		} catch (CidadeServiceException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new CidadeDTO(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CidadeDTO(e.getMessage()));
		}
	}

	@GetMapping(value = "/cidade/{id}")
	public ResponseEntity<CidadeDTO> buscarCidadePorId(@PathVariable("id") Long id) {
		try {
			CidadeDTO cidade = cidadeService.buscarCidadePorId(id);
			if (cidade == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CidadeDTO("Não encontrada."));
			}
			return ResponseEntity.ok(cidade);
		} catch (CidadeServiceException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new CidadeDTO(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CidadeDTO(e.getMessage()));
		}
	}

	@GetMapping(value = "/cidade/buscarPorNome/{nome}")
	public ResponseEntity<List<CidadeDTO>> buscarPorNome(@PathVariable("nome") String nome) {
		try {
			List<CidadeDTO> cidadesPorNome = cidadeService.buscarCidadePorNome(nome);
			if (cidadesPorNome.size() == 0) {				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Stream.of(new CidadeDTO("Não encontrada.")).collect(toList()));
			} else {
			}
			return ResponseEntity.ok(cidadesPorNome);
		} catch (CidadeServiceException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Stream.of(new CidadeDTO(e.getMessage())).collect(toList()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Stream.of(new CidadeDTO(e.getMessage())).collect(toList()));
		}
	}

	@GetMapping(value = "/cidade/buscarPorEstado/{estado}")
	public ResponseEntity<List<CidadeDTO>> buscarPorEstado(@PathVariable("estado") String estado) {
		try {
			List<CidadeDTO> cidadesPorEstado = cidadeService.buscarCidadePorEstado(estado);
			if (cidadesPorEstado.size() == 0) {				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Stream.of(new CidadeDTO("Não encontrada.")).collect(toList()));
			}
			return ResponseEntity.ok(cidadesPorEstado);
		} catch (CidadeServiceException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Stream.of(new CidadeDTO(e.getMessage())).collect(toList()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Stream.of(new CidadeDTO(e.getMessage())).collect(toList()));
		}
	}

}
