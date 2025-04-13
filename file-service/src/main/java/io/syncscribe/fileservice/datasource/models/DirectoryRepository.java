package io.syncscribe.fileservice.datasource.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<DirectoryModel, String> {
}
