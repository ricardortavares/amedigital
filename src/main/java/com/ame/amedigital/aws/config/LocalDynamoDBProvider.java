package com.ame.amedigital.aws.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

@Configuration
@ConditionalOnMissingBean(DynamoDBProvider.class)
public class LocalDynamoDBProvider {

    private static final String LOCAL_DYNAMO_ENDPOINT = getLocalDynamoEndpoint();

    private static final Log LOG = LogFactory.getLog(LocalDynamoDBProvider.class);

    @Bean
    protected DynamoDB createLocalDynamoDBClient() {
        LOG.info("Creating Local DynamoDB client");
        AWSCredentials awsCredentials = new BasicAWSCredentials("dummy-credentials", "dummy-credentials");
        final AmazonDynamoDBClientBuilder clientBuilder = AmazonDynamoDBClientBuilder.standard();
        clientBuilder.withCredentials(new AWSStaticCredentialsProvider(awsCredentials));
        clientBuilder.withEndpointConfiguration(new EndpointConfiguration(LOCAL_DYNAMO_ENDPOINT, "us-east-1"));
        LOG.info("DynamoDB endpoint set to " + LOCAL_DYNAMO_ENDPOINT);
        return new DynamoDB(clientBuilder.build());
    }

    private static String getLocalDynamoEndpoint() {
        String port = System.getProperty("dynamodb.port");
        if (port == null) {
            port = "8080";
        }
        return "http://127.0.0.1:" + port;
    }

}
