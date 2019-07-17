package com.ame.amedigital.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@NoArgsConstructor
@AllArgsConstructor
@GeneratePojoBuilder
public class Planets {
	
	@Getter
	@Setter
	private String planetId;
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private String terrain;
	
	@Getter
	@Setter
	private String climate;
	
	@Getter
	@Setter
	private Long films;

}
