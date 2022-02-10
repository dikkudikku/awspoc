package com.example.awss3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

import com.amazonaws.services.securitytoken.model.Credentials;
import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class S3Service {

    public static final Logger log = LoggerFactory.getLogger(AWSBasicCredentialsConfig.class);

    @Value("${aws.secret.id}")
    private String secretId;

    @Value("${aws.region.name}")
    private String awsRegionName;

    @Value("${aws.service.name}")
    private String awsServiceName;

    /*@Autowired
    AmazonS3 amazonS3client;*/

  /*  @Autowired
    AwsSecretsManagerConfig smConfigClient;*/

    @Autowired
    AWSBasicCredentialsConfig awsCredentials;



    /*public String listBuckets() {
        List<Bucket> buckets = amazonS3client.listBuckets();
        String name="";
        for(Bucket bucket : buckets) {
            name = bucket.getName();
        }
        return name;
    }

    public String getSecret (){
        GetSecretValueRequest secretValueRequest = new GetSecretValueRequest().withSecretId(secretId);
        GetSecretValueResult secretValueResult = null;

        try {
            secretValueResult = smConfigClient.getSecretsManagerClient().getSecretValue(secretValueRequest);
        } catch (Exception e){
            log.error(e.getMessage());
        }
        if(secretValueResult.getSecretString() != null){
            return secretValueResult.getARN();
        } else {
            return "unable to get your Secret";
        }
    }*/

    public void PrintHeaderValues() throws Exception {
        String req = "wereeqrerereretretrasdsadsdsfsfsfdsfsfsdfewrwre";  // Request payload
        String isoDateTime = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'").format(ZonedDateTime.now(ZoneOffset.UTC));
        byte[] bodyBytes = req.getBytes(StandardCharsets.UTF_8);
        String bodySha256 = AmazonRequestSignatureV4Utils.hex(AmazonRequestSignatureV4Utils.sha256(bodyBytes));

        Map<String, String> headers = new LinkedHashMap<String,String>();
        headers.put("Content-Type", "application/json");
        headers.put("Host", "pxl6j3vt38.execute-api.eu-west-2.amazonaws.com");
        headers.put("X-Amz-Content-Sha256", bodySha256);
        headers.put("X-Amz-Date", isoDateTime);
        headers.put("X-Amz-Security-Token", awsCredentials.getAWSBasicCredentails().getSessionToken());

        String authParameter = AmazonRequestSignatureV4Utils.calculateAuthorizationHeaders("POST","/nonprod/ods/v1/aep/account/transactions", "", headers, bodySha256, isoDateTime, awsCredentials.getAWSBasicCredentails().getAccessKeyId(),
                awsCredentials.getAWSBasicCredentails().getSecretAccessKey(), awsRegionName, awsServiceName);

        log.info(" authParameter::::" + authParameter + "   bodySha256 ::::"+ bodySha256 + "  isoDateTime::::" + isoDateTime + "  awsAccessKey :::"+ awsCredentials.getAWSBasicCredentails().getAccessKeyId() + "   awsSecret :::" + awsCredentials.getAWSBasicCredentails().getSecretAccessKey() + "  awsRegion  :::"+ awsRegionName + "   awsService :::" + awsServiceName);


    }

}
