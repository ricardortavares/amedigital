package com.ame.amedigital.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ame.amedigital.api.rest.client.response.PlanetsListClientResponse;
import com.ame.amedigital.api.rest.client.response.PlanetsResponseClient;
import com.ame.amedigital.api.rest.response.PlanetsResponse;
import com.ame.amedigital.repository.Planets;
import com.ame.amedigital.repository.PlanetsRepositoryDynamo;

@Service
public class PlanetsService {

	private static final Log LOG = LogFactory.getLog(PlanetsService.class);

	private static final String SERVER = "https://swapi.co/api/";

	@Autowired
	private PlanetsRepositoryDynamo repository;
	
	@Autowired
	private IdGenerator idGenerator;

	@Autowired
	@Qualifier("swapi-template")
	private RestTemplate restTemplate;

	public void save(String name, String climate, String terrain) {
		Planets entity = new Planets();

		entity.setPlanetId(idGenerator.generateId().toString().toUpperCase());
		entity.setClimate(climate);
		entity.setFilms(getCountFilms(name));
		entity.setName(name);
		entity.setTerrain(terrain);
		
		repository.save(entity);
	}

	public Planets getById(String planetsId) {
		return repository.findByIdUnchecked(planetsId);
	}
	
	public List<Planets> getPlanetsFromDB(){
		return repository.getAll();
	}

	public void deleteById(String planetId) {
		repository.delete(planetId);
	}

	public List<PlanetsResponse> getPlanetsFromClient() {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<?> entity = new HttpEntity<>(headers);

		PlanetsListClientResponse response = null;

		try {
			response = restTemplate
					.exchange(SERVER + "planets", HttpMethod.GET, entity, PlanetsListClientResponse.class).getBody();
		} catch (Exception e) {
			LOG.debug("Error on get planets!", e);
			throw e;
		}

		return response.getPlanets().stream().map(planets -> new PlanetsResponse(planets.getName(),
				planets.getClimate(), planets.getTerrain(), planets.getFilms().stream().count()))
				.collect(Collectors.toList());
	}

	public PlanetsResponse getByName(String name) throws Exception {
		Optional<Planets> planets = repository.findByName(name);
		if (!planets.isPresent()) {
			throw new Exception("Planet not found");
		}

		return planets.map(p -> new PlanetsResponse(p.getName(), p.getClimate(), p.getTerrain(), p.getFilms())).get();
	}

	private Long getCountFilms(String name) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<?> entity = new HttpEntity<>(headers);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("name", name);

		PlanetsResponseClient response = null;

		try {
			response = restTemplate.exchange(SERVER + "planets", HttpMethod.GET, entity, PlanetsResponseClient.class)
					.getBody();
		} catch (Exception e) {
			LOG.debug("Error on count films by name: " + name, e);
			throw e;
		}

		return response.getFilms().stream().count();
	}

}
