package io.syncscribe.documentservice.datasource.models;

import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Table(name = "document_media")
@Entity
public class StorageObject {
    @Id
    private String id;
    private String name;
    private String documentId;
    private String mimeType;
    private String url;
    private OffsetDateTime createdAt;
}
