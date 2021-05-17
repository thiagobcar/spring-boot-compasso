package com.compassouol.springbootcompasso.mserviceclientes.service.exception;

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

@Service
public class CidadeService {
	
	@Value("${cidades.service.name}")
	private String cidadesServiceName;
	
	@Value("${cidades.service.buscarPorId}")
	private String cidadesBuscarPorId;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Logger logger = LoggerFactory.getLogger(CidadeService.class);
	
	public URI getCidadesServiceURL() {
		List<ServiceInstance> serviceInstances = discoveryClient.getInstances(cidadesServiceName);
		ServiceInstance serviceInstance = serviceInstances.get(0);
		return serviceInstance.getUri();
	}
	
	public CidadeDTO buscarCidadePorId(Long id) {
		logger.debug("Inicio busca cidade por id '" + id + "'.");
		CidadeDTO cidadeDTO = null;
		try {			
			ResponseEntity<CidadeDTO> entity = restTemplate.getForEntity(getCidadesServiceURL() + cidadesBuscarPorId + id, CidadeDTO.class);
			if(entity.getStatusCode() == HttpStatus.OK) {				
				cidadeDTO = entity.getBody();
			} else {
				cidadeDTO = new CidadeDTO("Cidade id '" + id + "' não encontrada.");
				cidadeDTO.setId(id);
				logger.warn("Cidade id '" + id + "' não encontrada.");
			}
			logger.debug("Fim busca cidade por id '" + id + "'.");
		} catch (HttpClientErrorException e) {
			cidadeDTO = new CidadeDTO("Falha ao buscar cidade por id '" + id + "'. " + e.getMessage());
			cidadeDTO.setId(id);
			logger.error("Falha ao buscar cidade por id '" + id + "'. " + e.getMessage(), e);
		}
		
		return cidadeDTO;
	}
	
}
