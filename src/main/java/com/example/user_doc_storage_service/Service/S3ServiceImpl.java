package com.example.user_doc_storage_service.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class S3ServiceImpl implements S3Service{

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public PutObjectResult upload(String path, String name, Optional<Map<String, String>> optionalMetaData, InputStream inputStream) {
            ObjectMetadata objectMetadata = new ObjectMetadata();

            optionalMetaData.ifPresent(map -> {
                if (!map.isEmpty()) {
                    map.forEach(objectMetadata::addUserMetadata);
                }
            });
            log.debug("Path: " + path + ", FileName:" + name);
            return amazonS3.putObject(path, name, inputStream, objectMetadata);
    }

    public S3Object download(String path, String name) {
        return amazonS3.getObject(path, name);
    }
}
