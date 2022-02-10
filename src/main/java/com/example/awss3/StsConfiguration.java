package com.example.awss3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "aws", name = "service", havingValue = "sts")
public class StsConfiguration {

    @Value("${aws.region.name}")
    private String awsRegionName;

    @Autowired
    AWSBasicCredentialsConfig awsCredentials;


    @Bean
    public AmazonS3 getAmazonS3Client() {
        final AWSCredentials basicCredentials = new BasicSessionCredentials(awsCredentials.getAWSBasicCredentails().getAccessKeyId(),awsCredentials.getAWSBasicCredentails().getSecretAccessKey(),awsCredentials.getAWSBasicCredentails().getSessionToken());
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicCredentials))
                .withRegion(awsRegionName)
                .build();


    }
}
