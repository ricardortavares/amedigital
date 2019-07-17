package com.ame.amedigital.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PlanetsServerConfig {
	
	@Bean(name = "swapi-template")
	public RestTemplate buildRestTemplate() {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new FormHttpMessageConverter());
        return template;
    }

}
