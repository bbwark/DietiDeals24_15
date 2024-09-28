package com.dietideals.dietideals24_25.utils;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

    private final AmazonS3 s3Client;


    public S3Service() {
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion("eu-north-1")
                .build();
    }

    public String uploadFile(String bucketName, String folder, MultipartFile file) throws IOException {
        String fileName = folder + "/" + file.getOriginalFilename();

        // Crea un PutObjectRequest e imposta la visibilità pubblica dell'oggetto
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead);  // Rendi l'oggetto pubblico
        
        // Carica il file su S3
        s3Client.putObject(putObjectRequest);

        // Restituisce l'URL pubblico del file
        return s3Client.getUrl(bucketName, fileName).toString();
    }
}