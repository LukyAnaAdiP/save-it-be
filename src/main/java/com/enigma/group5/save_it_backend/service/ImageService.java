package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.entity.Image;
import io.imagekit.sdk.models.BaseFile;
import io.imagekit.sdk.models.results.Result;
import io.imagekit.sdk.models.results.ResultList;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageService {
    Image saveToLocal(MultipartFile multipartFile);

    Map<String, Object> getByNameFromCloud(String fileName);

    Image saveToCloud(MultipartFile multipartFile, String directoryImage);

    Image updateToCloud(MultipartFile multipartFile, String directoryImage, String imageId);

    boolean deleteInCloud(String fieldId);

    Resource getById(String id);

    Image searchById(String id);

    void deleteById(String id);

    Image createInit(MultipartFile multipartFile);
}
