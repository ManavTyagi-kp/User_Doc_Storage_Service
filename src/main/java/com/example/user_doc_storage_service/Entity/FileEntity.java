package com.example.user_doc_storage_service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "Docs")
@Getter @Setter
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String path;
    private String version;

    public FileEntity(String name, String path, String version) {
        this.name = name;
        this.path = path;
        this.version = version;
    }
}
