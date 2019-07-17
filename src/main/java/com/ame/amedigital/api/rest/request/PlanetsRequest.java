package com.ame.amedigital.api.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class PlanetsRequest {
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private String terrain;
	
	@Getter
	@Setter
	private String climate;

}
