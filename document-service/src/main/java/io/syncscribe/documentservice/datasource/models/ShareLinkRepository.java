package io.syncscribe.documentservice.datasource.models;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareLinkRepository extends JpaRepository<ShareLink, String> {
    void deleteByDocumentId(String documentId);
    Optional<ShareLink> findByDocumentId(String documentId);
}
