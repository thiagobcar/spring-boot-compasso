package com.compassouol.springbootcompasso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.compassouol.springbootcompasso.domain.Cidade;
import com.compassouol.springbootcompasso.service.CidadeService;

@RestController
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;

	@PostMapping(value = "/cidade")
	public ResponseEntity<Cidade> salvarCidade(@RequestBody Cidade cidade) {
		return ResponseEntity.ok(cidadeService.salvarCidade(cidade));
	}

	@GetMapping(value = "/cidade/{id}")
	public ResponseEntity<Cidade> buscarCidadePorId(@PathVariable("id") Long id) {
		return ResponseEntity.ok(cidadeService.buscarCidadePorId(id));
	}

	@GetMapping(value = "/cidade/buscarPorNome/{nome}")
	public ResponseEntity<List<Cidade>> buscarPorNome(@PathVariable("nome") String nome) {
		return ResponseEntity.ok(cidadeService.buscarCidadePorNome(nome));
	}

	@GetMapping(value = "/cidade/buscarPorEstado/{estado}")
	public ResponseEntity<List<Cidade>> buscarPorEstado(@PathVariable("estado") String estado) {
		return ResponseEntity.ok(cidadeService.buscarCidadePorEstado(estado));
	}

}
