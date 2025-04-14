package io.syncscribe.fileservice.datasource.models;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareLinkRepository extends JpaRepository<ShareLink, String> {
    Optional<ShareLink> findByFileId(String fileId);
}
