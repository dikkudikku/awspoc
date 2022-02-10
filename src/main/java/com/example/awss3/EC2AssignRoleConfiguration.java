package com.example.awss3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "aws", name = "service", havingValue = "ec2role")
public class EC2AssignRoleConfiguration {

    @Bean
    public AmazonS3 getAmazonS3Client() {
        return  AmazonS3ClientBuilder.defaultClient();
    }

}
