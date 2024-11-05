package com.lawencon.jobportal.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.lawencon.jobportal.persistence.entity.File;
import com.lawencon.jobportal.persistence.repository.FileRepository;
import com.lawencon.jobportal.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository repository;

    private final List<String> ALLOWED_IMAGE_EXTENSIONS = List.of("jpg", "jpeg", "png");
    private final List<String> ALLOWED_DOCUMENT_EXTENSIONS = List.of("pdf", "doc", "docx");

    @Value("${file.path}")
    private String filePath;

    @Override
    public List<File> save(List<MultipartFile> files, List<String> types) {
        List<File> fileEntityList = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            if (multipartFile.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
            }

            String originalFilename = multipartFile.getOriginalFilename();

            String fileExtension =
                    originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

            if (!allowedFileTypeExtension(multipartFile, types.get(files.indexOf(multipartFile)))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File type not allowed");
            }

            String fileName = UUID.randomUUID().toString() + "." + fileExtension;

            try {
                Files.createDirectories(Paths.get(filePath));
                Files.copy(multipartFile.getInputStream(), Paths.get(filePath).resolve(fileName));

                File fileEntity = new File();
                fileEntity.setFile(fileName);
                fileEntity.setIsActive(true);
                fileEntityList.add(fileEntity);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to save file: " + e.getMessage(), e);
            }
        }
        return repository.saveAll(fileEntityList);
    }

    @Override
    public void update(MultipartFile file, String type, String id) {
        if (!allowedFileTypeExtension(file, type)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File type not allowed");
        }

        Optional<File> fileEntity = repository.findById(id);
        if (fileEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File not found");
        }

        String originalFilename = file.getOriginalFilename();

        String fileExtension =
                originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        String fileName = UUID.randomUUID().toString() + "." + fileExtension;

        try {
            Files.createDirectories(Paths.get(filePath));
            Files.copy(file.getInputStream(), Paths.get(filePath).resolve(fileName));
            try {
                Files.deleteIfExists(Paths.get(filePath).resolve(fileEntity.get().getFile()));
            } catch (IOException e) {
                System.err.println("Failed to delete old file: " + e.getMessage());
            }

            fileEntity.get().setFile(fileName);
            fileEntity.get().setVersion(fileEntity.get().getVersion() + 1);
            repository.saveAndFlush(fileEntity.get());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to save file: " + e.getMessage(), e);
        }
    }

    @Override
    public Resource getFile(String id) {
        Optional<File> file = repository.findById(id);
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }

        try {
            String fullFileName = file.get().getFile();
            Path filePath = Paths.get(this.filePath).resolve(fullFileName);
            Resource resource = new FileSystemResource(filePath.toFile());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to load file: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String id) {
        Optional<File> file = repository.findById(id);
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }

        try {
            Files.deleteIfExists(Paths.get(filePath).resolve(file.get().getFile()));
            repository.delete(file.get());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to delete file: " + e.getMessage(), e);
        }
    }

    private Boolean allowedFileTypeExtension(MultipartFile file, String type) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.contains(".")) {
            return false;
        }

        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        if (type.equalsIgnoreCase("image")) {
            return ALLOWED_IMAGE_EXTENSIONS.contains(fileExtension);
        } else if (type.equalsIgnoreCase("document")) {
            return ALLOWED_DOCUMENT_EXTENSIONS.contains(fileExtension);
        } else {
            return false;
        }
    }


}
