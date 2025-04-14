package io.syncscribe.fileservice.datasource.models;

import io.syncscribe.common.auth.OAuthContext;
import io.syncscribe.common.utils.NanoIdGenerator;
import io.syncscribe.fileservice.contracts.IllegalActionException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "files")
public class FileModel {
    @Id
    private String id;
    private String fileName;
    private String ownerId;
    private Long sizeInBytes;
    private String mimeType;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "directory_id")
    private DirectoryModel directory;

    private Boolean markForDelete;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String updatedBy;

    public static FileModel newFile(String name, Long sizeInBytes, String mimeType) {
        var file = new FileModel();
        file.setId(NanoIdGenerator.generate());
        file.setOwnerId(OAuthContext.getUser().id());
        file.setFileName(name);
        file.setSizeInBytes(sizeInBytes);
        file.setMimeType(mimeType);
        file.setCreatedAt(OffsetDateTime.now());
        return file;
    }

    public ShareLink createShareLink() {
        var user = OAuthContext.getUser();
        if (!user.id().equals(this.ownerId)) {
            throw new IllegalActionException("You are not the owner of this file");
        }
        var link = new ShareLink();
        link.setId(NanoIdGenerator.generate());
        link.setFile(this);
        link.setRole(ShareLinkRole.READ);
        link.setCreatedAt(OffsetDateTime.now());
        return link;
    }

}
