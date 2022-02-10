package com.example.awss3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class S3Controller {
    @Autowired
    S3Service service;



    /*@GetMapping("/list/files")
    public ResponseEntity<String> getListOfFiles() {
        return new ResponseEntity<>(service.listBuckets(), HttpStatus.OK);
    }

    @GetMapping("/list/secret")
    public ResponseEntity<String> getSecret() {
        return new ResponseEntity<>(service.getSecret(), HttpStatus.OK);
    }
*/
    @GetMapping("/list/keys")
    public void getkeys() throws Exception {
         service.PrintHeaderValues();
    }

}
