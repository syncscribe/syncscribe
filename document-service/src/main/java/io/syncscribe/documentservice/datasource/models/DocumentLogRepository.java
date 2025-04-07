package io.syncscribe.documentservice.datasource.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentLogRepository extends JpaRepository<DocumentLog, String> {
    
}
