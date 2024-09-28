package com.dietideals.dietideals24_25.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Endpoint per caricare immagini
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Carica l'immagine su S3 e ottieni l'URL pubblico
            String imageUrl = s3Service.uploadFile("dietidealsbucket", "images", file);
            
            // Qui puoi salvare l'URL nel database con altri metadati (se necessario)
            
            return new ResponseEntity<>(imageUrl, HttpStatus.CREATED);  // Restituisci l'URL al frontend
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}