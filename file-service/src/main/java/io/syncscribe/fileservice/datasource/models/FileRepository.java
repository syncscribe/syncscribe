package io.syncscribe.fileservice.datasource.models;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileModel, String> {
    Page<FileModel> findByOwnerIdAndDirectoryId(String ownerId, String dirId, Pageable pageable);
}
