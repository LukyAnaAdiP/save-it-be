package com.enigma.group5.save_it_backend.controller;

import com.enigma.group5.save_it_backend.entity.Image;
import com.enigma.group5.save_it_backend.service.ImageService;
import io.imagekit.sdk.models.BaseFile;
import io.imagekit.sdk.models.results.Result;
import io.imagekit.sdk.models.results.ResultList;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    private static final String IMAGE_DIRECTORY = "/home/enigma/Documents/Procurement_Images";

    @GetMapping("/api/v1/products/images")
    public ResponseEntity<Resource> getImage(@RequestParam String filename) throws IOException {
        File imgFile = new File(IMAGE_DIRECTORY, filename);

        if (!imgFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(imgFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imgFile.length());

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping(path = "/api/v1/products/images/{imageId}")
    public ResponseEntity<?> downloadImage(
            @PathVariable(name = "imageId")	String id
    ){
        Resource imageById = imageService.getById(id);

        String headerValue = String.format("attachment; filename=%s",imageById.getFilename());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(imageById);

    }

    @PostMapping(path = "/api/v1/image/test-upload")
    public ResponseEntity<?> testUpload(
            @RequestPart(name = "image") MultipartFile multipartFile
    ){
        Image image = imageService.saveToCloud(multipartFile, "/uploads");
        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }

    @GetMapping(path = "/api/v1/image/get-by-name/{name}")
    public ResponseEntity<?> testUpload(
           @PathVariable(name = "name") String name
    ){
        Map<String, Object> image = imageService.getByNameFromCloud(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }

    @PutMapping("/api/v1/image/test-update/{fileId}")
    public ResponseEntity<String> updateImage(
            @PathVariable String fileId,
            @RequestPart(name = "image") MultipartFile multipartFile) {
        Image isUpdated = imageService.updateToCloud(multipartFile, "/uploads", fileId);
        if (isUpdated != null) {
            return ResponseEntity.ok("Image updated successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to update image.");
        }
    }

    @DeleteMapping("/api/v1/image/test-delete/{fileId}")
    public ResponseEntity<String> deleteImage(@PathVariable String fileId) {
        boolean isDeleted = imageService.deleteInCloud(fileId);
        if (isDeleted) {
            return ResponseEntity.ok("Image deleted successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to delete image.");
        }
    }

}
