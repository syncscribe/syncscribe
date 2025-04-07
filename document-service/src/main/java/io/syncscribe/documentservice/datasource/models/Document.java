package io.syncscribe.documentservice.datasource.models;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import io.syncscribe.common.auth.OAuthContext;
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
    private String ownerId;
    @OneToMany(mappedBy = "document")
    private List<DocumentLog> documentLogs;
    @OneToMany(mappedBy = "document")
    private List<ShareLink> shareLinks;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String updatedBy;

    public static Document newDocument(String name) {
        var doc = new Document();
        doc.setId(NanoIdGenerator.generate());
        doc.setOwnerId(OAuthContext.getUser().id());
        doc.setName(name);
        doc.setCreatedAt(OffsetDateTime.now());
        return doc;
    }

    public DocumentLog createDocumentLog() {
        var documentLog = new DocumentLog();
        documentLog.setDocument(this);
        documentLog.setCreatedAt(OffsetDateTime.now());
        documentLog.setContent(this.content);
        documentLog.setUpdatedBy(this.updatedBy);
        return documentLog;
    }

    public ShareLink createShareLink(List<String> emails, Boolean isPublic) {
        var shareLink = new ShareLink();
        shareLink.setId(NanoIdGenerator.generate());
        shareLink.setDocument(this);
        shareLink.setEmails(emails);
        shareLink.setIsPublic(isPublic);
        shareLink.setCreatedAt(OffsetDateTime.now());
        return shareLink;
    }
}
