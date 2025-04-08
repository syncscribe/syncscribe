package io.syncscribe.documentservice.datasource.models;

import java.time.OffsetDateTime;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "document_logs")
public class DocumentLog {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
    private String content;
    private OffsetDateTime createdAt;
    private String updatedBy;
}
