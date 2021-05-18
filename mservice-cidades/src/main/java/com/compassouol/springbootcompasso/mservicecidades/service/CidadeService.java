package com.compassouol.springbootcompasso.mservicecidades.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compassouol.springbootcompasso.mservicecidades.domain.Cidade;
import com.compassouol.springbootcompasso.mservicecidades.domain.Estado;
import com.compassouol.springbootcompasso.mservicecidades.dto.CidadeDTO;
import com.compassouol.springbootcompasso.mservicecidades.repository.CidadeRepository;
import com.compassouol.springbootcompasso.mservicecidades.service.exception.CidadeServiceException;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	private Logger logger = LoggerFactory.getLogger(CidadeService.class);

	public CidadeDTO buscarCidadePorId(Long id) throws CidadeServiceException {
		logger.debug("Inicio busca cidade por id '" + id + "'");
		AtomicReference<Cidade> cidade = new AtomicReference<>();
		try {

			Optional<Cidade> optCidade = cidadeRepository.findById(id);

			optCidade.ifPresentOrElse(c -> {
				cidade.set(c);
				logger.debug("Cidade com id '" + id + "' encontrada");
				logger.debug(c.toString());
			}, () -> logger.debug("Cidade com id '" + id + "' nao encontrada"));

			logger.debug("Fim busca cidade por id '" + id + "'");
		} catch (Exception e) {
			logger.error("Falha ao buscar cidade por id '" + id + "'", e);
			throw new CidadeServiceException("Falha ao buscar cidade por id '" + id + "'", e);
		}

		return CidadeDTO.fromDomain(cidade.get());
	}

	public List<CidadeDTO> buscarCidadePorNome(String nome) throws CidadeServiceException {
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

			return listCidadeToListCidadeDTO(listCidade);
		} catch (Exception e) {
			logger.error("Falha ao buscar cidade por nome '" + nome + "'", e);
			throw new CidadeServiceException("Falha ao buscar cidade por nome '" + nome + "'", e);
		}
	}

	public List<CidadeDTO> buscarCidadePorEstado(String estado) throws CidadeServiceException {
		logger.debug("Inicio busca cidade por estado '" + estado + "'");
		Estado estadoEnum = null;
		try {
			estadoEnum = Estado.valueOf(estado);
		} catch (Exception e) {
			logger.debug("Estado '" + estado + "' não existe.", e);
			throw new CidadeServiceException("Estado '" + estado + "' não existe.", e);
		}
		
		try {

			List<Cidade> listCidade = cidadeRepository.findByEstado(estadoEnum);

			if (listCidade.size() > 0) {
				logger.debug("Cidade com estado '" + estado + "' encontrada");
				listCidade.forEach(c -> logger.debug(c.toString()));
			} else {
				logger.debug("Cidade com estado '" + estado + "' nao encontrada");
			}

			logger.debug("Fim busca cidade por estado '" + estado + "'");

			return listCidadeToListCidadeDTO(listCidade);
		} catch (Exception e) {
			logger.error("Falha ao buscar cidade por estado '" + estado + "'", e);
			throw new CidadeServiceException("Falha ao buscar cidade por estado '" + estado + "'", e);
		}
	}

	public List<CidadeDTO> buscarCidadePorNomeEstado(Cidade cidade) throws CidadeServiceException {
		try {
			logger.debug("Inicio busca cidade por nome e estado: " + cidade);

			List<Cidade> listCidade = cidadeRepository.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());

			if (listCidade.size() > 0) {
				logger.debug("Cidade com nome e estado: " + cidade + " encontrada");
				listCidade.forEach(c -> logger.debug(c.toString()));
			} else {
				logger.debug("Cidade com nome e estado: " + cidade + " nao encontrada");
			}

			logger.debug("Fim busca cidade por nome e estado: " + cidade);

			return listCidadeToListCidadeDTO(listCidade);
		} catch (Exception e) {
			logger.error("Falha ao buscar cidade por nome e estado: " + cidade, e);
			throw new CidadeServiceException("Falha ao buscar cidade por nome e estado: " + cidade + ". " + e.getMessage(), e);
		}
	}

	public CidadeDTO salvarCidade(Cidade cidade) throws CidadeServiceException {
		logger.debug("Inicio salva " + cidade);
		
		if(cidade == null) {
			throw new CidadeServiceException("Cidade deve ser informada.");
		}
		if(cidade.getNome() == null || cidade.getNome().trim().isEmpty()) {
			throw new CidadeServiceException("Nome da cidade deve ser informado.");
		}
		if(cidade.getEstado() == null) {
			throw new CidadeServiceException("Estado da cidade deve ser informado.");
		}
		
		List<CidadeDTO> buscarCidadePorNomeEstado = buscarCidadePorNomeEstado(cidade);
		if (buscarCidadePorNomeEstado.size() > 0) {
			throw new CidadeServiceException("Cidade '" + cidade.getNome() + "-" + cidade.getEstado() + "' já cadastrada.");
		}
		
		try {
			Cidade salva = cidadeRepository.save(cidade);
			logger.debug("Fim salva " + cidade);
			return CidadeDTO.fromDomain(salva);
		} catch (Exception e) {
			logger.error("Falha ao salvar " + cidade.toString(), e);
			throw new CidadeServiceException("Falha ao salvar " + cidade + ". " + e.getMessage(), e);
		}
	}
	
	private List<CidadeDTO> listCidadeToListCidadeDTO(List<Cidade> listaCidade) {
		if (listaCidade == null) {
			return null;
		}
		
		return listaCidade.stream().map(c -> CidadeDTO.fromDomain(c)).collect(Collectors.toList());
	}

}
