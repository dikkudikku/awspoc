package com.example.awss3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSBasicCredentialsConfig {

    public static final Logger log = LoggerFactory.getLogger(AWSBasicCredentialsConfig.class);

    private String roleArn="arn:aws:iam::400611482846:role/access_apigateway";

    private String rolesesssionname="SESSION_1";



    public Credentials getAWSBasicCredentails(){
        AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard().build();
        AssumeRoleRequest roleRequest = new AssumeRoleRequest().withRoleArn(roleArn).withRoleSessionName(rolesesssionname).withDurationSeconds(3600);
        AssumeRoleResult assumeRoleResult = stsClient.assumeRole(roleRequest);
        Credentials tempCredentials = assumeRoleResult.getCredentials();
        return tempCredentials;

    }
}
