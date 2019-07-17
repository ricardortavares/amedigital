package com.ame.amedigital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.ame.amedigital.repository.Planets;

@Repository
public class PlanetsRepositoryDynamo extends AbstractDynamoRepository<Planets, String>{
	
	private static final String TABLE_NAME = "PLANETS";
	private static final String HASH_COLUMN = "PLANET_ID";
	private static final String PLANET_NAME_INDEX = "name-index";
			

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected String getHashColumnName() {
		return HASH_COLUMN;
	}
	
	public Optional<Planets> findByName(String name) {
		ItemCollection<QueryOutcome> items = getTable().getIndex(PLANET_NAME_INDEX).query("name", name);
        List<Planets> planets = getAllQuery(items);
        return planets.stream()
                .filter(p -> p.getName() == name)
                .findFirst();
	}

}
