package com.example.user_doc_storage_service.Service;

import com.amazonaws.services.s3.model.S3Object;
import com.example.user_doc_storage_service.Entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StorageService {
    FileEntity store(MultipartFile file, String userName) throws IOException;
    List<FileEntity> getAllDocs();

    Boolean duplicate(String userName, String fileName);
    S3Object download(String path, String fileName);
}
