package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.entity.Image;
import com.enigma.group5.save_it_backend.repository.ImageRepository;
import com.enigma.group5.save_it_backend.service.ImageService;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.BaseFile;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.GetFileListRequest;
import io.imagekit.sdk.models.results.Result;
import io.imagekit.sdk.models.results.ResultFileDelete;
import io.imagekit.sdk.models.results.ResultList;
import jakarta.validation.ConstraintViolationException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageKit imageKit;
    private final Path directoryPath;
    private final OkHttpClient client;
    private final String privateKey;
    private final String urlEndPoint;

    @Autowired
    public ImageServiceImpl(
            ImageRepository imageRepository,
            ImageKit imageKit,
            @Value("${save_it.multipart.path_location}") String directoryPath,
            @Value("${save_it.privatekey}") String privateKey,
            @Value("${save_it.urlendpoint}") String urlEndpoint) {
        this.imageRepository = imageRepository;
        this.imageKit = imageKit;
        this.directoryPath = Paths.get(directoryPath);
        this.client = new OkHttpClient();
        this.privateKey = privateKey;
        this.urlEndPoint = urlEndpoint;
    }

    @Transactional(rollbackFor = Exception.class)
    public void initDirectory() {
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectory(directoryPath);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Image saveToLocal(MultipartFile multipartFile) {

        initDirectory();

        try {
            if (!List.of("image/jpeg", "image/png", "image/jpg", "image/svg+xml").contains(multipartFile.getContentType())) {
                throw new ConstraintViolationException("invalid image format", null);
            }

            String uniqueFileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();

            Path filePath = directoryPath.resolve(uniqueFileName);
            Files.copy(multipartFile.getInputStream(), filePath);

            Image image = Image.builder()
                    .name(multipartFile.getOriginalFilename())
                    .size(multipartFile.getSize())
                    .contentType(multipartFile.getContentType())
                    .path(filePath.toString())
                    .fileName(multipartFile.getName())
                    .fileId(uniqueFileName)
                    .build();
            imageRepository.saveAndFlush(image);

            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> getByNameFromCloud(String fileName) {

        try {
            GetFileListRequest listRequest = new GetFileListRequest();
            // Enclose the fileName in quotes to handle special characters or spaces
            listRequest.setSearchQuery("name:" + '"' + fileName + '"');
            ResultList fileList = imageKit.getFileList(listRequest);


            if (!fileList.getResults().isEmpty()) {
                Map<String, Object> imageResponse = Map.of(
                        "fileId", fileList.getResults().get(0).getFileId(),
                        "name", fileList.getResults().get(0).getName(),
                        "url", fileList.getResults().get(0).getUrl(),
                        "size", fileList.getResults().get(0).getSize()
                );
                return imageResponse;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Image saveToCloud(MultipartFile multipartFile, String directoryImage) {
        try {
            if (!List.of("image/jpeg", "image/png", "image/jpg", "image/svg+xml").contains(multipartFile.getContentType())) {
                throw new ConstraintViolationException("Invalid image format", null);
            }

            byte[] fileBytes = multipartFile.getBytes();
            String base64File = Base64.getEncoder().encodeToString(fileBytes);

            FileCreateRequest request = new FileCreateRequest(base64File, multipartFile.getOriginalFilename());
            request.setFolder(directoryImage);

            Result result = imageKit.upload(request);

            if (result != null && result.getUrl() != null) {
                Image image = Image.builder()
                        .name(multipartFile.getOriginalFilename())
                        .size(multipartFile.getSize())
                        .contentType(multipartFile.getContentType())
                        .path(result.getUrl())
                        .fileId(result.getFileId())
                        .fileName(result.getName())
                        .build();
                imageRepository.saveAndFlush(image);
                return image;
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload image");
            }
        } catch (IOException | ForbiddenException | TooManyRequestsException | InternalServerException |
                 UnauthorizedException | BadRequestException | UnknownException e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Image updateToCloud(MultipartFile multipartFile, String directoryImage, String imageId) {

        Image currentImage = searchById(imageId);

        if (currentImage != null) {
            deleteInCloud(currentImage.getFileId());
            Image newImage = saveToCloud(multipartFile, directoryImage);
            imageRepository.delete(currentImage);
            return newImage;
        } else {
            throw new RuntimeException("Failed to update image");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteInCloud(String fieldId) {

//        String deleteEndpoint = "https://api.imagekit.io/v1/files/" + fieldId;
//
//        String auth = privateKey + ":";
//        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
//
//        Request request = new Request.Builder()
//                .url(deleteEndpoint)
//                .delete()
//                .addHeader("Accept", "application/json")
//                .addHeader("Authorization", "Basic " + encodedAuth)
//                .build();

        try {
            imageKit.deleteFile(fieldId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Resource getById(String id) {
        try {
            Image image = imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found"));

            Path imagePath = Paths.get(image.getPath());
            if (!Files.exists(imagePath)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found");
            }

            return new UrlResource(imagePath.toUri());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Image searchById(String id) {

        Optional<Image> searchById = imageRepository.findById(id);

        return searchById.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "image not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        try {
            Image image = imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found"));

            Path imagePath = Paths.get(image.getPath());
            if (!Files.exists(imagePath)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found");
            }

            Files.delete(imagePath);

            imageRepository.delete(image);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Image createInit(MultipartFile multipartFile) {

        Map<String, Object> result = getByNameFromCloud(multipartFile.getOriginalFilename());

        Long size = 0L;
        if (result.get("size") != null) {
            size = (Long) result.get("size");
        }

        return imageRepository.saveAndFlush(Image.builder()
                .name(multipartFile.getOriginalFilename())
                .size(size)
                .contentType(multipartFile.getContentType())
                .path(multipartFile.getName())
                .fileId(result.get("fileId").toString())
                .fileName(result.get("name").toString())
                .build());
    }
}
