package io.syncscribe.fileservice.datasource.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "directories", indexes = {
        @Index(name = "parent_id_idx", columnList = "parent_id")
})
public class DirectoryModel {
    @Id
    private String id;
    private String name;
    private String ownerId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private DirectoryModel parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<DirectoryModel> children = new ArrayList<>();

    @OneToMany(mappedBy = "directory", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<FileModel> files = new ArrayList<>();
}
