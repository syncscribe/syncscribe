package io.syncscribe.documentservice.datasource.models;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import io.syncscribe.documentservice.contracts.DocumentVisitor;
import io.syncscribe.documentservice.datasource.converters.DocumentVisitorStringListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private ShareLinkRole generalRole;
    @Convert(converter = DocumentVisitorStringListConverter.class)
    private List<DocumentVisitor> visitors;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public void addVisitor(String email, ShareLinkRole role) {
        if (visitors == null) {
            visitors = new ArrayList<>();
        }
        visitors.add(new DocumentVisitor(email, role));
        updatedAt = OffsetDateTime.now();
    }

    public void removeVisitor(String email) {
        if (visitors == null) {
            return;
        }
        visitors.removeIf(visitor -> visitor.email().equals(email));
        updatedAt = OffsetDateTime.now();
    }
}
