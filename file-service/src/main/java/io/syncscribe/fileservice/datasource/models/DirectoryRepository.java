package io.syncscribe.fileservice.datasource.models;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<DirectoryModel, String> {
    Page<DirectoryModel> findByOwnerIdAndParentId(String ownerId, String parentId, Pageable pageable);
}
