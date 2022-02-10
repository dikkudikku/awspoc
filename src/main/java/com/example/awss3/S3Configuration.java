package com.example.awss3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnProperty(prefix = "aws", name = "service", havingValue = "s3")
public class S3Configuration {

    public static final Logger log = LoggerFactory.getLogger(S3Configuration.class);

    @Value("${access.key.id}")
    private String accessKeyId;

    @Value("${access.key.secret}")
    private String accessKeySecret;

    @Value("${s3.region.name}")
    private String s3RegionName;


    @Bean
    public AmazonS3 getAmazonS3Client() {
        final AWSCredentials basicCredentials = new BasicAWSCredentials(accessKeyId,accessKeySecret);
       return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicCredentials))
                .withRegion(s3RegionName)
                .build();

    }
}

