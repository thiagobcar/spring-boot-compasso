package com.compassouol.springbootcompasso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.compassouol.springbootcompasso.domain.Cidade;
import com.compassouol.springbootcompasso.dto.ResponseMessageDTO;
import com.compassouol.springbootcompasso.service.CidadeService;
import com.compassouol.springbootcompasso.service.exception.CidadeServiceException;

@RestController
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;

	@PostMapping(value = "/cidade")
	public ResponseEntity<ResponseMessageDTO<Cidade>> salvarCidade(@RequestBody Cidade cidade) {
		try {
			return ResponseEntity.ok(new ResponseMessageDTO<Cidade>(cidadeService.salvarCidade(cidade), "sucesso"));
		} catch (CidadeServiceException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessageDTO<Cidade>(null, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessageDTO<Cidade>(null, e.getMessage()));
		}
	}

	@GetMapping(value = "/cidade/{id}")
	public ResponseEntity<ResponseMessageDTO<Cidade>> buscarCidadePorId(@PathVariable("id") Long id) {
		try {
			Cidade cidade = cidadeService.buscarCidadePorId(id);
			if (cidade != null) {				
				return ResponseEntity.ok(new ResponseMessageDTO<Cidade>(cidade, "sucesso"));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessageDTO<Cidade>(null, "Não encontrado."));
			}
		} catch (CidadeServiceException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessageDTO<Cidade>(null, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessageDTO<Cidade>(null, e.getMessage()));
		}
	}

	@GetMapping(value = "/cidade/buscarPorNome/{nome}")
	public ResponseEntity<ResponseMessageDTO<List<Cidade>>> buscarPorNome(@PathVariable("nome") String nome) {
		try {
			List<Cidade> cidadesPorNome = cidadeService.buscarCidadePorNome(nome);
			if (cidadesPorNome.size() > 0) {				
				return ResponseEntity.ok(new ResponseMessageDTO<List<Cidade>>(cidadesPorNome, "sucesso"));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessageDTO<List<Cidade>>(cidadesPorNome, "Não encontrado."));
			}
		} catch (CidadeServiceException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessageDTO<List<Cidade>>(null, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessageDTO<List<Cidade>>(null, e.getMessage()));
		}
	}

	@GetMapping(value = "/cidade/buscarPorEstado/{estado}")
	public ResponseEntity<ResponseMessageDTO<List<Cidade>>> buscarPorEstado(@PathVariable("estado") String estado) {
		try {
			List<Cidade> cidadesPorEstado = cidadeService.buscarCidadePorEstado(estado);
			if (cidadesPorEstado.size() > 0) {				
				return ResponseEntity.ok(new ResponseMessageDTO<List<Cidade>>(cidadesPorEstado, "sucesso"));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessageDTO<List<Cidade>>(cidadesPorEstado, "Não encontrado."));
			}
		} catch (CidadeServiceException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessageDTO<List<Cidade>>(null, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessageDTO<List<Cidade>>(null, e.getMessage()));
		}
	}

}
