package com.compassouol.springbootcompasso.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compassouol.springbootcompasso.domain.Cidade;
import com.compassouol.springbootcompasso.domain.Estado;
import com.compassouol.springbootcompasso.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	private Logger logger = LoggerFactory.getLogger(CidadeService.class);

	public Cidade buscarCidadePorId(Long id) {
		try {
			logger.debug("Inicio busca cidade por id '" + id + "'");

			Optional<Cidade> optCidade = cidadeRepository.findById(id);

			optCidade.ifPresentOrElse(c -> {
				logger.debug("Cidade com id '" + id + "' encontrada");
				logger.debug(c.toString());
			}, () -> logger.debug("Cidade com id '" + id + "' nao encontrada"));

			logger.debug("Fim busca cidade por id '" + id + "'");

			return optCidade.get();
		} catch (Exception e) {
			logger.error("Falha ao buscar cidade por id '" + id + "'", e);
		}

		return null;
	}

	public List<Cidade> buscarCidadePorNome(String nome) {
		try {
			logger.debug("Inicio busca cidade por nome '" + nome + "'");

			List<Cidade> listCidade = cidadeRepository.findByNome(nome);

			if (listCidade.size() > 0) {
				logger.debug("Cidade com nome '" + nome + "' encontrada");
				listCidade.forEach(c -> logger.debug(c.toString()));
			} else {
				logger.debug("Cidade com nome '" + nome + "' nao encontrada");
			}

			logger.debug("Fim busca cidade por nome '" + nome + "'");

			return listCidade;
		} catch (Exception e) {
			logger.error("Falha ao buscar cidade por nome '" + nome + "'", e);
		}

		return null;
	}

	public List<Cidade> buscarCidadePorEstado(String estado) {
		try {
			logger.debug("Inicio busca cidade por estado '" + estado + "'");

			List<Cidade> listCidade = cidadeRepository.findByEstado(Estado.valueOf(estado));

			if (listCidade.size() > 0) {
				logger.debug("Cidade com estado '" + estado + "' encontrada");
				listCidade.forEach(c -> logger.debug(c.toString()));
			} else {
				logger.debug("Cidade com estado '" + estado + "' nao encontrada");
			}

			logger.debug("Fim busca cidade por estado '" + estado + "'");

			return listCidade;
		} catch (Exception e) {
			logger.error("Falha ao buscar cidade por estado '" + estado + "'", e);
		}

		return null;
	}

	public Cidade salvarCidade(Cidade cidade) {
		try {
			logger.debug("Inicio salva cidade " + cidade);
			Cidade salva = cidadeRepository.save(cidade);
			logger.debug("Fim salva cidade " + cidade);
			return salva;
		} catch (Exception e) {
			logger.error("Falha ao salvar cidade " + cidade.toString(), e);
		}
		return null;
	}

}
