package com.ame.amedigital.api.rest.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PlanetsClientsResponse {
	
	@Getter
	private List<PlanetsResponse> planets;

}
