package io.syncscribe.documentservice.datasource.models;

import io.syncscribe.common.auth.OAuthContext;
import io.syncscribe.common.utils.NanoIdGenerator;
import io.syncscribe.documentservice.contracts.IllegalActionException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "documents")
public class Document {
    @Id
    private String id;
    private String name;
    private String ownerId;
    private String url;
    @OneToMany(mappedBy = "document")
    private List<DocumentLog> documentLogs;
    @OneToOne(mappedBy = "document")
    private ShareLink shareLink;
    private Boolean markForDelete;
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
        documentLog.setId(NanoIdGenerator.generate());
        documentLog.setDocument(this);
        documentLog.setCreatedAt(OffsetDateTime.now());
        documentLog.setUpdatedBy(this.updatedBy);
        return documentLog;
    }

    public ShareLink createShareLink() {
        var user = OAuthContext.getUser();
        if (!user.id().equals(this.ownerId)) {
            throw new IllegalActionException("You are not the owner of this document");
        }
        var link = new ShareLink();
        link.setId(NanoIdGenerator.generate());
        link.setDocument(this);
        link.setRole(ShareLinkRole.READ);
        link.setCreatedAt(OffsetDateTime.now());
        return link;
    }

}
