package io.syncscribe.documentservice.datasource.models;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import io.syncscribe.common.utils.NanoIdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "documents")
public class Document {
    @Id
    private String id;
    private String name;
    private String content;
    private String ownerGuid;
    @OneToMany(mappedBy = "document")
    private List<DocumentLog> documentLogs;
    @OneToMany(mappedBy = "document")
    private List<ShareLink> shareLinks;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public DocumentLog createDocumentLog() {
        DocumentLog documentLog = new DocumentLog();
        documentLog.setDocument(this);
        documentLog.setCreatedAt(OffsetDateTime.now());
        documentLog.setContent(this.content);
        return documentLog;
    }

    public ShareLink createShareLink(List<String> emails, Boolean isPublic) {
        ShareLink shareLink = new ShareLink();
        shareLink.setId(NanoIdGenerator.generate());
        shareLink.setDocument(this);
        shareLink.setEmails(emails);
        shareLink.setIsPublic(isPublic);
        shareLink.setCreatedAt(OffsetDateTime.now());
        return shareLink;
    }
}
