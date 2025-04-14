package io.syncscribe.fileservice.contracts;

import io.syncscribe.fileservice.datasource.models.DirectoryModel;

import java.time.OffsetDateTime;

public record Directory(String id, String name, OffsetDateTime createdAt,
                        OffsetDateTime updatedAt) {

    public static Directory from(DirectoryModel model) {
        return new Directory(model.getId(),
                model.getName(),
                model.getCreatedAt(),
                model.getUpdatedAt());
    }
}
