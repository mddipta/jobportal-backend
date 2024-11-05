package com.lawencon.jobportal.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.jobportal.persistence.entity.File;

public interface FileService {
    List<File> save(List<MultipartFile> file, List<String> type);

    void update(MultipartFile file, String type, String id);

    Resource getFile(String id);

    void delete(String id);
}
