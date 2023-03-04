package com.example.user_doc_storage_service.dao;

import com.example.user_doc_storage_service.Entity.FileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocRepo extends CrudRepository<FileEntity, Integer> {
}
