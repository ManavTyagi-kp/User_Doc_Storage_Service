package com.example.user_doc_storage_service.Controller;

import com.amazonaws.services.s3.model.*;
import com.example.user_doc_storage_service.Entity.FileEntity;
import com.example.user_doc_storage_service.Entity.FileRequestBody;
import com.example.user_doc_storage_service.Service.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class DocController {
    @Autowired
    private StorageService service;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;


    @PostMapping("/upload/{userName}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String userName) throws IOException{
        String message = "";
        try{
            if(service.duplicate(userName, file.getOriginalFilename())){
                message = "File with similar name exists: " + file.getOriginalFilename() + ", change file name to continue";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
            else {
                service.store(file, userName);
                message = "File uploaded successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            }
        } catch (Exception e) {
            message = "File cannot be uploaded: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/alldocs")
    public ResponseEntity<List<FileEntity>> getAllDocs(){
        List<FileEntity> docs = service.getAllDocs();
        return ResponseEntity.status(HttpStatus.OK).body(docs);
    }

    @GetMapping("/docs/{userName}/{fileName}")
    public HttpEntity<?> getFilebyName(@PathVariable String fileName, @PathVariable String userName, HttpServletRequest request) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        if(service.duplicate(userName, fileName)) {
            S3Object s3Object = service.download(bucketName+"/"+userName, fileName);
            String contentType = s3Object.getObjectMetadata().getContentType();

            var bytes = s3Object.getObjectContent().readAllBytes();
            headers.setContentType(MediaType.valueOf(contentType));
            headers.setContentLength(bytes.length);

            return new HttpEntity<byte[]>(bytes, headers);
        }
        else {
            return new HttpEntity<String>("File Does Not Exist");
        }

    }

    @GetMapping("/download")
    public HttpEntity<?> getFile(@RequestBody FileRequestBody body) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        if(service.duplicate(body.userName, body.fileName)) {
            S3Object s3Object = service.download(bucketName+"/"+body.userName, body.fileName);
            String contentType = s3Object.getObjectMetadata().getContentType();

            var bytes = s3Object.getObjectContent().readAllBytes();
            headers.setContentType(MediaType.valueOf(contentType));
            headers.setContentLength(bytes.length);

            return new HttpEntity<byte[]>(bytes, headers);
        }
        else {
            return new HttpEntity<String>("File Does Not Exist");
        }
    }
}
