package com.ame.amedigital.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ame.amedigital.api.commons.ResponseBuilder;
import com.ame.amedigital.api.commons.ResponseObject;
import com.ame.amedigital.api.rest.request.PlanetsRequest;
import com.ame.amedigital.api.rest.response.PlanetsResponse;
import com.ame.amedigital.repository.Planets;
import com.ame.amedigital.service.PlanetsService;

@RestController("/planets")
public class PlanetsController {

	@Autowired
	private PlanetsService service;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseObject<Void> save(@RequestBody PlanetsRequest planetsRequest) {
		service.save(planetsRequest.getName(), planetsRequest.getClimate(), planetsRequest.getTerrain());
		
		return new ResponseBuilder<Void>().build();
	}
	
	@GetMapping
	public ResponseObject<List<Planets>> getAllPlanets(){
		List<Planets> response = service.getPlanetsFromDB();
		return new ResponseBuilder<List<Planets>>().withData(response).build();
	}
	
	@GetMapping("/client")
	public ResponseObject<List<PlanetsResponse>> getPlanetsFromClient(){
		List<PlanetsResponse> response = service.getPlanetsFromClient();
		return new ResponseBuilder<List<PlanetsResponse>>().withData(response).build();
	}
	
	@GetMapping("/{name}")
	public ResponseObject<PlanetsResponse> getByName(@PathVariable("name") String name) throws Exception{
		PlanetsResponse response = service.getByName(name);
		
		return new ResponseBuilder<PlanetsResponse>().withData(response).build();
	}

	@GetMapping("/{id}")
	public ResponseObject<PlanetsResponse> get(@PathVariable("id") String id) {
		Planets planets = service.getById(id);
		
		PlanetsResponse response = new PlanetsResponse();
		response.setClimate(planets.getClimate());
		response.setFilms(planets.getFilms());
		response.setName(planets.getName());
		response.setTerrain(planets.getTerrain());
		
		return new ResponseBuilder<PlanetsResponse>().withData(response).build();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) {
		service.deleteById(id);
	}
	
}
