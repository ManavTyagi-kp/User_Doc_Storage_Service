package com.example.user_doc_storage_service.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.user_doc_storage_service.Entity.FileEntity;
import com.example.user_doc_storage_service.dao.DocRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService{
    @Autowired
    private DocRepo docRepo;

    @Autowired
    private S3Service s3service;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;


    @Override
    public FileEntity store(MultipartFile file, String userName) throws IOException {
        if (file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file");

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", bucketName, userName);
        String name = String.format("%s", file.getOriginalFilename());

        PutObjectResult putObjectResult = s3service.upload(
                path, name, Optional.of(metadata), file.getInputStream());

        return docRepo.save(new FileEntity(name, path, putObjectResult.getMetadata().getVersionId()));
    }

    @Override
    public List<FileEntity> getAllDocs() {
        List<FileEntity> file = new ArrayList<>();
        docRepo.findAll().forEach(file::add);
        return file;
    }

    @Override
    public Boolean duplicate(String userName, String fileName) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix(userName+"/");
        List<String> keys = new ArrayList<>();

        try {
            ObjectListing objects = s3Client.listObjects(listObjectsRequest);

            if(objects != null && objects.getObjectSummaries() != null) {
                while (true) {
                    List<S3ObjectSummary> summaries = objects.getObjectSummaries();
                    summaries.forEach(s -> keys.add(s.getKey()));
                    System.out.println(keys);
                    System.out.println(keys.contains(userName+"/"+fileName));
                    if(keys.contains(userName+"/"+fileName)){
                        return true;
                    }
                    if (objects.isTruncated()) {
                        objects = s3Client.listNextBatchOfObjects(objects);
                    } else {
                        break;
                    }
                }
            }

        } catch (AmazonServiceException e) {

            System.out.println(e.getErrorMessage());

        }
        return false;
    }

    @Override
    public S3Object download(String path, String fileName){
        return s3service.download(path, fileName);
    }

}
