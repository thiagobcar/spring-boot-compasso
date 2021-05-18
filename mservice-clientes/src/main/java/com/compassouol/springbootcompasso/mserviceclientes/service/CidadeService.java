package com.compassouol.springbootcompasso.mserviceclientes.service;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.compassouol.springbootcompasso.mserviceclientes.dto.CidadeDTO;
import com.compassouol.springbootcompasso.mserviceclientes.service.exception.CidadeServiceException;

@Service
public class CidadeService {
	
	@Value("${cidades.service.name}")
	private String cidadesServiceName;
	
	@Value("${cidades.service.buscar_por_id}")
	private String cidadesServiceBuscarPorId;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Logger logger = LoggerFactory.getLogger(CidadeService.class);
	
	private URI getCidadesServiceURL() throws CidadeServiceException {
		try {			
			List<ServiceInstance> serviceInstances = discoveryClient.getInstances(cidadesServiceName);
			ServiceInstance serviceInstance = serviceInstances.stream().findFirst().get();
			return serviceInstance.getUri();
		} catch (Exception e) {
			logger.error("Falha no service discovery para '"+cidadesServiceName+"'. " + e.getMessage(), e);
			throw new CidadeServiceException("Falha no service discovery para '"+cidadesServiceName+"'. " + e.getMessage());
		}
	}
	
	public CidadeDTO buscarCidadePorId(Long id) throws CidadeServiceException {
		logger.debug("Inicio busca cidade por id '" + id + "'.");
		CidadeDTO cidadeDTO = null;
		URI cidadesServiceURL = getCidadesServiceURL();
		try {			
			ResponseEntity<CidadeDTO> entity = restTemplate.getForEntity(cidadesServiceURL + cidadesServiceBuscarPorId + id, CidadeDTO.class);
			if(entity.getStatusCode() == HttpStatus.OK) {				
				cidadeDTO = entity.getBody();
			} else {
				logger.warn("Cidade id '" + id + "' não encontrada.");
				throw new CidadeServiceException("Cidade id '" + id + "' não encontrada.");
			}
			logger.debug("Fim busca cidade por id '" + id + "'.");
		} catch (HttpClientErrorException e) {
			cidadeDTO = new CidadeDTO(id, null, null);
			logger.error("Falha ao buscar cidade por id '" + id + "'. " + e.getMessage(), e);
			throw new CidadeServiceException("Falha ao buscar cidade por id '" + id + "'. " + e.getMessage());
		}
		
		return cidadeDTO;
	}
	
}
