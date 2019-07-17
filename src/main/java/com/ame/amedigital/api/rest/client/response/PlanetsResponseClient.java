package com.ame.amedigital.api.rest.client.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PlanetsResponseClient {
	
	@Getter
	private List<String> films;

}
