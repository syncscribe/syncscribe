package io.syncscribe.fileservice.contracts;

import io.syncscribe.fileservice.datasource.models.FileModel;

import java.time.OffsetDateTime;

public record File(
        String id,
        String fileName,
        Long sizeInBytes,
        String mimeType,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        String updatedBy) {

    public static File from(FileModel model) {
        return new File(
                model.getId(),
                model.getFileName(),
                model.getSizeInBytes(),
                model.getMimeType(),
                model.getCreatedAt(),
                model.getUpdatedAt(),
                model.getUpdatedBy()
        );
    }
}
