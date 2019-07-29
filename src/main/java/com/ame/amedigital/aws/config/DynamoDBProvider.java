package com.ame.amedigital.aws.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

@Configuration
@ConditionalOnProperty(name = "application.dynamo.provider", havingValue = "aws", matchIfMissing = true)
public class DynamoDBProvider {

    private static final Log LOG = LogFactory.getLog(DynamoDBProvider.class);

    @Autowired
    private RegionDetector regionDetector;

    @Bean
    protected DynamoDB createAWSDynamoDBClient() {
        LOG.info("Creating AWS DynamoDB client");
        String region = regionDetector.getRegion();
        LOG.info("DynamoDB region set to " + region);
        final AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
        return new DynamoDB(amazonDynamoDB);
    }

}
