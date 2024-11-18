package com.dietideals.dietideals24_25.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dietideals.dietideals24_25.utils.S3Service;

@RestController
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private S3Service s3Service;

    @PreAuthorize("hasAuthority('SELLER')")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
            }

            String imageUrl = s3Service.uploadFile("dietidealsbucket", "images", file);
           
            return new ResponseEntity<>(imageUrl, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error uploading file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}