package io.syncscribe.documentservice.datasource.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@Table(name = "share_links")
@Entity
public class ShareLink {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "document_id")
    private Document document;
    @Enumerated(EnumType.STRING)
    private ShareLinkRole role;
    private String password;
    private OffsetDateTime expiredAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
