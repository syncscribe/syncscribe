package io.syncscribe.documentservice.datasource.models;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import io.syncscribe.common.jpa.StringListConverter;
import io.syncscribe.common.utils.NanoIdGenerator;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "share_links")
@Entity
public class ShareLink {
    private String id;
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
    private Boolean isPublic;
    @Convert(converter = StringListConverter.class)
    private List<String> emails;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static ShareLink newShareLink(Document document, Boolean isPublic, List<String> emails) {
        var shareLink = new ShareLink();
        shareLink.setId(NanoIdGenerator.generate());
        shareLink.setDocument(document);
        shareLink.setIsPublic(isPublic);
        shareLink.setEmails(emails);
        shareLink.setCreatedAt(OffsetDateTime.now());
        return shareLink;
    }

    public void addSharee(String email) {
        if (emails == null) {
            emails = new ArrayList<>();
        }
        emails.add(email);
    }

    public void removeSharee(String email) {
        if (emails == null) {
            return;
        }
        emails.remove(email);
    }
}
