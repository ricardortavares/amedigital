package com.ame.amedigital.aws.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;



@Configuration
public class DynamoDBProvider {
	
	private static final Log LOG = LogFactory.getLog(DynamoDBProvider.class);
	
	@Autowired
    private RegionDetector regionDetector;

}
