package com.ame.amedigital.api.rest.client.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class PlanetsListClientResponse {
	
	@Getter
	@Setter
	@JsonProperty("results")
	private List<PlanetsResultResponse> planets;


	public static class PlanetsResultResponse {
		
		@Getter
		@Setter
		private String name;

		@Getter
		@Setter
		private List<String> films;

		@Getter
		@Setter
		private String climate;

		@Getter
		@Setter
		private String terrain;

	}

}
