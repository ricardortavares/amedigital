package com.ame.amedigital.api.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class PlanetsResponse {
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private String climate;
	
	@Getter
	@Setter
	private String terrain;
	
	@Getter
	@Setter
	private Long films;

}
