package com.example.awss3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSecretsManagerConfig {

    public static final Logger log = LoggerFactory.getLogger(AwsSecretsManagerConfig.class);

    @Value("${aws.region.name}")
    private String awsRegionName;

    @Autowired
    AWSBasicCredentialsConfig awsCredentials;

    /*@Bean
    public AWSSecretsManager getSecretsManagerClient(){
        final AWSCredentials basicCredentials = new BasicSessionCredentials(awsCredentials.getAWSBasicCredentails().getAccessKeyId(),awsCredentials.getAWSBasicCredentails().getSecretAccessKey(),awsCredentials.getAWSBasicCredentails().getSessionToken());
         return AWSSecretsManagerClientBuilder.standard().
             withRegion(awsRegionName).
             withCredentials(new AWSStaticCredentialsProvider(basicCredentials)).build();
    }*/


}
