package com.example.user_doc_storage_service.Service;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface S3Service {
    PutObjectResult upload(
            String path,
            String name,
            Optional<Map<String, String>> optionalMetaData,
            InputStream inputStream
    );
    S3Object download(String path, String name);
}
